package com.example.hci_prototyp_ws23.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hci_prototyp_ws23.DatabaseHelper;
import com.example.hci_prototyp_ws23.Models.User;
import com.example.hci_prototyp_ws23.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Homepage extends Fragment {
    View view;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    TextView topRatedTextView, bestDealsTextView;
    LinearLayout topRatedLayout, bestDealsLayout;
    Button allButton, topRatedButton, bestDealsButton, nearbyButton;
    DatabaseHelper databaseHelper;
    User currentUser;
    FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_homepage, container, false);
        toolbar = view.findViewById(R.id.homepage_tb);
        bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation_bar);
        allButton = view.findViewById(R.id.homepage_all_btn);
        topRatedButton = view.findViewById(R.id.homepage_top_rated_btn);
        bestDealsButton = view.findViewById(R.id.homepage_best_deals_btn);
        nearbyButton = view.findViewById(R.id.homepage_nearby_btn);
        topRatedTextView = view.findViewById(R.id.homepage_top_rated_tv);
        bestDealsTextView = view.findViewById(R.id.homepage_best_deals_tv);
        topRatedLayout = view.findViewById(R.id.homepage_top_rated_ll);
        bestDealsLayout = view.findViewById(R.id.homepage_best_deals_ll);
        databaseHelper = DatabaseHelper.getInstance(getContext());
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        bottomNavigationView.getMenu().getItem(1).setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.heart));
        bottomNavigationView.getMenu().getItem(2).setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.bed));
        bottomNavigationView.getMenu().getItem(3).setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.profile));
        bottomNavigationView.getMenu().getItem(0).setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.home_blue_black_tint));
        currentUser = databaseHelper.readUserBy("email", user.getEmail());
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle("Hi " + currentUser.getUsername() + " !");
        toolbar.getMenu().getItem(0).setOnMenuItemClickListener(item -> {
            HomepageDirections.ActionHomepageToSearch action = HomepageDirections.actionHomepageToSearch(currentUser);
            NavHostFragment.findNavController(Homepage.this).navigate(action);
            return true;
        });

        allButton.setOnClickListener(v -> {
            topRatedButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_button_white_background));
            topRatedButton.setTextColor(Color.BLACK);
            allButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_button_background));
            allButton.setTextColor(Color.WHITE);
            bestDealsButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_button_white_background));
            bestDealsButton.setTextColor(Color.BLACK);
            nearbyButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_button_white_background));
            nearbyButton.setTextColor(Color.BLACK);
            topRatedTextView.setVisibility(View.VISIBLE);
            topRatedLayout.setVisibility(View.VISIBLE);
            bestDealsTextView.setVisibility(View.VISIBLE);
            bestDealsLayout.setVisibility(View.VISIBLE);
        });

        topRatedButton.setOnClickListener(v -> {
            topRatedButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_button_background));
            topRatedButton.setTextColor(Color.WHITE);
            allButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_button_white_background));
            allButton.setTextColor(Color.BLACK);
            bestDealsButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_button_white_background));
            bestDealsButton.setTextColor(Color.BLACK);
            nearbyButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_button_white_background));
            nearbyButton.setTextColor(Color.BLACK);
            bestDealsTextView.setVisibility(View.GONE);
            bestDealsLayout.setVisibility(View.GONE);
            topRatedTextView.setVisibility(View.VISIBLE);
            topRatedLayout.setVisibility(View.VISIBLE);
        });

        bestDealsButton.setOnClickListener(v -> {
            topRatedButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_button_white_background));
            topRatedButton.setTextColor(Color.BLACK);
            allButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_button_white_background));
            allButton.setTextColor(Color.BLACK);
            bestDealsButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_button_background));
            bestDealsButton.setTextColor(Color.WHITE);
            nearbyButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_button_white_background));
            nearbyButton.setTextColor(Color.BLACK);
            topRatedTextView.setVisibility(View.GONE);
            topRatedLayout.setVisibility(View.GONE);
            bestDealsTextView.setVisibility(View.VISIBLE);
            bestDealsLayout.setVisibility(View.VISIBLE);
        });

        nearbyButton.setOnClickListener(v -> {
            topRatedButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_button_white_background));
            topRatedButton.setTextColor(Color.BLACK);
            allButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_button_white_background));
            allButton.setTextColor(Color.BLACK);
            bestDealsButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_button_white_background));
            bestDealsButton.setTextColor(Color.BLACK);
            nearbyButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_button_background));
            nearbyButton.setTextColor(Color.WHITE);
            topRatedTextView.setVisibility(View.VISIBLE);
            topRatedLayout.setVisibility(View.VISIBLE);
            bestDealsTextView.setVisibility(View.VISIBLE);
            bestDealsLayout.setVisibility(View.VISIBLE);
        });

    }
}