package com.example.hodhod.hotels;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hodhod.hotels.models.HotelItem;

import androidx.appcompat.app.AppCompatActivity;

public class HotelDetailsActivity extends AppCompatActivity {

    private static final String TAG = "HotelDetailsActivity";

    // ui components
    private ImageView mImageView;
    private TextView mHotelName, mLowRateValue, mHighRateValue, mAddress;


    // vars
    private HotelItem mHotelItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);

        mImageView = findViewById(R.id.hotel_details_image);
        mHotelName = findViewById(R.id.hotel_details_name);
        mLowRateValue = findViewById(R.id.hotel_lowRate_value);
        mHighRateValue = findViewById(R.id.hotel_highRate_value);
        mAddress = findViewById(R.id.hotel_address);

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
}
