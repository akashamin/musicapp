package com.example.aaapps.olaplay.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 16-12-2017.
 */

public class SongsModel {

    @SerializedName("song")
    String song;

    @SerializedName("url")
    String url;

    @SerializedName("artists")
    String artists;

    @SerializedName("cover_image")
    String cover_image;

    public String getSong() {
        return song;
    }

    public String getUrl() {
        return url;
    }

    public String getArtists() {
        return artists;
    }

    public String getCoverImage() {
        return cover_image;
    }
}
