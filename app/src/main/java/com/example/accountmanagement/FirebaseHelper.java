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

    final ArrayList<Users> userList = new ArrayList<Users>();

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


    public boolean addNewUser(Users userToAdd) {
        DatabaseReference fireRef = fireDB.getReference("Users");
        //Check to see if User exists
        if(doesUserExist(userToAdd)) {
            return false;
        }
        else {
            //Add new user, the user id will be the key to find each user
            //fireRef.push().setValue(userToAdd);
            //fireRef.child("userId").setValue(userToAdd.userId);
            fireRef.child(userToAdd.userId).child("email").setValue(userToAdd.email);
            fireRef.child(userToAdd.userId).child("name").setValue(userToAdd.name);
            fireRef.child(userToAdd.userId).child("password").setValue(userToAdd.password);
            fireRef.child(userToAdd.userId).child("phoneNumber").setValue(userToAdd.phoneNumber);
            fireRef.child(userToAdd.userId).child("numberOfLoginSessions").setValue(userToAdd.numberOfLoginSessions);
        }

        return true;
    }

    public boolean doesUserExist(Users user) {

        boolean userExist = false;

        for(int i = 0; i < userList.size(); i++) {
            if(user.email.equals(userList.get(i).email) || user.userId.equals(userList.get(i).userId))
                userExist = true;
        }

        return userExist;
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



}