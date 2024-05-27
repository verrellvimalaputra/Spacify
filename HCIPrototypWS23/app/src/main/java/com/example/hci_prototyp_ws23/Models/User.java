package com.example.hci_prototyp_ws23.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;

public class User implements Parcelable {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Address userAddress;
    private Date dateOfBirth;
    private Gender gender;
    private ArrayList<Booking> userBookings;
    private ArrayList<Hotel> savedHotels;

    protected User(Parcel in) {
        setUsername(in.readString());
        setFirstName(in.readString());
        setLastName(in.readString());
        setEmail(in.readString());
        setPhoneNumber(in.readString());
        setUserAddress(in.readParcelable(Address.class.getClassLoader()));
        setDateOfBirth(new Date(in.readLong()));
        setGender(Gender.valueOf(in.readString()));
        in.readTypedList(userBookings, Booking.CREATOR);
        in.readTypedList(savedHotels, Hotel.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Address getUserAddress() {
        return userAddress;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public ArrayList<Booking> getUserBookings() {
        return userBookings;
    }

    public ArrayList<Hotel> getSavedHotels() {
        return savedHotels;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUserAddress(Address userAddress) {
        this.userAddress = userAddress;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeString(phoneNumber);
        dest.writeParcelable(userAddress, flags);
        dest.writeLong(dateOfBirth.getTime());
        dest.writeString(gender.name());
        dest.writeTypedList(userBookings);
        dest.writeTypedList(savedHotels);
    }

    public enum Gender {
        MALE, FEMALE, OTHER, EMPTY
    }

    public User(String username, String firstName, String lastName, String email, String phoneNumber, Address userAddress, Date dateOfBirth, Gender gender) {
        this.userBookings = new ArrayList<>();
        this.savedHotels = new ArrayList<>();
        this.setUsername(username);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
        this.setUserAddress(userAddress);
        this.setDateOfBirth(dateOfBirth);
        this.setGender(gender);
    }
}
