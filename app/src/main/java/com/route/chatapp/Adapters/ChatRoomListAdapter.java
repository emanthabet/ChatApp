package com.route.chatapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.route.chatapp.Models.ChatRoom;
import com.route.chatapp.R;

import java.util.List;

public class ChatRoomListAdapter extends RecyclerView.Adapter<ChatRoomListAdapter.ViewHolder> {
    List<ChatRoom> chatRoomList;
    OnItemClickListner onItemClickListner;


    public ChatRoomListAdapter(List<ChatRoom> chatRoomList) {
        this.chatRoomList = chatRoomList;
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item_room,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
       final ChatRoom chatRoom=chatRoomList.get(i);
       viewHolder.name.setText(chatRoom.getName());
       viewHolder.desc.setText(chatRoom.getDesc());
       viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
onItemClickListner.OnItemClickListner(chatRoom);         }
       });
    }

    public void changedata(List<ChatRoom>chatRoomList)
    {this.chatRoomList=chatRoomList;
    notifyDataSetChanged();}


    @Override
    public int getItemCount() {
        if (chatRoomList==null) return 0;
        return chatRoomList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView desc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.nameofchatroom);
            desc=itemView.findViewById(R.id.description);
        }
    }

    public interface OnItemClickListner{
        public void OnItemClickListner(ChatRoom chatRoom);
    }
}
