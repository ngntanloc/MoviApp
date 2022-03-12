package com.example.moviapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviapp.DetailsMovieActivity;
import com.example.moviapp.Models.Genre;
import com.example.moviapp.Models.MovieModel;
import com.example.moviapp.R;
import com.example.moviapp.SearchMovieActivity;
import com.example.moviapp.utils.Credentials;
import com.example.moviapp.viewmodels.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.MyViewHolder> {

    private Context context;
    private List<MovieModel> movies = new ArrayList<>();
    private List<Genre> genres;

    private MovieListViewModel movieListViewModel;

    public MovieSearchAdapter(Context context) {
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
        MovieModel model = movies.get(position);

        Glide.with(context)
                .load(Credentials.GET_IMAGE + model.getPoster_path())
                .into(holder.imageView);

        holder.txtMovieName.setText(model.getTitle());
//        holder.txtGenre1.setText(genre1);
//        holder.txtGenre2.setText(genre2);
        holder.txtIMDB.setText(String.valueOf(model.getVote_average()));

        String genre1 = null, genre2 = null;

        if (model.getGenre_ids().size() != 0) {
            if (model.getGenre_ids().size() == 1) {

                for (Genre g: genres
                ) {
                    if (model.getGenre_ids().get(0) == g.getId()) {
                        genre1 = g.getName();
                    }
                }

                holder.txtGenre1.setText(genre1);
                holder.txtSymbol.setVisibility(View.GONE);
                holder.txtGenre2.setVisibility(View.GONE);

            } else if (model.getGenre_ids().size() != 0) {

                for (Genre g: genres
                     ) {
                    if (model.getGenre_ids().get(0) == g.getId()) {
                        genre1 = g.getName();
                    }
                    if (model.getGenre_ids().get(1) == g.getId()) {
                        genre2 = g.getName();
                    }
                }

                holder.txtSymbol.setVisibility(View.VISIBLE);
                holder.txtGenre1.setVisibility(View.VISIBLE);
                holder.txtGenre2.setVisibility(View.VISIBLE);

                holder.txtGenre1.setText(genre1);
                holder.txtGenre2.setText(genre2);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsMovieActivity.class);
                intent.putExtra("movieID", String.valueOf(model.getId()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(List<MovieModel> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
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
