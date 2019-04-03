package com.example.venadosdefinitivo.models.juegos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JuegosList {

    @SerializedName("games")
    private List<Juegos> list;

    public List<Juegos> getList() {
        return list;
    }

    public void setList(List<Juegos> list) {
        this.list = list;
    }

}
