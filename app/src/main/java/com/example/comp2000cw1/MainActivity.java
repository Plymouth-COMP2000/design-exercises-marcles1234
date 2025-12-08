package com.example.comp2000cw1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.comp2000cw1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        SharedPreferences sharedPreferences = getSharedPreferences("My Prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!sharedPreferences.contains("Signed In")) {
            editor.putBoolean("Signed In", false);
            editor.putString("First Name", "");
            editor.putString("Last Name", "");
            editor.putBoolean("Is Staff", false);
            editor.putBoolean("Push Notifications", false);
            editor.putBoolean("Reservation Changes", false);
            editor.putBoolean("Reservation Reminders", false);
            editor.apply();
        }


        binding.navBar.setOnItemSelectedListener( item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.menu) {
                replaceFragment(new MenuFragment());
            } else if (itemId == R.id.reservations) {
                if (!sharedPreferences.getBoolean("Signed In", false)) {
                    replaceFragment(new SignInFragment());
                } else {
                    replaceFragment(new ReservationsFragment());
                }
            } else if (itemId == R.id.info) {
                replaceFragment(new InfoFragment());
            } else if (itemId == R.id.profile) {
                if (!sharedPreferences.getBoolean("Signed In", false)) {
                    replaceFragment(new SignInFragment());
                } else {
                    replaceFragment(new ProfileFragment());
                }
            }

            return true;
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void goBack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        }
    }
}