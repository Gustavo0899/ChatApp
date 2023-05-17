package com.example.mychatapp_finalproject.common;

import androidx.annotation.NonNull;

public enum Model {
    CAPTAIN("Copilot"),
    COPILOT("Pilot");

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
