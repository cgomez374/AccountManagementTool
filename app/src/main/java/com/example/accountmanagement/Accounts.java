package com.example.accountmanagement;

public class Accounts {

    //Variables
    private Customers customer;
    private String securityQuestion;
    private String secQuestAnswer;
    private long pin;
    private String accountId;

    public Accounts() {
    }

    public Accounts(Customers customer, String securityQuestion, String secQuestAnswer, long pin) {
        this.customer = customer;
        this.securityQuestion = securityQuestion;
        this.secQuestAnswer = secQuestAnswer;
        this.pin = pin;
        this.accountId = createAccountId();
    }

    public String getId() {
        return accountId;
    }

    public Customers getCustomer() { return this.customer; }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecQuestAnswer() {
        return secQuestAnswer;
    }

    public long getPin() {
        return pin;
    }

    private String createAccountId(){
        //User id will have to minimum 8 characters made up of numbers and letter
        // index one and two will be letters and the rest will be numbers

        //empty string
        String accountId = "";

        //Fill index 1 and 2 with two letters
        // define the range
        int max = 122;
        int min = 97;
        int range = max - min + 1;

        // generate random numbers between 97 and 122 (ASCII), convert to char, add to userId
        for (int j = 0; j < 1; j++) {
            int rand = (int) (Math.random() * range) + min;
            char c = (char) rand;
            accountId += c;
        }

        //generate random numbers from 1 -9, add to user ID
        // define the range
        max = 9;
        min = 1;
        range = max - min + 1;

        for (int k = 0; k < 8; k++) {
            int rand = (int) (Math.random() * range) + min;
            accountId += rand;
        }

        //return
        return accountId;
    }
}
