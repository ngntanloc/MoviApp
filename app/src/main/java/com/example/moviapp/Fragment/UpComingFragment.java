package com.example.moviapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.moviapp.Adapter.ImageUpcomingAdapter;
import com.example.moviapp.Adapter.MoviePopularAdapter;
import com.example.moviapp.Models.MovieModel;
import com.example.moviapp.SearchMovieActivity;
import com.example.moviapp.databinding.FragmentUpcomingBinding;
import com.example.moviapp.viewmodels.MovieListViewModel;

import java.util.List;

public class UpComingFragment extends Fragment {

    FragmentUpcomingBinding binding;

    private MovieListViewModel movieListViewModel;
    private ImageUpcomingAdapter imageUpcomingAdapter;
    private MoviePopularAdapter movieItemAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentUpcomingBinding.inflate(inflater, container, false);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ObserveUpcomingMovies();

        movieListViewModel.searchUpComingMovie(1);

        ConfigureUI();
        SetupSearchView();
        
        return binding.getRoot();
    }

    private void ConfigureUI() {

        imageUpcomingAdapter = new ImageUpcomingAdapter(getContext());
        binding.imageUpcomingMovie.setAdapter(imageUpcomingAdapter);
        binding.imageUpcomingMovie.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        movieItemAdapter = new MoviePopularAdapter(getContext());
        binding.upcomingMovie.setAdapter(movieItemAdapter);
        binding.upcomingMovie.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    private void ObserveUpcomingMovies() {
        movieListViewModel.getUpcomingMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if (movieModels != null) {
                    imageUpcomingAdapter.setMovies(movieModels);
                    movieItemAdapter.setMovies(movieModels);
                }
            }
        });
    }


    private void SetupSearchView() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getContext(), SearchMovieActivity.class);
                intent.putExtra("query", "" + query);
                getContext().startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        binding.searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.txtTopic.setVisibility(View.INVISIBLE);
            }
        });

        binding.searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                binding.txtTopic.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }

}
