package com.example.moviapp.repositories;

import androidx.lifecycle.LiveData;

import com.example.moviapp.Models.Cast;
import com.example.moviapp.Models.Genre;
import com.example.moviapp.Models.MovieDetails;
import com.example.moviapp.Models.MovieModel;
import com.example.moviapp.request.MovieApiClient;

import java.util.List;

public class MovieRepository {
    private static MovieRepository instance;

    private int mPageNumber;
    private int movieID;
    private List<Integer> idList;

    private MovieApiClient movieApiClient;

    public static MovieRepository getInstance() {
        if (instance == null)
            instance = new MovieRepository();
        return instance;
    }

    public MovieRepository() {
        this.movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMoviesNowPlaying() {
        return movieApiClient.getmMoviesNowPlaying();
    }

    // 1. Calling method search with input parameter
    public void searchMovieNowPlaying(int pageNumber) {
        this.mPageNumber = pageNumber;
        movieApiClient.searchMovieNowPlaying(pageNumber);
    }

    public LiveData<MovieDetails> getMovieDetail() {
        return movieApiClient.getmMoviesDetails();
    }

    public void searchMovieDetail(int movieID) {
        this.movieID = movieID;
        movieApiClient.searchMovieDetails(movieID);
    }

    public LiveData<List<Cast>> getCast() {
        return movieApiClient.getmCastInfo();
    }

    public void searchCast(int movieID) {
        this.movieID = movieID;
        movieApiClient.searchCastInformationSummary(movieID);
    }

    public LiveData<List<MovieModel>> getMoviesUpcoming() {
        return movieApiClient.getmMoviesUpcoming();
    }

    public void searchMovieUpcoming(int pageNumber) {
        this.mPageNumber = pageNumber;
        movieApiClient.searchUpcomingMovies(pageNumber);
    }

    public LiveData<List<MovieModel>> getSearchingMovies() {
        return movieApiClient.getmSearchMovie();
    }

    public void searchMovie(String query, int pageNumber) {
        this.mPageNumber = pageNumber;
        movieApiClient.searchMovies(query, pageNumber);
    }

    public LiveData<List<Genre>> getGenres() {
        return movieApiClient.getmGetAllGenres();
    }

    public void searchGenres() {
        movieApiClient.searchGenres();
    }

    public LiveData<List<MovieDetails>> getMovieWatchList() {
        return movieApiClient.getmMovieWatchList();
    }

    public void searchMovieWatchList(List<Integer> ids) {
        this.idList = ids;
        movieApiClient.searchMovieWatchList(ids);
    }

    public void searchNextPage() {
        mPageNumber+=1;
        searchMovieNowPlaying(mPageNumber);
    }

}
