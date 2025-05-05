package com.example.skillshub;

public class User {
    public String uid;
    public String name;
    public String email;

    // Empty constructor required by Firebase
    public User() {}

    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }
}
