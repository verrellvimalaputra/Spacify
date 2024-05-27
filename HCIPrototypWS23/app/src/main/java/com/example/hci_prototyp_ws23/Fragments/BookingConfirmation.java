package com.example.hci_prototyp_ws23.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hci_prototyp_ws23.R;

public class BookingConfirmation extends Fragment {
    View view;
    Button bookingConfirmButton;
    String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_booking_confimation, container, false);
        bookingConfirmButton = view.findViewById(R.id.bookingConfirm_btn);
        email = BookingConfirmationArgs.fromBundle(getArguments()).getEmailArg();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bookingConfirmButton.setOnClickListener(v -> NavHostFragment.findNavController(BookingConfirmation.this).navigate(R.id.action_bookingConfimation_to_homepage));
    }
}