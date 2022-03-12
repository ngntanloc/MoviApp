package com.example.moviapp.response;

import com.example.moviapp.Models.DateOption;
import com.example.moviapp.Models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieSearchResponse {

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("results")
    @Expose
    private List<MovieModel> mMovies;


    public List<MovieModel> getMovies() {
        return mMovies;
    }

    @Override
    public String toString() {
        return "MovieSearchResponse{" +
                "page=" + page +
                ", mMovies=" + mMovies +
                '}';
    }
}
