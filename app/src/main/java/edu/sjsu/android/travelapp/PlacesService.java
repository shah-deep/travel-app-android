package edu.sjsu.android.travelapp;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.Call;

public interface PlacesService {
    @GET("place/nearbysearch/json")
    Call<NearbySearchResponse> getNearbyRestaurants(
            @Query("location") String location,
            @Query("radius") int radius,
            @Query("type") String type,
            @Query("key") String apiKey
    );
}
