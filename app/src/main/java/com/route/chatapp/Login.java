package com.route.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.route.chatapp.Base.BaseActivity;
import com.route.chatapp.FirebaseUtils.Mydatabase;
import com.route.chatapp.Models.User;

public class Login extends BaseActivity {

    protected TextInputLayout email;
    protected TextInputLayout password;
    protected Button buttonLogin;
    protected TextView Register, forgotpassword;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        initView();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getEditText().getText().toString();
                String pass = password.getEditText().getText().toString();
                if (validateEmptyEmail(mail) && validateEmptypass(pass)) {
                    ShowProgressBar();
                    auth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            hideprogressbar();
                            if (task.isSuccessful()) {
                                firebaseUser = auth.getCurrentUser();
                                startActivity(new Intent(activity, MainActivity.class));
                            }
                             else
                                ShowConfirmationMessage("warning", "invalid email or/and password", "ok", new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                });
                        }
                    });
                }
            }

        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, Registeration.class));
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity,ForgotPassword.class));
            }
        });

    }

    private void initView() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.button_login);
        Register = findViewById(R.id.register);
        forgotpassword = findViewById(R.id.forgotpassword);
    }

    public boolean validateEmptyEmail(String mail) {
        if (mail.isEmpty()) {
            email.setError("field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            email.setError("please enter a valid email");
            return false;
        } else {
            email.setError(null);
            return true;
        }
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
}
