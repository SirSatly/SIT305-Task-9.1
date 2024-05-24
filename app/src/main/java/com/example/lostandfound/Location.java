package com.example.lostandfound;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;

public class Location {

    private String name;
    private double longitude;
    private double latitude;

    public Location(String name, double longitude, double latitude)
    {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Location(Place place)
    {
        this.name = place.getName();
        this.latitude = place.getLatLng().latitude;
        this.longitude = place.getLatLng().longitude;
    }

    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }
}
