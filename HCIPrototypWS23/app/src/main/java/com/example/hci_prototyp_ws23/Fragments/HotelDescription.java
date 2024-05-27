package com.example.hci_prototyp_ws23.Fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hci_prototyp_ws23.DatabaseHelper;
import com.example.hci_prototyp_ws23.Models.Hotel;
import com.example.hci_prototyp_ws23.Models.User;
import com.example.hci_prototyp_ws23.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class HotelDescription extends Fragment {
    View view;
    ImageView imageView;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    TextView hotelNameTextView, checkInDateTextView, checkOutDateTextView, roomsAndGuestTextView,
            totalPriceTextView, hotelAddressTextView, hotelAcceptedPayments, descriptionTextView;
    Button seeYourOptionsButton;
    Hotel hotel;
    User user;
    int numberOfRooms, adultsNumber, childrenNumber;
    Date checkInDate = new Date();
    Date checkOutDate = new Date();
    long nights;
    DatabaseHelper databaseHelper;
    MenuItem item;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hotel_description, container, false);
        bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation_bar);
        imageView = view.findViewById(R.id.hotelDescription_iv);
        toolbar = view.findViewById(R.id.hotelDescription_tb);
        hotelNameTextView = view.findViewById(R.id.hotelName_tv);
        checkInDateTextView = view.findViewById(R.id.checkInDate_tv);
        checkOutDateTextView = view.findViewById(R.id.checkOutDate_tv);
        roomsAndGuestTextView = view.findViewById(R.id.roomsAndGuests_tv);
        totalPriceTextView = view.findViewById(R.id.totalPrice_tv);
        hotelAddressTextView = view.findViewById(R.id.hotelAddress_tv);
        descriptionTextView = view.findViewById(R.id.hotelDescription_tv);
        hotelAcceptedPayments = view.findViewById(R.id.hotelPayment_tv);
        seeYourOptionsButton = view.findViewById(R.id.seeYourOptions_btn);
        databaseHelper = DatabaseHelper.getInstance(getContext());
        user = HotelDescriptionArgs.fromBundle(getArguments()).getUserArg();
        hotel = HotelDescriptionArgs.fromBundle(getArguments()).getHotelArg();
        numberOfRooms = HotelDescriptionArgs.fromBundle(getArguments()).getNumberOfRoomsArg();
        adultsNumber = HotelDescriptionArgs.fromBundle(getArguments()).getAdultsNumberArg();
        childrenNumber = HotelDescriptionArgs.fromBundle(getArguments()).getChildrenNumberArg();

        try {
            checkInDate = sdf.parse(HotelDescriptionArgs.fromBundle(getArguments()).getCheckInDate());
            checkOutDate = sdf.parse(HotelDescriptionArgs.fromBundle(getArguments()).getCheckOutDate());
            nights = TimeUnit.MILLISECONDS.toDays(checkOutDate.getTime() - checkInDate.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView.setVisibility(View.GONE);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle(hotel.getHotelName());
        toolbar.inflateMenu(R.menu.top_action_bar_hotel_description);
        item = toolbar.getMenu().getItem(0);

        if(databaseHelper.readSavedHotelByHotelNameAndUsername(user, hotel, sdf.format(checkInDate), sdf.format(checkOutDate))) {
            Drawable icon = ContextCompat.getDrawable(requireContext(), R.drawable.heart_full_blue);
            item.setIcon(icon);
        } else {
            Drawable icon = ContextCompat.getDrawable(requireContext(), R.drawable.heart);
            item.setIcon(icon);
        }

        toolbar.getMenu().getItem(0).setOnMenuItemClickListener(item -> {
            if(databaseHelper.readSavedHotelByHotelNameAndUsername(user, hotel, sdf.format(checkInDate), sdf.format(checkOutDate))) {
                databaseHelper.deleteSavedHotel(user, hotel, sdf.format(checkInDate), sdf.format(checkOutDate));
                Toast.makeText(getContext(), "Removed from your saved list", Toast.LENGTH_SHORT).show();
                Drawable icon = ContextCompat.getDrawable(requireContext(), R.drawable.heart);
                item.setIcon(icon);
            } else {
                databaseHelper.insertSavedHotel(user, hotel, HotelDescriptionArgs.fromBundle(getArguments()).getCheckInDate(), HotelDescriptionArgs.fromBundle(getArguments()).getCheckOutDate(), numberOfRooms, adultsNumber, childrenNumber, nights * hotel.getPricePerNight() * numberOfRooms);
                Toast.makeText(getContext(), "Added to your saved list", Toast.LENGTH_SHORT).show();
                Drawable icon = ContextCompat.getDrawable(requireContext(), R.drawable.heart_full_blue);
                item.setIcon(icon);
            }
            return true;
        });
        toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(HotelDescription.this).popBackStack());
        seeYourOptionsButton.setOnClickListener(v -> {
            HotelDescriptionDirections.ActionHotelDescriptionToUserInfoOverview action = HotelDescriptionDirections.actionHotelDescriptionToUserInfoOverview(user, hotel, sdf.format(checkInDate), sdf.format(checkOutDate), adultsNumber, childrenNumber, numberOfRooms);
            NavHostFragment.findNavController(HotelDescription.this).navigate(action);
            }
        );
        imageView.setImageResource(getResources().getIdentifier(hotel.getImageURL(), "drawable", requireActivity().getPackageName()));
        hotelNameTextView.setText(hotel.getHotelName());
        checkInDateTextView.setText(sdf2.format(checkInDate));
        checkOutDateTextView.setText(sdf2.format(checkOutDate));

        String roomNum = numberOfRooms == 1 ? numberOfRooms + " room for" : numberOfRooms + " rooms for";
        String adultNum = adultsNumber == 1 ? adultsNumber + " adult" : adultsNumber + " adults";
        String night = nights == 1 ? nights + " night" : nights + " nights";
        String childrenNum;
        if(childrenNumber == 1) {
            childrenNum = childrenNumber + " child";
        } else if(childrenNumber == 0) {
            childrenNum = "no children";
        } else {
            childrenNum = childrenNumber + " children";
        }

        roomsAndGuestTextView.setText(roomNum + " " + adultNum + " " + childrenNum);
        totalPriceTextView.setText("Price for " + night + ": " + nights * hotel.getPricePerNight() * numberOfRooms + " €");
        hotelAddressTextView.setText(hotel.getHotelAddress().getStreetAddress() + ", " + hotel.getHotelAddress().getCity() + " " + hotel.getHotelAddress().getPostalCode() + ", " + hotel.getHotelAddress().getCountry());
        hotelAcceptedPayments.setText(hotel.toStringPayment());
        descriptionTextView.setText(hotel.getDescription());
    }
}