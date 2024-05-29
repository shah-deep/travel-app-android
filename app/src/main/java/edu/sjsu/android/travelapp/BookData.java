package edu.sjsu.android.travelapp;

public class BookData {
    private String User_id;
    private String BookingId ;
    private String BookingDate;
    private String BookingPrice;
    private String LocationId;
    private String PackageId;

    BookData(String User_id, String BookingId, String BookingDate,
             String BookingPrice, String LocationId, String PackageId){
        this.User_id = User_id;
        this.BookingId = BookingId;
        this.BookingDate = BookingDate;
        this.BookingPrice = BookingPrice;
        this.LocationId = LocationId;
        this.PackageId = PackageId;

    }
    public String getPackageId() {
        return PackageId;
    }

    public void setPackageId(String PackageId) {
        this.PackageId = PackageId;
    }

    public String getLocationId() {
        return LocationId;
    }

    public void setLocationId(String LocationId) {
        this.LocationId = LocationId;
    }

    public String getBookingPrice() {
        return BookingPrice;
    }

    public void setBookingPrice(String BookingPrice) {
        this.BookingPrice = BookingPrice;
    }

    public String getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(String BookingDate) {
        this.BookingDate = BookingDate;
    }

    public String getBookingId() {
        return BookingId;
    }

    public void setBookingId(String BookingId) {
        this.BookingId = BookingId;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String User_id) {
        this.User_id = User_id;
    }
}
