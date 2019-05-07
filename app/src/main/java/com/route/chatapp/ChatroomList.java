package com.route.chatapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.route.chatapp.Adapters.ChatRoomListAdapter;
import com.route.chatapp.Base.BaseFragment;
import com.route.chatapp.FirebaseUtils.Mydatabase;
import com.route.chatapp.Models.ChatRoom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatroomList extends BaseFragment {
    RecyclerView recyclerView;
    ChatRoomListAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    public ChatroomList() {
        // Required empty public constructor
    }

View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view= inflater.inflate(R.layout.fragment_chatroom_list, container, false);
        recyclerView = view.findViewById(R.id.chatroom_rv);
        adapter = new ChatRoomListAdapter(null);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        Mydatabase.getchatroomBranch().addValueEventListener(valueEventListener);


        adapter.setOnItemClickListner(new ChatRoomListAdapter.OnItemClickListner() {
            @Override
            public void OnItemClickListner(ChatRoom chatRoom) {
                ChatingRoom.chatRoom = chatRoom;
                startActivity(new Intent(getContext(), ChatingRoom.class));
            }
        });


        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Addchatroom.class));
            }
        });

       return view;
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<ChatRoom> chatRoomList = new ArrayList<>();
            for (DataSnapshot item : dataSnapshot.getChildren()) {
                ChatRoom room = item.getValue(ChatRoom.class);//kda b7wlo lno3 room l2ndo gy fe shakl json
                chatRoomList.add(room);
            }
            adapter.changedata(chatRoomList);
            Collections.reverse(chatRoomList);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            ShowMessage(getString(R.string.warnning), databaseError.getMessage(), getString(R.string.ok));
        }
    };

}
