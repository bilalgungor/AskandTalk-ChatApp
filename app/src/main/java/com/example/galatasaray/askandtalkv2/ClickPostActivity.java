package com.example.galatasaray.askandtalkv2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ClickPostActivity extends AppCompatActivity {


    private TextView PostQuestion, PostAnswerTrue,PostAnswerFalse,PostAnswerFalse1,PostAnswerFalse2;
    private Button DeletePostButton, EditPostButton;

    private DatabaseReference clickPostRef;
    private FirebaseAuth mAuth;

    private String PostKey, currentUserID, databaseUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_post);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        PostKey = getIntent().getExtras().get("PostKey").toString();
        clickPostRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(PostKey);

        PostQuestion = (TextView) findViewById(R.id.edit_post_question);
        PostAnswerTrue = (TextView) findViewById(R.id.edit_post_answer_true);
        PostAnswerFalse = (TextView) findViewById(R.id.edit_post_answer_false);
        PostAnswerFalse1 = (TextView) findViewById(R.id.edit_post_answer_false1);
        PostAnswerFalse2 = (TextView) findViewById(R.id.edit_post_answer_false2);
        DeletePostButton = (Button) findViewById(R.id.edit_delete_button);
        EditPostButton = (Button) findViewById(R.id.edit_edit_button);

        DeletePostButton.setVisibility(View.INVISIBLE);
        EditPostButton.setVisibility(View.INVISIBLE);

        clickPostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {
                    databaseUserID = dataSnapshot.child("uid").getValue().toString();

                    final String question = dataSnapshot.child("question").getValue().toString();
                    final String answerT = dataSnapshot.child("true_answer").getValue().toString();
                    final String answerF = dataSnapshot.child("false_answer").getValue().toString();
                    final String answerF1 = dataSnapshot.child("false_answer1").getValue().toString();
                    final String answerF2 = dataSnapshot.child("false_answer2").getValue().toString();


                    PostQuestion.setText(question);
                    PostAnswerTrue.setText(answerT);
                    PostAnswerFalse.setText(answerF);
                    PostAnswerFalse1.setText(answerF1);
                    PostAnswerFalse2.setText(answerF2);

                    if (currentUserID.equals(databaseUserID))
                    {
                        DeletePostButton.setVisibility(View.VISIBLE);
                        EditPostButton.setVisibility(View.VISIBLE);
                    }

                    EditPostButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            EditCurrentPost(question,answerT,answerF,answerF1,answerF2);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DeletePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteCurrentPost();
            }
        });
    }

    private void EditCurrentPost(String question, String answerT, String answerF, String answerF1, String answerF2)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ClickPostActivity.this);
        builder.setTitle("Düzenle:");

        View mView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        final EditText Question = (EditText) mView.findViewById(R.id.alert);
        final EditText Answer1 = (EditText) mView.findViewById(R.id.alert1);
        final EditText Answer2 = (EditText) mView.findViewById(R.id.alert2);
        final EditText Answer3 = (EditText) mView.findViewById(R.id.alert3);
        final EditText Answer4 = (EditText) mView.findViewById(R.id.alert4);

        Question.setText(question);
        Answer1.setText(answerT);
        Answer2.setText(answerF);
        Answer3.setText(answerF1);
        Answer4.setText(answerF2);

        builder.setPositiveButton("Güncelle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clickPostRef.child("question").setValue(Question.getText().toString());
                clickPostRef.child("true_answer").setValue(Answer1.getText().toString());
                clickPostRef.child("false_answer").setValue(Answer2.getText().toString());
                clickPostRef.child("false_answer1").setValue(Answer3.getText().toString());
                clickPostRef.child("false_answer2").setValue(Answer4.getText().toString());
            }
        });

        builder.setNegativeButton("Geri", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });



        builder.setView(mView);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_purple);
        dialog.show();



    }


    private void DeleteCurrentPost()
    {
        clickPostRef.removeValue();
        Toast.makeText(this, "Soru Silindi!", Toast.LENGTH_SHORT).show();
        SendUserToMainActivity();
    }

    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(ClickPostActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
