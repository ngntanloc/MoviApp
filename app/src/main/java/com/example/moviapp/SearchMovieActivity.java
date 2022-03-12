package com.example.moviapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.moviapp.Adapter.MovieSearchAdapter;
import com.example.moviapp.Models.Genre;
import com.example.moviapp.Models.MovieDetails;
import com.example.moviapp.Models.MovieModel;
import com.example.moviapp.databinding.ActivitySearchMovieBinding;
import com.example.moviapp.viewmodels.MovieListViewModel;

import java.util.List;

public class SearchMovieActivity extends AppCompatActivity {

    ActivitySearchMovieBinding binding;
    private MovieListViewModel movieListViewModel;

    private String query = null;
    private String querySearch = null;

    private MovieSearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        searchAdapter = new MovieSearchAdapter(this);
        binding.recyclerSearchingMovie.setAdapter(searchAdapter);
        binding.recyclerSearchingMovie.setLayoutManager(new LinearLayoutManager(SearchMovieActivity.this, RecyclerView.VERTICAL, false));

        ConfigureUI();
        ConfigureSearchView();

    }

    private void ConfigureSearchView() {
        binding.searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                query = null;
                binding.txtMovieSearch.setVisibility(View.INVISIBLE);
                return false;
            }
        });


        binding.searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.txtMovieSearch.setVisibility(View.INVISIBLE);
                binding.searchView.setQuery(query, false);
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovie(query, 1);
                movieListViewModel.getSearchingMovies().observe(SearchMovieActivity.this, new Observer<List<MovieModel>>() {
                    @Override
                    public void onChanged(List<MovieModel> movieModels) {

                        if (movieModels != null) {
                            searchAdapter.setMovies(movieModels);
                        }
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void ConfigureUI() {

        Intent intent = getIntent();
        if (intent != null) {
            querySearch = intent.getStringExtra("query");
            query = intent.getStringExtra("query");
            binding.txtMovieSearch.setText(query);
            binding.txtMovieSearch.setVisibility(View.VISIBLE);
        }

        movieListViewModel.searchGenre();
        movieListViewModel.getGenres().observe(SearchMovieActivity.this, new Observer<List<Genre>>() {
            @Override
            public void onChanged(List<Genre> genres) {
                if (genres.size() != 0) {
                    searchAdapter.setGenres(genres);
                }

            }
        });

        movieListViewModel.searchMovie(querySearch, 1);
        movieListViewModel.getSearchingMovies().observe(SearchMovieActivity.this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if (movieModels != null) {
                    searchAdapter.setMovies(movieModels);
                }
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchMovieActivity.super.onBackPressed();
            }
        });
    }
}