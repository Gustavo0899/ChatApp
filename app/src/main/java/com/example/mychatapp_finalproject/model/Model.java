package com.example.mychatapp_finalproject.model;

import androidx.annotation.NonNull;

public enum Model {
    USER_PROFILE("UserProfile"),
    USER_MESSAGE("UserMessage");

    private final String model;

    Model(String model) {
        this.model = model;
    }

    @NonNull
    @Override
    public String toString() {
        return this.model;
    }
}
