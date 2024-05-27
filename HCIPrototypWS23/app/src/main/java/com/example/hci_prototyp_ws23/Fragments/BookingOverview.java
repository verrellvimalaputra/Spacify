package com.example.hci_prototyp_ws23.Fragments;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hci_prototyp_ws23.Models.Hotel;
import com.example.hci_prototyp_ws23.Models.User;
import com.example.hci_prototyp_ws23.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class BookingOverview extends Fragment {
    View view;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    Button roomInfoButton;
    TextView hotelName, hotelAddress, checkIn, checkOut, guests, total;
    ImageView hotelImageView;
    User user;
    Hotel hotel;
    String checkInDate, checkOutDate;
    int adultsNumber, childrenNumber, numberOfRooms;
    double totalPrice;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd. MMM. yyyy", Locale.getDefault());
    long nights;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_booking_overview, container, false);
        bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation_bar);
        toolbar = view.findViewById(R.id.bookingOverview_tb);
        roomInfoButton = view.findViewById(R.id.bookingOverview_btn);
        hotelName = view.findViewById(R.id.bookingOverviewHotelName_tv);
        hotelAddress = view.findViewById(R.id.bookingOverViewHotelAddress_tv);
        checkIn = view.findViewById(R.id.booking_overview_checkIn_date_tv);
        checkOut = view.findViewById(R.id.booking_overview_checkOut_date_tv);
        guests = view.findViewById(R.id.booking_overview_guests_value_tv);
        total = view.findViewById(R.id.booking_overview_total_value_tv);
        hotelImageView = view.findViewById(R.id.bookingOverview_iv);

        user = BookingOverviewArgs.fromBundle(getArguments()).getUserArg();
        hotel = BookingOverviewArgs.fromBundle(getArguments()).getHotelArg();
        checkInDate = BookingOverviewArgs.fromBundle(getArguments()).getCheckInDateArg();
        checkOutDate = BookingOverviewArgs.fromBundle(getArguments()).getCheckOutDateArg();
        adultsNumber = BookingOverviewArgs.fromBundle(getArguments()).getAdultsNumberArg();
        childrenNumber = BookingOverviewArgs.fromBundle(getArguments()).getChildrenNumberArg();
        numberOfRooms = BookingOverviewArgs.fromBundle(getArguments()).getNumberOfRoomsArg();
        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView.setVisibility(View.GONE);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle("Overview");
        toolbar.inflateMenu(R.menu.top_action_bar_room_information);
        toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(BookingOverview.this).popBackStack());

        try {
            nights = TimeUnit.MILLISECONDS.toDays(Objects.requireNonNull(sdf.parse(checkOutDate)).getTime() - Objects.requireNonNull(sdf.parse(checkInDate)).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        totalPrice = numberOfRooms * nights * hotel.getPricePerNight();

        hotelImageView.setImageResource(getResources().getIdentifier(hotel.getImageURL(), "drawable", requireActivity().getPackageName()));
        hotelName.setText(hotel.getHotelName());
        hotelAddress.setText(hotel.getHotelAddress().getStreetAddress() + ", " + hotel.getHotelAddress().getCity() + ", " + hotel.getHotelAddress().getPostalCode() + " " + hotel.getHotelAddress().getCountry());
        try {
            checkIn.setText(sdf2.format(Objects.requireNonNull(sdf.parse(checkInDate))));
            checkOut.setText(sdf2.format(Objects.requireNonNull(sdf.parse(checkOutDate))));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        guests.setText(adultsNumber + " adults and " + childrenNumber + " children");
        total.setText(totalPrice + " €");
        roomInfoButton.setOnClickListener(v -> {
            BookingOverviewDirections.ActionBookingOverviewToPaymentMethod action = BookingOverviewDirections.actionBookingOverviewToPaymentMethod(user, hotel, checkInDate, checkOutDate, adultsNumber, childrenNumber, (float) totalPrice, numberOfRooms);
            NavHostFragment.findNavController(BookingOverview.this).navigate(action);
        });
    }
}