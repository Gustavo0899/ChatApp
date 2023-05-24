package com.example.mychatapp_finalproject.model;

import java.util.ArrayList;

public class UserProfile implements IModel {
    private String id;
    private String username;
    private String email;
    private String profilePictureId;
    private ArrayList<String> contacts;

    UserProfile() { /* empty constructor required for some databases */}

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePictureId() {
        return this.profilePictureId;
    }

    public void setProfilePictureId(String profilePictureId) {
        this.profilePictureId = profilePictureId;
    }

    public ArrayList<String> getContacts() {
        return this.contacts;
    }

    public void setContacts(ArrayList<String> contacts) {
        this.contacts = contacts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
