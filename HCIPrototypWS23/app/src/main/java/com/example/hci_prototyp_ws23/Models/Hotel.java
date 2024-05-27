package com.example.hci_prototyp_ws23.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Hotel implements Parcelable {
    private final String hotelName;
    private final Address hotelAddress;
    private final String description;
    private final double pricePerNight;
    private final String imageURL;
    private final ArrayList<String> services;
    private ArrayList<String> acceptedPaymentMethods;

    private Hotel(Parcel in) {
        hotelName = in.readString();
        hotelAddress = in.readParcelable(Address.class.getClassLoader());
        description = in.readString();
        pricePerNight = in.readDouble();
        services = in.createStringArrayList();
        setAcceptedPaymentMethods(in.createStringArrayList());
        imageURL = in.readString();
    }

    public static final Creator<Hotel> CREATOR = new Creator<Hotel>() {
        @Override
        public Hotel createFromParcel(Parcel in) {
            return new Hotel(in);
        }

        @Override
        public Hotel[] newArray(int size) {
            return new Hotel[size];
        }
    };

    public String getHotelName() {
        return hotelName;
    }
    public Address getHotelAddress() {
        return hotelAddress;
    }
    public String getDescription() {
        return description;
    }
    public ArrayList<String> getServices() {
        return services;
    }
    public double getPricePerNight() {
        return pricePerNight;
    }
    public Hotel(String hotelName, Address hotelAddress, String description, double pricePerNight, String imageURL) {
        this.imageURL = imageURL;
        services = new ArrayList<>();
        setAcceptedPaymentMethods(new ArrayList<>());
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.description = description;
        this.pricePerNight = pricePerNight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(hotelName);
        dest.writeParcelable(hotelAddress, flags);
        dest.writeString(description);
        dest.writeDouble(pricePerNight);
        dest.writeStringList(services);
        dest.writeStringList(getAcceptedPaymentMethods());
        dest.writeString(getImageURL());
    }

    public String getImageURL() {
        return imageURL;
    }

    public ArrayList<String> getAcceptedPaymentMethods() {
        return acceptedPaymentMethods;
    }

    public void setAcceptedPaymentMethods(ArrayList<String> acceptedPaymentMethods) {
        this.acceptedPaymentMethods = acceptedPaymentMethods;
    }
    public String toStringPayment() {
        StringBuilder payment = new StringBuilder("We accept: ");
        for(String p: this.getAcceptedPaymentMethods()) {
            payment.append(p).append(", ");
        }
        return payment.toString();
    }
}
