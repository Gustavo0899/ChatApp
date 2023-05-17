package com.example.mychatapp_finalproject.database;

/**
 * ServiceLocator is a singleton for the creation of services
 */
public class ServiceLocator {
    private static final ServiceLocator instance = new ServiceLocator();
    private IDatabaseHelper databaseHelper;
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
}
