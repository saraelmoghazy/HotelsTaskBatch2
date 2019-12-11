package com.example.hodhod.hotels.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HotelItem implements Parcelable {

    @SerializedName("summary")
    private Summary summary;

    @SerializedName("image")
    private List<ImageItem> image;

    @SerializedName("location")
    private Location location;

    @SerializedName("hotelId")
    private int hotelId;

    protected HotelItem(Parcel in) {
        summary = in.readParcelable(Summary.class.getClassLoader());
        image = in.createTypedArrayList(ImageItem.CREATOR);
        location = in.readParcelable(Location.class.getClassLoader());
        hotelId = in.readInt();
    }

    public static final Creator<HotelItem> CREATOR = new Creator<HotelItem>() {
        @Override
        public HotelItem createFromParcel(Parcel in) {
            return new HotelItem(in);
        }

        @Override
        public HotelItem[] newArray(int size) {
            return new HotelItem[size];
        }
    };

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setImage(List<ImageItem> image) {
        this.image = image;
    }

    public List<ImageItem> getImage() {
        return image;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getHotelId() {
        return hotelId;
    }

    @Override
    public String toString() {
        return
                "HotelItem{" +
                        "summary = '" + summary + '\'' +
                        ",image = '" + image + '\'' +
                        ",location = '" + location + '\'' +
                        ",hotelId = '" + hotelId + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(summary, flags);
        dest.writeTypedList(image);
        dest.writeParcelable(location, flags);
        dest.writeInt(hotelId);
    }
}