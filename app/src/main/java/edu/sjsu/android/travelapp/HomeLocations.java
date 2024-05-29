package edu.sjsu.android.travelapp;

import java.io.Serializable;
import java.util.List;

public class HomeLocations implements Serializable {

    private String id;
    private String title;
    private String location;
    // private String description;
    private double rating;
    private String pic;

    private double latitude;
    private double longitude;
    private List<String> allPics;
    private List<Packages> packages;

    public HomeLocations(String id, String title, String location, double rating, String pic, double latitude, double longitude, List<String> allPics, List<Packages> packages) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.rating = rating;
        this.pic = pic;
        this.latitude = latitude;
        this.longitude = longitude;
        this.allPics = allPics;
        this.packages = packages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

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

    public List<String> getAllPics() {
        return allPics;
    }

    public void setAllPics(List<String> allPics) {
        this.allPics = allPics;
    }

    public List<Packages> getPackages() {
        return packages;
    }

    public void setPackages(List<Packages> packages) {
        this.packages = packages;
    }
}
