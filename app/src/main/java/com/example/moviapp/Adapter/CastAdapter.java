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
import com.example.moviapp.Models.Cast;
import com.example.moviapp.R;
import com.example.moviapp.utils.Credentials;

import java.util.ArrayList;
import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.MyViewHolder> {

    private Context context;
    private List<Cast> casts = new ArrayList<>();

    public CastAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cast cast = casts.get(position);

        Glide.with(context)
                .load(Credentials.GET_IMAGE + cast.getProfile_path())
                .into(holder.imgCast);

        holder.txtCastName.setText(cast.getName());

        holder.txtCharacterName.setText(cast.getCharacter());

    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    public void setCast(List<Cast> cast) {
        this.casts = cast;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCast;
        private TextView txtCastName;
        private TextView txtCharacterName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCast = itemView.findViewById(R.id.img_cast);
            txtCastName = itemView.findViewById(R.id.txt_cast_name);
            txtCharacterName = itemView.findViewById(R.id.txt_character_name);
        }
    }

}
