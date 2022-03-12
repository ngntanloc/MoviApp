package com.example.moviapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviapp.DetailsMovieActivity;
import com.example.moviapp.Models.MovieModel;
import com.example.moviapp.R;
import com.example.moviapp.utils.Credentials;

import java.util.ArrayList;
import java.util.List;

public class MoviePopularAdapter extends RecyclerView.Adapter<MoviePopularAdapter.MyViewHolder> {

    private Context context;
    private List<MovieModel> movies = new ArrayList<>();

    public MoviePopularAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_movie_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MovieModel movie = movies.get(position);

        Glide.with(context)
                .load(Credentials.GET_IMAGE + movie.getPoster_path())
                .into(holder.imageView);

        holder.txtMovieName.setText(movie.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsMovieActivity.class);
                intent.putExtra("movieID", "" + movie.getId());
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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView txtMovieName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageNewMovie);
            txtMovieName = itemView.findViewById(R.id.txtNewMovieName);
        }
    }
}
