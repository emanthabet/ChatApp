package com.route.chatapp.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.route.chatapp.Models.ChatRoom;
import com.route.chatapp.Models.User;
import com.route.chatapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersRecycleviewAdapter extends RecyclerView.Adapter<UsersRecycleviewAdapter.ViewHolder>{
    private List<User> users;
    private Context thecontext;
     OnItemClickListner onItemClickListner;

    public UsersRecycleviewAdapter(Context context,List<User> users) {
        this.thecontext=context;
        this.users = users;
    }


    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(thecontext).inflate(R.layout.card_item_users,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final User user=users.get(i);
            viewHolder.name.setText(user.getUsername());
            viewHolder.profilepic.setImageResource(R.drawable.harrystyles);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListner.onItemClickListner(user);
            }
        });
            }

    @Override
    public int getItemCount() {
        if(users==null)
            return 0;

        return users.size();
    }

    public void changedata(List<User>users)
    {this.users=users;
        notifyDataSetChanged();}

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profilepic;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilepic=itemView.findViewById(R.id.userPhoto);
            name=itemView.findViewById(R.id.name);

        }
    }
    public interface OnItemClickListner{
         void onItemClickListner(User user);
    }
}
