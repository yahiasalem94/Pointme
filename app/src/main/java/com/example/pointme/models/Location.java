package com.example.pointme.models;

import com.google.firebase.firestore.GeoPoint;

public class Location {
    private GeoPoint geoPoint;
    private boolean editableByCR;
    private boolean editableBySP;
    private String name;


    public Location() {

    }

    public Location(GeoPoint geoPoint, boolean editableByCR, boolean editableBySP, String name) {
        this.geoPoint = geoPoint;
        this.editableByCR = editableByCR;
        this.editableBySP = editableBySP;
        this.name = name;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public boolean isEditableByCR() {
        return editableByCR;
    }

    public void setEditableByCR(boolean editableByCR) {
        this.editableByCR = editableByCR;
    }

    public boolean isEditableBySP() {
        return editableBySP;
    }

    public void setEditableBySP(boolean editableBySP) {
        this.editableBySP = editableBySP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
