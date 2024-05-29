package edu.sjsu.android.travelapp;

import com.google.gson.annotations.SerializedName;

class Packages {
    @SerializedName("Package_Id")
    private String packageId;
    @SerializedName("Package_Name")
    private String name;
    @SerializedName("Package_Price")
    private int price;
    @SerializedName("Package_start_date")
    private String startDate;
    @SerializedName("Package_end_date")
    private String endDate;
    @SerializedName("Package_remainingCount")
    private int remainingCount;

    // Getters and setters


    public Packages(String packageId, String name, int price, String startDate, String endDate, int remainingCount) {
        this.packageId = packageId;
        this.name = name;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remainingCount = remainingCount;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public int getprice() {
        return price;
    }

    public void setprice(int price) {
        this.price = price;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getRemainingCount() {
        return remainingCount;
    }

    public void setRemainingCount(int remainingCount) {
        this.remainingCount = remainingCount;
    }
}
