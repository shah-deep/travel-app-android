package edu.sjsu.android.travelapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BookingDataInterface {

    @POST("app/application-0-wkqnnmj/endpoint/booking")
    Call<BookData> createPost(
            @Body BookData bookData
    );
}
