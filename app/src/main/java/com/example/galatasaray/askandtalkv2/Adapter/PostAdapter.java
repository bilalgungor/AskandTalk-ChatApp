package com.example.galatasaray.askandtalkv2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.galatasaray.askandtalkv2.AllUsersActivity;
import com.example.galatasaray.askandtalkv2.ChatActivity;
import com.example.galatasaray.askandtalkv2.R;
import com.example.galatasaray.askandtalkv2.model.Posts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PostAdapter extends  RecyclerView.Adapter<PostAdapter.ViewHolder> {
    public Context mContext;
    public List<Posts> mPosts;

    private FirebaseUser firebaseUser;

    FirebaseUser fuser;

    public PostAdapter(Context mContext, List<Posts> mPosts) {
        this.mContext = mContext;
        this.mPosts = mPosts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.all_post_layout, viewGroup, false);

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        return new PostAdapter.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Posts posts = mPosts.get(i);


        viewHolder.question.setText(posts.getQuestion());
        Glide.with(mContext).load(posts.getProfileimage()).into(viewHolder.image_profile);
        viewHolder.username.setText("@"+posts.getUsername());
        viewHolder.fullname.setText(posts.getFullname());
        viewHolder.true_answer.setText(posts.getTrue_answer());
        viewHolder.false_answer.setText(posts.getFalse_answer());
        viewHolder.false_answer1.setText(posts.getFalse_answer1());
        viewHolder.false_answer2.setText(posts.getFalse_answer2());
        viewHolder.date.setText(posts.getDate());
        viewHolder.time.setText(posts.getTime());


        viewHolder.true_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("visit_user_id", posts.getUid());
                intent.putExtra("userName",posts.getUsername());
                mContext.startActivity(intent);
            }

        });
        viewHolder.false_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Üzgünüm Cevap Yanlış", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.false_answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Üzgünüm Cevap Yanlış", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.false_answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Üzgünüm Cevap Yanlış", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image_profile;
        public TextView username,fullname, question, time, date;
        public Button true_answer, false_answer, false_answer1, false_answer2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_profile = itemView.findViewById(R.id.post_profile_image);
            username = itemView.findViewById(R.id.post_user_name);
            fullname = itemView.findViewById(R.id.post_fullname);
            question = itemView.findViewById(R.id.post_question1);
            true_answer = itemView.findViewById(R.id.answer_true);
            false_answer = itemView.findViewById(R.id.answer_false);
            false_answer1 = itemView.findViewById(R.id.answer_false1);
            false_answer2 = itemView.findViewById(R.id.answer_false2);
            time = itemView.findViewById(R.id.post_time);
            date = itemView.findViewById(R.id.post_date);

        }
    }
}

