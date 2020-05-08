package com.example.pointme.models;

import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;

public class BookingDetails {

    private String date;
    private Location location;
    private String time;

    public BookingDetails() {

    }
    public BookingDetails(String date, Location location, String time) {
        this.date = date;
        this.location = location;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
