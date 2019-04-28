package com.route.chatapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.route.chatapp.Adapters.ChatThreadAdapter;
import com.route.chatapp.Base.BaseActivity;
import com.route.chatapp.FirebaseUtils.MessagesDao;
import com.route.chatapp.FirebaseUtils.Mydatabase;
import com.route.chatapp.Models.ChatRoom;
import com.route.chatapp.Models.Messages;
import com.route.chatapp.Models.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatingRoom extends BaseActivity implements View.OnClickListener {
    public static ChatRoom chatRoom;
    protected ImageView sendButton;
    protected EditText message;
    RecyclerView recyclerView;
    ChatThreadAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    FirebaseAuth auth;
    FirebaseUser firebaseuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_chat_room);
        initView();
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.chat_rv);
        layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        adapter = new ChatThreadAdapter(null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        auth = FirebaseAuth.getInstance();
        firebaseuser = auth.getCurrentUser();
        Mydatabase.getUserBranch().orderByChild("id").equalTo(firebaseuser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    User userinfo=new User();
                    userinfo.setId(dataSnapshot.getValue(User.class).getId());
                   userinfo.setUsername(dataSnapshot.getValue(User.class).getUsername());
                   userinfo.setEmail(dataSnapshot.getValue(User.class).getEmail());
                   Dataholders.Currentuser=userinfo;
                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        MessagesDao.getmessagesbyroomid(chatRoom.getId()).addChildEventListener(childEventListener);
        adapter.setOnItemClickListner(new ChatThreadAdapter.OnItemClickListner() {
            @Override
            public void OnItemClickListner(final Messages message, final int i) {
                if(message.getSenderid().equals(firebaseuser.getUid())){
                ShowConfirmationMessage("Are you sure", "are you sure you want to delete this message?",
                        "yes", new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                ShowProgressBar();
                                Mydatabase.getMessagesBranch().child(message.getId()).removeValue();
                                adapter.notifyItemRemoved(i);
                                hideprogressbar();
                                dialog.dismiss();

                            }
                        }, "No", new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        });
                }
                else
                {ShowMessage("Sorry","you can't delete someone else's message","ok");}
            }
        });
    }
        @Override
        public void onClick (View view){
            if (view.getId() == R.id.send_button) {
                String content = message.getText().toString();
                Messages messages = new Messages();
                messages.setRoomid(chatRoom.getId());
                messages.setContent(content);
                messages.setSenderid(firebaseuser.getUid());
                messages.setSendername(firebaseuser.getDisplayName());
                SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy  hh:mm");
                String currenttimestamp = s.format(new Date());
                messages.setTimestamp(currenttimestamp);
                MessagesDao.Addmessage(messages, new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        message.setText(null);
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, R.string.try_again, Toast.LENGTH_SHORT);
                    }
                });


            }
        }

        private void initView () {
            recyclerView = (RecyclerView) findViewById(R.id.chat_rv);
            sendButton = (ImageView) findViewById(R.id.send_button);
            sendButton.setOnClickListener(ChatingRoom.this);
            message = (EditText) findViewById(R.id.message);
        }

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Messages message = dataSnapshot.getValue(Messages.class);
                if (message != null) {
                    adapter.AddMessage(message);
                    int position = adapter.getItemCount() - 1;
                    recyclerView.smoothScrollToPosition(position);
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
