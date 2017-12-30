package com.example.aaapps.olaplay.network;

import com.example.aaapps.olaplay.model.SongsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by akash on 16-12-2017.
 */

public interface SongAPI {
    String ENDPOINT = "http://starlord.hackerearth.com";

    @GET("studio")
    Call<ArrayList<SongsModel>> getSongs();
}
