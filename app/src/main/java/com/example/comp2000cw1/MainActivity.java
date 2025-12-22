package com.example.comp2000cw1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.comp2000cw1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    //SET VIEW AND INITIALISE HOME FRAGMENT
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());


        //SHARED PREFERENCES
        SharedPreferences sharedPreferences = getSharedPreferences("My Prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!sharedPreferences.contains("New Reservation")) {
            editor.putBoolean("Signed In", false);
            editor.putString("First Name", "");
            editor.putString("Last Name", "");
            editor.putBoolean("Is Staff", false);
            editor.putBoolean("Push Notifications", false);
            editor.putBoolean("Reservation Changes", false);
            editor.putBoolean("New Reservation", false);
            editor.putInt("No. Reservations", 0);
            editor.apply();
        }

        //NAVIGATION BAR LOGIC
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

    //FUNCTION TO SWAP FRAGMENTS
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //FUNCTION FOR BACK BUTTON
    public void goBack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        }
    }

    //FUNCTION TO CALL NOTIFICATIONS
    public void makeNotification(String title, String text){
        String chanelID = "my_channel";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                this, chanelID);
        builder.setSmallIcon(R.drawable.notifications_24)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(chanelID);

            if (notificationChannel == null) {
                int importance = NotificationManager.IMPORTANCE_HIGH;

                notificationChannel = new NotificationChannel(
                        chanelID, "My Notification", importance);

                notificationChannel.setLightColor(Color.BLUE);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        notificationManager.notify(0, builder.build());
    }
}