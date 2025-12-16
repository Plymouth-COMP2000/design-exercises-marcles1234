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

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReservationsFragment extends Fragment {

    private ReservationAdapter adapter;
    public static int selectedReservation;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private TextView infotext;

    List<DataModelReservations> reservations;


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
        ImageButton incrementDayBtn = view.findViewById(R.id.incrementDay);
        ImageButton decrementDayBtn = view.findViewById(R.id.decrementDay);
        ImageButton incrementMonthBtn = view.findViewById(R.id.incrementMonth);
        ImageButton decrementMonthBtn = view.findViewById(R.id.decrementMonth);
        infotext = view.findViewById(R.id.infoText);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("My Prefs", Context.MODE_PRIVATE);
        RecyclerView recyclerView = view.findViewById(R.id.reservationsText1);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        //GET TODAYS DATE
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(calendar.getTime());


        //DECREMENT MONTH
        decrementMonthBtn.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, -1);
            updateReservationFrag();
        });


        //DECREMENT DAY
        decrementDayBtn.setOnClickListener(v -> {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            updateReservationFrag();
        });


        //INCREMENT DAY
        incrementDayBtn.setOnClickListener(v -> {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            updateReservationFrag();
        });


        //INCREMENT MONTH
        incrementMonthBtn.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, 1);
            updateReservationFrag();
        });


        //CHANGE LAYOUT IF STAFF MEMBER IS LOGGED IN
        if (sharedPreferences.getBoolean("Is Staff", false)) {
            editReservationBtn.setVisibility(View.GONE);
            removeReservationBtn.setVisibility(View.GONE);
            infotext.setText(formattedDate);
            infotext.setGravity(Gravity.CENTER_HORIZONTAL);
            makeReservationBtn.setText("Remove Reservation");
            decrementDayBtn.setVisibility(View.VISIBLE);
            decrementMonthBtn.setVisibility(View.VISIBLE);
            incrementDayBtn.setVisibility(View.VISIBLE);
            incrementMonthBtn.setVisibility(View.VISIBLE);
        }


        //RESERVATIONS FETCH
        String name = sharedPreferences.getString("First Name", "No name") + " " + sharedPreferences.getString("Last Name", "No name");
        DatabaseHelper DatabaseHelper = new DatabaseHelper(requireContext());
        reservations = DatabaseHelper.getAllReservations(name);
        if (sharedPreferences.getBoolean("Is Staff", false)) {
            reservations = DatabaseHelper.getAllReservationsByDate(infotext.getText().toString());
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
                adapter.setReservations(DatabaseHelper.getAllReservationsByDate(infotext.getText().toString()));
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
                adapter.setReservations(DatabaseHelper.getAllReservationsByDate(infotext.getText().toString()));
                selectedReservation = -1;
            } else {
                addReservationFragment newFragment = new addReservationFragment();
                ((MainActivity) requireActivity()).replaceFragment(newFragment);
            }
        });
    }

    private void updateReservationFrag() {
        String newDate = dateFormat.format(calendar.getTime());
        infotext.setText(newDate);

        DatabaseHelper db = new DatabaseHelper(requireContext());

        if (requireContext()
                .getSharedPreferences("My Prefs", Context.MODE_PRIVATE)
                .getBoolean("Is Staff", false)) {
            adapter.setReservations(db.getAllReservationsByDate(newDate));
        }
    }

}