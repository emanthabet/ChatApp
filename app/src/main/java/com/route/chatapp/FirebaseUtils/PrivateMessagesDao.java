package com.route.chatapp.FirebaseUtils;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.Query;
import com.route.chatapp.Models.Messages;
import com.route.chatapp.Models.PrivateMessages;

public class PrivateMessagesDao {
    public static void Addmessage(PrivateMessages messages, OnSuccessListener onSuccessListener, OnFailureListener onFailureListener){
        DatabaseReference msgnode= Mydatabase.getPrivateMessagesBranch().push();
        messages.setId(msgnode.getKey());
        msgnode.setValue(messages).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }


}
