package com.asi.m3alig.Tools.maps;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ahmed.shokry on 3/1/2017.
 */
public class InnerModle implements Parcelable {

    private double Longitude;
    private double Latitude;
    private String address;

    public InnerModle() {
    }

    public InnerModle(double longitude, double latitude, String address) {

        Longitude = longitude;
        Latitude = latitude;
        address = address;

    }

    protected InnerModle(Parcel in) {
        Longitude = in.readDouble();
        Latitude = in.readDouble();
        address = in.readString();
    }

    public static final Creator<InnerModle> CREATOR = new Creator<InnerModle>() {
        @Override
        public InnerModle createFromParcel(Parcel in) {
            return new InnerModle(in);
        }

        @Override
        public InnerModle[] newArray(int size) {
            return new InnerModle[size];
        }
    };

    public double getLongitude() {
        return Longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLongitude(double longitude) {

        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(Longitude);
        parcel.writeDouble(Latitude);
    }
}
