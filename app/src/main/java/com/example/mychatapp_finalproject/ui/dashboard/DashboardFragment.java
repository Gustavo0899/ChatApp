package com.example.mychatapp_finalproject.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mychatapp_finalproject.R;
import com.example.mychatapp_finalproject.databinding.FragmentDashboardBinding;
import com.example.mychatapp_finalproject.ui.dialog.AddContactDialogFragment;
import com.example.mychatapp_finalproject.ui.dialog.DialogButtonClickListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment implements DialogButtonClickListener {
    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private ChatPreviewAdapter adapter;
    private List<String> textItems;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        recyclerView = view.findViewById(R.id.chats_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding.addContactFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showAddContactDialog();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textItems = new ArrayList<>();
        adapter = new ChatPreviewAdapter(textItems, getContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onButtonClicked() {
        // get the necessary data and add it to the string list
        textItems.add(0, "User1");

        // notify the adapter that a new item has been added
        adapter.notifyItemInserted(0);

        // scroll to the top of the recyclerView to show added item
        recyclerView.scrollToPosition(0);
    }

    private void showAddContactDialog() {
        AddContactDialogFragment dialogFragment = new AddContactDialogFragment(DashboardFragment.this);
        dialogFragment.show(getActivity().getSupportFragmentManager(), AddContactDialogFragment.TAG);
    }
}