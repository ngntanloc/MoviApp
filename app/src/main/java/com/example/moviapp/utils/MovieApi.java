package com.example.moviapp.utils;

import com.example.moviapp.Models.Genre;
import com.example.moviapp.Models.MovieDetails;
import com.example.moviapp.Models.MovieModel;
import com.example.moviapp.response.CastSearchResponse;
import com.example.moviapp.response.GetGenreResponse;
import com.example.moviapp.response.MovieSearchResponse;
import com.google.gson.annotations.Expose;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    // Get popular movie
    // https://api.themoviedb.org/3/movie/now_playing?api_key=dbbf03936c875096317ff2e5ea3e9886&page=1
    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getDiscoveryMovie(
            @Query("api_key") String key,
            @Query("page") int page
    );

    @GET("/3/movie/{movie_id}?")
    Call<MovieDetails> getMoviesDetails(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    //    https://api.themoviedb.org/3/movie/{movie_id}/credits?api_key=7910d3ba7d6a7c13cb6139493d9aa7ba
//    https://api.themoviedb.org/3/movie/634649/credits?api_key=7910d3ba7d6a7c13cb6139493d9aa7ba
    @GET("/3/movie/{movie_id}/credits")
    Call<CastSearchResponse> getSummaryCastInfo(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    // Get upcoming movies
    // https://api.themoviedb.org/3/movie/upcoming?api_key=7910d3ba7d6a7c13cb6139493d9aa7ba&page=1
    @GET("/3/movie/upcoming")
    Call<MovieSearchResponse> getUpcomingMovie(
            @Query("api_key") String key,
            @Query("page") int page
    );

    // https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    // https://api.themoviedb.org/3/genre/movie/list?api_key=7910d3ba7d6a7c13cb6139493d9aa7ba
    @GET("/3/genre/movie/list")
    Call<GetGenreResponse> getAllGenres(
            @Query("api_key") String key
    );

}
