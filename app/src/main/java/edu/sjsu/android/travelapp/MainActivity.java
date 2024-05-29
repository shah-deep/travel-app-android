package edu.sjsu.android.travelapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewLocations;
    private PopularAdapter populularAdapter;

    private SearchView searchView;
    private ArrayList<HomeLocations> allItems;
    public static ArrayList<HomeLocations> allLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        fetchData();
        initMenuBar();

    }



    private void initMenuBar() {
        ImageView menuBar = findViewById(R.id.menuBar);
        menuBar.setOnClickListener(this::showPopupMenu);
    }

    private void fetchData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-west-2.aws.data.mongodb-api.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LocationService service = retrofit.create(LocationService.class);

        Log.d("SERVICE", "NOT NULL");
        Call<List<Location>> call = service.getLocations();

        Log.d("REQUEST Body", String.valueOf(call.request().body()));
        Log.d("REQUEST Url", String.valueOf(call.request().url()));

        call.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                Log.d("REQUEST Body", String.valueOf(call.request().body()));
                Log.d("RESPONSE Code", String.valueOf(response.code()));
                if (response.isSuccessful()) {
                    Log.d("RESPONSE", "SUCCESS");

                    List<Location> res = response.body();
                    assert res != null;
                    // results = res; //.getResults();
                    System.out.println(res);

                    ArrayList<HomeLocations> homeLocationsList = new ArrayList<>();

                    for (Location loc : res) {
                        HomeLocations homeLocation = getHomeLocations(loc);
                        homeLocationsList.add(homeLocation);
                    }

                    allItems = homeLocationsList;
                    allLocations = allItems;
                    initRecyclerView();

                } else {
                    Log.d("RESPONSE", "ERROR");
                }
            }

            @NonNull
            private HomeLocations getHomeLocations(Location loc) {
                String firstPic = loc.getImages().isEmpty() ? "" : loc.getImages().get(0); // Assuming 'getImages()' returns a list of image URLs.
                return new HomeLocations(
                        loc.getId(),
                        loc.getLocationName(),
                        loc.getCountry(),
                        loc.getRating(),
                        firstPic,
                        loc.getCoordinates().getLatitude(),
                        loc.getCoordinates().getLongitude(),
                        loc.getImages(),
                        loc.getPackages()
                );
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                // Handle network error
                Log.d("NETWORK", "FAIL: " + t.getMessage());
            }
        });

    }

    public void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(MainActivity.this, view);
        popup.getMenuInflater().inflate(R.menu.main_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                // if (id == R.id.home) {
                    // Return home
                // } else
                    if (id == R.id.action_booking_history) {
                    // Action for "Booking History"
                    Intent intent = new Intent(MainActivity.this, DisplayBookings.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.action_app_info) {
                    // Action for "App Info"
                    Context context = getApplicationContext();
                    showAlertDialog();
                    return true;
                } else {
                    return false;
                }
            }
        });
        popup.show();
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("App Info");
        builder.setMessage("Trip Genie - A Travel Package Booking App \n\nBy: \n\n Team 7 \n\n Members: \n\n   Bhargavi Chevva \n\n   Ketan Jadhav \n\n   Jovian Jaison \n\n   Deep Shah\n\n\n\n");
        builder.setPositiveButton("OK", (dialog, which) -> {

        });
        builder.show();
    }

    private void initRecyclerView() {
        System.out.println(allItems);

        recyclerViewLocations = findViewById(R.id.view_loc);
        recyclerViewLocations.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        populularAdapter = new PopularAdapter(this, allItems);
        populularAdapter.setRecyclerViewInterface(position -> {
            Intent intent = new Intent(MainActivity.this, DestinationActivity.class);
            intent.putExtra("locationID", populularAdapter.getItems().get(position).getId());
            startActivity(intent);
        });
        recyclerViewLocations.setAdapter(populularAdapter);

        searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                populularAdapter.filter(newText);
                return true;
            }
        });


    }
}