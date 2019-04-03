package com.example.venadosdefinitivo.models.estadisticas;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EstadisticasList {

    @SerializedName("statistics")
    private List<Estadisticas> list;

    public List<Estadisticas> getList() {
        return list;
    }

    public void setList(List<Estadisticas> list) {
        this.list = list;
    }

}
