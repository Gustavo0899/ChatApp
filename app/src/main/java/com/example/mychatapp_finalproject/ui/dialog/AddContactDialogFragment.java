package com.example.mychatapp_finalproject.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mychatapp_finalproject.R;

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
                    buttonClickListener.onButtonClicked();
                }
                dismiss();
            }
        });

        return view;
    }
}
