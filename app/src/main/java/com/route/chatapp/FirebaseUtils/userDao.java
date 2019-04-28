package com.route.chatapp.FirebaseUtils;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.route.chatapp.Models.User;

public class userDao
{
    public static void InsertUser(User user,String id, OnSuccessListener onSuccessListener, OnFailureListener onFailureListener){
     DatabaseReference usernode= Mydatabase.getUserBranch().child(id);
     user.setId(id);
     usernode.setValue(user).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }
    public static void findUserByusername(String name,ValueEventListener valueEventListener){
      Mydatabase.getUserBranch()
             .equalTo(name,"username").
                     addListenerForSingleValueEvent(valueEventListener);
    }





}
