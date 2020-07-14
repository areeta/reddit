package com.example.reddit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView postListView;
    private ListView replyListView;
    private PostAdapter postAdapter;
    private ArrayList<Post> postList = new ArrayList<Post>();
    private ReplyAdapter replyAdapter;
    private ArrayList<Reply> replyList = new ArrayList<Reply>();

    private DatabaseReference myRef;
    private DatabaseReference mPostRef;
    private static int hasBeenCalled = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRef = FirebaseDatabase.getInstance().
                getReferenceFromUrl("https://lab-5-b042d.firebaseio.com/");

        postListView = (ListView) findViewById(R.id.post_list);
        postAdapter = new PostAdapter(this, postList);
        postListView.setAdapter(postAdapter);

        myRef.child("post").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Post post = new Post();
                String key = "";

                post.setTitle(dataSnapshot.child("title").getValue().toString());
                post.setMessage(dataSnapshot.child("message").getValue().toString());
                post.setScore(dataSnapshot.child("score").getValue(Integer.class));
                key = dataSnapshot.child("key").getValue().toString();
                post.setKey(key);

                ArrayList<Reply> replyArrayList = new ArrayList<Reply>();

                for (int i = 0; i < dataSnapshot.child(key).child("replies").getChildrenCount(); i++) {
                    replyArrayList.add(dataSnapshot.child(key).child("replies").getValue(Reply.class));
                }

                post.setReplies(replyArrayList);

                postAdapter.add(post);
                postListView.invalidateViews();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                String key = dataSnapshot.getKey();
                int index = postList.indexOf(key);

                if (index != -1) {
                    postList.remove(index);
                    postAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        myRef.child("replies").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                ArrayList<Reply> reply = dataSnapshot.getValue(ArrayList.class);

                for (int i = 0; i < reply.size(); i++) {
                    replyAdapter.add(reply.get(i));
                    replyList.add(reply.get(i));
                }
                replyListView.invalidateViews();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                String key = dataSnapshot.getKey();
                int index = replyList.indexOf(key);

                if (index != -1) {
                    replyList.remove(index);
                    replyAdapter.remove(replyList.get(index));
                    replyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void addPost(View view) {

        Post post = new Post();

        TextView title = (TextView) findViewById(R.id.editTextTitle);
        post.setTitle(title.getText().toString());

        TextView message = (TextView) findViewById(R.id.editTextMessage);
        post.setMessage(message.getText().toString());

        mPostRef = myRef.child("post");

        String key = myRef.push().getKey();
        post.setKey(key);

        if(post.getMessage().length() > 0 && post.getTitle().length() > 0)    {
            mPostRef.child(key).setValue(post);
        }

        title.setText("");
        message.setText("");
    }

    public void addReply(View view) {

        hasBeenCalled += 1;

        Reply reply = new Reply();

        TextView message = (TextView) findViewById(R.id.editTextReply);
        reply.setMessage(message.getText().toString());

        Button replyBtn = (Button) view;
        LinearLayout linearLayout = (LinearLayout) ((LinearLayout) replyBtn.getParent()).getParent();
        TextView keyView = (TextView) linearLayout.findViewById(R.id.key);

        RelativeLayout relativeL = (RelativeLayout) linearLayout.getParent();

        replyListView = (ListView) relativeL.findViewById(R.id.reply_list);
        replyAdapter = new ReplyAdapter(this, replyList);
        replyListView.setAdapter(replyAdapter);

        ArrayList<Reply> repliesList = new ArrayList<Reply>();

        for (int i = 0; i < postList.size(); i++) {
            if (postList.get(i).getKey() == keyView.getText().toString()) {
                postList.get(i).addReply(reply);
                repliesList = postList.get(i).getReplies();
                break;
            }
        }

        String key = myRef.push().getKey();
        reply.setKey(key);

        replyAdapter.add(reply);
        replyList.add(reply);

        myRef.child("post").child(keyView.getText().toString()).child("replies").setValue(repliesList);

        message.setText("");
    }

    public void upVote(View view) {

        ImageButton up = (ImageButton) view;
        RelativeLayout relativeLayout = (RelativeLayout) up.getParent();
        TextView scoreView = (TextView) relativeLayout.findViewById(R.id.scorePost);
        int score = Integer.parseInt(scoreView.getText().toString()) + 1;
        scoreView.setText(Integer.toString(score));

        TextView keyView = (TextView) relativeLayout.findViewById(R.id.key);

        for (int i = 0; i < postList.size(); i++) {
            if (postList.get(i).getKey() == keyView.getText().toString()) {
                postList.get(i).setScore(score);
                break;
            }
        }

        myRef.child("post").child(keyView.getText().toString()).child("score").setValue(score);
    }

    public void downVote(View view) {

        ImageButton down = (ImageButton) view;
        RelativeLayout relativeLayout = (RelativeLayout) down.getParent();
        TextView scoreView = (TextView) relativeLayout.findViewById(R.id.scorePost);
        int score = Integer.parseInt(scoreView.getText().toString()) - 1;
        if (score >= 0) {
            scoreView.setText(Integer.toString(score));
        }

        TextView keyView = (TextView) relativeLayout.findViewById(R.id.key);

        for (int i = 0; i < postList.size(); i++) {
            if (postList.get(i).getKey() == keyView.getText().toString()) {
                postList.get(i).setScore(score);
                break;
            }
        }

        myRef.child("post").child(keyView.getText().toString()).child("score").setValue(score);
    }

    public void deletePost(View view) {

        Button down = (Button) view;
        RelativeLayout relativeLayout = (RelativeLayout) down.getParent();
        TextView keyView = (TextView) relativeLayout.findViewById(R.id.key);

        myRef.child("post").child(keyView.getText().toString()).removeValue();

        for (int i = 0; i < postList.size(); i++) {
            if (postList.get(i).getKey() == keyView.getText().toString()) {
                postAdapter.remove(postAdapter.getItem(i));
            }
        }
    }

    public void upVoteReply(View view) {

        ImageButton up = (ImageButton) view;
        RelativeLayout relativeLayout = (RelativeLayout) up.getParent();

        TextView scoreView = (TextView) relativeLayout.findViewById(R.id.reply_score);
        int score = Integer.parseInt(scoreView.getText().toString()) + 1;
        scoreView.setText(Integer.toString(score));

        LinearLayout linearLayout = (LinearLayout) relativeLayout.getParent();
        TextView replyKeyView = (TextView) linearLayout.findViewById(R.id.reply_key);
        String replyKey = replyKeyView.getText().toString();

        RelativeLayout relativeLayoutParent=  (RelativeLayout) linearLayout.getParent().getParent().getParent().getParent();
        TextView keyView = (TextView) relativeLayoutParent.findViewById(R.id.key);
        String key = keyView.getText().toString();

        myRef.child("post").child(key).child("replies").
                child(replyKey).child("score").setValue(score);
    }

    public void downVoteReply(View view) {

        ImageButton down = (ImageButton) view;
        RelativeLayout relativeLayout = (RelativeLayout) down.getParent();
        TextView scoreView = (TextView) relativeLayout.findViewById(R.id.reply_score);
        int score = Integer.parseInt(scoreView.getText().toString()) - 1;
        if (score >= 0) {
            scoreView.setText(Integer.toString(score));
        }

        LinearLayout linearLayout = (LinearLayout) relativeLayout.getParent();
        TextView replyKeyView = (TextView) linearLayout.findViewById(R.id.reply_key);

        RelativeLayout relativeLayoutParent=  (RelativeLayout) linearLayout.getParent().getParent().getParent().getParent();
        TextView keyView = (TextView) relativeLayoutParent.findViewById(R.id.key);

        myRef.child("post").child(keyView.getText().toString()).child("replies").
                child(replyKeyView.toString()).child("score").setValue(score);
    }

    public void deleteReply(View view) {

        Button delete = (Button) view;
        LinearLayout linearLayout = ((LinearLayout) ((RelativeLayout) delete.getParent()).getParent());
        TextView keyView = (TextView) linearLayout.findViewById(R.id.reply_key);

        myRef.child("replies").child(keyView.getText().toString()).removeValue();

        for (int i = 0; i < replyList.size(); i++) {
            if (replyList.get(i).getKey() == keyView.getText().toString()) {
                replyAdapter.remove(replyAdapter.getItem(i));
                replyList.remove(replyList.remove(i));
                myRef.child("post").child(keyView.getText().toString()).child("replies").setValue(replyList);
            }
        }
    }

}
