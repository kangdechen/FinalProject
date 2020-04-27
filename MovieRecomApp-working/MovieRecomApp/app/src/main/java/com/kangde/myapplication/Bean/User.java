package com.kangde.myapplication.Bean;

public class User {
    private int Id;
    private String username;
    private String password;
    private  String pic;

    public User() {


        // TODO Auto-generated constructor stub
    }

    public int getId()
    {
        return Id;
    }
    public void SetId(int id)
    {
        this.Id=id;
    }
    public String getUsername() {
        return username;
    }
    public  String getImg(){return pic;};

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public  void  setImg (String img) {this.pic =img;}
}
