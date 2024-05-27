package com.example.hci_prototyp_ws23.Models;

import java.util.ArrayList;
import java.util.Date;

public class SavedHotel {
    private final String username;
    private final String hotelName;
    private final Address hotelAddress;
    private final Date checkInDate;
    private final Date checkOutDate;
    private final int numberOfRoom;
    private final int adultNumber;
    private final int childrenNumber;
    private final double totalPrice;
    private final String imageURL;
    private ArrayList<String> acceptedPaymentMethods;

    public SavedHotel(String username, String hotelName, Address hotelAddress, Date checkInDate, Date checkOutDate, int numberOfRoom, int adultNumber, int childrenNumber, double totalPrice, String imageURL) {
        this.username = username;
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfRoom = numberOfRoom;
        this.adultNumber = adultNumber;
        this.childrenNumber = childrenNumber;
        this.totalPrice = totalPrice;
        this.imageURL = imageURL;
        setAcceptedPaymentMethods(new ArrayList<>());
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getImageURL() {
        return imageURL;
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

    public Address getHotelAddress() {
        return hotelAddress;
    }

    public int getNumberOfRoom() {
        return numberOfRoom;
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
