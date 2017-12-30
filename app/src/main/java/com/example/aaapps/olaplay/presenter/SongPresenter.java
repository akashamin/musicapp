package com.example.aaapps.olaplay.presenter;

import android.util.Log;
import android.widget.ImageView;

import com.example.aaapps.olaplay.model.SongsModel;
import com.example.aaapps.olaplay.network.SongAPI;
import com.example.aaapps.olaplay.view.IMainView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * Created by akash on 16-12-2017.
 */

public class SongPresenter {

    IMainView view;
    public SongPresenter(IMainView view){
        this.view=view;
    }

    public void fetchSongs(){
        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SongAPI.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SongAPI songAPI = retrofit.create(SongAPI.class);
        songAPI.getSongs().enqueue(songsCallback);
    }

    Callback<ArrayList<SongsModel>> songsCallback = new Callback<ArrayList<SongsModel>>() {
        @Override
        public void onResponse(Call<ArrayList<SongsModel>> call, Response<ArrayList<SongsModel>> response) {
            if (response.isSuccessful()) {
                ArrayList<SongsModel> songs = response.body();
                view.displaySongs(songs);
                Log.d(TAG, "onResponse: "+ response.body());
            } else {
                Log.d(TAG, "onResponse: Code :" + response.code() + " Message :" + response.message());
            }
        }

        @Override
        public void onFailure(Call<ArrayList<SongsModel>> call, Throwable t) {
            t.printStackTrace();
        }
    };


}
