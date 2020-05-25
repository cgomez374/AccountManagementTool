package com.example.accountmanagement;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class FirebaseHelper {

    //List of Users, Customers, and Accounts
    final ArrayList<Users> userList = new ArrayList<Users>();

    ArrayList<Customers> customerList = new ArrayList<Customers>();

    //Firebase Database variable
    FirebaseDatabase fireDB;

    //establish connection to app database
    public FirebaseHelper() {

        fireDB = FirebaseDatabase.getInstance();
    }

    //Methods performed on Firebase data

    public Users findUser(String userId, String userPass) {
        for(int i = 0; i < userList.size(); i++) {
            if(userList.get(i).userId.equals(userId) && userList.get(i).password.equals(userPass))
                return userList.get(i);
        }

        return null;

    }

    public Users findUserById(String userId) {
        for(int i = 0; i < userList.size(); i++) {
            System.out.println("USER LIST: " + userList.get(i).name);
            if(userList.get(i).userId.equals(userId))
                return userList.get(i);
        }

        return null;
    }

    public Customers getCustomer(long social) {

        for(int i = 0; i < customerList.size(); i++) {
            if(customerList.get(i).getSocial() == social)
                return customerList.get(i);
        }

        return null;

    }


    public boolean addNewUser(Users userToAdd) {
        DatabaseReference fireRef = fireDB.getReference("Users");

        //Check to see if User exists
        if(doesUserExist(userToAdd)) {
            return false;
        }
        else {
            fireRef.child(userToAdd.userId).child("email").setValue(userToAdd.email);
            fireRef.child(userToAdd.userId).child("name").setValue(userToAdd.name);
            fireRef.child(userToAdd.userId).child("password").setValue(userToAdd.password);
            fireRef.child(userToAdd.userId).child("phoneNumber").setValue(userToAdd.phoneNumber);
            fireRef.child(userToAdd.userId).child("numberOfLoginSessions").setValue(userToAdd.numberOfLoginSessions);
        }

        return true;
    }

    public boolean addNewCustomer(Customers customer) {
        try {
            DatabaseReference fireRef = fireDB.getReference("Customers");

            //Check to see if User exists
            if(doesCustomerExist(customer)) {
                return false;
            }
            else {
                fireRef.child(customer.getCustomerId()).child("Name").setValue(customer.getName());
                fireRef.child(customer.getCustomerId()).child("Email").setValue(customer.getEmail());
                fireRef.child(customer.getCustomerId()).child("Social Security").setValue(customer.getSocial());
                fireRef.child(customer.getCustomerId()).child("Address").setValue(customer.getAddress());
                fireRef.child(customer.getCustomerId()).child("Phone Number").setValue(customer.getPhoneNum());
                fireRef.child(customer.getCustomerId()).child("Approved Lines").setValue(customer.getApprovedLines());

            }

            return true;
        }
        catch(Exception e) {
            System.out.println("Error from add new customer " + e.getMessage());
            return false;
        }


    }

    public boolean addNewAccount(Accounts account) {
        try {
            //Create path
            DatabaseReference fireRef = fireDB.getReference("Accounts");

            //Grab customer
            Customers customer = new Customers();
            customer.setCustomer(account.getCustomer());

            //Upload attributes to firebase
            fireRef.child(account.getId()).child("Customer Name").setValue(customer.getName());
            fireRef.child(account.getId()).child("Customer Email").setValue(customer.getEmail());
            fireRef.child(account.getId()).child("Customer Social Security").setValue(customer.getSocial());
            fireRef.child(account.getId()).child("Customer Address").setValue(customer.getAddress());
            fireRef.child(account.getId()).child("Approved Lines").setValue(customer.getApprovedLines());
            fireRef.child(account.getId()).child("Security Question").setValue(account.getSecurityQuestion());
            fireRef.child(account.getId()).child("Security Question Answer").setValue(account.getSecQuestAnswer());
            fireRef.child(account.getId()).child("Active Status").setValue(account.getStatus());

            return true;


        }
        catch(Exception e) {
            System.out.println("Error from addNewAcocunt " + e.getMessage());
            return false;
        }
    }



    public boolean doesUserExist(Users user) {

        boolean userExist = false;

        for(int i = 0; i < userList.size(); i++) {
            if(user.email.equals(userList.get(i).email) || user.userId.equals(userList.get(i).userId))
                userExist = true;
        }

        return userExist;
    }

    public boolean doesCustomerExist(Customers customer) {

        boolean customerExist = false;

        for(int i = 0; i < customerList.size(); i++) {
            if(customer.getSocial() == customerList.get(i).getSocial()) {
                customerExist = true;
                System.out.println("Customer already exists");
            }
            else
                System.out.println("Customer does not exists!");

        }

        return customerExist;
    }


    public void updateUser(Users autUser) {

        DatabaseReference fireRef = fireDB.getReference("Users/" + autUser.userId);
        fireRef.child("email").setValue(autUser.email);
        fireRef.child("name").setValue(autUser.name);
        fireRef.child("password").setValue(autUser.password);
        fireRef.child("phoneNumber").setValue(autUser.phoneNumber);
        fireRef.child("numberOfLoginSessions").setValue(autUser.numberOfLoginSessions);


    }

    public void retrieveAllUsers() {

        DatabaseReference fireRef = fireDB.getReference("Users");

        fireRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(userList.isEmpty() == false) {
                    userList.clear();
                }
                else {
                    for(DataSnapshot data : dataSnapshot.getChildren()) {
                        if(data.hasChild("name") && data.hasChild("email") && data.hasChild("password") && data.hasChild("phoneNumber") && data.hasChild("numberOfLoginSessions")) {
                            String name = data.child("name").getValue().toString();
                            String email = data.child("email").getValue().toString();
                            String userId = data.getKey();
                            String password = data.child("password").getValue().toString();
                            Long ptnNum = Long.decode(data.child("phoneNumber").getValue().toString());
                            Long sessions = Long.decode(data.child("numberOfLoginSessions").getValue().toString());

                            Users newUser = new Users(name, email, userId, password, ptnNum, sessions);
                            userList.add(newUser);
                        }

                    }
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void retrieveAllCustomers() {

        DatabaseReference fireRef = fireDB.getReference("Customers");

        try {
            fireRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (customerList.isEmpty() == false) {
                        customerList.clear();
                    } else {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            if (data.hasChild("Name") && data.hasChild("Email") && data.hasChild("Social Security")
                                                            && data.hasChild("Phone Number") && data.hasChild("Address")
                                                            && data.hasChild("Approved Lines")) {
                                String name = data.child("Name").getValue().toString();
                                String email = data.child("Email").getValue().toString();
                                String customerId = data.getKey();
                                long social = Long.decode(data.child("Social Security").getValue().toString());
                                Long ptnNum = Long.decode(data.child("Phone Number").getValue().toString());
                                String address = data.child("Address").getValue().toString();
                                int  approvedLines = Integer.decode(data.child("Approved Lines").getValue().toString());

                                //Customers(String name, String email, String address, long phoneNum, long socialSecurity, String customerId)
                                Customers customer = new Customers(name, email, address, ptnNum, social, customerId, approvedLines);
                                customerList.add(customer);
                            }

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e) {
            System.out.println("From retrieve customer list "  +  e.getMessage());
        }

    }



}
