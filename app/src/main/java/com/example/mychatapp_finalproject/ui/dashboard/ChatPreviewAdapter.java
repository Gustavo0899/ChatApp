package com.example.mychatapp_finalproject.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mychatapp_finalproject.R;

import java.util.List;

public class ChatPreviewAdapter extends RecyclerView.Adapter<ChatPreviewAdapter.ViewHolder>{
    private final List<String> textItems;
    private final OnItemClickListener clickListener;

    public ChatPreviewAdapter(List<String> itemList, ChatPreviewAdapter.OnItemClickListener clickListener) {
        this.textItems = itemList;
        this.clickListener = clickListener;
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
        String text = textItems.get(position);
        holder.chatPrevUsername.setText(text);
    }

    @Override
    public int getItemCount() {
        return textItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView chatPrevUsername;
        TextView chatPrevMessage;
        ImageView profilePic;

        // initializing views to be used in the constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chatPrevUsername = itemView.findViewById(R.id.chat_prev_username);
            chatPrevMessage = itemView.findViewById(R.id.chat_prev_message);
            profilePic = itemView.findViewById(R.id.profile_pic);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                String text = textItems.get(position);
                clickListener.onItemClick(text);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String text);
    }
}
