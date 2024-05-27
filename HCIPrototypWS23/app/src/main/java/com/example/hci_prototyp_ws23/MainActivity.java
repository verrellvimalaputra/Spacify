package com.example.hci_prototyp_ws23;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setActiveIndicatorLabelPadding(5);
        bottomNavigationView.setItemActiveIndicatorHeight(150);
        bottomNavigationView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setItemActiveIndicatorColor(new ColorStateList(
                new int[][]{
                        new int[] {android.R.attr.state_checked}
                },
                new int[] {ContextCompat.getColor(getApplicationContext(), R.color.white)}));
        bottomNavigationView.setItemPaddingBottom(20);
        bottomNavigationView.setItemIconSize(110);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int menuItemId = item.getItemId();
            //navigateFromMenuItem(homepage);
            if (menuItemId == R.id.user_profile_item) {
                NavHostFragment.findNavController(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment))).navigate(R.id.userProfile);
                return true;
            } else if(menuItemId == R.id.home_item) {
                NavHostFragment.findNavController(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment))).navigate(R.id.homepage);
                return true;
            } else if(menuItemId == R.id.saved_item) {
                NavHostFragment.findNavController(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment))).navigate(R.id.savedList);
                return true;
            } else if(menuItemId == R.id.user_bookings_item) {
                NavHostFragment.findNavController(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment))).navigate(R.id.userBookings);
                return true;
            }
            return true;
        });
        bottomNavigationView.setOnItemReselectedListener(item -> {});
    }

}