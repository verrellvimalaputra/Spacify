package com.example.hci_prototyp_ws23.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hci_prototyp_ws23.DatabaseHelper;
import com.example.hci_prototyp_ws23.Models.Address;
import com.example.hci_prototyp_ws23.Models.User;
import com.example.hci_prototyp_ws23.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class Register extends Fragment {
    View view;
    Button registerButton;
    BottomNavigationView bottomNavigationView;
    EditText usernameEditText, registerEmailEditText, registerPasswordEditText, firstNameEditText, lastNameEditText,
            mobileNumberEditText, countryEditText, cityEditText, streetEditText, postalCodeEditText;
    RadioButton genderMaleRadioButton, genderFemaleRadioButton, genderOtherRadioButton;
    TextView loginNowTextView, dateOfBirthTextView;
    FirebaseAuth mAuth;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation_bar);
        mAuth = FirebaseAuth.getInstance();
        registerEmailEditText = view.findViewById(R.id.register_email_et);
        registerPasswordEditText = view.findViewById(R.id.register_password_et);
        usernameEditText = view.findViewById(R.id.register_username_et);
        loginNowTextView = view.findViewById(R.id.register_login_now_tv);
        registerButton = view.findViewById(R.id.register_btn);
        firstNameEditText = view.findViewById(R.id.register_first_name_et);
        lastNameEditText = view.findViewById(R.id.register_last_name_et);
        mobileNumberEditText = view.findViewById(R.id.register_mobile_number_et);
        countryEditText = view.findViewById(R.id.register_country_et);
        cityEditText = view.findViewById(R.id.register_city_et);
        streetEditText = view.findViewById(R.id.register_street_address_et);
        postalCodeEditText = view.findViewById(R.id.register_postal_code_et);
        genderMaleRadioButton = view.findViewById(R.id.register_gender_male_rb);
        genderFemaleRadioButton = view.findViewById(R.id.register_gender_female_rb);
        genderOtherRadioButton = view.findViewById(R.id.register_gender_other_rb);
        dateOfBirthTextView = view.findViewById(R.id.register_date_of_birth_tv);
        databaseHelper = DatabaseHelper.getInstance(getContext());
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            NavHostFragment.findNavController(Register.this).navigate(R.id.action_register_to_logIn);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView.setVisibility(View.GONE);

        dateOfBirthTextView.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    (view1, year1, month1, dayOfMonth) -> dateOfBirthTextView.setText(year1 + "-" + (month1+1) + "-" + dayOfMonth), year, month, day);
            datePickerDialog.show();
        });

        registerButton.setOnClickListener(v -> {
            final String email = String.valueOf(registerEmailEditText.getText());
            final String password = String.valueOf(registerPasswordEditText.getText());
            final String username = String.valueOf(usernameEditText.getText());
            final String firstName = String.valueOf(firstNameEditText.getText());
            final String lastName = String.valueOf(lastNameEditText.getText());
            final String mobileNumber = String.valueOf(mobileNumberEditText.getText());
            final String dateOfBirth = String.valueOf(dateOfBirthTextView.getText());
            final String country = String.valueOf(countryEditText.getText());
            final String city = String.valueOf(cityEditText.getText());
            final String streetAddress = String.valueOf(streetEditText.getText());
            final String postalCode = String.valueOf(postalCodeEditText.getText());
            final User.Gender gender;

            if(genderMaleRadioButton.isChecked()) {
                gender = User.Gender.MALE;
            } else if (genderFemaleRadioButton.isChecked()) {
                gender = User.Gender.FEMALE;
            } else if (genderOtherRadioButton.isChecked()) {
                gender = User.Gender.OTHER;
            } else {
                gender = User.Gender.EMPTY;
            }

            if(hasEmptyField(new ArrayList<>(Arrays.asList(email, password, username, firstName, lastName, mobileNumber, dateOfBirth, country, city, streetAddress, postalCode))) || gender == User.Gender.EMPTY) {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if(email.isEmpty() || password.isEmpty() ) {
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if(databaseHelper != null) {
                                try {
                                    User testUser = new User(username, firstName, lastName, email, mobileNumber, new Address(country, city, streetAddress, Integer.parseInt(postalCode)),  new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateOfBirth), gender);
                                    databaseHelper.insertUser(testUser, sqLiteDatabase);
                                    Toast.makeText(getContext(), "New Account created ", Toast.LENGTH_SHORT).show();
                                    NavHostFragment.findNavController(Register.this).navigate(R.id.action_register_to_logIn);
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                Toast.makeText(getContext(), "Error occurred. Please try again later", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "Email already used.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        loginNowTextView.setOnClickListener(v -> NavHostFragment.findNavController(Register.this).navigate(R.id.action_register_to_logIn));
    }

    public boolean hasEmptyField(ArrayList<String> array) {
        for(String str: array) {
            if (str.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}