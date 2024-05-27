package com.example.hci_prototyp_ws23.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.hci_prototyp_ws23.DatabaseHelper;
import com.example.hci_prototyp_ws23.MainActivity;
import com.example.hci_prototyp_ws23.Models.Address;
import com.example.hci_prototyp_ws23.Models.User;
import com.example.hci_prototyp_ws23.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserProfile extends Fragment {
    View view;
    BottomNavigationView bottomNavigationView;
    Button signOutButton, saveChanges;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseHelper databaseHelper;
    EditText firstNameEditText, lastNameEditText, emailEditText, mobileNumberEditText, dateOfBirthEditText, countryEditText, cityEditText, streetAddressEditText, postalCodeEditText;
    RadioButton genderMaleRadioButton, genderFemaleRadioButton, genderOtherRadioButton;
    String email;
    User currentUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        databaseHelper = DatabaseHelper.getInstance(getContext());
        bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation_bar);

        firstNameEditText = view.findViewById(R.id.profile_first_name_et);
        lastNameEditText = view.findViewById(R.id.profile_last_name_et);
        emailEditText = view.findViewById(R.id.profile_email_et);
        mobileNumberEditText = view.findViewById(R.id.profile_mobile_number_et);
        dateOfBirthEditText = view.findViewById(R.id.profile_date_of_birth_et);
        countryEditText = view.findViewById(R.id.profile_country_et);
        cityEditText = view.findViewById(R.id.profile_city_et);
        streetAddressEditText = view.findViewById(R.id.profile_street_address_et);
        postalCodeEditText = view.findViewById(R.id.profile_postal_code_et);
        genderMaleRadioButton = view.findViewById(R.id.gender_male_rb);
        genderFemaleRadioButton = view.findViewById(R.id.gender_female_rb);
        genderOtherRadioButton = view.findViewById(R.id.gender_other_rb);

        signOutButton = view.findViewById(R.id.sign_out_btn);
        saveChanges = view.findViewById(R.id.save_changes_btn);
        email = user.getEmail();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.getMenu().getItem(3).setChecked(true);
        bottomNavigationView.getMenu().getItem(0).setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.home));
        bottomNavigationView.getMenu().getItem(2).setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.bed));
        bottomNavigationView.getMenu().getItem(1).setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.heart));
        bottomNavigationView.getMenu().getItem(3).setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.profile_blue_black_tint));

        currentUser = databaseHelper.readUserBy("email", email);

        dateOfBirthEditText.setFocusable(false);
        dateOfBirthEditText.setClickable(true);
        dateOfBirthEditText.setInputType(InputType.TYPE_NULL);
        dateOfBirthEditText.setKeyListener(null);
        dateOfBirthEditText.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    (view1, year1, month1, dayOfMonth) -> dateOfBirthEditText.setText(year1 + "-" + (month1+1) + "-" + dayOfMonth), year, month, day);
            datePickerDialog.show();
        });

        firstNameEditText.setText(currentUser.getFirstName());
        lastNameEditText.setText(currentUser.getLastName());
        emailEditText.setText(currentUser.getEmail());
        dateOfBirthEditText.setText(sdf.format(currentUser.getDateOfBirth()));
        countryEditText.setText(currentUser.getUserAddress().getCountry());
        cityEditText.setText(currentUser.getUserAddress().getCity());
        streetAddressEditText.setText(currentUser.getUserAddress().getStreetAddress());
        postalCodeEditText.setText(String.valueOf(currentUser.getUserAddress().getPostalCode()));
        mobileNumberEditText.setText(String.valueOf(currentUser.getPhoneNumber()));
        if (currentUser.getGender().equals(User.Gender.MALE)) {
            genderMaleRadioButton.setChecked(true);
        } else if (currentUser.getGender().equals(User.Gender.FEMALE)) {
            genderFemaleRadioButton.setChecked(true);
        } else {
            genderOtherRadioButton.setChecked(true);
        }

        saveChanges.setOnClickListener(v -> {
            User.Gender gender;
            if (genderMaleRadioButton.isChecked()) {
                gender = User.Gender.MALE;
            } else if (genderFemaleRadioButton.isChecked()) {
                gender = User.Gender.FEMALE;
            } else {
                gender = User.Gender.OTHER;
            }
            User newUser;
            try {
                newUser = new User(
                        currentUser.getUsername(),
                        firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        mobileNumberEditText.getText().toString(),
                        new Address(countryEditText.getText().toString(),
                                cityEditText.getText().toString(),
                                streetAddressEditText.getText().toString(),
                                Integer.parseInt(postalCodeEditText.getText().toString())),
                        sdf.parse(dateOfBirthEditText.getText().toString()),
                        gender
                );
                databaseHelper.updateUser(newUser);
                Toast.makeText(getContext(), "Your changes are saved", Toast.LENGTH_SHORT).show();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });

        signOutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            getActivity().finish();
            startActivity(intent);
        });
    }
}