package com.example.mychatapp_finalproject.database;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.mychatapp_finalproject.model.UserMessage;
import com.example.mychatapp_finalproject.model.UserProfile;
import com.google.firebase.auth.FirebaseAuth;

/**
 * ServiceLocator is a singleton for the creation of services
 */
public class ServiceLocator {
    private static final ServiceLocator instance = new ServiceLocator();
    private IDatabaseHelper databaseHelper;
    private FirebaseAuth firebaseAuth;
    private ServiceLocator() { }

    public static ServiceLocator getInstance() {
        return instance;
    }

    public IDatabaseHelper getDatabase() {
        if (databaseHelper == null) {
            databaseHelper = new FirestoreDb();
        }
        return databaseHelper;
    }
    
    public FirebaseAuth getFirebaseAuth() {
        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }
    
    public AwesomeValidation getValidator() {
        return new AwesomeValidation(ValidationStyle.BASIC);
    }

    public UserProfile getNewUserProfile() {
        return new UserProfile();
    }

    public UserMessage getNewUserMessage() {
        return new UserMessage();
    }
}
