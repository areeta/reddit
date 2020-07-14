package com.example.reddit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ReplyAdapter extends ArrayAdapter<Reply> {

    private Context mContext;
    private ArrayList<Reply> replyList;

    public ReplyAdapter(Context context, ArrayList<Reply> list) {
        super(context, 0, list);
        mContext = context;
        replyList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;
        final Reply currentReply = replyList.get(position);

        if(listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.reply, parent,false);
        }

        TextView message = (TextView) listItem.findViewById(R.id.reply_message);
        message.setText(currentReply.getMessage());

        TextView key = (TextView) listItem.findViewById(R.id.reply_key);
        key.setText(currentReply.getKey());

        TextView score = (TextView) listItem.findViewById(R.id.reply_score);
        score.setText(Integer.toString(currentReply.getScore()));

        return listItem;
    }
}

