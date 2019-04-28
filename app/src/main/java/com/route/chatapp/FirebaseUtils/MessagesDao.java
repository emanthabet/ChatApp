package com.route.chatapp.FirebaseUtils;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.route.chatapp.Models.Messages;

public class MessagesDao {

    public static void Addmessage(Messages messages, OnSuccessListener onSuccessListener, OnFailureListener onFailureListener){
        DatabaseReference msgnode= Mydatabase.getMessagesBranch().push();
        messages.setId(msgnode.getKey());
        msgnode.setValue(messages).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }

    public static Query getmessagesbyroomid(String roomid)
    {
   return Mydatabase.getMessagesBranch().orderByChild("roomid").equalTo(roomid);
    }
}
