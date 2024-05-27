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

import com.example.hci_prototyp_ws23.Models.Hotel;
import com.example.hci_prototyp_ws23.R;

import java.util.List;

public class SearchResultListAdapter extends RecyclerView.Adapter<SearchResultListAdapter.SearchResultListAdapterViewHolder> {
    private final List<Hotel> hotelList;
    private Context context;
    private onClickListener onClickListener;
    private final int numberOfRooms;
    public SearchResultListAdapter(List<Hotel> hotelList, int numberOfRooms) {
        this.hotelList = hotelList;
        this.numberOfRooms = numberOfRooms;
    }
    @NonNull
    @Override
    public SearchResultListAdapter.SearchResultListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_list_item, parent, false);
        context = parent.getContext();
        return new SearchResultListAdapterViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SearchResultListAdapter.SearchResultListAdapterViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Hotel hotel = hotelList.get(position);
        holder.nameView.setText(hotelList.get(position).getHotelName());
        String Address =  hotelList.get(position).getHotelAddress().getStreetAddress() + " " + hotelList.get(position).getHotelAddress().getCity() +  " " + hotelList.get(position).getHotelAddress().getPostalCode() + ", " + hotelList.get(position).getHotelAddress().getCountry();
        holder.addressView.setText(Address);

        if(numberOfRooms > 1) {
            holder.numberOfRoomView.setText(numberOfRooms + " rooms");
        } else {
            holder.numberOfRoomView.setText(numberOfRooms + " room");
        }

        holder.priceView.setText(hotel.getPricePerNight() + " €/night");
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
    public void setOnClickListener(onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface onClickListener {
        void onClick(int position, Hotel hotel);
    }

    public static class SearchResultListAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameView, addressView, numberOfRoomView, priceView;
        public SearchResultListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.searchResultList_iv);
            nameView = itemView.findViewById(R.id.searchResultListHotelName_tv);
            addressView = itemView.findViewById(R.id.searchResultListHotelAddress_tv);
            numberOfRoomView = itemView.findViewById(R.id.searchResultListRoomNumber_tv);
            priceView = itemView.findViewById(R.id.searchResultListPrice_tv);
            }
        }
    }


