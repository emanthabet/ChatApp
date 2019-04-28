package com.route.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    protected Toolbar toolbar;
    protected TextInputLayout email;
    protected Button buttonReset;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_forgot_password);
        initView();
        auth=FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_Reset) {
         String mail= email.getEditText().getText().toString();
         if(validateEmptyEmail(mail)){
             auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     if(task.isSuccessful())
                     {
                         Toast.makeText(ForgotPassword.this,R.string.checkemail,Toast.LENGTH_LONG).show();
                         startActivity(new Intent(ForgotPassword.this,Login.class));
                     }
                     else
                     {
                         Toast.makeText(ForgotPassword.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 }
             });
         }
        }
    }

    private void initView() {
        toolbar =  findViewById(R.id.toolbar);
        email =  findViewById(R.id.email);
        buttonReset = findViewById(R.id.button_Reset);
        buttonReset.setOnClickListener(ForgotPassword.this);
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
}
