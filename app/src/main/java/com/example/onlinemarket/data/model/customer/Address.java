package com.example.onlinemarket.data.model.customer;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Address")
public class Address {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "primaryId")
    private int primaryId;

    @SerializedName("id")
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "information")
    private String information;
    @ColumnInfo(name = "latitude")
    private double latitude;
    @ColumnInfo(name = "longitude")
    private double longitude;
    @ColumnInfo(name = "selected")
    private boolean selected;

    public Address() {
    }


    public Address(String name, String information,
                   double latitude, double longitude,
                   boolean selected) {
        this.name = name;
        this.information = information;
        this.latitude = latitude;
        this.longitude = longitude;
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId( int id) {
        this.id = id;
    }

    public void setPrimaryId(int primaryId) {
        this.primaryId = primaryId;
    }

    public int getPrimaryId() {
        return primaryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

   /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address myAddress = (Address) o;
        return id == myAddress.id;
    }*/

    public String showAddress() {
        return name + " : " + information;
    }
}
