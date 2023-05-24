package com.example.mychatapp_finalproject.ui.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mychatapp_finalproject.R;
import com.example.mychatapp_finalproject.database.ICallback;
import com.example.mychatapp_finalproject.database.IDatabaseHelper;
import com.example.mychatapp_finalproject.database.ServiceLocator;
import com.example.mychatapp_finalproject.model.Model;
import com.example.mychatapp_finalproject.model.UserProfile;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class AddContactDialogFragment extends DialogFragment {
    public static String TAG = "AddContactDialogFragment";
    private final DialogButtonClickListener buttonClickListener;

    public AddContactDialogFragment(DialogButtonClickListener dialogButtonClickListener) {
        this.buttonClickListener = dialogButtonClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_contact, container, false);
        Button cancelBtn = view.findViewById(R.id.button_cancel);
        Button addBtn = view.findViewById(R.id.button_add);
        EditText etContactEmail = view.findViewById(R.id.et_email);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonClickListener != null) {
                    addContact(etContactEmail.getText().toString());
                }
                dismiss();
            }
        });

        return view;
    }

    private void addContact(String email) {
        FirebaseUser loggedUser = ServiceLocator.getInstance().getFirebaseAuth().getCurrentUser();
        IDatabaseHelper databaseHelper = ServiceLocator.getInstance().getDatabase();
        if (loggedUser != null) {
            databaseHelper.getModelWithSameEmail(email, Model.USER_PROFILE, new ICallback() {
                @Override
                public <T> void onCallback(T callback) {
                    UserProfile userProfileWithSameEmail = (UserProfile) callback;
                    String contactId = userProfileWithSameEmail.getId();
                    if (contactId != null) {
                        addContactToContactList(databaseHelper, loggedUser, contactId);
                        buttonClickListener.onButtonClicked(userProfileWithSameEmail);
                    } else {
                        Log.d(TAG, "No user found with email: " + email);
                    }
                }
            });
        } else {
            Log.d(TAG, "No logged-in user found");
        }
    }

    private void addContactToContactList(IDatabaseHelper databaseHelper, FirebaseUser loggedUser, String contactId) {
        databaseHelper.getModel(loggedUser.getUid(), Model.USER_PROFILE, UserProfile.class, new ICallback() {
            @Override
            public <t> void onCallback(t callback) {
                UserProfile currentUserProfile = (UserProfile) callback;
                if (currentUserProfile.getContacts() != null) {
                    if (!currentUserProfile.getContacts().contains(contactId)) {
                        currentUserProfile.getContacts().add(contactId);
                        databaseHelper.update(loggedUser.getUid(), Model.USER_PROFILE, currentUserProfile);
                    } else {
                        Log.d(TAG, "Contact already in contact list");
                    }
                } else {
                    currentUserProfile.setContacts(new ArrayList<>());
                    currentUserProfile.getContacts().add(contactId);
                    databaseHelper.update(loggedUser.getUid(), Model.USER_PROFILE, currentUserProfile);
                }
            }
        });
    }
}
