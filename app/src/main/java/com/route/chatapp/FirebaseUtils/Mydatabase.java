package com.route.chatapp.FirebaseUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.route.chatapp.Models.User;

public class Mydatabase {
    private static FirebaseDatabase firebaseDatabase;
   public static FirebaseDatabase getInstance(){
       if(firebaseDatabase==null)
       {firebaseDatabase=FirebaseDatabase.getInstance(); }
       return firebaseDatabase;
   }

  // final static String User ="user";
  public static DatabaseReference getUserBranch(){
       return getInstance().getReference("User");
   }


    public static DatabaseReference getchatroomBranch(){
        return getInstance().getReference("ChatingRoom");
    }

    public static DatabaseReference getMessagesBranch(){
       return getInstance().getReference("Messages");
    }

}
