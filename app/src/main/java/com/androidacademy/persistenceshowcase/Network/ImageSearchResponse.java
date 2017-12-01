package com.androidacademy.persistenceshowcase.Network;

import java.util.List;

public class ImageSearchResponse {

    int totalEstimatedMatches;
    List<ImageValue> value;

    public int getTotalHits() {
        return totalEstimatedMatches;
    }

    public List<ImageValue> getHits() {
        return value;
    }
}
