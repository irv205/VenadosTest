package com.example.venadosdefinitivo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.venadosdefinitivo.R;
import com.example.venadosdefinitivo.models.estadisticas.Estadisticas;

import java.util.ArrayList;
import java.util.List;

public class EstadisticasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    List<Estadisticas> adapterList = new ArrayList<>();

    public EstadisticasAdapter(Context context) {
        this.context = context;
    }

    public class EstadisticasA extends RecyclerView.ViewHolder{

        private TextView posicion;
        private ImageView imagen;
        private TextView equipo;
        private TextView juegos;
        private TextView goles;
        private TextView puntos;

        public EstadisticasA(@NonNull View itemView) {
            super(itemView);

            posicion = itemView.findViewById(R.id.posicionEs);
            imagen =  itemView.findViewById(R.id.imageEs);
            equipo =  itemView.findViewById(R.id.equipoEs);
            juegos = itemView.findViewById(R.id.juegosEs);
            goles = itemView.findViewById(R.id.difgolesEs);
            puntos = itemView.findViewById(R.id.puntosEs);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.view_estadisticas,viewGroup,false);
        return new EstadisticasA(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        EstadisticasA e = (EstadisticasA)viewHolder;
        listaEstadisticas(e,i);
    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public void addEstadisticas(List<Estadisticas> lista) {

        if (adapterList != null && adapterList.size() > 0) {
            lista.clear();
        } else {
            adapterList.addAll(lista);
            notifyDataSetChanged();

        }
    }

    public void listaEstadisticas(EstadisticasA statistics, int p){

        statistics.posicion.setText(String.valueOf(adapterList.get(p).getPosition()));
        Glide.with(context).load(adapterList.get(p).getImage()).into(statistics.imagen);
        statistics.equipo.setText(String.valueOf(adapterList.get(p).getTeam()));
        statistics.juegos.setText(String.valueOf(adapterList.get(p).getGames()));
        statistics.goles.setText(String.valueOf(adapterList.get(p).getScore_diff()));
        statistics.puntos.setText(String.valueOf(adapterList.get(p).getPoints()));
    }
}
