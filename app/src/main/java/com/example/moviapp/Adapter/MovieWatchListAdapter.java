package com.example.moviapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviapp.Models.Genre;
import com.example.moviapp.Models.MovieDetails;
import com.example.moviapp.Models.MovieModel;
import com.example.moviapp.R;
import com.example.moviapp.utils.Credentials;

import java.util.ArrayList;
import java.util.List;

public class MovieWatchListAdapter extends RecyclerView.Adapter<MovieWatchListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<MovieDetails> movies = new ArrayList<>();

    public MovieWatchListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MovieDetails movie = movies.get(position);

        Glide.with(context)
                .load(Credentials.GET_IMAGE + movie.getPoster_path())
                .into(holder.imageView);

        holder.txtMovieName.setText(movie.getTitle());

        if (movie.getGenres().size() != 0) {
            if (movie.getGenres().size() == 1) {

                holder.txtGenre1.setText(movie.getGenres().get(0).getName());
                holder.txtSymbol.setVisibility(View.GONE);
                holder.txtGenre2.setVisibility(View.GONE);

            } else if (movie.getGenres().size() != 0) {


                holder.txtSymbol.setVisibility(View.VISIBLE);
                holder.txtGenre1.setVisibility(View.VISIBLE);
                holder.txtGenre2.setVisibility(View.VISIBLE);

                holder.txtGenre1.setText(movie.getGenres().get(0).getName());
                holder.txtGenre2.setText(movie.getGenres().get(1).getName());
            }
        }
        holder.txtIMDB.setText(String.valueOf(movie.getVote_average()));

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(ArrayList<MovieDetails> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView txtMovieName, txtGenre1, txtGenre2, txtSymbol, txtIMDB;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_movie);
            txtMovieName = itemView.findViewById(R.id.txt_movie_name);
            txtIMDB = itemView.findViewById(R.id.txt_imdbPoint);
            txtSymbol = itemView.findViewById(R.id.symbol);
            txtGenre1 = itemView.findViewById(R.id.txt_genre1);
            txtGenre2 = itemView.findViewById(R.id.txt_genre2);
        }
    }

}
