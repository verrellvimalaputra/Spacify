package com.example.hci_prototyp_ws23.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.hci_prototyp_ws23.Models.Hotel;
import com.example.hci_prototyp_ws23.Models.User;
import com.example.hci_prototyp_ws23.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserInfoOverview extends Fragment {
    View view;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    Button userInfoButton;
    EditText firstName, lastName, email, streetAddress, city, country, mobileNumber;
    User user;
    Hotel hotel;
    String checkInDate, checkOutDate;
    int adultsNumber, childrenNumber, numberOfRooms;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_info_overview, container, false);
        bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation_bar);
        toolbar = view.findViewById(R.id.userInfoOverview_tb);
        userInfoButton = view.findViewById(R.id.userInfoOverview_btn);
        firstName = view.findViewById(R.id.user_overview_first_name_et);
        lastName = view.findViewById(R.id.user_overview_last_name_et);
        email = view.findViewById(R.id.user_overview_email_et);
        streetAddress = view.findViewById(R.id.user_overview_street_address_et);
        city = view.findViewById(R.id.user_overview_city_et);
        country = view.findViewById(R.id.user_overview_country_et);
        mobileNumber = view.findViewById(R.id.user_overview_mobile_number);

        user = UserInfoOverviewArgs.fromBundle(getArguments()).getUserArg();
        hotel = UserInfoOverviewArgs.fromBundle(getArguments()).getHotelArg();
        checkInDate = UserInfoOverviewArgs.fromBundle(getArguments()).getCheckInDateArg();
        checkOutDate = UserInfoOverviewArgs.fromBundle(getArguments()).getCheckOutDateArg();
        adultsNumber = UserInfoOverviewArgs.fromBundle(getArguments()).getAdultsNumberArg();
        childrenNumber = UserInfoOverviewArgs.fromBundle(getArguments()).getChildrenNumberArg();
        numberOfRooms = UserInfoOverviewArgs.fromBundle(getArguments()).getNumberOfRoomsArg();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle("Your information");
        toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(UserInfoOverview.this).popBackStack());
        userInfoButton.setOnClickListener(v -> {
            UserInfoOverviewDirections.ActionUserInfoOverviewToBookingOverview action = UserInfoOverviewDirections.actionUserInfoOverviewToBookingOverview(user, hotel,  checkInDate, checkOutDate, adultsNumber, childrenNumber, numberOfRooms);
            NavHostFragment.findNavController(UserInfoOverview.this).navigate(action);
        });
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());
        streetAddress.setText(user.getUserAddress().getStreetAddress());
        city.setText(user.getUserAddress().getCity());
        country.setText(user.getUserAddress().getCountry());
        mobileNumber.setText(user.getPhoneNumber());
    }
}