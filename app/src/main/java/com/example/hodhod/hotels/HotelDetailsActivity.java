package com.example.hodhod.hotels;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.hodhod.hotels.models.HotelItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class HotelDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "HotelDetailsActivity";
    // ui components
    private ImageView mImageView;
    private TextView mHotelName, mLowRateValue, mHighRateValue, mAddress;
    private ImagePopup imagePopup;
    private ScrollView mScrollView;
    // vars
    private HotelItem mHotelItem;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);
        mImageView = findViewById(R.id.hotel_details_image);
        mHotelName = findViewById(R.id.hotel_details_name);
        mLowRateValue = findViewById(R.id.hotel_lowRate_value);
        mHighRateValue = findViewById(R.id.hotel_highRate_value);
        mAddress = findViewById(R.id.hotel_address);
        mScrollView = findViewById(R.id.scroll_view);
        getIncomingIntent();
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide
                .with(this)
                .load(mHotelItem.getImage().get(0).getUrl())
                .placeholder(R.drawable.failed)
                .error(R.drawable.failed)
                .into(mImageView);
        mHotelName.setText(mHotelItem.getSummary().getHotelName());
        mLowRateValue.setText(String.valueOf(mHotelItem.getSummary().getLowRate()));
        mHighRateValue.setText(String.valueOf(mHotelItem.getSummary().getHighRate()));
        mAddress.setText(mHotelItem.getLocation().getAddress());
        latitude = mHotelItem.getLocation().getLatitude();
        longitude = mHotelItem.getLocation().getLongitude();
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initImagePopUp();
    }

    public void initImagePopUp() {
        // library used to put images in popup dialogue
        imagePopup = new ImagePopup(this);
        imagePopup.setBackgroundColor(Color.BLACK);
        imagePopup.setFullScreen(true);
        imagePopup.setHideCloseIcon(true);
        imagePopup.setImageOnClickClose(true);
        imagePopup.initiatePopupWithGlide(mHotelItem.getImage().get(0).getUrl());
        // image is set to full screen when clicked
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.viewPopup();
            }
        });
    }

    public void getIncomingIntent() {
        if (getIntent() != null) {
            if (getIntent().hasExtra("hotel")) {
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    mHotelItem = bundle.getParcelable("hotel");
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in hotel location
        // and move the map's camera to the same location.
        LatLng hotelLocation = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(hotelLocation)
                .title(mHotelItem.getSummary().getHotelName()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(hotelLocation));
        // disable scroll view when scrolling in map
        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                mScrollView.requestDisallowInterceptTouchEvent(true);
            }
        });
    }
}
