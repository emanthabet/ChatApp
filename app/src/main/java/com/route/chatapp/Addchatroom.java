package com.route.chatapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.route.chatapp.Base.BaseActivity;
import com.route.chatapp.FirebaseUtils.ChatRoomDao;
import com.route.chatapp.Models.ChatRoom;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class Addchatroom extends BaseActivity implements View.OnClickListener {

    protected TextInputLayout addName;
    protected TextInputLayout addDescription;
    protected Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_addchatroom);
        initView();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_button) {

            String name = addName.getEditText().getText().toString();
            String desc = addDescription.getEditText().getText().toString();
            SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            String currenttimestamp = s.format(new Date());
            if (validatname(name)) {
                ChatRoom chatroom = new ChatRoom(name, desc, currenttimestamp);
                ShowProgressBar();
                ChatRoomDao.InsertChatroom(chatroom, new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        hideprogressbar();
                        ShowConfirmationMessage(R.string.success, R.string.successadd, R.string.ok, new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                finish();
                            }
                        });
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideprogressbar();
                        ShowConfirmationMessage("warning", e.getMessage(), "ok", new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
            }
        }
    }

    private void initView() {
        addName = (TextInputLayout) findViewById(R.id.add_name);
        addDescription = (TextInputLayout) findViewById(R.id.add_description);
        addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(Addchatroom.this);
    }


    public boolean validatname(String name) {
        if (name.isEmpty()) {
            addName.setError("field can't be empty");
            return false;
        } else {
            addName.setError(null);
            return true;
        }

    }
}
