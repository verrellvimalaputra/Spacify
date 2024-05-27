package com.example.hci_prototyp_ws23.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hci_prototyp_ws23.Models.User;
import com.example.hci_prototyp_ws23.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Search extends Fragment {
    View view;
    BottomNavigationView bottomNavigationView;
    Button search_button, apply_button;
    TextView date_textView, guestTextView, roomNumberTextView, adultNumberTextView, childrenNumberTextView;
    Toolbar toolbar;
    EditText destinationEditText;
    User user;
    String destination;
    int numberOfRooms, adultNumber, childrenNumber;
    String checkInDate, checkOutDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation_bar);
        toolbar = view.findViewById(R.id.search_tb);
        search_button = view.findViewById(R.id.search_btn);
        date_textView = view.findViewById(R.id.date_edt);
        destinationEditText = view.findViewById(R.id.destination_edt);
        guestTextView = view.findViewById(R.id.guest_tv);

        user = SearchArgs.fromBundle(getArguments()).getUserArg();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView.setVisibility(View.GONE);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(Search.this).popBackStack());

        guestTextView.setOnClickListener(v -> showDialog());

        search_button.setOnClickListener(v -> {
            destination = destinationEditText.getText().toString();
            if(roomNumberTextView == null || adultNumberTextView == null || childrenNumberTextView == null || date_textView.getText().toString().isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            SearchDirections.ActionSearchToSearchResultList action = SearchDirections.actionSearchToSearchResultList(destination, numberOfRooms, adultNumber, childrenNumber, user, checkInDate, checkOutDate);
                NavHostFragment.findNavController(Search.this).navigate(action);
        });

        date_textView.setOnClickListener(v -> {
            MaterialDatePicker.Builder<Pair<Long, Long>> materialDatePickerBuilder = MaterialDatePicker.Builder.dateRangePicker();
            materialDatePickerBuilder.setTitleText("Select a date range");
            MaterialDatePicker<Pair<Long, Long>> datePicker = materialDatePickerBuilder.build();
            datePicker.addOnPositiveButtonClickListener(selection -> {
                Long startDate = selection.first;
                Long endDate = selection.second;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                checkInDate = sdf.format(new Date(startDate));
                checkOutDate = sdf.format(new Date(endDate));
                String selectedDateRange = sdf2.format(new Date(startDate)) + " - " + sdf2.format(new Date(endDate));

                date_textView.setText(selectedDateRange);
            });

            datePicker.show(requireActivity().getSupportFragmentManager(), "Date");
        });
    }

    @SuppressLint("SetTextI18n")
    private void showDialog() {
        TextView minusRoom, plusRoom, minusAdult, plusAdult, minusChildren, plusChildren;
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        roomNumberTextView = dialog.findViewById(R.id.roomNumber_tv);
        adultNumberTextView = dialog.findViewById(R.id.adultNumber_tv);
        childrenNumberTextView = dialog.findViewById(R.id.childrenNumber_tv);
        minusRoom = dialog.findViewById(R.id.minus_room_tv);
        plusRoom = dialog.findViewById(R.id.plus_room_tv);
        minusAdult = dialog.findViewById(R.id.minus_adult_tv);
        plusAdult = dialog.findViewById(R.id.plus_adult_tv);
        minusChildren = dialog.findViewById(R.id.minus_children_tv);
        plusChildren = dialog.findViewById(R.id.plus_children_tv);
        apply_button = dialog.findViewById(R.id.apply_btn);

        minusRoom.setOnClickListener(v -> {
            if (Integer.parseInt(roomNumberTextView.getText().toString()) > 1) {
                roomNumberTextView.setText(String.valueOf(Integer.parseInt(roomNumberTextView.getText().toString()) - 1));
            }
        });

        plusRoom.setOnClickListener(v -> roomNumberTextView.setText(String.valueOf(Integer.parseInt(roomNumberTextView.getText().toString())+1)));

        minusAdult.setOnClickListener(v -> {
            if(Integer.parseInt(adultNumberTextView.getText().toString()) > 1) {
                adultNumberTextView.setText(String.valueOf(Integer.parseInt(adultNumberTextView.getText().toString()) - 1));
            }
        });

        plusAdult.setOnClickListener(v -> adultNumberTextView.setText(String.valueOf(Integer.parseInt(adultNumberTextView.getText().toString())+1)));

        minusChildren.setOnClickListener(v -> {
            if(Integer.parseInt(childrenNumberTextView.getText().toString()) > 0) {
                childrenNumberTextView.setText(String.valueOf(Integer.parseInt(childrenNumberTextView.getText().toString()) - 1));
            }
        });

        plusChildren.setOnClickListener(v -> childrenNumberTextView.setText(String.valueOf(Integer.parseInt(childrenNumberTextView.getText().toString()) + 1)));


        apply_button.setOnClickListener(v -> {
            numberOfRooms = Integer.parseInt(roomNumberTextView.getText().toString());
            adultNumber = Integer.parseInt(adultNumberTextView.getText().toString());
            childrenNumber = Integer.parseInt(childrenNumberTextView.getText().toString());

            String room, adult, children;

            if (numberOfRooms == 1) {
                room = numberOfRooms + " room";
            } else {
                room = numberOfRooms + " rooms";
            }

            if (adultNumber == 1) {
                adult = adultNumber + " adult";
            } else {
                adult = adultNumber + " adults";
            }

            if (childrenNumber == 1) {
                children = childrenNumber + " child";
            } else if(childrenNumber == 0) {
                children = "No children";
            } else {
                children = childrenNumber + " children";
            }


            guestTextView.setText(room + " - " + adult + " - " + children);
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}