package edu.sjsu.android.travelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayBookings extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookingAdapter adapter;
    private List<Booking> bookingList;
    private BookingService bookingService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_bookings); // Use the modified activity_main.xml

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookingList = new ArrayList<>();  // Initialize booking list
        adapter = new BookingAdapter(this, bookingList);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();  // Update adapter after adding data
        String userId = "6637149d949df174011dd09e";  // Example user ID

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-west-2.aws.data.mongodb-api.com/")
                .addConverterFactory(GsonConverterFactory.create())  // Add Gson converter
                .build();

        bookingService = retrofit.create(BookingService.class);

        fetchBookings(userId);
    }

    private void fetchBookings(String userId) {
        bookingService.getBookings(userId).enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                Log.d("REQUEST", String.valueOf(call.request().url()));
                Log.d("REQUEST", String.valueOf(call.request().body()));
                if (response.isSuccessful()) {
                    Log.d("RESPONSE", "SUCCESS");
                    Log.d("BOOKING", String.valueOf(response.body()));
                    bookingList.clear();  // Clear existing data
                    bookingList.addAll(response.body());
                    Collections.reverse(bookingList);
                    adapter.notifyDataSetChanged();  // Update adapter
                } else {
                    // Handle API call failure
                }
            }

            @Override
            public void onFailure(Call<List<Booking>> call, Throwable t) {
                // Handle network error
            }
        });
    }

}