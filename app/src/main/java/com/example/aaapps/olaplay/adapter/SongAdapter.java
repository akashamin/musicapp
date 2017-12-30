package com.example.aaapps.olaplay.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.renderscript.ScriptGroup;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aaapps.olaplay.R;
import com.example.aaapps.olaplay.model.SongsModel;
import com.example.aaapps.olaplay.view.MainActivity;
import com.example.aaapps.olaplay.view.MediaPlayerActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by akash on 16-12-2017.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> implements Filterable {

    private ArrayList<SongsModel> mSongsList;
    private ArrayList<SongsModel> mFilteredSongsList;
    ImageLoader imageLoader;
    Context mContext;

    public SongAdapter(Context context, ArrayList<SongsModel> songsList) {
        mSongsList = songsList;
        mFilteredSongsList = songsList;
        imageLoader = ImageLoader.getInstance();
        mContext = context;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_song_card, parent, false);
        return new SongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder,final int position) {
        final SongsModel song = mFilteredSongsList.get(position);
        imageLoader.displayImage(song.getCoverImage(), holder.mCoverImage);

        holder.mSongTitle.setText(song.getSong());
        holder.mArtist.setText(song.getArtists());

        File f = new File(mContext.getExternalFilesDir("/Ola Play Music"), song.getSong() + ".mp3");
        if (f.exists()) {
            holder.mDownload.setImageResource(R.drawable.ic_check);
        }

        holder.mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playerintent = new Intent(mContext, MediaPlayerActivity.class);
                playerintent.putExtra("songurl", song.getUrl());
                mContext.startActivity(playerintent);
            }
        });

        holder.mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri song_uri = Uri.parse(song.getUrl());
                DownloadManager.Request download_request = new DownloadManager.Request(song_uri);

                File download_dir = new File(android.os.Environment.getExternalStorageDirectory(), "Ola Play Music");
                if (!download_dir.exists())
                    download_dir.mkdirs();
                download_request.setTitle(song.getSong());
                download_request.setDestinationInExternalFilesDir(mContext,"/Ola Play Music", song.getSong() + ".mp3");
                downloadManager.enqueue(download_request);
                ((MainActivity) mContext).refreshAdapterItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredSongsList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredSongsList = mSongsList;
                } else {
                    ArrayList<SongsModel> filteredList = new ArrayList<>();
                    for (SongsModel row : mSongsList) {

                        if (row.getSong().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mFilteredSongsList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredSongsList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredSongsList = (ArrayList<SongsModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
        public ImageView mCoverImage;
        public TextView mSongTitle;
        public TextView mArtist;
        public ImageButton mPlay;
        public ImageButton mDownload;

        public SongViewHolder(View itemView) {
            super(itemView);
            mCoverImage = (ImageView) itemView.findViewById(R.id.iv_cover_image);
            mSongTitle = (TextView) itemView.findViewById(R.id.tv_song_title);
            mArtist = (TextView) itemView.findViewById(R.id.tv_artists);
            mPlay = (ImageButton) itemView.findViewById(R.id.iv_play);
            mDownload = (ImageButton) itemView.findViewById(R.id.iv_download);
        }
    }
}
