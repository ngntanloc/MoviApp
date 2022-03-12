package com.example.moviapp.response;

import com.example.moviapp.Models.Genre;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetGenreResponse {

    @SerializedName("genres")
    @Expose
    List<Genre> genres;

    public List<Genre> getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        return "GetGenreResponse{" +
                "genres=" + genres +
                '}';
    }
}
