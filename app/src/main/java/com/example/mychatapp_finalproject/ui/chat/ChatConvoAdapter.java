package com.example.mychatapp_finalproject.ui.chat;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mychatapp_finalproject.R;
import com.example.mychatapp_finalproject.database.ICallback;
import com.example.mychatapp_finalproject.database.IDatabaseHelper;
import com.example.mychatapp_finalproject.database.ServiceLocator;
import com.example.mychatapp_finalproject.model.Model;
import com.example.mychatapp_finalproject.model.UserMessage;
import com.example.mychatapp_finalproject.model.UserProfile;

import java.util.List;

public class ChatConvoAdapter extends RecyclerView.Adapter<ChatConvoAdapter.ViewHolder> {
    private final String TAG = "ChatConvoAdapter";
    private static final int TYPE_RECEIVER = 1;
    private static final int TYPE_SENDER = 2;
    private List<UserMessage> messages;
    private String loggedUser;
    private String contact;
    private int type;

    public ChatConvoAdapter(List<UserMessage> messages, String contact, String loggedUser) {
        this.messages = messages;
        this.contact = contact;
        this.loggedUser = loggedUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == TYPE_SENDER) {
            view = inflater.inflate(R.layout.item_container_sent_message, parent, false);
        } else {
            view = inflater.inflate(R.layout.item_container_received_message, parent, false);
        }
        type = viewType;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserMessage message = messages.get(position);
        IDatabaseHelper databaseHelper = ServiceLocator.getInstance().getDatabase();
        if (type == TYPE_SENDER) {
            databaseHelper.getModel(loggedUser, Model.USER_PROFILE, UserProfile.class, new ICallback() {
                @Override
                public <T> void onCallback(T callback) {
                    UserProfile loggedUser = (UserProfile) callback;
                    holder.usernameTv.setText(loggedUser.getUsername());
                    Log.d(TAG, "Sender username: " + loggedUser.getUsername());
                    // add profile pic
                }
            });
        } else {
            databaseHelper.getModel(contact, Model.USER_PROFILE, UserProfile.class, new ICallback() {
                @Override
                public <T> void onCallback(T callback) {
                    UserProfile contact = (UserProfile) callback;
                    holder.usernameTv.setText(contact.getUsername());
                    Log.d(TAG, "Receiver username: " + contact.getUsername());
                    // add profile pic
                }
            });
        }
        Log.d(TAG, "Message was of type: " + type);
        holder.messageTv.setText(message.getContent());
        holder.dateTimeTv.setText(message.getCreatedAt());
        Log.d(TAG, "Message content posted: " + message.getContent());
        Log.d(TAG, "Message createdAt: " + message.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        UserMessage message = messages.get(position);
        Log.d(TAG, "Item: " + message);
        if (message.getSenderId().equals(loggedUser)) {
            return TYPE_SENDER;
        } else {
            return TYPE_RECEIVER;
        }
    }

    public String getContact() {
        return contact;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<UserMessage> dataList) {
        messages.clear();
        messages.addAll(dataList);
        Log.d(TAG, "Number of messages: " + messages.size());
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTv;
        TextView dateTimeTv;
        TextView messageTv;
        AppCompatImageView profilePic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTv = itemView.findViewById(R.id.username);
            dateTimeTv = itemView.findViewById(R.id.dateTime);
            messageTv = itemView.findViewById(R.id.message);
            profilePic = itemView.findViewById(R.id.profile_pic);
        }
    }
}
