package com.example.moviapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moviapp.Fragment.DiscoverFragment;
import com.example.moviapp.Fragment.ProfileFragment;
import com.example.moviapp.Fragment.UpComingFragment;
import com.example.moviapp.Fragment.WatchListFragment;
import com.example.moviapp.Models.MovieModel;
import com.example.moviapp.Models.User;
import com.example.moviapp.databinding.ActivityHoneBinding;
import com.example.moviapp.viewmodels.MovieListViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    public static final String ACCOUNT_USER = "user_account";
    public static final String WATCH_LIST_IDS = "watch_list";

    ActivityHoneBinding binding;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private FirebaseStorage storage;

    private User user;
    private ArrayList<Integer> movieIDWatchList;

    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNav.setOnItemSelectedListener(selectedListener);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://moviapp-3d13c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        storage = FirebaseStorage.getInstance();

        database.getReference().child("Users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        movieIDWatchList = new ArrayList<>();

        database.getReference().child("Users").child(auth.getUid()).child("watchList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String movieIDString = data.getKey();
                    int movieID = Integer.parseInt(movieIDString);
                    movieIDWatchList.add(movieID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        DiscoverFragment discoverFragment = new DiscoverFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, discoverFragment);
        transaction.commit();
    }


    private NavigationBarView.OnItemSelectedListener selectedListener = new NavigationBarView.OnItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.discover:
                    FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                    transaction1.replace(R.id.content, new DiscoverFragment());
                    transaction1.commit();
                    return true;
                case R.id.upcoming:
                    FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                    transaction2.replace(R.id.content, new UpComingFragment());
                    transaction2.commit();
                    return true;
                case R.id.watchlist:
                    FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                    transaction3.replace(R.id.content, WatchListFragment.getInstance(movieIDWatchList));
                    transaction3.commit();
                    return true;
                case R.id.profile:
                    FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                    transaction4.replace(R.id.content, ProfileFragment.getInstance(user));
                    transaction4.commit();
                    return true;
            }
            return false;
        }
    };

}