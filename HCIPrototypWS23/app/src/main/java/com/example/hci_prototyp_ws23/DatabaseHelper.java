package com.example.hci_prototyp_ws23;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.hci_prototyp_ws23.Models.Address;
import com.example.hci_prototyp_ws23.Models.Booking;
import com.example.hci_prototyp_ws23.Models.Hotel;
import com.example.hci_prototyp_ws23.Models.SavedHotel;
import com.example.hci_prototyp_ws23.Models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String HOTEL_TABLE = "hotel";
    private static final String HOTEL_NAME_COLUMN = "hotel_name";
    private static final String HOTEL_COUNTRY_COLUMN = "country";
    private static final String HOTEL_CITY_COLUMN = "city";
    private static final String HOTEL_STREET_ADDRESS_COLUMN = "street_address";
    private static final String HOTEL_POSTAL_CODE_COLUMN = "postal_code";
    private static final String HOTEL_PRICE_PER_NIGHT = "price_per_night";
    private static final String HOTEL_IMAGE_URL = "image_url";
    private static final String USER_TABLE = "user";
    private static final String USERNAME_COLUMN = "username";
    private static final String USER_FIRST_NAME_COLUMN = "first_name";
    private static final String USER_LAST_NAME_COLUMN = "last_name";
    private static final String USER_EMAIL_COLUMN = "email";
    private static final String USER_COUNTRY_COLUMN = "country";
    private static final String USER_CITY_COLUMN = "city";
    private static final String USER_STREET_ADDRESS_COLUMN = "street_address";
    private static final String USER_POSTAL_CODE_COLUMN = "postal_code";
    private static final String USER_PHONE_NUMBER_COLUMN = "phone_number";
    private static final String USER_GENDER_COLUMN = "gender";
    private static final String USER_DATE_OF_BIRTH_COLUMN = "date_of_birth";
    private static final String ADDRESS_TABLE = "address";
    private static final String COUNTRY_COLUMN = "country";
    private static final String CITY_COLUMN = "city";
    private static final String STREET_ADDRESS_COLUMN = "street_address";
    private static final String POSTAL_CODE_COLUMN = "postal_code";
    private static final String HOTEL_DESCRIPTION_COLUMN = "description";
    private static final String SERVICE_TABLE = "service";
    private static final String SERVICE_NAME_COLUMN = "name";
    private static final String SERVICE_HOTEL_NAME_COLUMN = "hotel_name";
    private static final String BOOKING_TABLE = "booking";
    private static final String BOOKING_USERNAME_COLUMN = "username";
    private static final String BOOKING_HOTEL_NAME_COLUMN = "hotel_name";
    private static final String BOOKING_NUMBER_OF_ROOM = "number_of_room";
    private static final String BOOKING_CHECK_IN_DATE_COLUMN = "check_in_date";
    private static final String BOOKING_CHECK_OUT_DATE_COLUMN = "check_out_date";
    private static final String BOOKING_ADULT_NUMBER_COLUMN = "adult_number";
    private static final String BOOKING_CHILDREN_NUMBER_COLUMN = "children_number";
    private static final String BOOKING_TOTAL_PRICE_COLUMN = "total_price";
    private static final String BOOKING_PAYMENT_METHOD_COLUMN = "payment_method";
    private static final String SAVED_HOTEL_TABLE = "saved_hotel";
    private static final String SAVED_USERNAME_COLUMN = "username";
    private static final String SAVED_HOTEL_NAME_COLUMN = "hotel_name";
    private static final String SAVED_CHECK_IN_DATE_COLUMN = "check_in_date";
    private static final String SAVED_CHECK_OUT_DATE_COLUMN = "check_out_date";
    private static final String SAVED_ADULT_NUMBER_COLUMN = "adult_number";
    private static final String SAVED_CHILDREN_NUMBER_COLUMN = "children_number";
    private static final String SAVED_TOTAL_PRICE_COLUMN = "total_price";
    private static final String SAVED_HOTEL_COUNTRY_COLUMN = "country";
    private static final String SAVED_HOTEL_CITY_COLUMN = "city";
    private static final String SAVED_HOTEL_STREET_ADDRESS_COLUMN = "street_address";
    private static final String SAVED_HOTEL_POSTAL_CODE_COLUMN = "postal_code";
    private static final String SAVED_HOTEL_IMAGE_URL = "image_url";
    private static final String SAVED_NUMBER_OF_ROOMS_COLUMN = "number_of_rooms";
    private static final String HOTEL_PAYMENT_METHOD_TABLE = "hotel_payment_method";
    private static final String PAYMENT_METHOD_HOTEL_NAME_COLUMN = "hotel_name";
    private static final String PAYMENT_METHOD_NAME_COLUMN = "payment_method";
    public static DatabaseHelper instance;
    private static final String description1 = "Mercure is a contemporary urban haven, conveniently located near major attractions and public transportation. Modern rooms, a business center, and a welcoming atmosphere make it an ideal choice for both business and leisure travelers.";
    private static final String description2 = "Nestled in the prestigious Kaiserstrasse, Azure Haven Hotel epitomizes luxury living. With spacious, elegantly designed suites, personalized concierge services, and proximity to high-end shopping, this hotel offers a refined experience for the discerning traveler.";
    private static final String description3 = "Crimson Crown Inn, located on the iconic Friedrichstraße, is an intimate retreat blending historic charm with modern elegance. Unique rooms, personalized services, and proximity to cultural landmarks make it a haven for those seeking a distinctive Berlin experience.";
    private static final String description4 = "Perfectly positioned in the business district of Potsdamer Platz, Mystic Oasis Resort offers sophisticated accommodations with state-of-the-art meeting facilities. Ideal for business travelers, it combines professionalism with contemporary comfort.";
    private static final String description5 = "Enjoy breathtaking views of the harbor at Radiant Horizon Hotel. With stylish accommodations, waterfront dining, and a serene ambiance, this hotel offers a tranquil escape in the heart of Hamburg.";
    private static final String description6 = "Immerse yourself in Hamburg's trendy Sternschanze district at Urban Hostel. With modern facilities, social spaces, and a vibrant atmosphere, this hostel is perfect for travelers seeking a contemporary and communal experience in the city.";
    ArrayList<Address> initialAddresses = new ArrayList<>(Arrays.asList(
            new Address("Germany", "Frankfurt am Main", "Goethestrasse 123", 60323),
            new Address("Germany", "Frankfurt am Main", "Kaiserstrasse 456", 60329),
            new Address("Germany", "Berlin", "Friedlichstrasse 234", 10117),
            new Address("Germany", "Berlin", "Potsdamer Platz 567", 10785),
            new Address("Germany", "Hamburg", "Hafenstrasse 123", 20095),
            new Address("Germany", "Hamburg", "Sternschanze 789", 20357)
    ));

    ArrayList<Hotel> initialHotels = new ArrayList<>(Arrays.asList(
            new Hotel("Mercure", initialAddresses.get(0),"Nearby: 70m from nearest bus stop and 90m from nearest tram station." +"\n \n" + description1, 90, "pic"),
            new Hotel("Azure Haven Hotel", initialAddresses.get(1),"Nearby: 100m from nearest train station." + "\n \n" + description2, 100, "pic2"),
            new Hotel("Crimson Crown Inn", initialAddresses.get(2),"Nearby: 8km from nearest airport." + "\n \n" + description3, 85, "pic6"),
            new Hotel("Mystic Oasis Resort", initialAddresses.get(3),"Nearby: 50m from nearest bus stop and 100m from nearest train station." + "\n \n" + description4, 90, "pic3"),
            new Hotel("Radiant Horizon Hotel", initialAddresses.get(4),"Nearby: 80m from nearest bus stop and 4km from city centre." + "\n \n" + description5, 95, "pic4"),
            new Hotel("Alpine Haven Lodge", initialAddresses.get(5),"Nearby: 200m from nearest train station." + "\n \n" + description6, 110, "pic5")
    ));

    public static synchronized DatabaseHelper getInstance(Context context) {
        if(instance == null) {
            return new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }
    public DatabaseHelper(@Nullable Context context) {
        super(context, "HotelApp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createAddressTable = "CREATE TABLE " + ADDRESS_TABLE + " ("
                + COUNTRY_COLUMN + " TEXT, "
                + CITY_COLUMN + " TEXT, "
                + STREET_ADDRESS_COLUMN + " TEXT, "
                + POSTAL_CODE_COLUMN + " INTEGER, "
                + "PRIMARY KEY (" + COUNTRY_COLUMN + ", " + CITY_COLUMN + ", " + STREET_ADDRESS_COLUMN + ", " + POSTAL_CODE_COLUMN
                + "));";

        String createHotelTable = "CREATE TABLE " + HOTEL_TABLE + " ("
                + HOTEL_NAME_COLUMN + " TEXT, "
                + HOTEL_COUNTRY_COLUMN + " TEXT NOT NULL REFERENCES " + ADDRESS_TABLE + "(" + STREET_ADDRESS_COLUMN + "), "
                + HOTEL_CITY_COLUMN + " TEXT NOT NULL REFERENCES " + ADDRESS_TABLE + "(" + CITY_COLUMN + "), "
                + HOTEL_STREET_ADDRESS_COLUMN + " TEXT NOT NULL REFERENCES " + ADDRESS_TABLE + "(" + STREET_ADDRESS_COLUMN + "), "
                + HOTEL_POSTAL_CODE_COLUMN + " INTEGER NOT NULL REFERENCES " + ADDRESS_TABLE + "(" + POSTAL_CODE_COLUMN + "), "
                + HOTEL_DESCRIPTION_COLUMN + " TEXT NOT NULL, "
                + HOTEL_PRICE_PER_NIGHT + " FLOAT NOT NULL, "
                + HOTEL_IMAGE_URL + " TEXT NOT NULL, "
                + "PRIMARY KEY (" + HOTEL_NAME_COLUMN
                + "));";

        String createSavedHotel = "CREATE TABLE " + SAVED_HOTEL_TABLE + " ("
                + SAVED_USERNAME_COLUMN + " TEXT REFERENCES " + USER_TABLE + "(" + USERNAME_COLUMN + "), "
                + SAVED_HOTEL_NAME_COLUMN + " TEXT REFERENCES " + HOTEL_TABLE + "(" + HOTEL_NAME_COLUMN + "), "
                + SAVED_HOTEL_COUNTRY_COLUMN + " TEXT NOT NULL REFERENCES " + ADDRESS_TABLE + "(" + STREET_ADDRESS_COLUMN + "), "
                + SAVED_HOTEL_CITY_COLUMN + " TEXT NOT NULL REFERENCES " + ADDRESS_TABLE + "(" + CITY_COLUMN + "), "
                + SAVED_HOTEL_STREET_ADDRESS_COLUMN + " TEXT NOT NULL REFERENCES " + ADDRESS_TABLE + "(" + STREET_ADDRESS_COLUMN + "), "
                + SAVED_HOTEL_POSTAL_CODE_COLUMN + " INTEGER NOT NULL REFERENCES " + ADDRESS_TABLE + "(" + POSTAL_CODE_COLUMN + "), "
                + SAVED_HOTEL_IMAGE_URL + " TEXT NOT NULL REFERENCES " + HOTEL_TABLE + "(" + HOTEL_IMAGE_URL + "), "
                + SAVED_CHECK_IN_DATE_COLUMN + " DATE NOT NULL, "
                + SAVED_CHECK_OUT_DATE_COLUMN + " DATE NOT NULL, "
                + SAVED_ADULT_NUMBER_COLUMN + " INTEGER NOT NULL, "
                + SAVED_CHILDREN_NUMBER_COLUMN + " INTEGER NOT NULL, "
                + SAVED_NUMBER_OF_ROOMS_COLUMN + " INTEGER NOT NULL, "
                + SAVED_TOTAL_PRICE_COLUMN + " FLOAT NOT NULL, "
                + "PRIMARY KEY (" + SAVED_USERNAME_COLUMN + ", " + SAVED_HOTEL_NAME_COLUMN + ", " + SAVED_CHECK_IN_DATE_COLUMN + ", " + SAVED_CHECK_OUT_DATE_COLUMN
                + "));";

        String createUserTable = "CREATE TABLE " + USER_TABLE + " ("
                + USERNAME_COLUMN + " TEXT, "
                + USER_FIRST_NAME_COLUMN + " TEXT NOT NULL, "
                + USER_LAST_NAME_COLUMN + " TEXT NOT NULL, "
                + USER_EMAIL_COLUMN + " TEXT NOT NULL, "
                + USER_COUNTRY_COLUMN + " TEXT NOT NULL REFERENCES " + ADDRESS_TABLE + "(" + STREET_ADDRESS_COLUMN + "), "
                + USER_CITY_COLUMN + " TEXT NOT NULL REFERENCES " + ADDRESS_TABLE + "(" + CITY_COLUMN + "), "
                + USER_STREET_ADDRESS_COLUMN + " TEXT NOT NULL REFERENCES " + ADDRESS_TABLE + "(" + STREET_ADDRESS_COLUMN + "), "
                + USER_POSTAL_CODE_COLUMN + " INTEGER NOT NULL REFERENCES " + ADDRESS_TABLE + "(" + POSTAL_CODE_COLUMN + "), "
                + USER_PHONE_NUMBER_COLUMN + " TEXT NOT NULL, "
                + USER_GENDER_COLUMN + " TEXT NOT NULL, "
                + USER_DATE_OF_BIRTH_COLUMN + " DATE NOT NULL, "
                + "PRIMARY KEY (" + USERNAME_COLUMN
                + "));";

        String createBookingTable = "CREATE TABLE " + BOOKING_TABLE + " ("
                + BOOKING_USERNAME_COLUMN + " TEXT REFERENCES " + USER_TABLE + "(" + USERNAME_COLUMN + "), "
                + BOOKING_HOTEL_NAME_COLUMN + " TEXT REFERENCES " + HOTEL_TABLE + "(" + HOTEL_NAME_COLUMN + "), "
                + BOOKING_NUMBER_OF_ROOM + " INTEGER NOT NULL, "
                + BOOKING_CHECK_IN_DATE_COLUMN + " DATE NOT NULL, "
                + BOOKING_CHECK_OUT_DATE_COLUMN + " DATE NOT NULL, "
                + BOOKING_ADULT_NUMBER_COLUMN + " INTEGER NOT NULL, "
                + BOOKING_CHILDREN_NUMBER_COLUMN + " INTEGER NOT NULL, "
                + BOOKING_TOTAL_PRICE_COLUMN + " FLOAT NOT NULL, "
                + BOOKING_PAYMENT_METHOD_COLUMN + " TEXT NOT NULL, "
                + "PRIMARY KEY (" + BOOKING_USERNAME_COLUMN + ", " + BOOKING_HOTEL_NAME_COLUMN + ", " + BOOKING_CHECK_IN_DATE_COLUMN + ", " + BOOKING_CHECK_OUT_DATE_COLUMN
                + "));";

        String createServiceTable = "CREATE TABLE " + SERVICE_TABLE + " ("
                + SERVICE_HOTEL_NAME_COLUMN + " TEXT REFERENCES " + HOTEL_TABLE + "(" + HOTEL_NAME_COLUMN + "), "
                + SERVICE_NAME_COLUMN + " TEXT NOT NULL, "
                + "PRIMARY KEY (" + SERVICE_HOTEL_NAME_COLUMN
                + "));";

        String createHotelPaymentMethodTable = "CREATE TABLE " + HOTEL_PAYMENT_METHOD_TABLE + " ("
                + PAYMENT_METHOD_HOTEL_NAME_COLUMN + " TEXT REFERENCES " + HOTEL_TABLE + "(" + HOTEL_NAME_COLUMN + "), "
                + PAYMENT_METHOD_NAME_COLUMN + " TEXT NOT NULL, "
                + "PRIMARY KEY (" + PAYMENT_METHOD_HOTEL_NAME_COLUMN + ", " + PAYMENT_METHOD_NAME_COLUMN
                + "));";


        db.execSQL(createAddressTable);
        db.execSQL(createHotelTable);
        db.execSQL(createUserTable);
        db.execSQL(createServiceTable);
        db.execSQL(createBookingTable);
        db.execSQL(createSavedHotel);
        db.execSQL(createHotelPaymentMethodTable);
        loadInitialAddress(db);
        loadInitialHotels(db);
        insertHotelPaymentMethod(initialHotels.get(0).getHotelName(), Booking.PaymentMethod.DEBIT, db);
        insertHotelPaymentMethod(initialHotels.get(0).getHotelName(), Booking.PaymentMethod.PAYPAL, db);
        insertHotelPaymentMethod(initialHotels.get(0).getHotelName(), Booking.PaymentMethod.CASH, db);
        insertHotelPaymentMethod(initialHotels.get(0).getHotelName(), Booking.PaymentMethod.SEPA, db);
        insertHotelPaymentMethod(initialHotels.get(1).getHotelName(), Booking.PaymentMethod.DEBIT, db);
        insertHotelPaymentMethod(initialHotels.get(1).getHotelName(), Booking.PaymentMethod.CASH, db);
        insertHotelPaymentMethod(initialHotels.get(1).getHotelName(), Booking.PaymentMethod.SEPA, db);
        insertHotelPaymentMethod(initialHotels.get(2).getHotelName(), Booking.PaymentMethod.CASH, db);
        insertHotelPaymentMethod(initialHotels.get(2).getHotelName(), Booking.PaymentMethod.SEPA, db);
        insertHotelPaymentMethod(initialHotels.get(3).getHotelName(), Booking.PaymentMethod.PAYPAL, db);
        insertHotelPaymentMethod(initialHotels.get(3).getHotelName(), Booking.PaymentMethod.CASH, db);
        insertHotelPaymentMethod(initialHotels.get(3).getHotelName(), Booking.PaymentMethod.SEPA, db);
        insertHotelPaymentMethod(initialHotels.get(4).getHotelName(), Booking.PaymentMethod.DEBIT, db);
        insertHotelPaymentMethod(initialHotels.get(4).getHotelName(), Booking.PaymentMethod.PAYPAL, db);
        insertHotelPaymentMethod(initialHotels.get(4).getHotelName(), Booking.PaymentMethod.CASH, db);
        insertHotelPaymentMethod(initialHotels.get(4).getHotelName(), Booking.PaymentMethod.SEPA, db);
        insertHotelPaymentMethod(initialHotels.get(4).getHotelName(), Booking.PaymentMethod.GIROPAY, db);
        insertHotelPaymentMethod(initialHotels.get(5).getHotelName(), Booking.PaymentMethod.DEBIT, db);
        insertHotelPaymentMethod(initialHotels.get(5).getHotelName(), Booking.PaymentMethod.PAYPAL, db);
        insertHotelPaymentMethod(initialHotels.get(5).getHotelName(), Booking.PaymentMethod.CASH, db);
        insertHotelPaymentMethod(initialHotels.get(5).getHotelName(), Booking.PaymentMethod.SEPA, db);
        insertHotelPaymentMethod(initialHotels.get(5).getHotelName(), Booking.PaymentMethod.GIROPAY, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void loadInitialAddress(SQLiteDatabase db) {
        for(int i = 0; i < initialAddresses.size(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(COUNTRY_COLUMN, initialAddresses.get(i).getCountry());
            cv.put(CITY_COLUMN, initialAddresses.get(i).getCity());
            cv.put(STREET_ADDRESS_COLUMN, initialAddresses.get(i).getStreetAddress());
            cv.put(POSTAL_CODE_COLUMN, initialAddresses.get(i).getPostalCode());
            db.insert(ADDRESS_TABLE, null, cv);
        }
    }

    public void loadInitialHotels(SQLiteDatabase db) {
        for(int i = 0; i < initialHotels.size(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(HOTEL_NAME_COLUMN, initialHotels.get(i).getHotelName());
            cv.put(HOTEL_COUNTRY_COLUMN, initialHotels.get(i).getHotelAddress().getCountry());
            cv.put(HOTEL_CITY_COLUMN, initialHotels.get(i).getHotelAddress().getCity());
            cv.put(HOTEL_STREET_ADDRESS_COLUMN, initialHotels.get(i).getHotelAddress().getStreetAddress());
            cv.put(HOTEL_POSTAL_CODE_COLUMN, initialHotels.get(i).getHotelAddress().getPostalCode());
            cv.put(HOTEL_DESCRIPTION_COLUMN, initialHotels.get(i).getDescription());
            cv.put(HOTEL_PRICE_PER_NIGHT, initialHotels.get(i).getPricePerNight());
            cv.put(HOTEL_IMAGE_URL, initialHotels.get(i).getImageURL());
            db.insert(HOTEL_TABLE, null, cv);
        }
    }

    public void insertUser(User user, SQLiteDatabase db) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        ContentValues cv = new ContentValues();
        cv.put(USERNAME_COLUMN, user.getUsername());
        cv.put(USER_FIRST_NAME_COLUMN, user.getFirstName());
        cv.put(USER_LAST_NAME_COLUMN, user.getLastName());
        cv.put(USER_EMAIL_COLUMN, user.getEmail());
        cv.put(USER_COUNTRY_COLUMN, user.getUserAddress().getCountry());
        cv.put(USER_CITY_COLUMN, user.getUserAddress().getCity());
        cv.put(USER_STREET_ADDRESS_COLUMN, user.getUserAddress().getStreetAddress());
        cv.put(USER_POSTAL_CODE_COLUMN, user.getUserAddress().getPostalCode());
        cv.put(USER_PHONE_NUMBER_COLUMN, user.getPhoneNumber());
        cv.put(USER_GENDER_COLUMN, user.getGender().name());
        cv.put(USER_DATE_OF_BIRTH_COLUMN, sdf.format(user.getDateOfBirth()));
        db.insert(USER_TABLE, null, cv);
    }

    public void insertBooking(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        ContentValues cv = new ContentValues();
        cv.put(BOOKING_USERNAME_COLUMN, booking.getUser().getUsername());
        cv.put(BOOKING_HOTEL_NAME_COLUMN, booking.getHotel().getHotelName());
        cv.put(BOOKING_NUMBER_OF_ROOM, booking.getNumberOfRooms());
        cv.put(BOOKING_CHECK_IN_DATE_COLUMN, sdf.format(booking.getCheckInDate()));
        cv.put(BOOKING_CHECK_OUT_DATE_COLUMN, sdf.format(booking.getCheckOutDate()));
        cv.put(BOOKING_ADULT_NUMBER_COLUMN, booking.getAdultNumber());
        cv.put(BOOKING_CHILDREN_NUMBER_COLUMN, booking.getChildrenNumber());
        cv.put(BOOKING_TOTAL_PRICE_COLUMN, booking.getTotalPrice());
        cv.put(BOOKING_PAYMENT_METHOD_COLUMN, booking.getPaymentMethod().name());
        db.insert(BOOKING_TABLE, null, cv);
    }

    public void insertSavedHotel(User user, Hotel hotel, String checkInDate, String checkOutDate, int numberOfRooms, int adultNumber, int childrenNumber, double totalPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SAVED_USERNAME_COLUMN, user.getUsername());
        cv.put(SAVED_HOTEL_NAME_COLUMN, hotel.getHotelName());
        cv.put(SAVED_HOTEL_COUNTRY_COLUMN, hotel.getHotelAddress().getCountry());
        cv.put(SAVED_HOTEL_CITY_COLUMN, hotel.getHotelAddress().getCity());
        cv.put(SAVED_HOTEL_STREET_ADDRESS_COLUMN, hotel.getHotelAddress().getStreetAddress());
        cv.put(SAVED_HOTEL_POSTAL_CODE_COLUMN, hotel.getHotelAddress().getPostalCode());
        cv.put(SAVED_HOTEL_IMAGE_URL, hotel.getImageURL());
        cv.put(SAVED_CHECK_IN_DATE_COLUMN, checkInDate);
        cv.put(SAVED_CHECK_OUT_DATE_COLUMN, checkOutDate);
        cv.put(SAVED_ADULT_NUMBER_COLUMN, adultNumber);
        cv.put(SAVED_CHILDREN_NUMBER_COLUMN, childrenNumber);
        cv.put(SAVED_NUMBER_OF_ROOMS_COLUMN, numberOfRooms);
        cv.put(SAVED_TOTAL_PRICE_COLUMN, totalPrice);
        db.insert(SAVED_HOTEL_TABLE, null, cv);
    }

    public void insertHotelPaymentMethod(String hotelName, Booking.PaymentMethod paymentMethod, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(PAYMENT_METHOD_HOTEL_NAME_COLUMN, hotelName);
        cv.put(PAYMENT_METHOD_NAME_COLUMN, paymentMethod.name());
        db.insert(HOTEL_PAYMENT_METHOD_TABLE, null, cv);
    }

    public ArrayList<String> readAllPaymentMethodsByHotelName(String hotelName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> results = new ArrayList<>();
        String selection = "hotel_name = ?";
        String[] selectionArgs = {hotelName};

        Cursor cursor = db.query(HOTEL_PAYMENT_METHOD_TABLE, null, selection, selectionArgs, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                String paymentMethod = cursor.getString(1);
                results.add(paymentMethod);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return results;
    }

    public void deleteSavedHotel(User user, Hotel hotel, String check_in_date, String check_out_date) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "username = ? AND hotel_name = ? AND check_in_date = ? AND check_out_date = ?";
        String[] whereArgs = {user.getUsername(), hotel.getHotelName(), check_in_date, check_out_date};
        db.delete(SAVED_HOTEL_TABLE, whereClause, whereArgs);
    }

    public boolean readSavedHotelByHotelNameAndUsername(User user, Hotel hotel, String check_in_date, String check_out_date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "username = ? AND hotel_name = ? AND check_in_date = ? AND check_out_date = ?";
        String[] selectionArgs = {user.getUsername(), hotel.getHotelName(), check_in_date, check_out_date};

        Cursor cursor = db.query(SAVED_HOTEL_TABLE, null, selection, selectionArgs, null, null, null);
        if(cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public ArrayList<SavedHotel> readSavedHotelByUsername(String username) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<SavedHotel> resultList = new ArrayList<>();
        String query = "SELECT * FROM " + SAVED_HOTEL_TABLE + " WHERE " + SAVED_USERNAME_COLUMN + "=" + "'" + username + "'" + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                String readUsername = cursor.getString(0);
                String readHotelName = cursor.getString(1);
                String readCountry = cursor.getString(2);
                String readCity = cursor.getString(3);
                String readStreetAddress = cursor.getString(4);
                int readPostalCode = cursor.getInt(5);
                String imageURL = cursor.getString(6);
                String readCheckInDate = cursor.getString(7);
                String readCheckOutDate = cursor.getString(8);
                int readAdultNumber = cursor.getInt(9);
                int readChildrenNumber = cursor.getInt(10);
                int readNumberOfRooms = cursor.getInt(11);
                double readTotalPrice = cursor.getFloat(12);
                ArrayList<String> acceptedPaymentMethods = readAllPaymentMethodsByHotelName(readHotelName);
                try {
                    SavedHotel savedHotel = new SavedHotel(readUsername, readHotelName, new Address(readCountry, readCity, readStreetAddress, readPostalCode), sdf.parse(readCheckInDate), sdf.parse(readCheckOutDate), readNumberOfRooms, readAdultNumber, readChildrenNumber, readTotalPrice, imageURL);
                    savedHotel.setAcceptedPaymentMethods(acceptedPaymentMethods);
                    resultList.add(savedHotel);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return resultList;
    }

    public ArrayList<Booking> readBookingByUsername(String username) {
        ArrayList<Booking> bookings = new ArrayList<>();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String query = "SELECT * FROM " + BOOKING_TABLE + " WHERE " + BOOKING_USERNAME_COLUMN + "=" + "'" + username + "'" + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                String readUsername = cursor.getString(0);
                String readHotelName = cursor.getString(1);
                int readNumberOfRoom = cursor.getInt(2);
                String readCheckInDate = cursor.getString(3);
                String readCheckOutDate = cursor.getString(4);
                int readAdultNumber = cursor.getInt(5);
                int readChildrenNumber = cursor.getInt(6);
                double readTotalPrice = cursor.getFloat(7);
                String readPaymentMethod = cursor.getString(8);

                User user = readUserBy(USERNAME_COLUMN, readUsername);
                Hotel hotel = readHotelBy(HOTEL_NAME_COLUMN, readHotelName);
                Booking.PaymentMethod paymentMethod = Booking.PaymentMethod.stringToEnum(readPaymentMethod);

                try {
                    Booking booking = new Booking(user, hotel, readNumberOfRoom, sdf.parse(readCheckInDate), sdf.parse(readCheckOutDate), readAdultNumber, readChildrenNumber, readTotalPrice, paymentMethod);
                    bookings.add(booking);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookings;
    }

    public User readUserBy(String column, String email) {
        User user;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String queryUser = "SELECT * FROM " + USER_TABLE + " WHERE " + column + "=" + "'"+email+"'" + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryUser, null);
        if(cursor.moveToFirst()) {
            String readUsername = cursor.getString(0);
            String readFirstName = cursor.getString(1);
            String readLastName = cursor.getString(2);
            String readEmail = cursor.getString(3);
            String readCountry = cursor.getString(4);
            String readCity = cursor.getString(5);
            String readAddress = cursor.getString(6);
            int readPostalCode = cursor.getInt(7);
            String readPhoneNumber = cursor.getString(8);
            String readGender = cursor.getString(9);
            String readDateOfBirth = cursor.getString(10);
            try {
                user = new User(readUsername, readFirstName, readLastName, readEmail, readPhoneNumber, new Address(readCountry, readCity, readAddress, readPostalCode), sdf.parse(readDateOfBirth), User.Gender.valueOf(readGender));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
        cursor.close();
        db.close();
        return user;
    }

    public void updateUser(User user) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME_COLUMN, user.getUsername());
        values.put(USER_FIRST_NAME_COLUMN, user.getFirstName());
        values.put(USER_LAST_NAME_COLUMN, user.getLastName());
        values.put(USER_EMAIL_COLUMN, user.getEmail());
        values.put(USER_COUNTRY_COLUMN, user.getUserAddress().getCountry());
        values.put(USER_CITY_COLUMN, user.getUserAddress().getCity());
        values.put(USER_STREET_ADDRESS_COLUMN, user.getUserAddress().getStreetAddress());
        values.put(USER_POSTAL_CODE_COLUMN, String.valueOf(user.getUserAddress().getPostalCode()));
        values.put(USER_PHONE_NUMBER_COLUMN, user.getPhoneNumber());
        values.put(USER_GENDER_COLUMN, user.getGender().name());
        values.put(USER_DATE_OF_BIRTH_COLUMN, sdf.format(user.getDateOfBirth()));
        String whereClause = USER_EMAIL_COLUMN + "= ? ";
        String[] whereArgs = {user.getEmail()};
        db.update(USER_TABLE, values, whereClause, whereArgs);
        db.close();
    }

    public Hotel readHotelBy(String column, String value) {
        Hotel hotel;
        String queryUser = "SELECT * FROM " + HOTEL_TABLE + " WHERE " + column + "=" + "'"+value+"'" + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryUser, null);
        if(cursor.moveToFirst()) {
            String readHotelName = cursor.getString(0);
            String readCountry = cursor.getString(1);
            String readCity = cursor.getString(2);
            String readStreetAddress = cursor.getString(3);
            int readPostalCode = cursor.getInt(4);
            String readDescription = cursor.getString(5);
            double readPricePerNight = cursor.getFloat( 6);
            String imageURL = cursor.getString(7);
            ArrayList<String> acceptedPaymentMethods = readAllPaymentMethodsByHotelName(readHotelName);
            hotel = new Hotel(readHotelName, new Address(readCountry, readCity, readStreetAddress, readPostalCode), readDescription, readPricePerNight, imageURL);
            hotel.setAcceptedPaymentMethods(acceptedPaymentMethods);
        } else {
            return null;
        }
        cursor.close();
        db.close();
        return hotel;
    }

    public ArrayList<Hotel> readAllHotels() {
        ArrayList<Hotel> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + HOTEL_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                String readHotelName = cursor.getString(0);
                String readCountry = cursor.getString(1);
                String readCity = cursor.getString(2);
                String readStreetAddress = cursor.getString(3);
                int readPostalCode = cursor.getInt(4);
                String readDescription = cursor.getString(5);
                double readPricePerNight = cursor.getFloat( 6);
                String imageURL = cursor.getString(7);
                ArrayList<String> acceptedPaymentMethods = readAllPaymentMethodsByHotelName(readHotelName);
                Hotel hotel = new Hotel(readHotelName, new Address(readCountry, readCity, readStreetAddress, readPostalCode), readDescription, readPricePerNight, imageURL);
                hotel.setAcceptedPaymentMethods(acceptedPaymentMethods);
                arrayList.add(hotel);
            } while(cursor.moveToNext());
        } else {
            return arrayList;
        }

        cursor.close();
        db.close();
        return arrayList;
    }
}
