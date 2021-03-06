package com.example.onlinemarket.data.model.customer;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Address")
public class Address {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "addressId")
    private Integer id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "billingAddress")
    private String billingAddress;
    @ColumnInfo(name = "latitude")
    private double latitude;
    @ColumnInfo(name = "longitude")
    private double longitude;
    @ColumnInfo(name = "selected")
    private boolean selected;

    public Address() {
    }


    public Address(String name, String billingAddress,
                   double latitude, double longitude,
                   boolean selected) {
        this.name = name;
        this.billingAddress = billingAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.selected = selected;
    }


    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
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
        return name + " : " + billingAddress;
    }
}
