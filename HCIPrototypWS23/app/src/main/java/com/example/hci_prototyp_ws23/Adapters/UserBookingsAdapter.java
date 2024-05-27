package com.example.hci_prototyp_ws23.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hci_prototyp_ws23.Models.Booking;
import com.example.hci_prototyp_ws23.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class UserBookingsAdapter extends RecyclerView.Adapter<UserBookingsAdapter.UserBookingAdapterViewHolder> {
    private Context context;
    private final List<Booking> bookingList;
    public UserBookingsAdapter(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }
    @NonNull
    @Override
    public UserBookingAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_bookings_item, parent, false);
        context = parent.getContext();
        return new UserBookingAdapterViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserBookingAdapterViewHolder holder, int position) {
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd", Locale.getDefault());
        holder.nameTextView.setText(bookingList.get(position).getHotel().getHotelName());
        String date = sdf3.format(bookingList.get(position).getCheckInDate()) + " - " + sdf2.format(bookingList.get(position).getCheckOutDate());
        holder.dateTextView.setText(date);
        holder.totalPriceTextView.setText(bookingList.get(position).getTotalPrice() + "€");
        holder.imageView.setImageResource(context.getResources().getIdentifier(bookingList.get(position).getHotel().getImageURL(), "drawable", context.getPackageName()));
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class UserBookingAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView, dateTextView, totalPriceTextView;
        public UserBookingAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.userBookings_iv);
            nameTextView = itemView.findViewById(R.id.userBookingsHotelName_tv);
            dateTextView = itemView.findViewById(R.id.userBookingsHotelDate_tv);
            totalPriceTextView = itemView.findViewById(R.id.userBookingsTotalPrice_tv);
        }
    }
}
