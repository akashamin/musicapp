package com.example.aaapps.olaplay.view;

import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.example.aaapps.olaplay.R;
import com.example.aaapps.olaplay.adapter.SongAdapter;
import com.example.aaapps.olaplay.model.SongsModel;
import com.example.aaapps.olaplay.presenter.SongPresenter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity implements IMainView, SwipeRefreshLayout.OnRefreshListener {

    SongPresenter mSongPresenter;

    @BindView(R.id.edt_search)
    EditText mSearch;

    @BindView(R.id.main_recyclerview)
    RecyclerView mRecyclerView;

    SongAdapter mSongAdapter;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(MainActivity.this));
        mSongPresenter = new SongPresenter(this);

        swipeRefreshLayout.setOnRefreshListener(this);
        mSongPresenter.fetchSongs();
    }

    @Override
    public void displaySongs(ArrayList<SongsModel> songs) {
        mSongAdapter = new SongAdapter(this, songs);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mSongAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @OnTextChanged(R.id.edt_search)
    public void onSearchTextChanged() {
        mSongAdapter.getFilter().filter(mSearch.getText());
    }

    @Override
    public void onRefresh() {
        mSongPresenter.fetchSongs();
    }

    public void refreshAdapterItem(int position) {
        mSongAdapter.notifyItemChanged(position);
    }
}
