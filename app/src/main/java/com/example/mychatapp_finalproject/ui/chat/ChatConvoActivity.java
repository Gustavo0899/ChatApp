package com.example.mychatapp_finalproject.ui.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mychatapp_finalproject.MainActivity;
import com.example.mychatapp_finalproject.database.ICallback;
import com.example.mychatapp_finalproject.database.IDatabaseHelper;
import com.example.mychatapp_finalproject.database.ServiceLocator;
import com.example.mychatapp_finalproject.databinding.ActivityChatConvoBinding;
import com.example.mychatapp_finalproject.model.Model;
import com.example.mychatapp_finalproject.model.UserMessage;
import com.example.mychatapp_finalproject.model.UserProfile;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ChatConvoActivity extends AppCompatActivity {
    private final String TAG = "ChatConvoActivity";
    private RecyclerView recyclerView;
    private ChatConvoAdapter adapter;
    private List<UserMessage> messages;
    ActivityChatConvoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatConvoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String contactId = intent.getStringExtra("CONTACT_ID");
        loadContactDetails(contactId);

        FirebaseUser user = ServiceLocator.getInstance().getFirebaseAuth().getCurrentUser();

        if (user != null) {
            messages = new ArrayList<>();
            adapter = new ChatConvoAdapter(messages, contactId, user.getUid());
            recyclerView = binding.chatRecyclerView;
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }

        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToDashboard = new Intent(ChatConvoActivity.this, MainActivity.class);
                startActivity(intentToDashboard);
                Log.d(TAG, "toMainActivity");
            }
        });

        binding.imageSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    String content = binding.inputMessage.getText().toString();
                    if (!content.trim().equals("")) {
                        Log.d(TAG, "Message to send: " + content);
                        sendMessage(content, user, contactId);
                        binding.inputMessage.setText("");
                    }
                }
            }
        });

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadContactDetails(String contactId) {
        IDatabaseHelper databaseHelper = ServiceLocator.getInstance().getDatabase();
        databaseHelper.getModel(contactId, Model.USER_PROFILE, UserProfile.class, new ICallback() {
            @Override
            public <T> void onCallback(T callback) {
                UserProfile contact = (UserProfile) callback;
                binding.NameText.setText(contact.getUsername());
                // set profile pic
            }
        });

    }

    private void loadData() {
        FirebaseUser loggedUser = ServiceLocator.getInstance().getFirebaseAuth().getCurrentUser();
        IDatabaseHelper databaseHelper = ServiceLocator.getInstance().getDatabase();
        if (loggedUser != null) {
            ArrayList<String> modelsId = new ArrayList<>();
            modelsId.add(loggedUser.getUid());
            modelsId.add(adapter.getContact());
            Log.d(TAG, "UserId: " + loggedUser.getUid());
            Log.d(TAG, "ContactId: " + adapter.getContact());
            databaseHelper.getAllMessagesOfChat(modelsId, Model.USER_MESSAGE, new ICallback() {
                @Override
                public <T> void onCallback(T callback) {
                    List<UserMessage> messages = (List<UserMessage>) callback;
                    adapter.updateData(messages);
                }
            });
        } else {
            Log.d(TAG, "No logged-in user found");
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void sendMessage(String content, @NonNull FirebaseUser user, String contactId) {
        IDatabaseHelper databaseHelper = ServiceLocator.getInstance().getDatabase();
        String id = UUID.randomUUID().toString();
        UserMessage message = ServiceLocator.getInstance().getNewUserMessage();
        message.setId(id);
        message.setCreatedAt(new Timestamp(new Date()).toDate().toString());
        message.setContent(content);
        message.setSenderId(user.getUid());
        message.setReceiverId(contactId);
        databaseHelper.create(id, Model.USER_MESSAGE, message);
        Log.d(TAG, "UserMessage send");
        loadData();
    }
}