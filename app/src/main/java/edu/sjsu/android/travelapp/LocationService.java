package edu.sjsu.android.travelapp;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.Call;

public interface LocationService {
    @GET("app/application-0-wkqnnmj/endpoint/location/all")
    Call<List<Location>> getLocations();
}