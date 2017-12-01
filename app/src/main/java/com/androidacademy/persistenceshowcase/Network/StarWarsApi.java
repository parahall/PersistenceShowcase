package com.androidacademy.persistenceshowcase.Network;

import com.androidacademy.persistenceshowcase.Network.FilmsResponse;
import com.androidacademy.persistenceshowcase.Network.ImageSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface StarWarsApi {
    @GET("films/")
    Call<FilmsResponse> listFilms();

    @GET
    @Headers("Ocp-Apim-Subscription-Key: d13a1fa9220c4a4b93ee7860bc685ba9")
    Call<ImageSearchResponse> filmPicture(@Url String url);
}
