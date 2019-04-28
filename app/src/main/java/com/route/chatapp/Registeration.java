package com.route.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.Validator;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.route.chatapp.Base.BaseActivity;
import com.route.chatapp.FirebaseUtils.Mydatabase;
import com.route.chatapp.FirebaseUtils.userDao;
import com.route.chatapp.Models.ChatRoom;
import com.route.chatapp.Models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Registeration extends BaseActivity {
    FirebaseAuth auth;
    private static final Pattern Password_Pattern = Pattern.compile("^" +
            "(?=.*[0-9])" +         //at least 1 digit
            //"(?=.*[a-z])" +         //at least 1 lower case letter
            //"(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            "(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{6,}" +               //at least 6 characters b3d elcoma elmax
            "$");//end of string
    protected TextInputLayout username;
    protected TextInputLayout email;
    protected TextInputLayout password;
    protected Button buttonRegister;
    protected TextView login;
    FirebaseUser firebaseUser;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_registeration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        auth = FirebaseAuth.getInstance();
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = username.getEditText().getText().toString();
                String mail = email.getEditText().getText().toString();
                String pass = password.getEditText().getText().toString();
                if (validatUsername(name) && validatemail(mail) && validatepassword(pass)) {
                    ShowProgressBar();
                    user = new User();
                    user.setUsername(name);
                    user.setEmail(mail);
                    auth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                firebaseUser = auth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name).build();
                                firebaseUser.updateProfile(profileUpdates);
                                String id = firebaseUser.getUid();
                                user.setId(id);
                                userDao.InsertUser(user, id, new OnSuccessListener() {
                                            @Override
                                            public void onSuccess(Object o) {
                                                hideprogressbar();
                                                Dataholders.Currentuser = user;
                                                ShowConfirmationMessage(R.string.success, R.string.sucessfull, R.string.ok, new MaterialDialog.SingleButtonCallback() {
                                                    @Override
                                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                        startActivity(new Intent(activity, MainActivity.class));
                                                        finish();
                                                    }
                                                });
                                            }

                                        },
                                        new OnFailureListener() {//elnet momkin yfsel wala 7aga w mytsavish
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                hideprogressbar();
                                                ShowConfirmationMessage(R.string.warnning, R.string.failed, R.string.ok, new MaterialDialog.SingleButtonCallback() {
                                                    @Override
                                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                            }
                                        });

                            } else {
                                hideprogressbar();
                                ShowConfirmationMessage("warning", task.getException().getMessage(), "ok", new MaterialDialog.SingleButtonCallback() {
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
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, Login.class));
                finish();
            }
        });
    }


    private void initView() {
        username = (TextInputLayout) findViewById(R.id.username);
        email = (TextInputLayout) findViewById(R.id.email);
        password = (TextInputLayout) findViewById(R.id.password);
        buttonRegister = (Button) findViewById(R.id.button_register);
        login = findViewById(R.id.login);
    }

    public boolean validatemail(String mail) {

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

    public boolean validatepassword(String pass) {
        if (pass.isEmpty()) {
            password.setError("field can't be empty");
            return false;
        } else if (!Password_Pattern.matcher(pass).matches()) {
            if (pass.length() < 6) {
                password.setError("Password too short minimum length is 6");
                return false;
            } else {
                password.setError("Password too week must include at least 1 special charachter and 1 digit");
                return false;
            }
        }
        {
            password.setError(null);
            return true;
        }
    }


    public boolean validatUsername(final String name) {
        if (name.isEmpty()) {
            username.setError("field can't be empty");
            return false;

        }
        else

            username.setError(null);
        return true;



    }


}