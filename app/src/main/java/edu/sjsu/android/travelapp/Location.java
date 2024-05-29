package edu.sjsu.android.travelapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Location {
    @SerializedName("_id")
    private String id;
    @SerializedName("Location_name")
    private String locationName;
    @SerializedName("Location_coordinates")
    private LocationCoordinates coordinates;
    @SerializedName("Location_images")
    private List<String> images;
    @SerializedName("Packages")
    private List<Packages> packages;
    @SerializedName("Rating")
    private int rating;
    @SerializedName("Country")
    private String country;

    // Getters and setters for each field


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public LocationCoordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LocationCoordinates coordinates) {
        this.coordinates = coordinates;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<Packages> getPackages() {
        return packages;
    }

    public void setPackages(List<Packages> packages) {
        this.packages = packages;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

class LocationCoordinates {
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;

    // Getters and setters

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
