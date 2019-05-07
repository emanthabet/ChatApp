package com.route.chatapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.route.chatapp.Models.Messages;
import com.route.chatapp.Models.PrivateMessages;
import com.route.chatapp.R;

import java.util.ArrayList;
import java.util.List;

public class PrivateChatThreadAdapter extends RecyclerView.Adapter<PrivateChatThreadAdapter.ViewHolder> {
    private List<PrivateMessages> messagesList;
    private OnItemClickListner onItemClickListner;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public PrivateChatThreadAdapter(List<PrivateMessages> messagesList) {
        this.messagesList = messagesList;


    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public static final int incoming = 10;
    public static final int outgoing = 20;

    @Override
    public int getItemViewType(int position) {
        if (user != null) {
            if (messagesList.get(position).getSenderid().equals(user.getUid()))
                return outgoing;
            return incoming;
        }
        return incoming;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        if (i == incoming)
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item_incoming_msg, viewGroup, false);
        else if (i == outgoing)
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item_outgoing_msg, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final PrivateMessages messages = messagesList.get(i);
        viewHolder.contentmessage.setText(messages.getContent());
        viewHolder.date.setText(messages.getTimestamp());
        int viewtype = getItemViewType(i);
        if (viewtype == incoming)
            viewHolder.name.setText(messages.getSendername());


    }

    public void ChangeData(List<PrivateMessages> messages) {
        this.messagesList = messages;
        notifyDataSetChanged();
    }

    public void deletedata(List<Messages> messages, int i) {
        messages.remove(messages.get(i));
        notifyItemRemoved(i);
    }

    public void AddMessage(PrivateMessages messages) {
        if (messagesList == null)
        {  messagesList = new ArrayList<>();}
        messagesList.add(messages);
        notifyItemChanged(messagesList.size() - 1);
    }

    @Override
    public int getItemCount() {
        if (messagesList == null)
            return 0;
        return messagesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView contentmessage;
        TextView date;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.sender_name);
            contentmessage = view.findViewById(R.id.content);
            date = view.findViewById(R.id.time);

        }
    }

    public interface OnItemClickListner {
       void onItemClickListner(PrivateMessages messagese, int i);
    }
}
