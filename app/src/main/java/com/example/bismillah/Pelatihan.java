// File: Pelatihan.java
package com.example.bismillah;

import android.os.Parcel;
import android.os.Parcelable;

public class Pelatihan implements Parcelable {
    private String title;
    private int imageResId;
    private int price;

    // Konstruktor yang diperbaiki
    public Pelatihan(String title, int imageResId, int price) {
        this.title = title;
        this.imageResId = imageResId;
        this.price = price;
    }

    // Getter untuk title
    public String getTitle() {
        return title;
    }

    // Getter untuk imageResId
    public int getImageResId() {
        return imageResId;
    }

    // Getter untuk price
    public int getPrice() {
        return price;
    }

    // Implementasi Parcelable
    protected Pelatihan(Parcel in) {
        title = in.readString();
        imageResId = in.readInt();
        price = in.readInt();
    }

    public static final Creator<Pelatihan> CREATOR = new Creator<Pelatihan>() {
        @Override
        public Pelatihan createFromParcel(Parcel in) {
            return new Pelatihan(in);
        }

        @Override
        public Pelatihan[] newArray(int size) {
            return new Pelatihan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(imageResId);
        dest.writeInt(price);
    }
}
