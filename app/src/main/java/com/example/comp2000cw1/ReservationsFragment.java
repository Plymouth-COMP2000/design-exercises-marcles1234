package com.example.comp2000cw1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ReservationsFragment extends Fragment {

    private ReservationAdapter adapter;
    public static int selectedReservation;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // BACK BUTTON
        View root = inflater.inflate(R.layout.fragment_reservations, container, false);
        View backButton = root.findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).goBack();
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button makeReservationBtn = view.findViewById(R.id.addReservation);
        Button editReservationBtn = view.findViewById(R.id.editReservation);
        Button removeReservationBtn = view.findViewById(R.id.removeReservation);

        RecyclerView recyclerView = view.findViewById(R.id.reservationsText1);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("My Prefs", Context.MODE_PRIVATE);


        //CHANGE LAYOUT IF STAFF MEMBER IS LOGGED IN
        if (sharedPreferences.getBoolean("Is Staff", false)) {
            editReservationBtn.setVisibility(View.GONE);
            removeReservationBtn.setVisibility(View.GONE);
            makeReservationBtn.setText("Remove Reservation");
        }


        //RESERVATIONS FETCH
        String name = sharedPreferences.getString("First Name", "No name") + " " + sharedPreferences.getString("Last Name", "No name");
        DatabaseHelper DatabaseHelper = new DatabaseHelper(requireContext());
        List<DataModelReservations> reservations = DatabaseHelper.getAllReservations(name);
        if (sharedPreferences.getBoolean("Is Staff", false)) {
            reservations = DatabaseHelper.getAllReservations();
        }


        //RESERVATION DISPLAY AND ID FETCH
        adapter = new ReservationAdapter(reservations, new ReservationAdapter.OnItemClickListener() {@Override
            public void onItemClick(DataModelReservations reservation) {
                selectedReservation = reservation.getReservationId();
            }
        });
        recyclerView.setAdapter(adapter);


        //USER RESERVATION DELETE
        removeReservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.deleteReservation(selectedReservation);
                Toast.makeText(requireContext(), "Reservation deleted successfully", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                selectedReservation = -1;
            }
        });


        //DIRECT USER TO EDIT RESERVATION
        editReservationBtn.setOnClickListener(v -> {
            editReservationFragment newFragment = new editReservationFragment();

            ((MainActivity) requireActivity()).replaceFragment(newFragment);
        });


        //DIRECT USER TO CREATE RESERVATION OR DELETE RESERVATION FOR STAFF
        makeReservationBtn.setOnClickListener(v -> {
            if (sharedPreferences.getBoolean("Is Staff", false)) {
                DatabaseHelper.deleteReservation(selectedReservation);
                Toast.makeText(requireContext(), "Reservation deleted successfully", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                selectedReservation = -1;
            } else {
                addReservationFragment newFragment = new addReservationFragment();
                ((MainActivity) requireActivity()).replaceFragment(newFragment);
            }
        });
    }

}