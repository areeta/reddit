package com.example.reddit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter<Post> {

    private Context mContext;
    private ArrayList<Post> postList;

    public PostAdapter(Context context, ArrayList<Post> list) {
        super(context, 0, list);
        mContext = context;
        postList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;
        final Post currentPost = postList.get(position);

        if(listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.post, parent,false);
        }

        TextView title = (TextView) listItem.findViewById(R.id.title);
        title.setText(currentPost.getTitle());

        TextView message = (TextView) listItem.findViewById(R.id.message);
        message.setText(currentPost.getMessage());

        TextView key = (TextView) listItem.findViewById(R.id.key);
        key.setText(currentPost.getKey());

        TextView score = (TextView) listItem.findViewById(R.id.scorePost);
        score.setText(Integer.toString(currentPost.getScore()));

        return listItem;
    }
}

