package edu.sjsu.android.travelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;

//import edu.sjsu.android.travelapp.databinding.ActivityMainBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DestinationActivity extends AppCompatActivity implements OnMapReadyCallback, RecyclerViewInterface {

    private GoogleMap googleMap;
    private String locationID;
    private double latitude;
    private double longitude;
    private String locationTitle;

    private String selectedTypes = "";
    ArrayList<TravelPackage> travelPackagesList = new ArrayList<TravelPackage>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        this.locationID = getIntent().getStringExtra("locationID");
        Log.d("GOT ID", this.locationID);
        setUpPackages();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);

        CheckBox restaurantBox = findViewById(R.id.checkbox_restaurant);
        restaurantBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSelectedTypes());

        CheckBox parkBox = findViewById(R.id.checkbox_park);
        parkBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSelectedTypes());

        CheckBox clubBox = findViewById(R.id.checkbox_club);
        clubBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSelectedTypes());

        CheckBox museumBox = findViewById(R.id.checkbox_museum);
        museumBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSelectedTypes());

        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

        RecyclerView recyclerView = findViewById(R.id.travelPackages);
        TravelPackageAdapter travelPackageAdapter = new TravelPackageAdapter(this, travelPackagesList, this);
        recyclerView.setAdapter(travelPackageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    protected void setUpPackages() {
        HomeLocations currLocation = null;
        for (HomeLocations location : MainActivity.allLocations) {
            if(location.getId().equals(this.locationID)){
                currLocation = location;
                break;
            }
        }

        assert currLocation != null;

        this.latitude = currLocation.getLatitude();
        this.longitude = currLocation.getLongitude();
        this.locationTitle = currLocation.getTitle();

        for(Packages pkg : currLocation.getPackages()) {
            travelPackagesList.add(new TravelPackage(pkg.getPackageId(), pkg.getname(), pkg.getprice(), pkg.getRemainingCount(), pkg.getStartDate(), pkg.getEndDate() ));
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(DestinationActivity.this, DetailActivity.class);
        intent.putExtra("locationID", this.locationID);
        intent.putExtra("name", travelPackagesList.get(position).name);
        intent.putExtra("remainingCount", travelPackagesList.get(position).remainingCount);
        intent.putExtra("startDate", travelPackagesList.get(position).startDate);
        intent.putExtra("endDate", travelPackagesList.get(position).endDate);
        intent.putExtra("price", travelPackagesList.get(position).price);
        intent.putExtra("id", travelPackagesList.get(position).id);

        startActivity(intent);
    }

    private void updateSelectedTypes() {
        selectedTypes = "";  // Reset the string
        StringBuilder sb = new StringBuilder();

        int type = 0;

        if (((CheckBox) findViewById(R.id.checkbox_restaurant)).isChecked()) {
            sb.append("restaurant,");
            type = 1;
        }
        if (((CheckBox) findViewById(R.id.checkbox_park)).isChecked()) {
            sb.append("park,");
            type = 2;
        }
        if (((CheckBox) findViewById(R.id.checkbox_club)).isChecked()) {
            sb.append("club,");
            type = 3;
        }
        if (((CheckBox) findViewById(R.id.checkbox_museum)).isChecked()) {
            sb.append("museum,");
            type = 4;
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        selectedTypes = sb.toString();

        if (selectedTypes.isEmpty()) {
            return;
        }

        callPlacesAPI(selectedTypes, type);
    }

    private void callPlacesAPI(String types, int placeType) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlacesService service = retrofit.create(PlacesService.class);

        String locationString = String.format("%f,%f", latitude, longitude);
        if (service != null) {
            Log.d("SERVICE", "NOT NULL");
            Call<NearbySearchResponse> call = service.getNearbyRestaurants(
                    locationString,
                    500, // Radius in meters (optional)
                    types,
                    "<Add Your API Key Here>"
            );

            Log.d("REQUEST", String.valueOf(call.request().body()));
            Log.d("REQUEST", String.valueOf(call.request().url()));

            call.enqueue(new Callback<NearbySearchResponse>() {
                @Override
                public void onResponse(Call<NearbySearchResponse> call, Response<NearbySearchResponse> response) {
                    Log.d("REQUEST", String.valueOf(call.request().body()));
                    Log.d("RESPONSE", String.valueOf(response.code()));
                    if (response.isSuccessful()) {
                        Log.d("RESPONSE", "SUCCESS");

                        NearbySearchResponse nearbySearchResponse = response.body();
                        List<Restaurant> restaurants = parseRestaurants(nearbySearchResponse.getResults());
                        addRestaurantMarkersToMap(restaurants, placeType);
                    } else {
                        Log.d("RESPONSE", "ERROR");
                        // Handle API error
                    }
                }

                @Override
                public void onFailure(Call<NearbySearchResponse> call, Throwable t) {
                    // Handle network error
                    Log.d("NETWORK", "FAIL");
                }
            });
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng loc = new LatLng(latitude, longitude);
        this.googleMap = googleMap;
        googleMap.addMarker(new MarkerOptions().position(loc).title(locationTitle));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 19));
    }

    private List<Restaurant> parseRestaurants(List<NearbySearchResponse.Result> results) {
        List<Restaurant> restaurants = new ArrayList<>();
        for (NearbySearchResponse.Result result : results) {
            Restaurant restaurant = new Restaurant();
            restaurant.setName(result.getName());

            NearbySearchResponse.Result.Geometry geometry = result.getGeometry();
            if (geometry != null && geometry.getLocation() != null) {
                restaurant.setLatitude(geometry.getLocation().getLat());
                restaurant.setLongitude(geometry.getLocation().getLng());
            }

            restaurants.add(restaurant);
        }
        return restaurants;
    }

    private void addRestaurantMarkersToMap(List<Restaurant> restaurants, int placeType) {

        int markerColor = (int) BitmapDescriptorFactory.HUE_AZURE;

        switch(placeType){
            case 0:
                break;
            case 1:
                markerColor = (int) BitmapDescriptorFactory.HUE_AZURE;
                break;
            case 2:
                markerColor = (int) BitmapDescriptorFactory.HUE_GREEN;
                break;
            case 3:
                markerColor = (int) BitmapDescriptorFactory.HUE_MAGENTA;
                break;
            case 4:
                markerColor = (int) BitmapDescriptorFactory.HUE_YELLOW;
                break;
        }

        Log.d("RESTAURANT", "INIT");
        for (Restaurant restaurant : restaurants) {
            Log.d("RESTAURANT", restaurant.getName());
            LatLng location = new LatLng(restaurant.getLatitude(), restaurant.getLongitude());
            if(googleMap != null) {
                Log.d("MAP", "NOT NULL");
                googleMap.addMarker(new MarkerOptions()
                        .position(location)
                        .icon(BitmapDescriptorFactory.defaultMarker(markerColor))
                        .title(restaurant.getName()));
            }
        }
    }
}
