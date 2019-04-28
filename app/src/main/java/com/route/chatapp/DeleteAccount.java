package com.route.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.route.chatapp.Base.BaseActivity;
import com.route.chatapp.FirebaseUtils.Mydatabase;

public class DeleteAccount extends BaseActivity implements View.OnClickListener {

    protected Toolbar toolbar;
    protected TextInputLayout email;
    protected TextInputLayout password;
    protected Button buttonDelete;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_delete_account);
        initView();
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_Delete) {
        String mail= email.getEditText().getText().toString();
        String thepassword= password.getEditText().getText().toString();
        if(validateEmptyemail(mail)&&validateEmptypass(thepassword)) {
            ShowProgressBar();
            auth.signInWithEmailAndPassword(mail, thepassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        hideprogressbar();
                        ShowConfirmationMessage("Are you Sure?", "Are you sure you want to delete this account forever",
                                "Yes", new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        String userid=user.getUid();
                                        user.delete();
                                        Mydatabase.getUserBranch().child(userid).removeValue();
                                        dialog.dismiss();
                                        startActivity(new Intent(DeleteAccount.this, Registeration.class));
                                        finish();
                                    }
                                }, "No", new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                });
                    } else {
                        hideprogressbar();
                        ShowMessage("Error", "Wrong email or/and password", "ok");
                    }
                }
            });
        }
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        email = (TextInputLayout) findViewById(R.id.email);
        password = (TextInputLayout) findViewById(R.id.password);
        buttonDelete = (Button) findViewById(R.id.button_Delete);
        buttonDelete.setOnClickListener(DeleteAccount.this);
    }

    public boolean validateEmptypass(String pass) {
        if (pass.isEmpty()) {
            password.setError("field can't be empty");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    public boolean validateEmptyemail(String mail) {
        if (mail.isEmpty()) {
            email.setError("field can't be empty");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }
}
