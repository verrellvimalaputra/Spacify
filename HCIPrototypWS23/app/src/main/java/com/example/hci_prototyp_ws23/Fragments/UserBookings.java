package com.example.hci_prototyp_ws23.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hci_prototyp_ws23.Adapters.UserBookingsAdapter;
import com.example.hci_prototyp_ws23.DatabaseHelper;
import com.example.hci_prototyp_ws23.Models.Booking;
import com.example.hci_prototyp_ws23.Models.User;
import com.example.hci_prototyp_ws23.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class UserBookings extends Fragment {
    View view;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    User currentUser;
    FirebaseAuth mAuth;
    FirebaseUser user;
    ArrayList<Booking> bookings = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_your_bookings, container, false);
        bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation_bar);
        toolbar = view.findViewById(R.id.userBookings_tb);
        recyclerView = view.findViewById(R.id.userBookings_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        databaseHelper = DatabaseHelper.getInstance(getContext());
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        currentUser = databaseHelper.readUserBy("email", user.getEmail());
        bookings = databaseHelper.readBookingByUsername(currentUser.getUsername());
        UserBookingsAdapter userBookingsAdapter = new UserBookingsAdapter(bookings);
        recyclerView.setAdapter(userBookingsAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        bottomNavigationView.getMenu().getItem(0).setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.home));
        bottomNavigationView.getMenu().getItem(1).setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.heart));
        bottomNavigationView.getMenu().getItem(3).setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.profile));
        bottomNavigationView.getMenu().getItem(2).setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.bed_blue_black_tint));
        toolbar.setVisibility(View.VISIBLE);
        toolbar.inflateMenu(R.menu.top_action_bar_user_bookings);
    }
}