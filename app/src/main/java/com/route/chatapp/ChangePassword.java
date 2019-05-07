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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.view.Change;
import com.route.chatapp.Base.BaseActivity;

import java.util.regex.Pattern;

public class ChangePassword extends BaseActivity implements View.OnClickListener {
    protected Toolbar toolbar;
    protected TextInputLayout password;
    protected Button buttonLogin;
    FirebaseAuth auth;
    private static final Pattern Password_Pattern = Pattern.compile("^" +
            //"(?=.*[0-9])" +         //at least 1 digit
            //"(?=.*[a-z])" +         //at least 1 lower case letter
            //"(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            "(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{6,}" +               //at least 6 characters b3d elcoma elmax
            "$");//end of string

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_change_password);
        auth = FirebaseAuth.getInstance();
        initView();


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_login) {
            String pass = password.getEditText().getText().toString();
            auth.getCurrentUser().updatePassword(pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        ShowConfirmationMessage("Done", "you have changed your password successfully", "ok", new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                startActivity(new Intent(ChangePassword.this, MainActivity.class));
                            }
                        });
                    } else {
                        ShowConfirmationMessage("Error", "failed to change password", "ok", new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }

                        });
                    }
                }
            });
        }
    }
    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        password = (TextInputLayout) findViewById(R.id.password);
        buttonLogin = (Button) findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(ChangePassword.this);
    }


    public boolean validatepassword(String pass) {
        if (pass.isEmpty()) {
            password.setError("field can't be empty");
            return false;
        } else if (!Password_Pattern.matcher(pass).matches()) {
            password.setError("Password too weak");
            return false;
        }
        {
            password.setError(null);
            return true;
        }
    }


}