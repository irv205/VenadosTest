package com.example.venadosdefinitivo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.venadosdefinitivo.R;
import com.example.venadosdefinitivo.adapter.JugadoresAdapter;
import com.example.venadosdefinitivo.models.data;
import com.example.venadosdefinitivo.models.jugadores.Jugadores;
import com.example.venadosdefinitivo.models.jugadores.JugadoresList;
import com.example.venadosdefinitivo.myInterface.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_Jugadores extends Fragment {

    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    JugadoresAdapter jugadoresAdapter;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__jugadores, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.Recyclejugadores);

        progressBar = (ProgressBar) view.findViewById(R.id.circular);

        jugadoresAdapter = new JugadoresAdapter(getContext());

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(jugadoresAdapter);

        call();

        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshJugadores);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    public void call(){

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://venados.dacodes.mx/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        Service service = retrofit.create(Service.class);

        Call<data> call = service.getJuegadores("application/json");

        call.enqueue(new Callback<data>() {
            @Override
            public void onResponse(Call<data> call, Response<data> response) {

                if(response.isSuccessful()){

                    List<Jugadores> jugadores = new ArrayList<>();
                    jugadores.addAll(response.body().getDataList().getJugadores().getForwards());
                    jugadores.addAll(response.body().getDataList().getJugadores().getCenters());
                    jugadores.addAll(response.body().getDataList().getJugadores().getDefenses());
                    jugadores.addAll(response.body().getDataList().getJugadores().getGoalkeepers());
                    jugadores.addAll(response.body().getDataList().getJugadores().getCoaches());

                    List<Jugadores> equipo = new ArrayList<>();

                    for(int c=0; c<jugadores.size();c++){
                        equipo.add(jugadores.get(c));
                    }

                    jugadoresAdapter.addJugadores(equipo);
                    jugadoresAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
                    refreshLayout.setRefreshing(false);
                    progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<data> call, Throwable t) {

                Log.d("Error",t.getMessage());
                refreshLayout.setRefreshing(false);
            }
        });
    }
}
