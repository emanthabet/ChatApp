package com.route.chatapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.route.chatapp.Adapters.ChatThreadAdapter;
import com.route.chatapp.Adapters.PrivateChatThreadAdapter;
import com.route.chatapp.Base.BaseActivity;
import com.route.chatapp.FirebaseUtils.MessagesDao;
import com.route.chatapp.FirebaseUtils.Mydatabase;
import com.route.chatapp.FirebaseUtils.PrivateMessagesDao;
import com.route.chatapp.Models.Messages;
import com.route.chatapp.Models.PrivateMessages;
import com.route.chatapp.Models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PrivateChating extends BaseActivity implements View.OnClickListener {


    CircleImageView userPhoto;
    TextView name;
     Toolbar toolbar;
     AppBarLayout appbar;
    ImageView sendButton;
    EditText message;
    RecyclerView recyclerView;
    PrivateChatThreadAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String friendid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_private_chating);
        appbar =  findViewById(R.id.appbar);
        toolbar =  findViewById(R.id.toolbar);
        userPhoto =  findViewById(R.id.userPhoto);
        name =  findViewById(R.id.name);
        String friendname = getIntent().getStringExtra("friendname");
        friendid = getIntent().getStringExtra("friendid");
        name.setText(friendname);
        recyclerView
                = findViewById(R.id.private_chat_rv);
        layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        adapter = new PrivateChatThreadAdapter(null);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(PrivateChating.this);
        message = findViewById(R.id.message);
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        Mydatabase.getPrivateMessagesBranch().addChildEventListener(childEventListener);




}

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_button) {
            String content = message.getText().toString();
            PrivateMessages messages = new PrivateMessages();
            messages.setContent(content);
            messages.setSenderid(firebaseUser.getUid());
            messages.setRecevierid(friendid);
            messages.setSendername(firebaseUser.getDisplayName());
            SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy  hh:mm");
            String currenttimestamp = s.format(new Date());
            messages.setTimestamp(currenttimestamp);
            PrivateMessagesDao.Addmessage(messages, new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    message.setText(null);
                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(activity,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });


        }
    }



    private void initView() {



    }
    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
           PrivateMessages message = dataSnapshot.getValue(PrivateMessages.class);
            if (message != null) {
                if (message.getSenderid().equals(firebaseUser.getUid())&&message.getRecevierid().equals(friendid) ||
                        message.getSenderid().equals(friendid)&&message.getRecevierid().equals(firebaseUser.getUid())){
                adapter.AddMessage(message);
                int position = adapter.getItemCount() - 1;
                recyclerView.smoothScrollToPosition(position);}
            }
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


}
