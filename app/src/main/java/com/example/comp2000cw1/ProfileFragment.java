package com.example.comp2000cw1;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class ProfileFragment extends Fragment {

    Switch reservationNotifications;;
    Button btn;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        //BACK BUTTON
        View backButton = root.findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).goBack();
        });

        btn = root.findViewById(R.id.signOutButton);
        reservationNotifications = root.findViewById(R.id.reservationNotifications);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("My Prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //CHANGE SWITCH TEXT IF STAFF
        if (sharedPreferences.getBoolean("Is Staff", false)) {
            reservationNotifications.setText("New Reservations");
        } else {
            reservationNotifications.setText("Reservation Changes");
        }

        //SET SWITCH
        if (sharedPreferences.getBoolean("Reservation Changes", false)) {
            reservationNotifications.setChecked(true);
        } else {
            reservationNotifications.setChecked(false);
        }

        //SWITCH LOGIC
        reservationNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("Reservation Changes", isChecked);
            editor.apply();
        });

        //NOTFICIATION LOGIC
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        //LOG OUT BUTTON
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("My Prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("Signed In", false);
                editor.putBoolean("Is Staff", false);
                editor.apply();
            }
        });
        return root;
    }
}