package com.example.venadosdefinitivo.myInterface;

import com.example.venadosdefinitivo.models.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Service {

    @GET("games")
    Call<data> getJuegos (@Header("Accept") String header);

    @GET("players")
    Call<data> getJuegadores (@Header("Accept") String header);

    @GET("statistics")
    Call<data> getEstadisticas (@Header("Accept") String header);
}
