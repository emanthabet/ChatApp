package com.route.chatapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.route.chatapp.Adapters.UsersRecycleviewAdapter;
import com.route.chatapp.FirebaseUtils.Mydatabase;
import com.route.chatapp.Models.ChatRoom;
import com.route.chatapp.Models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

public class UsersList extends Fragment {
RecyclerView recyclerView;
UsersRecycleviewAdapter usersRecycleviewAdapter;
RecyclerView.LayoutManager layoutManager;

    FirebaseAuth  auth;
    FirebaseUser firebaseUser;



    public UsersList() {
        // Required empty public constructor
    }

View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_users_list, container, false);
        recyclerView=view.findViewById(R.id.contacts_recycler_view);
        usersRecycleviewAdapter=new UsersRecycleviewAdapter(getContext(),null);
        layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(usersRecycleviewAdapter);
        recyclerView.setLayoutManager(layoutManager);
        auth=FirebaseAuth.getInstance();
        firebaseUser=auth.getCurrentUser();
        Mydatabase.getUserBranch().addValueEventListener(valueEventListener);
        usersRecycleviewAdapter.setOnItemClickListner(new UsersRecycleviewAdapter.OnItemClickListner() {
            @Override
            public void onItemClickListner(User user) {
                Intent intent=new Intent(getContext(),PrivateChating.class);
                intent.putExtra("friendname",user.getUsername());
                intent.putExtra("friendid",user.getId());
                 startActivity(intent);
            }
        });
        return view;
    }

ValueEventListener valueEventListener=new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        List<User> userslist = new ArrayList<>();
        for (DataSnapshot item : dataSnapshot.getChildren()) {
            User user = item.getValue(User.class);
            if(!firebaseUser.getUid().equals(user.getId()))
           userslist.add(user);
        }
       usersRecycleviewAdapter.changedata(userslist);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
};
}
