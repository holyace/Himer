package com.himer.android.player;

import android.os.Parcel;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/30.
 */
public class Audio  implements IAudio {

    private String path;

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
    }

    public Audio() {
    }

    protected Audio(Parcel in) {
        this.path = in.readString();
    }

    public static final Creator<Audio> CREATOR = new Creator<Audio>() {
        @Override
        public Audio createFromParcel(Parcel source) {
            return new Audio(source);
        }

        @Override
        public Audio[] newArray(int size) {
            return new Audio[size];
        }
    };
}
