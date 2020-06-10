package com.example.uas_mobile_3_00000025672_samuelputra;

import org.w3c.dom.Comment;

import java.io.Serializable;

public class comment implements Serializable {
    private String comment;
    private String commenter;


    public comment(String nm, String ds){
        comment = nm;
        commenter = ds;
    }

    public void setComment(String nm){ comment = nm; }
    public void setCommenter(String ds){ commenter = ds; }

    public String getComment(){ return comment; }
    public String getCommenter(){ return commenter; }
}