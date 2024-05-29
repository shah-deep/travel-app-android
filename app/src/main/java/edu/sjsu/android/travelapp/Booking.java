package edu.sjsu.android.travelapp;

import com.google.gson.annotations.SerializedName;

public class Booking {

    @SerializedName("_id")
    private String id;
    @SerializedName("User_id")
    private String User_id;
    @SerializedName("BookingId")
    private String bookingId;
    @SerializedName("BookingDate")
    private String bookingDate;
    @SerializedName("BookingPrice")
    private String bookingPrice;
    @SerializedName("LocationId")
    private String locationId;
    private String packageId;

    public Booking(String _id, String User_id, String BookingId, String BookingDate, String BookingPrice, String LocationId, String PackageId) {
        this.id = _id;
        this.User_id = User_id;
        this.bookingId = BookingId;
        this.bookingDate = BookingDate;
        this.bookingPrice = BookingPrice;
        this.locationId = LocationId;
        this.packageId = PackageId;
    }

    public String getUser_id() {
        return User_id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getBookingPrice() {
        return bookingPrice;
    }

    public String getLocation() {
        for (HomeLocations location : MainActivity.allLocations) {
            if(location.getId().equals(locationId)){
                return location.getTitle() + ", " + location.getLocation();
            }
        }

        return locationId;
    }

    public String getPackageId() {
        return packageId;
    }

}

