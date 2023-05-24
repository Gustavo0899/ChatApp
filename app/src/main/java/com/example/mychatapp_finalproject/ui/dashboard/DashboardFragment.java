package com.example.mychatapp_finalproject.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mychatapp_finalproject.R;
import com.example.mychatapp_finalproject.database.ICallback;
import com.example.mychatapp_finalproject.database.IDatabaseHelper;
import com.example.mychatapp_finalproject.database.ServiceLocator;
import com.example.mychatapp_finalproject.databinding.FragmentDashboardBinding;
import com.example.mychatapp_finalproject.model.Model;
import com.example.mychatapp_finalproject.model.UserProfile;
import com.example.mychatapp_finalproject.ui.dialog.AddContactDialogFragment;
import com.example.mychatapp_finalproject.ui.dialog.DialogButtonClickListener;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment implements DialogButtonClickListener {
    private final String TAG = "DashboardFragment";
    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private ChatPreviewAdapter adapter;
    private List<UserProfile> userProfiles;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        recyclerView = view.findViewById(R.id.chats_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.addContactFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddContactDialog();
            }
        });

        loadData();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userProfiles = new ArrayList<>();
        adapter = new ChatPreviewAdapter(userProfiles, getContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public <T> void onButtonClicked(T userProfile) {
        // get the necessary data and add it to the string list
        userProfiles.add(0, (UserProfile) userProfile);
        showContactsList();

        // notify the adapter that a new item has been added
        adapter.notifyItemInserted(0);

        // scroll to the top of the recyclerView to show added item
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void showAddContactDialog() {
        AddContactDialogFragment dialogFragment = new AddContactDialogFragment(DashboardFragment.this);
        dialogFragment.show(getActivity().getSupportFragmentManager(), AddContactDialogFragment.TAG);
    }

    private void loadData() {
        FirebaseUser loggedUser = ServiceLocator.getInstance().getFirebaseAuth().getCurrentUser();
        IDatabaseHelper databaseHelper = ServiceLocator.getInstance().getDatabase();
        if (loggedUser != null) {
            databaseHelper.getModel(loggedUser.getUid(), Model.USER_PROFILE, UserProfile.class, new ICallback() {
                @Override
                public <T> void onCallback(T callback) {
                    UserProfile userProfile = (UserProfile) callback;
                    if (userProfile.getContacts() != null) {
                        if (!userProfile.getContacts().isEmpty()) {
                            loadAllContactsOfUser(databaseHelper, userProfile);
                            showContactsList();
                        } else {
                            showNoContactsToShowText();
                        }
                    } else {
                       showNoContactsToShowText();
                    }
                }
            });
        } else {
            Log.d(TAG, "No logged-in user found");
        }
    }

    private void loadAllContactsOfUser(@NonNull IDatabaseHelper databaseHelper, @NonNull UserProfile userProfile) {
        databaseHelper.getAllContactsOfUser(userProfile.getContacts(), Model.USER_PROFILE, new ICallback() {
            @Override
            public <t> void onCallback(t callback) {
                List<UserProfile> contacts = (List<UserProfile>) callback;
                adapter.updateData(contacts);
            }
        });
    }

    private void showNoContactsToShowText() {
        binding.noContactsToShowTv.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void showContactsList() {
        binding.noContactsToShowTv.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}