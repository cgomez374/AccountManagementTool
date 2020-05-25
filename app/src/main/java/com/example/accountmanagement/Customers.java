package com.example.accountmanagement;

public class Customers {

    //Attributes
    private String name;
    private String email;
    private String customerId;
    private String address;
    private long phoneNum;
    private long socialSecurity;
    private int approvedLines;

    //Constructors
    public Customers() {
    }

    //New customer constructor will assign ID and set other attributes
    public Customers(String name, String email, String address, long phoneNum, long socialSecurity, int approvedLines) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNum = phoneNum;
        this.socialSecurity = socialSecurity;
        this.approvedLines = approvedLines;

        //Implement auto customer id
        customerId = CreateCustomerId();

    }

    //This constructor will be used for existing customers that already have an id
    public Customers(String name, String email, String address, long phoneNum, long socialSecurity, String customerId, int approvedLines) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNum = phoneNum;
        this.socialSecurity = socialSecurity;
        this.customerId = customerId;
        this.approvedLines = approvedLines;

    }

    public void setCustomer(Customers customer) {
        this.name = customer.name;
        this.email = customer.email;
        this.address = customer.address;
        this.phoneNum = customer.phoneNum;
        this.socialSecurity = customer.socialSecurity;
        this.customerId = customer.customerId;
        this.approvedLines = customer.approvedLines;
    }

    private String CreateCustomerId(){
        //User id will have to minimum 8 characters made up of numbers and letter
        // index one and two will be letters and the rest will be numbers

        //empty string
        String customerId = "";

        //generate random numbers from 1 -9, add to user ID
        // define the range
        int max = 9;
        int min = 1;
        int range = max - min + 1;

        for (int k = 0; k < 9; k++) {
            int rand = (int) (Math.random() * range) + min;
            customerId += rand;
        }

        //return
        return customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public long getPhoneNum() {
        return phoneNum;
    }

    public long getSocial() {
        return socialSecurity;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public int getApprovedLines() {
        return approvedLines;
    }
}
