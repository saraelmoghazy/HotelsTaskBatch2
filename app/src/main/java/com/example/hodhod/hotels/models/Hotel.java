package com.example.hodhod.hotels.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hotel {

    @SerializedName("hotel")
    private List<HotelItem> hotel;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHotel(List<HotelItem> hotel) {
        this.hotel = hotel;
    }

    public List<HotelItem> getHotel() {

        return hotel;
    }

    @Override
    public String toString() {
        return
                "Hotel{" +
                        "hotel = '" + hotel + '\'' +
                        "}";
    }
}