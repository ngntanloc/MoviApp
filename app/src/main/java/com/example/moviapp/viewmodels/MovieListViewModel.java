package com.example.moviapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviapp.Models.Cast;
import com.example.moviapp.Models.Genre;
import com.example.moviapp.Models.MovieDetails;
import com.example.moviapp.Models.MovieModel;
import com.example.moviapp.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    private MovieRepository movieRepository;

    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMoviesNowPlaying() {
        return movieRepository.getMoviesNowPlaying();
    }

    public void searchMovieNowPlaying(int pageNumber) {
        movieRepository.searchMovieNowPlaying(pageNumber);
    }

    public LiveData<MovieDetails> getMovieDetail() {
        return movieRepository.getMovieDetail();
    }

    public void searchMovieDetail(int movieID) {
        movieRepository.searchMovieDetail(movieID);
    }

    public LiveData<List<Cast>> getCast() {
        return movieRepository.getCast();
    }

    public void searchCast(int movieID) {
        movieRepository.searchCast(movieID);
    }

    public void searchUpComingMovie(int pageNumber) {
        movieRepository.searchMovieUpcoming(pageNumber);
    }

    public LiveData<List<MovieModel>> getUpcomingMovies() {
        return movieRepository.getMoviesUpcoming();
    }

    public void searchMovie(String query, int pageNumber) {
        movieRepository.searchMovie(query, pageNumber);
    }

    public LiveData<List<MovieModel>> getSearchingMovies() {
        return movieRepository.getSearchingMovies();
    }

    public void searchGenre() {
        movieRepository.searchGenres();
    }

    public LiveData<List<Genre>> getGenres() {
        return movieRepository.getGenres();
    }

    public void searchWatchList(List<Integer> ids) {
        movieRepository.searchMovieWatchList(ids);
    }

    public LiveData<List<MovieDetails>> getMovieWatchList() {
        return movieRepository.getMovieWatchList();
    }

    public void searchNextPage() {
        movieRepository.searchNextPage();
    }


}
