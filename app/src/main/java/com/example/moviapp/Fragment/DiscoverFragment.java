package com.example.moviapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.moviapp.Adapter.ImageDiscoveryAdapter;
import com.example.moviapp.Adapter.MoviePopularAdapter;
import com.example.moviapp.Models.MovieModel;
import com.example.moviapp.SearchMovieActivity;
import com.example.moviapp.databinding.FragmentDiscoverBinding;
import com.example.moviapp.viewmodels.MovieListViewModel;

import java.util.List;

public class DiscoverFragment extends Fragment {

    FragmentDiscoverBinding binding;

    private ImageDiscoveryAdapter adapterTopic;
    private MoviePopularAdapter adapterPopularMovie;

    private MovieListViewModel movieListViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ObserveNowPlayingMovies();

        movieListViewModel.searchMovieNowPlaying(1);

        ConfigureTopicImageRecyclerView();
        ConfigureNewMovie();
        SetupSearchView();

        return binding.getRoot();
    }

    private void ConfigureTopicImageRecyclerView() {
        adapterTopic = new ImageDiscoveryAdapter(getContext());
        binding.recyclerTopicImage.setAdapter(adapterTopic);

        binding.recyclerTopicImage.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
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

    private void ConfigureNewMovie() {
        adapterPopularMovie = new MoviePopularAdapter(getContext());
        binding.recyclerViewNewMovie.setAdapter(adapterPopularMovie);
        binding.recyclerViewNewMovie.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    private void ObserveNowPlayingMovies() {
        movieListViewModel.getMoviesNowPlaying().observe(getViewLifecycleOwner(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // Observing for any data change
                if (movieModels != null) {
                    adapterTopic.setMovies(movieModels);
                    adapterPopularMovie.setMovies(movieModels);
                }
            }
        });
    }

}
