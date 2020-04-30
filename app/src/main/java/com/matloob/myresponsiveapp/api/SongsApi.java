package com.matloob.myresponsiveapp.api;

import com.matloob.myresponsiveapp.models.TopTagsResponse;
import com.matloob.myresponsiveapp.models.TopTracksResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Serar Matloob on 4/29/2020.
 */
public interface SongsApi {

    @GET("?method=tag.getTopTags&format=json")
    Call<TopTagsResponse> getTopTags(@Query("api_key") String apiKey);

    @GET("?method=tag.getTopTracks&format=json")
    Call<TopTracksResponse> getTopTracks(@Query("api_key") String apiKey, @Query("tag") String tag);
}
