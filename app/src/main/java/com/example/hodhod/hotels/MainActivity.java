package com.example.hodhod.hotels;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.hodhod.hotels.adapterds.HotelRecyclerAdapter;
import com.example.hodhod.hotels.adapterds.OnHotelListener;
import com.example.hodhod.hotels.models.Hotel;
import com.example.hodhod.hotels.models.HotelItem;
import com.example.hodhod.hotels.util.Resource;
import com.example.hodhod.hotels.viewmodels.HotelViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class MainActivity extends AppCompatActivity implements OnHotelListener {

    private static final String TAG = "MainActivity";

    // ui components
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private ConstraintLayout mConstraintLayout;

    // vars
    private HotelViewModel mHotelViewModel;
    private HotelRecyclerAdapter mAdapter;
    private List<HotelItem> mHotelList;

    // constants
    private static final int NUM_COLUMNS = 2; // number of columns for staggeredGridLayout


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHotelList = new ArrayList<>();
        mHotelViewModel = ViewModelProviders.of(this).get(HotelViewModel.class);
        mRecyclerView = findViewById(R.id.hotels_recyclerView);
        mProgressBar = findViewById(R.id.progressbar);
        mConstraintLayout = findViewById(R.id.constraint_layout);
        observeHotels();
    }

    // initialize recycler view with adapter
    public void initRecycler() {
        mAdapter = new HotelRecyclerAdapter(this, this, mHotelList);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void showProgressbar(boolean isVisible) {
        if (isVisible) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    public void observeHotels() {
       // mHotelViewModel.getHotel().removeObservers(this);
        mHotelViewModel.getHotelLiveData().observe(this, new Observer<Resource<Hotel>>() {
            @Override
            public void onChanged(Resource<Hotel> hotelResource) {

                if (hotelResource != null) {

                    switch (hotelResource.status) {

                        case LOADING: {
                            if (hotelResource.isLoading)
                                showProgressbar(true);
                            else showProgressbar(false);
                            break;
                        }

                        case SUCCESS: {
                            Log.d(TAG, "onChanged: SUCCESS: " + hotelResource.data.getHotel().toString());
                            mHotelList.addAll(hotelResource.data.getHotel());
                            initRecycler();
                            break;
                        }

                        case ERROR: {
                            Log.d(TAG, "onChanged: ERROR " + hotelResource.message);
                            Snackbar.make(mConstraintLayout, "There is no internet connection", Snackbar.LENGTH_LONG)
                                    .show();
                            break;
                        }

                    }

                }

            }
        });
    }

    @Override
    public void onHotelClick(int position) {
        Bundle bundle = new Bundle();
        HotelItem hotelItem = mHotelList.get(position);
        bundle.putParcelable("hotel", hotelItem);
        Intent intent = new Intent(MainActivity.this, HotelDetailsActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}
