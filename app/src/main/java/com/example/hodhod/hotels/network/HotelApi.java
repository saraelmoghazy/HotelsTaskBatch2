package com.example.hodhod.hotels.network;

import com.example.hodhod.hotels.models.Hotel;

import java.util.Observable;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HotelApi {

    @GET("hotels")
    Flowable<Hotel> getHotel();
}
