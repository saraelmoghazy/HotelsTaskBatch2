package com.example.hodhod.hotels.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hodhod.hotels.models.Hotel;
import com.example.hodhod.hotels.network.ServiceGenerator;
import com.example.hodhod.hotels.util.Resource;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HotelViewModel extends ViewModel {
    private static final String TAG = "HotelViewModel";
    private MutableLiveData<Resource<Hotel>> hotelLiveData = new MutableLiveData<>();

    public HotelViewModel() {
        getHotel();
    }

    public void getHotel() {
        ServiceGenerator.getHotelApi()
                .getHotel().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new io.reactivex.Observer<Hotel>() {
            @Override
            public void onSubscribe(Disposable d) {
                hotelLiveData.setValue(Resource.loading(true));
            }

            @Override
            public void onNext(Hotel response) {
                hotelLiveData.setValue(Resource.loading(false));
                hotelLiveData.setValue(Resource.success(response));
            }

            @Override
            public void onError(Throwable e) {
                hotelLiveData.setValue(Resource.loading(false));
                hotelLiveData.setValue(Resource.<Hotel>error(e.getMessage()));
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public MutableLiveData<Resource<Hotel>> getHotelLiveData() {
        return hotelLiveData;
    }
}
