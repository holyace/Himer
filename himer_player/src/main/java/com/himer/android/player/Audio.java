package com.himer.android.player;

import android.os.Parcel;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/30.
 */
public class Audio  implements IAudio {

    private String path;
    private String title;
    private String cover;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeString(this.title);
        dest.writeString(this.cover);
    }

    public Audio() {
    }

    protected Audio(Parcel in) {
        this.path = in.readString();
        this.title = in.readString();
        this.cover = in.readString();
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

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getCoverPath() {
        return cover;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCoverPath(String cover) {
        this.cover = cover;
    }
}
