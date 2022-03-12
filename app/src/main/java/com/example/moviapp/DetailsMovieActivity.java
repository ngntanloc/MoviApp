package com.example.moviapp;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviapp.Adapter.CastAdapter;
import com.example.moviapp.Models.Cast;
import com.example.moviapp.Models.MovieDetails;
import com.example.moviapp.Models.User;
import com.example.moviapp.databinding.ActivityDetailsMovieBinding;
import com.example.moviapp.utils.Credentials;
import com.example.moviapp.utils.MyUtils;
import com.example.moviapp.viewmodels.MovieListViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import okhttp3.internal.Util;

public class DetailsMovieActivity extends AppCompatActivity {

    ActivityDetailsMovieBinding binding;
    int movieID = 0;

    private MovieListViewModel movieListViewModel;

    private CastAdapter castAdapter;

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initData();

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ObserverMovieDetail();

        movieListViewModel.searchMovieDetail(movieID);
        movieListViewModel.searchCast(movieID);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailsMovieActivity.super.onBackPressed();
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            String data = intent.getStringExtra("movieID");
            movieID = Integer.parseInt(data);
        }

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://moviapp-3d13c-default-rtdb.asia-southeast1.firebasedatabase.app/");

    }

    private void ObserverMovieDetail() {
        movieListViewModel.getMovieDetail().observe(this, new Observer<MovieDetails>() {
            @Override
            public void onChanged(MovieDetails movieDetails) {
                if (movieDetails != null) {
                    ConfigureUI(movieDetails);
                }
            }
        });

        movieListViewModel.getCast().observe(this, new Observer<List<Cast>>() {
            @Override
            public void onChanged(List<Cast> casts) {
                if (casts != null) {
                    ConfigureCastUI(casts);
                }
            }
        });
    }

    private void ConfigureCastUI(List<Cast> casts) {

        castAdapter = new CastAdapter(this);
        binding.castRecycler.setAdapter(castAdapter);
        binding.castRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        castAdapter.setCast(casts);
    }

    private void ConfigureUI(MovieDetails movieDetails) {
        binding.txtMovieName.setText(movieDetails.getTitle());

        Glide.with(this)
                .load(Credentials.GET_IMAGE + movieDetails.getBackdrop_path())
                .into(binding.imgBackdrop);

        binding.txtDescription.setText(movieDetails.getOverview());
        binding.txtImdbPoint.setText("" + movieDetails.getVote_average());

        if (movieDetails.getGenres().size() == 1) {
            binding.txtGenre1.setVisibility(View.VISIBLE);
            binding.txtGenre1.setText(movieDetails.getGenres().get(0).getName());

            binding.txtGenre2.setVisibility(View.GONE);
            binding.txtGenre3.setVisibility(View.GONE);
        } else if (movieDetails.getGenres().size() == 2) {
            binding.txtGenre1.setVisibility(View.VISIBLE);
            binding.txtGenre1.setText(movieDetails.getGenres().get(0).getName());
            binding.txtGenre2.setVisibility(View.VISIBLE);
            binding.txtGenre2.setText(movieDetails.getGenres().get(1).getName());

            binding.txtGenre3.setVisibility(View.GONE);
        } else if (movieDetails.getGenres().size() == 3) {
            binding.txtGenre1.setVisibility(View.VISIBLE);
            binding.txtGenre1.setText(movieDetails.getGenres().get(0).getName());
            binding.txtGenre2.setVisibility(View.VISIBLE);
            binding.txtGenre2.setText(movieDetails.getGenres().get(1).getName());
            binding.txtGenre3.setVisibility(View.VISIBLE);
            binding.txtGenre3.setText(movieDetails.getGenres().get(2).getName());
        }

        binding.btnAddToWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMovieToWatchList(movieID);
            }
        });
    }

    private void addMovieToWatchList(int movieID) {

        String movieKey = "movieID";
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put(movieKey, movieID);

        database.getReference().child("Users").child(auth.getUid()).child("watchList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int dataKey = 0;
                boolean isAdded = false;
                for (DataSnapshot data : snapshot.getChildren()) {
                    dataKey = Integer.parseInt(data.getKey());
                    if (dataKey == movieID) {
                        isAdded = true;
                    }
                }

                if (isAdded) {
                    Toast.makeText(DetailsMovieActivity.this, "This Movie Already Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailsMovieActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("Users").child(auth.getUid()).child("watchList").child("" + movieID).setValue(hashMap);

    }

}