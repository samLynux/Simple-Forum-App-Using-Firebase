package com.example.uas_mobile_3_00000025672_samuelputra;

import java.io.Serializable;

public class User implements Serializable {
    private String userID;
    private String NIM;
    private String nama;
    private String pass;
    private String Email;

    public static String currentUsername;

    public User(String un, String ps, String nim, String name, String email){
        userID = un;
        pass = ps;
        NIM = nim;
        nama = name;
        Email = email;
    }

    public void setUsername(String un){ userID = un; }
    public void setNIM(String un){ NIM = un; }
    public void setName(String un){ nama = un; }
    public void setEmail(String un){ Email = un; }
    public void setPass(String ps){ pass = ps; }

    public String getUsername(){ return userID; }
    public String getNIM(){ return NIM; }
    public String getName(){ return nama; }
    public String getEmail(){ return Email; }
    public String getPass(){ return pass; }


}
