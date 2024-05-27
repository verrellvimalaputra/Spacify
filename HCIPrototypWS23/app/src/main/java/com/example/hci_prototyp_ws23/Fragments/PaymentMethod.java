package com.example.hci_prototyp_ws23.Fragments;

import static com.example.hci_prototyp_ws23.Models.Booking.PaymentMethod.stringToEnum;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hci_prototyp_ws23.DatabaseHelper;
import com.example.hci_prototyp_ws23.Models.Booking;
import com.example.hci_prototyp_ws23.Models.Hotel;
import com.example.hci_prototyp_ws23.Models.User;
import com.example.hci_prototyp_ws23.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class PaymentMethod extends Fragment {

   View view;
    BottomNavigationView bottomNavigationView;
    Button roomInfoButton;
    RadioButton paypalRadioButton, debitRadioButton, giropayRadioButton, sepaRadioButton, cashRadioButton;
    Toolbar toolbar;
    User user;
    Hotel hotel;
    String checkInDate, checkOutDate;
    int adultsNumber, childrenNumber, numberOfRooms;
    double totalPrice;
    Booking.PaymentMethod paymentMethod;
    DatabaseHelper databaseHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_payment_method, container, false);
        bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation_bar);
        toolbar = view.findViewById(R.id.paymentMethod_tb);
        databaseHelper = DatabaseHelper.getInstance(getContext());
        roomInfoButton = view.findViewById(R.id.paymentMethod_btn);
        paypalRadioButton = view.findViewById(R.id.payment_paypal_rb);
        debitRadioButton = view.findViewById(R.id.payment_debit_rb);
        giropayRadioButton = view.findViewById(R.id.payment_giropay_rb);
        sepaRadioButton = view.findViewById(R.id.payment_sepa_rb);
        cashRadioButton = view.findViewById(R.id.payment_cash_rb);

        user = PaymentMethodArgs.fromBundle(getArguments()).getUserArg();
        hotel = PaymentMethodArgs.fromBundle(getArguments()).getHotelArg();
        checkInDate = PaymentMethodArgs.fromBundle(getArguments()).getCheckInDateArg();
        checkOutDate = PaymentMethodArgs.fromBundle(getArguments()).getCheckOutDateArg();
        adultsNumber = PaymentMethodArgs.fromBundle(getArguments()).getAdultsNumberArg();
        childrenNumber = PaymentMethodArgs.fromBundle(getArguments()).getChildrenNumberArg();
        numberOfRooms = PaymentMethodArgs.fromBundle(getArguments()).getNumberOfRoomsArg();
        totalPrice = PaymentMethodArgs.fromBundle(getArguments()).getTotalPriceArg();
        checkPaymentMethods();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView.setVisibility(View.GONE);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.inflateMenu(R.menu.top_action_bar_room_information);
        toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(PaymentMethod.this).popBackStack());

        roomInfoButton.setOnClickListener(v -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            if(paypalRadioButton.isChecked()) {
                paymentMethod = Booking.PaymentMethod.PAYPAL;
            } else if (debitRadioButton.isChecked()) {
                paymentMethod = Booking.PaymentMethod.DEBIT;
            } else if (giropayRadioButton.isChecked()) {
                paymentMethod = Booking.PaymentMethod.GIROPAY;
            } else {
                paymentMethod = Booking.PaymentMethod.SEPA;
            }
            try {
                Booking booking = new Booking(user, hotel, numberOfRooms, sdf.parse(checkInDate), sdf.parse(checkOutDate), adultsNumber, childrenNumber, totalPrice, paymentMethod);
                databaseHelper.insertBooking(booking);

                if(databaseHelper.readSavedHotelByHotelNameAndUsername(user, hotel, checkInDate, checkOutDate)) {
                    databaseHelper.deleteSavedHotel(user, hotel, checkInDate, checkOutDate);
                }

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            PaymentMethodDirections.ActionPaymentMethodToBookingConfimation action = PaymentMethodDirections.actionPaymentMethodToBookingConfimation(user.getEmail());
            NavHostFragment.findNavController(PaymentMethod.this).navigate(action);
        });
    }

    public void checkPaymentMethods() {
        for(String p: hotel.getAcceptedPaymentMethods()) {
            if(Objects.equals(stringToEnum(p), Booking.PaymentMethod.PAYPAL)) {
                paypalRadioButton.setClickable(true);
                paypalRadioButton.setAlpha(1);
            } else if(Objects.equals(stringToEnum(p), Booking.PaymentMethod.DEBIT)) {
                debitRadioButton.setClickable(true);
                debitRadioButton.setAlpha(1);
            } else if(Objects.equals(stringToEnum(p), Booking.PaymentMethod.GIROPAY)) {
                giropayRadioButton.setClickable(true);
                giropayRadioButton.setAlpha(1);
            } else if(Objects.equals(stringToEnum(p), Booking.PaymentMethod.SEPA)) {
                sepaRadioButton.setClickable(true);
                sepaRadioButton.setAlpha(1);
            } else {
                cashRadioButton.setClickable(true);
                cashRadioButton.setAlpha(1);
            }
        }
    }
}