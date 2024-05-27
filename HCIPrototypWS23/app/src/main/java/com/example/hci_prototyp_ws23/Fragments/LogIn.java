package com.example.hci_prototyp_ws23.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hci_prototyp_ws23.DatabaseHelper;
import com.example.hci_prototyp_ws23.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends Fragment {
    View view;
    Button loginBtn;
    BottomNavigationView bottomNavigationView;
    EditText loginEmailEditText, loginPasswordEditText;
    TextView registerNowTextView;
    DatabaseHelper databaseHelper;
    FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_log_in, container, false);
        mAuth = FirebaseAuth.getInstance();
        loginBtn = view.findViewById(R.id.register_btn);
        bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation_bar);
        loginEmailEditText = view.findViewById(R.id.loginEmail_et);
        loginPasswordEditText = view.findViewById(R.id.loginPassword_et);
        registerNowTextView = view.findViewById(R.id.registerNow_tv);
        databaseHelper = DatabaseHelper.getInstance(getContext());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            FirebaseAuth.getInstance().signOut();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView.setVisibility(View.GONE);
        loginBtn.setOnClickListener(v -> {
            final String email = loginEmailEditText.getText().toString();
            final String password = loginPasswordEditText.getText().toString();
            if(email.isEmpty()) {
                Toast.makeText(getContext(), "Enter your email", Toast.LENGTH_SHORT).show();
                return;
            }

            if(password.isEmpty()) {
                Toast.makeText(getContext(), "Enter your password", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && databaseHelper.readUserBy("email", email) != null) {
                            NavHostFragment.findNavController(LogIn.this).navigate(R.id.action_logIn_to_homepage);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        registerNowTextView.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            NavHostFragment.findNavController(LogIn.this).navigate(R.id.action_logIn_to_register);
        });
    }
}