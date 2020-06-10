package com.example.uas_mobile_3_00000025672_samuelputra;

import java.io.Serializable;

public class Menu implements Serializable {
    private String title;
    private String desc;
    private String poster;
    private int commentsCount;

    public Menu(){

    }

    public Menu(String nm, String ds, String user, int mc){
        title = nm;
        desc = ds;
        poster = user;
        commentsCount = mc ;
    }

    public void setTitle(String nm){ title = nm; }
    public void setDeskripsi(String ds){ desc = ds; }
    public void setPoster(String user){ poster = user; }
    public void setComments(int mc){ commentsCount = mc; }

    public String getTitle(){ return title; }
    public String getDeskripsi(){ return desc; }
    public String getPoster(){ return poster; }
    public int getComments(){ return commentsCount; }
}
