package com.example.moviapp.Fragment;

import static com.example.moviapp.HomeActivity.WATCH_LIST_IDS;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviapp.Adapter.MovieSearchAdapter;
import com.example.moviapp.Adapter.MovieWatchListAdapter;
import com.example.moviapp.DetailsMovieActivity;
import com.example.moviapp.Models.MovieDetails;
import com.example.moviapp.Models.MovieModel;
import com.example.moviapp.R;
import com.example.moviapp.databinding.FragmentWatchlistBinding;
import com.example.moviapp.viewmodels.MovieListViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class WatchListFragment extends Fragment {

    FragmentWatchlistBinding binding;

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    private MovieListViewModel movieListViewModel;
    private MovieWatchListAdapter adapter;

    private ArrayList<MovieDetails> movieDetailsArrayList;
    private ArrayList<Integer> movieListIds;

    public static WatchListFragment getInstance(ArrayList<Integer> movieIds) {
        WatchListFragment watchListFragment = new WatchListFragment();
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(WATCH_LIST_IDS, movieIds);
        watchListFragment.setArguments(bundle);


        return watchListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWatchlistBinding.inflate(inflater, container, false);

        configureRecyclerData();

        movieListViewModel = new ViewModelProvider(getActivity()).get(MovieListViewModel.class);

        movieDetailsArrayList = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://moviapp-3d13c-default-rtdb.asia-southeast1.firebasedatabase.app/");

        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<Integer> movieIds = bundle.getIntegerArrayList(WATCH_LIST_IDS);
            for (int movieID : movieIds) {
                movieListViewModel.searchMovieDetail(movieID);
            }

            movieListViewModel.getMovieDetail().observe(getViewLifecycleOwner(), new Observer<MovieDetails>() {
                @Override
                public void onChanged(MovieDetails movieDetails) {
                    movieDetailsArrayList.add(movieDetails);
                    adapter.setMovies(movieDetailsArrayList);
                }
            });
        }

        return binding.getRoot();
    }

    private void configureRecyclerData() {
        adapter = new MovieWatchListAdapter(getContext());
        binding.recyclerMovieItem.setAdapter(adapter);
        binding.recyclerMovieItem.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

}
