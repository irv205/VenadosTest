package com.example.venadosdefinitivo.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.venadosdefinitivo.R;
import com.example.venadosdefinitivo.adapter.JuegosAdapter;
import com.example.venadosdefinitivo.models.data;
import com.example.venadosdefinitivo.models.juegos.Juegos;
import com.example.venadosdefinitivo.myInterface.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Fragment_Ascenso extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    JuegosAdapter juegosAdapter;
    RecyclerView recyclerView;

    RecyclerView.Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__ascenso, container, false);

        juegosAdapter = new JuegosAdapter(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.RecycleAscenso);
        recyclerView.setAdapter(juegosAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        call();

        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshAscenso);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                call();
            }
        });

        return view;
    }

    public void call(){

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://venados.dacodes.mx/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        Service service = retrofit.create(Service.class);

        Call<data> call = service.getJuegos("application/json");

        call.enqueue(new Callback<data>() {
            @Override
            public void onResponse(Call<data> call, Response<data> response) {

                //Hacer filtro de Ascenso
                //if(response.isSuccessful()){

                    List<Juegos> juegos = response.body().getDataList().getJuegos();
                    List<Juegos> ascenso = new ArrayList<>();

                    for(int c=0;c<juegos.size();c++){
                        if (response.body().getDataList().getJuegos().get(c).getLeague().equals("Ascenso MX")) {
                            ascenso.add(juegos.get(c));
                        }else{
                            Toast.makeText(getContext(),"No hay equipos en Ascenso MX", Toast.LENGTH_SHORT).show();
                        }
                    }
                        juegosAdapter.addjuegos(ascenso);
                    response.body();
                //}
                    refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<data> call, Throwable t) {
                Log.d("Error", t.getMessage());
                refreshLayout.setRefreshing(false);
            }
        });
    }
}
