package com.example.moviapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MovieDetails implements Parcelable {

    private int id;
    private String title;
    private String release_date;
    private String overview;
    private boolean adult;
    private float vote_average;
    private String backdrop_path;
    private String poster_path;
    private List<Genre> genres;

    public MovieDetails() {
    }

    public MovieDetails(int id, String title, String release_date, String overview, boolean adult, float vote_average, String backdrop_path, String poster_path, List<Genre> genres) {
        this.id = id;
        this.title = title;
        this.release_date = release_date;
        this.overview = overview;
        this.adult = adult;
        this.vote_average = vote_average;
        this.backdrop_path = backdrop_path;
        this.poster_path = poster_path;
        this.genres = genres;
    }

    protected MovieDetails(Parcel in) {
        id = in.readInt();
        title = in.readString();
        release_date = in.readString();
        overview = in.readString();
        adult = in.readByte() != 0;
        vote_average = in.readFloat();
        backdrop_path = in.readString();
        poster_path = in.readString();
    }

    public static final Creator<MovieDetails> CREATOR = new Creator<MovieDetails>() {
        @Override
        public MovieDetails createFromParcel(Parcel in) {
            return new MovieDetails(in);
        }

        @Override
        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(release_date);
        parcel.writeString(overview);
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeFloat(vote_average);
        parcel.writeString(backdrop_path);
        parcel.writeString(poster_path);
    }
}
