package com.example.accountmanagement;

public class Customers {

    //Attributes
    private String name;
    private String email;
    private String customerId;
    private int pin;
    private String sqAnswer;
    private String address;
    private long phoneNum;
    private long socialSecurity;

    //Constructors
    public Customers() {
    }

    public Customers(String name, String email, String customerId, int pin, String sqAnswer, String address, long phoneNum, long socialSecurity) {
        this.name = name;
        this.email = email;
        this.customerId = customerId;
        this.pin = pin;
        this.sqAnswer = sqAnswer;
        this.address = address;
        this.phoneNum = phoneNum;
        this.socialSecurity = socialSecurity;
    }
}
