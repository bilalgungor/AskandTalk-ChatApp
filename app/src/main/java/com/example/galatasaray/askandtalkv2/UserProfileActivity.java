package com.example.galatasaray.askandtalkv2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    private TextView userName, userProfName, userStatus;
    private CircleImageView userProfileImage;
    private String receiverUserID, usersIDs;

    private Toolbar mToolbar;


    private DatabaseReference UsersRef ,post;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        mAuth = FirebaseAuth.getInstance();
       receiverUserID = getIntent().getExtras().get("visit_user_id").toString();


        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        InitializeField();

        UsersRef.child(receiverUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {
                    String myProfileImage = dataSnapshot.child("profileimage").getValue().toString();
                    String myUserName = dataSnapshot.child("username").getValue().toString();
                    String myProfileName = dataSnapshot.child("fullname").getValue().toString();
                    String myProfileStatus = dataSnapshot.child("biography").getValue().toString();

                    Picasso.with(UserProfileActivity.this).load(myProfileImage).placeholder(R.drawable.profile).into(userProfileImage);

                    userName.setText("@"+myUserName);
                    userProfName.setText(myProfileName);
                    userStatus.setText(myProfileStatus);


                    mToolbar = (Toolbar) findViewById(R.id.user_profile_bar);
                    setSupportActionBar(mToolbar);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setTitle(myProfileName+ "  Profili");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void InitializeField()
    {
        userName = (TextView) findViewById(R.id.user_username);
        userProfName = (TextView) findViewById(R.id.user_profile_fullname);
        userStatus = (TextView) findViewById(R.id.user_profile_status);
        userProfileImage = (CircleImageView) findViewById(R.id.user_profile_picture);

    }
}
