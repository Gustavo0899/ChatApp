package com.example.mychatapp_finalproject.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.airportapp.common.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class FirestoreDb implements IDatabaseHelper {
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final String TAG = "FirestoreDb";

    FirestoreDb() { }

    @Override
    public <T> void create(String id, @NonNull Model model, T data) {
        firestore.collection(model.toString()).document(id).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "DocumentSnapshot written with ID: " + id);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document: ", e);
            }
        });
    }

    @Override
    public <T> void update(String id, @NonNull Model model, T data) {
        firestore.collection(model.toString()).document(id).set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "DocumentSnapshot successfully updated!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error updating document: ", e);
            }
        });
    }

    @Override
    public void delete(String id, @NonNull Model model) {
        firestore.collection(model.toString()).document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "DocumentSnapshot successfully deleted!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error deleting document: ", e);
            }
        });
    }

    @Override
    public void getDataOfModel(String id, @NonNull Model model, ICallback callback) {
        firestore.collection(model.toString()).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    callback.onCallback(task.getResult().getData());
                    Log.w(TAG, "Success retrieving data!");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error retrieving data: ", e);
            }
        });
    }

    @Override
    public void getDataOfAllOfModel(String id, Model model, ICallback callback) { }

    @Override
    public <T> void getModel(String id, Model model, Class<T> clazz, ICallback callback) {
        firestore.collection(model.toString()).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    callback.onCallback(task.getResult().toObject(clazz));
                    Log.w(TAG, "Success retrieving model!");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error retrieving model: ", e);
            }
        });
    }

    @Override
    public <T> void getAllOfModel(String id, Model model, Class<T> clazz, ICallback callback) { }
}