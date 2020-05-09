package com.example.accountmanagement;

import androidx.appcompat.app.AppCompatActivity;

public class Users {

    public String name;
    public String email;
    public String userId;
    public String password;
    public Long phoneNumber;
    public Long numberOfLoginSessions;

    public Users() {
    }

    public Users(String name, String email, String userId, String password, Long phoneNumber, Long numberOfLoginSessions) {
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.numberOfLoginSessions = numberOfLoginSessions;
    }
}
