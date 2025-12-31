package com.example.comp2000cw1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InfoFragment extends Fragment {
    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_info, container, false);

        //BACK BUTTON
        View backButton = root.findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).goBack();
        });
        return root;
    }
}