package com.example.hci_prototyp_ws23.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hci_prototyp_ws23.Adapters.SearchResultListAdapter;
import com.example.hci_prototyp_ws23.DatabaseHelper;
import com.example.hci_prototyp_ws23.Models.Hotel;
import com.example.hci_prototyp_ws23.Models.User;
import com.example.hci_prototyp_ws23.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Objects;

public class SearchResultList extends Fragment {
    View view;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    RecyclerView recyclerView;
    User user;
    String destination;
    String checkInDate;
    int numberOfRooms;
    int adultNumber;
    int childrenNumber;
    String checkOutDate;
    ArrayList<Hotel> hotels;
    DatabaseHelper databaseHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search_result_list, container, false);
        bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation_bar);
        toolbar = view.findViewById(R.id.searchResultList_tb);
        recyclerView = view.findViewById(R.id.searchResultList_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        databaseHelper = DatabaseHelper.getInstance(getContext());

        if (getArguments() != null) {
            user = SearchResultListArgs.fromBundle(getArguments()).getUserArg();
            destination = SearchResultListArgs.fromBundle(getArguments()).getDestinationArg();
            numberOfRooms = SearchResultListArgs.fromBundle(getArguments()).getNumberOfRoomsArg();
            adultNumber = SearchResultListArgs.fromBundle(getArguments()).getAdultsNumberArg();
            childrenNumber = SearchResultListArgs.fromBundle(getArguments()).getChildrenNumberArg();
            checkInDate = SearchResultListArgs.fromBundle(getArguments()).getCheckInDate();
            checkOutDate = SearchResultListArgs.fromBundle(getArguments()).getCheckOutDate();
        }

        hotels = databaseHelper.readAllHotels();
        ArrayList<Hotel> results = new ArrayList<>();

        for(Hotel hotel: hotels) {
            if(Objects.equals(hotel.getHotelAddress().getCity(), destination)) {
                results.add(hotel);
            }
        }

        if(results.isEmpty()) {
            results = hotels;
        }

        SearchResultListAdapter searchResultListAdapter = new SearchResultListAdapter(results, numberOfRooms);
        recyclerView.setAdapter(searchResultListAdapter);
        searchResultListAdapter.setOnClickListener((position, hotel) -> {
            SearchResultListDirections.ActionSearchResultListToHotelDescription action = SearchResultListDirections.actionSearchResultListToHotelDescription(user, hotel, adultNumber, childrenNumber, checkInDate, checkOutDate, numberOfRooms);
            NavHostFragment.findNavController(SearchResultList.this).navigate(action);
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView.setVisibility(View.GONE);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle("Search Result");
        toolbar.inflateMenu(R.menu.top_action_bar_search_result);
        toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(SearchResultList.this).popBackStack());
    }
}