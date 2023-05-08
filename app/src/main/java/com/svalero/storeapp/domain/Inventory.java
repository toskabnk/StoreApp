package com.svalero.storeapp.domain;

import java.io.Serializable;

public class Inventory implements Serializable {
    private long id;
    private String location;
    private String address;
    private String lastUpdate;
    private float totalValue;
    private double latitude;
    private double longitude;

    public Inventory(long id, String location, String address, String lastUpdate, float totalValue, double latitude, double longitude) {
        this.id = id;
        this.location = location;
        this.address = address;
        this.lastUpdate = lastUpdate;
        this.totalValue = totalValue;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public float getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(float totalValue) {
        this.totalValue = totalValue;
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
}
