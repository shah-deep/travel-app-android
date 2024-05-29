package edu.sjsu.android.travelapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookingService {

    @GET("/app/application-0-wkqnnmj/endpoint/booking")
    Call<List<Booking>> getBookings(@Query("_id") String userId);

    @GET("/app/application-0-wkqnnmj/endpoint/location")
    Call<List<Location>> getLocationDetails(@Query("_id") String locationId);
}


