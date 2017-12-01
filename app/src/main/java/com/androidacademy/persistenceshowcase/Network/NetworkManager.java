package com.androidacademy.persistenceshowcase.Network;

import com.androidacademy.persistenceshowcase.Models.Film;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

    private static final String SwapiBaseUrl = "https://swapi.co/api/";
    private static final String imageSearchUrl = "https://api.cognitive.microsoft.com/bing/v7.0/images/search?q=";
    private static volatile NetworkManager singleton = null;
    private final StarWarsApi starWarsApi;
    private WeakReference<StarWarsDataCallback> callbackWeakReference;
    private Thread thread;

    public NetworkManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SwapiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        starWarsApi = retrofit.create(StarWarsApi.class);
    }

    public static NetworkManager getInstance() {
        if (singleton == null) {
            synchronized (NetworkManager.class) {
                if (singleton == null) {
                    singleton = new NetworkManager();
                }
            }
        }
        return singleton;
    }

    public void getData(StarWarsDataCallback callback) {
        callbackWeakReference = new WeakReference<>(callback);

        //never do that. summoning new thread is very expensive and can cause for a lot of error
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<FilmsResponse> response = starWarsApi.listFilms().execute();
                    if (response.code() == 200) {
                        List<Film> results = response.body().getResults();
                        for (Film result : results) {
                            String url = imageSearchUrl + URLEncoder.encode(result.getTitle(), "UTF-8");
                            Response<ImageSearchResponse> imageSearchResponse = starWarsApi.filmPicture(url).execute();
                            List<ImageValue> hits = imageSearchResponse.body().getHits();
                            if (hits != null && hits.size() > 0) {
                                result.setUrl(hits.get(0).getWebformatURL());
                            }
                        }
                        if (callbackWeakReference.get() != null) {
                            callbackWeakReference.get().onDataReady(results);
                        }
                    } else {
                        callbackWeakReference.get().onError(response.message());
                    }
                } catch (IOException e) {
                    callbackWeakReference.get().onError(e.getMessage());
                }
            }
        });
        thread.start();
    }
}
