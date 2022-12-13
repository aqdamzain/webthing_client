package com.example.webthingclient;

import android.os.Parcel;
import android.os.Parcelable;

public class ThingsList implements Parcelable {
    private String name;
    private String description;
    private Integer photo;

    public ThingsList() {
    }

    protected ThingsList(Parcel in) {
        name = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            photo = null;
        } else {
            photo = in.readInt();
        }
    }

    public static final Creator<ThingsList> CREATOR = new Creator<ThingsList>() {
        @Override
        public ThingsList createFromParcel(Parcel in) {
            return new ThingsList(in);
        }

        @Override
        public ThingsList[] newArray(int size) {
            return new ThingsList[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPhoto() {
        return photo;
    }

    public void setPhoto(Integer photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        if (photo == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(photo);
        }
    }
}
