package com.androidacademy.persistenceshowcase.Network;

import com.androidacademy.persistenceshowcase.Models.Film;

import java.util.List;

public class FilmsResponse {
    private int count;
    private List<Film> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Film> getResults() {
        return results;
    }

    public void setResults(List<Film> results) {
        this.results = results;
    }
}
