package com.example.hodhod.hotels.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Summary implements Parcelable {

    @SerializedName("highRate")
    private double highRate;

    @SerializedName("lowRate")
    private double lowRate;

    @SerializedName("hotelName")
    private String hotelName;

    protected Summary(Parcel in) {
        highRate = in.readDouble();
        lowRate = in.readDouble();
        hotelName = in.readString();
    }

    public static final Creator<Summary> CREATOR = new Creator<Summary>() {
        @Override
        public Summary createFromParcel(Parcel in) {
            return new Summary(in);
        }

        @Override
        public Summary[] newArray(int size) {
            return new Summary[size];
        }
    };

    public void setHighRate(double highRate) {
        this.highRate = highRate;
    }

    public double getHighRate() {
        return highRate;
    }

    public void setLowRate(double lowRate) {
        this.lowRate = lowRate;
    }

    public double getLowRate() {
        return lowRate;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelName() {
        return hotelName;
    }

    @Override
    public String toString() {
        return
                "Summary{" +
                        "highRate = '" + highRate + '\'' +
                        ",lowRate = '" + lowRate + '\'' +
                        ",hotelName = '" + hotelName + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(highRate);
        dest.writeDouble(lowRate);
        dest.writeString(hotelName);
    }
}