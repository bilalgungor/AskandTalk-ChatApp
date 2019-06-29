package com.example.galatasaray.askandtalkv2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.galatasaray.askandtalkv2.ChatActivity;
import com.example.galatasaray.askandtalkv2.MessageActivity;
import com.example.galatasaray.askandtalkv2.R;
import com.example.galatasaray.askandtalkv2.UserProfileActivity;
import com.example.galatasaray.askandtalkv2.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<Users> mUsers;

    private FirebaseUser firebaseUser;



    public UserAdapter(Context mContext, List<Users> mUsers){
        this.mUsers = mUsers;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);


        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Users user = mUsers.get(position);
        holder.username.setText(user.getFullname());
        holder.status.setText(user.getBiography());
        holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        Glide.with(mContext).load(user.getProfileimage()).into(holder.profile_image);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UserProfileActivity.class);
                intent.putExtra("visit_user_id", user.getId());
                intent.putExtra("userName", user.getFullname());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username, status;
        public ImageView profile_image;


        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.display_all_user_name);
            profile_image = itemView.findViewById(R.id.display_all_user_profile);
            status = itemView.findViewById(R.id.display_all_user_status);


        }
    }

}