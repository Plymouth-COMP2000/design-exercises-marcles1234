package com.example.comp2000cw1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ReservationsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button makeReservationBtn = view.findViewById(R.id.addReservation);
        Button editReservationBtn = view.findViewById(R.id.editReservation);

        makeReservationBtn.setOnClickListener(v -> {
            addReservationFragment newFragment = new addReservationFragment();

            ((MainActivity) requireActivity()).replaceFragment(newFragment);
        });

        editReservationBtn.setOnClickListener(v -> {
            editReservationFragment newFragment = new editReservationFragment();

            ((MainActivity) requireActivity()).replaceFragment(newFragment);
        });
    }

}