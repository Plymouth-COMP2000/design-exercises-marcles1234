package com.example.comp2000cw1;

import static com.example.comp2000cw1.ReservationsFragment.selectedReservation;

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

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class editReservationFragment extends Fragment {

    Spinner selectTimeList, selectGuestsList;
    Button confirmEdit;
    CalendarView calendar;
    String selectedDate;
    Boolean dateChanged, timeChanged, guestsChanged;

    public editReservationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_reservation, container, false);

        //BACK BUTTON
        View backButton = root.findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).goBack();
        });

        String text = "";
        TextView reservationDisplay = root.findViewById(R.id.reservationTable);


        //GET RESERVATION
        DatabaseHelper DatabaseHelper = new DatabaseHelper(requireContext());
        List<DataModelReservations> reservations = Collections.singletonList(DatabaseHelper.getReservationByID(selectedReservation));
        for (DataModelReservations reservation : reservations) {
            text += reservation.getReservationName() + "    " + reservation.getReservationDate() + "   " + reservation.getReservationTime() + "  " + reservation.getReservationGuests();
        }
        reservationDisplay.setText(text);


        //TIME SPINNER
        selectTimeList = root.findViewById(R.id.timeEditList);
        String currentTime = DatabaseHelper.getReservationByID(selectedReservation).getReservationTime();
        String[] times = {"17:00", "17:30", "18:00", "18:30", "19:00"};
        for (int i = 0; i < times.length; i++) {
            if (times[i].equals(currentTime)) {
                int temp = i;
                for (int j = 0; j < i; j++) {
                    times[temp] = times[temp-1];
                    temp--;
                }
            }
        }
        times[0] = currentTime;
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.spinner_template,
                times
        );
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectTimeList.setAdapter(timeAdapter);


        //GUEST SPINNER
        selectGuestsList = root.findViewById(R.id.guestEditList);
        String currentGuests = DatabaseHelper.getReservationByID(selectedReservation).getReservationGuests();
        String[] guests = {"1", "2", "3", "4", "5", "6", "7", "8"};
        for (int i = 0; i < guests.length; i++) {
            if (guests[i].equals(currentGuests)) {
                int temp = i;
                for (int j = 0; j < i; j++) {
                    guests[temp] = guests[temp-1];
                    temp--;
                }
            }
        }
        guests[0] = currentGuests;
        ArrayAdapter<String> guestAdapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.spinner_template,
                guests
        );
        guestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectGuestsList.setAdapter(guestAdapter);


        //CALENDAR
        calendar = root.findViewById(R.id.dateEditCalendar);
        selectedDate = DatabaseHelper.getReservationByID(selectedReservation).getReservationDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date date = sdf.parse(selectedDate);
            calendar.setDate(date.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
        });

        //CONFIRM EDIT BUTTON
        confirmEdit = root.findViewById(R.id.confirmEdit);
        confirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UPDATE RESERVATION
                DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
                DataModelReservations originalReservation = DatabaseHelper.getReservationByID(selectedReservation);
                DataModelReservations data = new DataModelReservations(
                        selectedDate,
                        selectTimeList.getSelectedItem().toString(),
                        selectGuestsList.getSelectedItem().toString()
                );
                databaseHelper.updateReservation(selectedReservation, data);

                //NOTIFICATION DISPLAY LOGIC
                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("My Prefs", Context.MODE_PRIVATE);
                if (sharedPreferences.getBoolean("Reservation Changes", false)) {
                    String updates = "The ";
                    if (!originalReservation.getReservationDate().equals(selectedDate)) {
                        dateChanged = true;
                        updates += "date";
                    } else {
                        dateChanged = false;
                    }

                    if (!originalReservation.getReservationTime().equals(selectTimeList.getSelectedItem().toString())) {
                        if (dateChanged) {
                            updates += ", ";
                        }
                        timeChanged = true;
                        updates += "time";
                    } else {
                        timeChanged = false;
                    }

                    if (!originalReservation.getReservationGuests().equals(selectGuestsList.getSelectedItem().toString())) {
                        if (dateChanged || timeChanged) {
                            updates += " and ";
                        } else {
                            guestsChanged = false;
                        }
                        guestsChanged = true;
                        updates += "guests";
                    }
                    updates += " have been updated on your reservation that was for the: ";
                    updates += originalReservation.getReservationDate();

                    ((MainActivity) requireActivity()).makeNotification("Reservation Update", updates);
                }

                ReservationsFragment newFragment = new ReservationsFragment();
                ((MainActivity) requireActivity()).replaceFragment(newFragment);
            }
        });
        return root;
    }
}