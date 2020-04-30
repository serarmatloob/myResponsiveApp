package com.matloob.myresponsiveapp.repository;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.matloob.myresponsiveapp.Constants;
import com.matloob.myresponsiveapp.api.SongsApi;
import com.matloob.myresponsiveapp.models.Tag;
import com.matloob.myresponsiveapp.models.TopTagsResponse;
import com.matloob.myresponsiveapp.models.TopTracksResponse;
import com.matloob.myresponsiveapp.models.Track;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.matloob.myresponsiveapp.Constants.BASE_URL;

/**
 * Created by Serar Matloob on 4/30/2020.
 */
public class SongsRepository {
    private Retrofit retrofit;

    private MutableLiveData<List<Tag>> topTags = new MutableLiveData<>();

    private MutableLiveData<List<Track>> topTracks = new MutableLiveData<>();

    public SongsRepository() {
        setupRetrofit();
        loadTopTags();
    }

    /**
     * Function to setup retrofit library object
     */
    private void setupRetrofit() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    /**
     * Function to load top tags from the API
     */
    private void loadTopTags() {
        SongsApi songsApi = retrofit.create(SongsApi.class);
        songsApi.getTopTags(Constants.API_KEY).enqueue(new Callback<TopTagsResponse>() {
            @Override
            public void onResponse(@Nullable Call<TopTagsResponse> call, @Nullable Response<TopTagsResponse> response) {
                if(response != null && response.body() != null) {
                    Log.i("TAG", response.message());
                    TopTagsResponse topTagsResponse = response.body();
                    if(topTagsResponse.getToptags() != null) {
                        topTags.setValue(topTagsResponse.getToptags().getTag());
                        Log.i("TAG", "size "+ topTagsResponse.getToptags().getTag().size());
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<TopTagsResponse> call, @Nullable Throwable t) {
                Log.i("TAG", "Failed: " + (t != null ? t.getMessage() : "Unknown error"));
            }
        });
    }

    /**
     * Function to load top tracks from the API
     */
    private void loadTopTracks(String tag) {
        SongsApi songsApi = retrofit.create(SongsApi.class);
        songsApi.getTopTracks(Constants.API_KEY, tag).enqueue(new Callback<TopTracksResponse>() {
            @Override
            public void onResponse(@Nullable Call<TopTracksResponse> call, @Nullable Response<TopTracksResponse> response) {
                if(response != null && response.body() != null) {
                    Log.i("TAG", response.message());
                    TopTracksResponse topTracksResponse = response.body();
                    if(topTracksResponse.getTracks() != null) {
                        topTracks.setValue(topTracksResponse.getTracks().getTrack());
                        Log.i("TAG", ""+ topTracksResponse.getTracks().getTrack());
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<TopTracksResponse> call, @Nullable Throwable t) {
                Log.i("TAG", "Failed: " + (t != null ? t.getMessage() : "Unknown error"));
            }
        });
    }

    /**
     * Returns list of Tags
     * @return a {@link LiveData} object
     */
    public LiveData<List<Tag>> getTopTags() {
        return topTags;
    }

    /**
     * Returns list of Tags
     * @return a {@link LiveData} object
     */
    public LiveData<List<Track>> getTopTracks(String tag) {
        loadTopTracks(tag);
        return topTracks;
    }
}
