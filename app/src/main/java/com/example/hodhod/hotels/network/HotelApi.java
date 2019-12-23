package com.example.hodhod.hotels.network;

import com.example.hodhod.hotels.models.Hotel;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface HotelApi {
    @GET("hotels")
    Observable<Hotel> getHotel();
}
