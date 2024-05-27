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

import com.example.hci_prototyp_ws23.Models.SavedHotel;
import com.example.hci_prototyp_ws23.R;

import java.util.List;

public class SavedListAdapter extends RecyclerView.Adapter<SavedListAdapter.SavedListAdapterViewHolder> {
    private final List<SavedHotel> hotelList;
    private Context context;
    private onClickListener onClickListener;
    public SavedListAdapter(List<SavedHotel> hotelList) {
        this.hotelList = hotelList;
    }
    @NonNull
    @Override
    public SavedListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_list_item, parent, false);
        context = parent.getContext();
        return new SavedListAdapterViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SavedListAdapterViewHolder holder, int position) {
        SavedHotel hotel = hotelList.get(position);
        holder.nameView.setText(hotelList.get(position).getHotelName());
        SavedHotel sh = hotelList.get(position);
        String Address = sh.getHotelAddress().getStreetAddress() + ", "  + sh.getHotelAddress().getPostalCode() + " " + sh.getHotelAddress().getCity() + ", " + sh.getHotelAddress().getCountry();
        holder.addressView.setText(Address);

        String roomNum;
        if(hotel.getNumberOfRoom() == 1) {
            roomNum = hotel.getNumberOfRoom() + " room for";
        } else {
            roomNum = hotel.getNumberOfRoom() + " rooms for";
        }

        String adultNum;
        if(hotel.getAdultNumber() == 1) {
            adultNum = hotel.getAdultNumber() + " adult";
        } else {
            adultNum = hotel.getAdultNumber() + " adults";
        }

        String childrenNum;
        if(hotel.getChildrenNumber() == 1) {
            childrenNum = hotel.getChildrenNumber() + " child";
        } else if(hotel.getChildrenNumber() == 0) {
            childrenNum = "no children";
        } else {
            childrenNum = hotel.getChildrenNumber() + " children";
        }

        holder.guestAndRoomTextView.setText(roomNum + " " + adultNum + ", " + childrenNum);
        holder.hotelPriceTextView.setText(hotel.getTotalPrice() + "€");
        holder.imageView.setImageResource(context.getResources().getIdentifier(hotel.getImageURL(), "drawable", context.getPackageName()));
        holder.itemView.setOnClickListener(v -> {
            if(onClickListener != null) {
                onClickListener.onClick(position, hotel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }
    public void setOnClickListener(SavedListAdapter.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public interface onClickListener {
        void onClick(int position, SavedHotel hotel);
    }

    public static class SavedListAdapterViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView nameView, addressView, guestAndRoomTextView, hotelPriceTextView;
        public SavedListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.savedList_iv);
            nameView = itemView.findViewById(R.id.savedListHotelName_tv);
            addressView = itemView.findViewById(R.id.savedListHotelAddress_tv);
            guestAndRoomTextView = itemView.findViewById(R.id.savedListGuestAndRoom_tv);
            hotelPriceTextView = itemView.findViewById(R.id.savedListHotelPrice_tv);
        }
    }
}
