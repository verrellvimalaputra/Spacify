package com.example.hci_prototyp_ws23.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class Booking implements Parcelable {
    private User user;
    private Hotel hotel;
    private final int numberOfRooms;
    private final Date checkInDate;
    private final Date checkOutDate;
    private final int adultNumber;
    private final int childrenNumber;
    private final double totalPrice;
    private final PaymentMethod paymentMethod;

    protected Booking(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        hotel = in.readParcelable(Hotel.class.getClassLoader());
        numberOfRooms = in.readInt();
        checkInDate = new Date(in.readLong());
        checkOutDate = new Date(in.readLong());
        adultNumber = in.readInt();
        childrenNumber = in.readInt();
        totalPrice = in.readDouble();
        paymentMethod = PaymentMethod.valueOf(in.readString());
    }

    public static final Creator<Booking> CREATOR = new Creator<Booking>() {
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }

        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Hotel getHotel() {
        return hotel;
    }
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    public int getNumberOfRooms() {
        return numberOfRooms;
    }
    public Date getCheckInDate() {
        return checkInDate;
    }
    public Date getCheckOutDate() {
        return checkOutDate;
    }
    public int getAdultNumber() {
        return adultNumber;
    }
    public int getChildrenNumber() {
        return childrenNumber;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeParcelable(hotel, flags);
        dest.writeInt(numberOfRooms);
        dest.writeLong(checkInDate.getTime());
        dest.writeLong(checkOutDate.getTime());
        dest.writeInt(adultNumber);
        dest.writeInt(childrenNumber);
        dest.writeDouble(totalPrice);
        dest.writeString(paymentMethod.name());
    }

    public enum PaymentMethod {
        PAYPAL, DEBIT, GIROPAY, SEPA, CASH;
        public static PaymentMethod stringToEnum(String input) {
            for(PaymentMethod value: PaymentMethod.values()) {
                if(value.name().equals(input)) {
                    return value;
                }
            }
            return null;
        }
    }
    public Booking(User user, Hotel hotel, int numberOfRooms, Date checkInDate, Date checkOutDate, int adultNumber, int childrenNumber, double totalPrice, PaymentMethod paymentMethod) {
        this.user = user;
        this.hotel = hotel;
        this.numberOfRooms = numberOfRooms;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.adultNumber = adultNumber;
        this.childrenNumber = childrenNumber;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
    }
}
