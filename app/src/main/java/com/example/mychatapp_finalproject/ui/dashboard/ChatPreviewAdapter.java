package com.example.mychatapp_finalproject.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mychatapp_finalproject.R;
import com.example.mychatapp_finalproject.model.UserProfile;
import com.example.mychatapp_finalproject.ui.chat.ChatConvoActivity;

import java.util.List;

public class ChatPreviewAdapter extends RecyclerView.Adapter<ChatPreviewAdapter.ViewHolder>{
    private final List<UserProfile> userProfiles;
    private final Context context;

    public ChatPreviewAdapter(List<UserProfile> itemList, Context context) {
        this.userProfiles = itemList;
        this.context = context;
    }

    // inflates the main layout container for views
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_preview_layout, parent, false);
        return new ViewHolder(view);
    }

    // sets the layout views to add them in the holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserProfile userProfile = userProfiles.get(position);
        holder.chatPrevUsername.setText(userProfile.getUsername());
        holder.chatPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Dashboard Fragment","OnItemClick");
                Intent intent = new Intent(context, ChatConvoActivity.class);
                intent.putExtra("CONTACT_ID", userProfile.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userProfiles.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<UserProfile> dataList) {
        userProfiles.clear();
        userProfiles.addAll(dataList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView chatPrevUsername;
        TextView chatPrevMessage;
        ImageView profilePic;
        LinearLayout chatPreview;

        // initializing views to be used in the constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chatPrevUsername = itemView.findViewById(R.id.chat_prev_username);
            chatPrevMessage = itemView.findViewById(R.id.chat_prev_message);
            profilePic = itemView.findViewById(R.id.profile_pic);
            chatPreview = itemView.findViewById(R.id.ChatPreview);
        }
    }
}