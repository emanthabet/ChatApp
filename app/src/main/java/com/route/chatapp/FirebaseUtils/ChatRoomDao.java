package com.route.chatapp.FirebaseUtils;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.route.chatapp.Models.ChatRoom;

public class ChatRoomDao {

    public static void InsertChatroom(ChatRoom chatroom, OnSuccessListener onSuccessListener, OnFailureListener onFailureListener)
    {
        DatabaseReference chatroomnode= Mydatabase.getchatroomBranch().push();
        chatroom.setId(chatroomnode.getKey());
        chatroomnode.setValue(chatroom).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }
}
