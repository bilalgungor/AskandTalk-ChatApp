package com.example.galatasaray.askandtalkv2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.galatasaray.askandtalkv2.model.Posts;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyPostActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView myPostList;

    private FirebaseAuth mAuth;
    private DatabaseReference PostsRef;

    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");

        mToolbar = (Toolbar) findViewById(R.id.mypost_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Sorularım");

        myPostList = (RecyclerView) findViewById(R.id.my_all_posts_list);
        myPostList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        myPostList.setLayoutManager(linearLayoutManager);

        DisplayAllPosts();

    }

    private void DisplayAllPosts()
    {
        Query myPostsQuery = PostsRef.orderByChild("uid").startAt(currentUserID).endAt(currentUserID + "\uf8ff");

        FirebaseRecyclerAdapter<Posts, MyPostsViewHolder> firebaseRecyclerAdapter
                =new FirebaseRecyclerAdapter<Posts, MyPostsViewHolder>
                (
                        Posts.class,
                        R.layout.all_post_layout,
                        MyPostsViewHolder.class,
                        myPostsQuery
                ) {
            @Override
            protected void populateViewHolder(MyPostsViewHolder viewHolder, Posts model, int position)
            {
                final String PostKey = getRef(position).getKey();


                viewHolder.setUsername("@"+model.getUsername());
                viewHolder.setFullname(model.getFullname());
                viewHolder.setTime(model.getTime());
                viewHolder.setDate(model.getDate());
                viewHolder.setProfileimage(getApplicationContext(), model.getProfileimage());
                viewHolder.setQuestion(model.getQuestion());
                viewHolder.setTrue_answer(model.getTrue_answer());
                viewHolder.setFalse_answer(model.getFalse_answer());
                viewHolder.setFalse_answer1(model.getFalse_answer1());
                viewHolder.setFalse_answer2(model.getFalse_answer2());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent clickPostIntent = new Intent(MyPostActivity.this, ClickPostActivity.class);
                        clickPostIntent.putExtra("PostKey", PostKey);
                        startActivity(clickPostIntent);

                    }
                });

            }
        };

        myPostList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class MyPostsViewHolder extends RecyclerView.ViewHolder
    {
        View mView;

        public MyPostsViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public  void setUsername(String username)
        {
            TextView username1 = (TextView) mView.findViewById(R.id.post_user_name);

            username1.setText(username);
        }

        public  void setFullname(String fullname)
        {
            TextView fullname1 = (TextView) mView.findViewById(R.id.post_fullname);

            fullname1.setText(fullname);
        }

        public void setProfileimage(Context ctx, String profileimage)
        {
            CircleImageView image = (CircleImageView) mView.findViewById(R.id.post_profile_image);
            Picasso.with(ctx).load(profileimage).into(image);
        }

        public void setQuestion(String question)
        {
            TextView Question = (TextView) mView.findViewById(R.id.post_question1);
            Question.setText(question);
        }

        public void setTime(String time)
        {
            TextView PostTime = (TextView) mView.findViewById(R.id.post_time);
            PostTime.setText("  " +time);
        }
        public void setDate(String date)
        {
            TextView PostDate = (TextView) mView.findViewById(R.id.post_date);
            PostDate.setText("  "+date);
        }
        public void setTrue_answer(String true_answer)
        {
            TextView PostAnswer = (TextView) mView.findViewById(R.id.answer_true);
            PostAnswer.setText(true_answer);
        }
        public void setFalse_answer(String false_answer)
        {
            TextView PostAnswer1 = (TextView) mView.findViewById(R.id.answer_false);
            PostAnswer1.setText(false_answer);
        }
        public void setFalse_answer1(String false_answer1)
        {
            TextView PostAnswer2 = (TextView) mView.findViewById(R.id.answer_false1);
            PostAnswer2.setText(false_answer1);
        }
        public void setFalse_answer2(String false_answer2)
        {
            TextView PostAnswer3 = (TextView) mView.findViewById(R.id.answer_false2);
            PostAnswer3.setText(false_answer2);
        }
    }
}
