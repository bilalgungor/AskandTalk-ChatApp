package com.example.galatasaray.askandtalkv2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class PostActivity extends AppCompatActivity
{
    private Toolbar mToolbar;
    private ProgressDialog loadingBar;

    private Button UpdatePostButton;
    private EditText PostQuestion;
    private TextView TrueAnswer, FalseAnswer, FalseAnswer1, FalseAnswer2;

    private Uri ImageUri;
    private String Question, Answer,Answer1,Answer2,Answer3;

    private DatabaseReference UsersRef, PostsRef;
    private FirebaseAuth mAuth;

    private String saveCurrentDate, saveCurrentTime, postRandomName, current_user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();


        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");


        UpdatePostButton = (Button) findViewById(R.id.update_post_button);
        PostQuestion =(EditText) findViewById(R.id.post_question);
        TrueAnswer = (TextView) findViewById(R.id.true_answer);
        FalseAnswer = (TextView) findViewById(R.id.false_answer);
        FalseAnswer1 = (TextView) findViewById(R.id.false_answer1);
        FalseAnswer2 = (TextView) findViewById(R.id.false_answer2);
        loadingBar = new ProgressDialog(this);


        mToolbar = (Toolbar) findViewById(R.id.post_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Soru Sor");




        UpdatePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ValidatePostInfo();
            }
        });
    }



    private void ValidatePostInfo()
    {
        Question = PostQuestion.getText().toString();
        Answer = TrueAnswer.getText().toString();
        Answer1 = FalseAnswer.getText().toString();
        Answer2 = FalseAnswer1.getText().toString();
        Answer3 = FalseAnswer2.getText().toString();

        if(TextUtils.isEmpty(Answer) && TextUtils.isEmpty(Answer1) && TextUtils.isEmpty(Answer2) && TextUtils.isEmpty(Answer3))
        {
            Toast.makeText(this, "Boş alan bırakmamalısınız...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Question))
        {
            Toast.makeText(this, "Soru Sormadan Yayınlayamazsınız...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Yeni Soru Sor");
            loadingBar.setMessage("Soru yayınlanıyor...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            StoringImageToFirebaseStorage();
        }
    }



    private void StoringImageToFirebaseStorage()
    {
        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordDate.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;


        SavingPostInformationToDatabase();

    }




    private void SavingPostInformationToDatabase()
    {
        UsersRef.child(current_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    String userName = dataSnapshot.child("username").getValue().toString();
                    String userFullName = dataSnapshot.child("fullname").getValue().toString();
                    String userProfileImage = dataSnapshot.child("profileimage").getValue().toString();
                    String answer_true = TrueAnswer.getText().toString();
                    String answer_false = FalseAnswer.getText().toString();
                    String answer_false1 = FalseAnswer1.getText().toString();
                    String answer_false2 = FalseAnswer2.getText().toString();

                    HashMap postsMap = new HashMap();
                    postsMap.put("uid", current_user_id);
                    postsMap.put("date", saveCurrentDate);
                    postsMap.put("time", saveCurrentTime);
                    postsMap.put("question", Question);
                    postsMap.put("true_answer", answer_true);
                    postsMap.put("false_answer", answer_false);
                    postsMap.put("false_answer1", answer_false1);
                    postsMap.put("false_answer2", answer_false2);
                    postsMap.put("profileimage", userProfileImage);
                    postsMap.put("fullname", userFullName);
                    postsMap.put("username", userName);
                    PostsRef.child(current_user_id + postRandomName).updateChildren(postsMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        SendUserToMainActivity();
                                        Toast.makeText(PostActivity.this, "Soru yayında.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                    else
                                    {
                                        Toast.makeText(PostActivity.this, "Boru yayınlanırken bir hata meydana geldi.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            SendUserToMainActivity();
        }

        return super.onOptionsItemSelected(item);
    }



    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(PostActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }
}