package com.example.reddit;

import java.util.ArrayList;

public class Post {

    private String message;
    private String title;
    private int score;
    private String key;
    private ArrayList<Reply> replies;

    public Post () {
        this.score = 0;
        replies = new ArrayList<Reply>();
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public void setScore (int score) {
        this.score = score;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void addReply(Reply reply) {
        this.replies.add(reply);
    }

    public void setReplies(ArrayList<Reply> replies) { this.replies = replies; }

    public String getMessage() {
        return this.message;
    }

    public int getScore() {
        return this.score;
    }

    public String getTitle () {
        return this.title;
    }

    public String getKey() {
        return key;
    }

    public ArrayList<Reply> getReplies() {
        return this.replies;
    }

}
