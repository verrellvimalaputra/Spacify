package com.example.hci_prototyp_ws23.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hci_prototyp_ws23.Adapters.SavedListAdapter;
import com.example.hci_prototyp_ws23.DatabaseHelper;
import com.example.hci_prototyp_ws23.Models.Hotel;
import com.example.hci_prototyp_ws23.Models.SavedHotel;
import com.example.hci_prototyp_ws23.Models.User;
import com.example.hci_prototyp_ws23.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.List;

public class SavedList extends Fragment {
    View view;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<SavedHotel> hotels;
    DatabaseHelper databaseHelper;
    User currentUser;
    FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_saved_list, container, false);
        bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation_bar);
        toolbar = view.findViewById(R.id.savedList_tb);
        databaseHelper = DatabaseHelper.getInstance(getContext());
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        recyclerView = view.findViewById(R.id.savedList_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        bottomNavigationView.getMenu().getItem(0).setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.home));
        bottomNavigationView.getMenu().getItem(2).setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.bed));
        bottomNavigationView.getMenu().getItem(3).setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.profile));
        bottomNavigationView.getMenu().getItem(1).setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.heart_full_blue));
        toolbar.setVisibility(View.VISIBLE);
        toolbar.inflateMenu(R.menu.top_action_bar_saved);
        currentUser = databaseHelper.readUserBy("email", user.getEmail());
        hotels = databaseHelper.readSavedHotelByUsername(currentUser.getUsername()); // Query from database

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SavedListAdapter savedListAdapter = new SavedListAdapter(hotels);
        recyclerView.setAdapter(savedListAdapter);
        savedListAdapter.setOnClickListener((position, hotel) -> {
            Hotel hotelParam = databaseHelper.readHotelBy("hotel_name", hotel.getHotelName());
            SavedListDirections.ActionSavedListToHotelDescription action = SavedListDirections.actionSavedListToHotelDescription(currentUser, hotelParam, hotel.getAdultNumber(), hotel.getChildrenNumber(), sdf.format(hotel.getCheckInDate()), sdf.format(hotel.getCheckOutDate()), hotel.getNumberOfRoom());
            NavHostFragment.findNavController(SavedList.this).navigate(action);
        });
    }
}