package com.example.galatasaray.askandtalkv2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private TextView userName, userProfName, userStatus;
    private CircleImageView userProfileImage;
    private ImageButton editButton;
    private Button myPost;

    private DatabaseReference profileUsersRef, PostRef;
    private FirebaseAuth mAuth;

    private Toolbar mToolbar;

    private String currentUserID;

    private int countPosts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mToolbar = (Toolbar) findViewById(R.id.profile_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Hesabım");

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        profileUsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
        PostRef = FirebaseDatabase.getInstance().getReference().child("Posts");

        userName = (TextView) findViewById(R.id.my_username);
        userProfName = (TextView) findViewById(R.id.my_profile_fullname);
        userStatus = (TextView) findViewById(R.id.my_profile_status);
        userProfileImage = (CircleImageView) findViewById(R.id.my_profile_picture);
        editButton = (ImageButton) findViewById(R.id.edit_btn);
        myPost = (Button) findViewById(R.id.myPost);

        myPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SendUserToMyPostActivity();
            }
        });

        PostRef.orderByChild("uid").startAt(currentUserID).endAt(currentUserID + "\uf8ff")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists())
                        {
                            countPosts = (int) dataSnapshot.getChildrenCount();
                            myPost.setText(Integer.toString(countPosts)+ " Soru");
                        }
                        else
                        {
                            myPost.setText("Soru yok");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        profileUsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    String myProfileImage = dataSnapshot.child("profileimage").getValue().toString();
                    String myUserName = dataSnapshot.child("username").getValue().toString();
                    String myProfileName = dataSnapshot.child("fullname").getValue().toString();
                    String myProfileStatus = dataSnapshot.child("biography").getValue().toString();



                    Picasso.with(ProfileActivity.this).load(myProfileImage).placeholder(R.drawable.profile).into(userProfileImage);

                    userName.setText("@"+myUserName);
                    userProfName.setText(myProfileName);
                    userStatus.setText(myProfileStatus);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNewPostIntent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(addNewPostIntent);
            }
        });

    }

    private void SendUserToMyPostActivity()
    {
        Intent postIntent = new Intent(ProfileActivity.this, MyPostActivity.class);
        startActivity(postIntent);
    }
}
