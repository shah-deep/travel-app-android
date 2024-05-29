package edu.sjsu.android.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.security.SecureRandom;
import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {
    String locationID;
    String name;
    float price;
    int remainingCount;
    String startDate;
    String endDate;
    String pkg_id;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static SecureRandom random = new SecureRandom();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        locationID = getIntent().getStringExtra("locationID");
        name = getIntent().getStringExtra("name");
        price = getIntent().getFloatExtra("price", (float)100.00);
        remainingCount = getIntent().getIntExtra("remainingCount", 0);
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        pkg_id = getIntent().getStringExtra("id");

        TextView nameTextView = findViewById(R.id.name);
        nameTextView.setText(name);

        TextView avSlotsTextView = findViewById(R.id.avSlots);
        avSlotsTextView.setText("Our Travel app provides the best quality services with the best possible deals!"+"\nJust " +remainingCount+" more slots left !!"+"\n\nHit Book now to SAVE THE DEAL!");

        TextView priceTextView = findViewById(R.id.price);
        priceTextView.setText("Price: $"+price);

        TextView startDateTextView = findViewById(R.id.startDate);
        startDateTextView.setText("Start Date: "+startDate);

        TextView endDateTextView = findViewById(R.id.endDate);
        endDateTextView.setText("End Date: "+endDate);

        Button book = findViewById(R.id.book);

        book.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendPostRequest();
                        Intent i = new Intent(DetailActivity.this,BookActivity.class);
                        startActivity(i);
                    }
                }
        );

    }

    public static String generateRandomAlphaNumeric(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    protected void sendPostRequest(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-west-2.aws.data.mongodb-api.com/")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        BookingDataInterface retrofitAPI = retrofit.create(BookingDataInterface.class);
        String bookingID = generateRandomAlphaNumeric(5);
        String currentDate = null; // startDate+" - "+endDate
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = LocalDate.now()+"";
        } else {
            currentDate =startDate+"";
        }
        BookData bookData = new BookData("6637149d949df174011dd09e", bookingID,currentDate, price+"", locationID, pkg_id);
        Call<BookData> call = retrofitAPI.createPost(bookData);

        call.enqueue(new Callback<BookData>() {
             @Override
             public void onResponse(Call<BookData> call, Response<BookData> response) {
                 // this method is called when we get response from our api.
                 BookData responseFromAPI = response.body();

                 // on below line we are getting our data from modal class
                 // and adding it to our string.
                 String responseString = "Response Code : " + response.code();
                 Log.e("response", responseString);
                 if (response.isSuccessful()) {
                     Toast.makeText(DetailActivity.this, "Travel Package Booked!", Toast.LENGTH_SHORT).show();
                     Log.d("RESPONSE", "SUCCESS");
                     Log.d("BOOKING", String.valueOf(response.body()));

                 } else {
                     Log.d("Fail in request","Post failed");
                     // Handle API call failure (e.g., display a toast)
                 }
             }

             @Override
             public void onFailure(Call<BookData> call, Throwable t) {
                 System.out.println("Booking failed!");
             }
         });
    }
}