package com.example.reddit;

public class Reply {

    private String message;
    private int score;
    private String key;


    public Reply () {
        this.score = 0;
        this.message = "";
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public void setScore (int score) {
        this.score = score;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return this.message;
    }

    public int getScore() {
        return this.score;
    }

    public String getKey() {
        return key;
    }

}
