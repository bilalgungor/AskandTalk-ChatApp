package com.example.galatasaray.askandtalkv2.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.galatasaray.askandtalkv2.R;
import com.example.galatasaray.askandtalkv2.model.Messages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>
{
    private List<Messages> userMessagesList;
    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;

    private Context context;

    public MessagesAdapter (List<Messages> userMessagesList)
    {
        this.userMessagesList = userMessagesList;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView SenderMessageText, ReceiverMessageText;
        public CircleImageView ReceiverProfileImage;

        public MessageViewHolder(View itemView) {
            super(itemView);

            SenderMessageText = (TextView) itemView.findViewById(R.id.sender_message_text);
            ReceiverMessageText = (TextView) itemView.findViewById(R.id.receiver_message_text);
            ReceiverProfileImage = (CircleImageView) itemView.findViewById(R.id.message_profile_image);
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_layout, parent, false);

        mAuth = FirebaseAuth.getInstance();

        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, int position)
    {
        String messageSenderID = mAuth.getCurrentUser().getUid();
        Messages messages = userMessagesList.get(position);

        String fromUserID = messages.getFrom();
        String fromMessageType = messages.getType();

        userDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(fromUserID);
        userDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                        String image = dataSnapshot.child("profileimage").getValue().toString();
                        Picasso.with(holder.ReceiverProfileImage.getContext()).load(image).placeholder(R.drawable.profile).into(holder.ReceiverProfileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (fromMessageType.equals("text"))
        {
            holder.ReceiverMessageText.setVisibility(View.INVISIBLE);
            holder.ReceiverProfileImage.setVisibility(View.INVISIBLE);

            if (fromUserID.equals(messageSenderID))
            {
                holder.SenderMessageText.setBackgroundResource(R.drawable.sender_message_background);
                holder.SenderMessageText.setTextColor(Color.WHITE);
                holder.SenderMessageText.setGravity(Gravity.LEFT);
                holder.SenderMessageText.setText(messages.getMessage());
            }
            else
            {
                holder.SenderMessageText.setVisibility(View.INVISIBLE);

                holder.ReceiverMessageText.setVisibility(View.VISIBLE);
                holder.ReceiverProfileImage.setVisibility(View.VISIBLE);

                holder.ReceiverMessageText.setBackgroundResource(R.drawable.receiver_message_background);
                holder.ReceiverMessageText.setTextColor(Color.WHITE);
                holder.ReceiverMessageText.setGravity(Gravity.LEFT);
                holder.ReceiverMessageText.setText(messages.getMessage());


            }
        }
    }

    @Override
    public int getItemCount() {
        return userMessagesList.size();
    }
}
