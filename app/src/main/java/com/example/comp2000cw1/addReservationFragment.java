package com.example.comp2000cw1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class addReservationFragment extends Fragment {

    Spinner selectTimeList, selectGuestsList;
    CalendarView calendar;
    Button reserveButton;
    String selectedDate;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public addReservationFragment() {
        // Required empty public constructor
    }

    public static addReservationFragment newInstance(String param1, String param2) {
        addReservationFragment fragment = new addReservationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_reservation, container, false);
        View backButton = root.findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).goBack();
        });

        selectTimeList = root.findViewById(R.id.selectTimeList);
        String[] times = {"17:00", "17:30", "18:00", "18:30", "19:00"};
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.spinner_template,
                times
        );
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectTimeList.setAdapter(timeAdapter);

        selectGuestsList = root.findViewById(R.id.selectGuestsList);
        String[] guests = {"1", "2", "3", "4", "5", "6", "7", "8"};
        ArrayAdapter<String> guestAdapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.spinner_template,
                guests
        );
        guestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectGuestsList.setAdapter(guestAdapter);

        calendar = root.findViewById(R.id.calendar);
        reserveButton = root.findViewById(R.id.reserveButton);
        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
        });
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("My Prefs", Context.MODE_PRIVATE);
        String fullName = sharedPreferences.getString("First Name", "") + " " + sharedPreferences.getString("Last Name", "");
        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataModelReservations data = new DataModelReservations(
                        fullName,
                        selectedDate,
                        selectTimeList.getSelectedItem().toString(),
                        selectGuestsList.getSelectedItem().toString()

                );
                DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
                long success = databaseHelper.addReservation(data);
                if(success != -1) {
                    Toast.makeText(requireContext(), "Reservation added successfully", Toast.LENGTH_SHORT).show();
                    sharedPreferences.edit().putBoolean("New Reservation", true).apply();
                    sharedPreferences.edit().putInt("No. Reservations", sharedPreferences.getInt("No. Reservations", 0) + 1).apply();
                    ReservationsFragment newFragment = new ReservationsFragment();
                    ((MainActivity) requireActivity()).replaceFragment(newFragment);
                } else {
                    Toast.makeText(requireContext(), "Error adding reservation", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }
}