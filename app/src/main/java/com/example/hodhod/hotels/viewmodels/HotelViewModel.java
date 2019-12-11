package com.example.hodhod.hotels.viewmodels;

import android.util.Log;

import com.example.hodhod.hotels.models.Hotel;
import com.example.hodhod.hotels.network.ServiceGenerator;
import com.example.hodhod.hotels.util.Resource;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HotelViewModel extends ViewModel {

    private static final String TAG = "HotelViewModel";

    private MediatorLiveData<Resource<Hotel>> hotel;

    public LiveData<Resource<Hotel>> getHotel() {

        if (hotel == null) {
            hotel = new MediatorLiveData<>();
            hotel.setValue(Resource.loading((Hotel) null));

            final LiveData<Resource<Hotel>> source = LiveDataReactiveStreams.fromPublisher(
                    ServiceGenerator.getHotelApi()
                            .getHotel()
                            .onErrorReturn(new Function<Throwable, Hotel>() {
                                @Override
                                public Hotel apply(Throwable throwable) throws Exception {
                                    Log.d(TAG, "apply: " + throwable.getMessage());
                                    Hotel hotel = new Hotel();
                                    hotel.setId(-1);
                                    return hotel;
                                }
                            })
                            .map(new Function<Hotel, Resource<Hotel>>() {
                                @Override
                                public Resource<Hotel> apply(Hotel hotel) throws Exception {
                                    if (hotel.getId() == -1) {
                                        return Resource.error("error retrieving data", null);
                                    }
                                    return Resource.success(hotel);
                                }
                            })

                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
            );

            hotel.addSource(source, new Observer<Resource<Hotel>>() {
                @Override
                public void onChanged(Resource<Hotel> hotelResource) {
                    hotel.setValue(hotelResource);
                    hotel.removeSource(source);
                }
            });
        }

        return hotel;
    }

}
