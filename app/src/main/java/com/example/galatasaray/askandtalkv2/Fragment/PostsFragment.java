package com.example.galatasaray.askandtalkv2.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.galatasaray.askandtalkv2.Adapter.PostAdapter;
import com.example.galatasaray.askandtalkv2.R;
import com.example.galatasaray.askandtalkv2.model.Posts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static  com.example.galatasaray.askandtalkv2.R.layout.fragment_posts;
public class PostsFragment extends Fragment {

    private RecyclerView recyclerView;


    private PostAdapter postAdapter;
    private List<Posts> postsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(fragment_posts, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_post);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);




        postsList = new ArrayList<>();
        postAdapter= new PostAdapter(getContext(), postsList);
        recyclerView.setAdapter(postAdapter);

        displayPosts();



        return view;
    }


    private void displayPosts()
    {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Posts posts = snapshot.getValue(Posts.class);
                    postsList.add(posts);

                }
                Collections.shuffle(postsList);
                postAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
