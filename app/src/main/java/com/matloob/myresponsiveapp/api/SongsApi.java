package com.matloob.myresponsiveapp.api;

import com.matloob.myresponsiveapp.models.TopTagsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Serar Matloob on 4/29/2020.
 */
public interface SongsApi {

    @GET("?method=tag.getTopTags&format=json")
    Call<TopTagsResponse> getSongTags(@Query("api_key") String apiKey);
}
