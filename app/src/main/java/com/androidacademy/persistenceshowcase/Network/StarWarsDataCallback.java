package com.androidacademy.persistenceshowcase.Network;

import com.androidacademy.persistenceshowcase.Models.Film;

import java.util.List;

public interface StarWarsDataCallback {

    void onDataReady(List<Film> films);

    void onError(String message);
}
