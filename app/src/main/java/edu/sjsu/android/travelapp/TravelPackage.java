package edu.sjsu.android.travelapp;

public class TravelPackage {
    /*
    Package Id
		Package Name
		Package Price
		Package start date
		Package end date
		Package remainingCount
     */
    String id;
    String name;
    float price;
    int remainingCount;
    String startDate;
    String endDate;
    TravelPackage(String id, String name, float price, int remainingCount, String startDate, String endDate)
    {
        this.id = id;
        this.name = name;
        this.price=price;
        this.remainingCount = remainingCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
