package com.example.venadosdefinitivo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.venadosdefinitivo.R;
import com.example.venadosdefinitivo.adapter.EstadisticasAdapter;
import com.example.venadosdefinitivo.models.data;
import com.example.venadosdefinitivo.myInterface.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Fragment_Estadisticas extends Fragment {

    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    EstadisticasAdapter estadisticasAdapter;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__estadisticas, container, false);

        estadisticasAdapter = new EstadisticasAdapter(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerStatistics);
        recyclerView.setAdapter(estadisticasAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        progressBar = (ProgressBar) view.findViewById(R.id.circular);

        call();

        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshEstadisticas);
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

        Call<data> call = service.getEstadisticas("application/json");

        call.enqueue(new Callback<data>() {
            @Override
            public void onResponse(Call<data> call, Response<data> response) {

                if(response.isSuccessful()){
                    estadisticasAdapter.addEstadisticas(response.body().getDataList().getEstadisticas());
                    response.body().getDataList();
                    progressBar.setVisibility(View.GONE);
                }
                    refreshLayout.setRefreshing(false);
                    progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<data> call, Throwable t) {
                Log.d("Error", t.getMessage());
                refreshLayout.setRefreshing(false);
            }
        });

    }
}
