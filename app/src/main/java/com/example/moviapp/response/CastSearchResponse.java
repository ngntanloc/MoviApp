package com.example.moviapp.response;

import com.example.moviapp.Models.Cast;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastSearchResponse {

    @SerializedName("id")
    @Expose
    private int cast_id;

    @SerializedName("cast")
    @Expose
    private List<Cast> mCast;

    public List<Cast> getmCast() {
        return mCast;
    }

    @Override
    public String toString() {
        return "CastSearchResponse{" +
                "cast_id=" + cast_id +
                ", mCast=" + mCast +
                '}';
    }
}
