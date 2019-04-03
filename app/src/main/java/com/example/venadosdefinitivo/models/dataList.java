package com.example.venadosdefinitivo.models;

import com.example.venadosdefinitivo.models.estadisticas.Estadisticas;
import com.example.venadosdefinitivo.models.juegos.Juegos;
import com.example.venadosdefinitivo.models.jugadores.JugadoresList;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class dataList {

    @SerializedName("games")
    private List<Juegos> juegos;
    @SerializedName("team")
    private JugadoresList jugadores;
    @SerializedName("statistics")
    private List<Estadisticas> estadisticas;

    public List<Juegos> getJuegos() {
        return juegos;
    }

    public void setJuegos(List<Juegos> juegos) {
        this.juegos = juegos;
    }

    public JugadoresList getJugadores() {
        return jugadores;
    }

    public void setJugadores(JugadoresList jugadores) {
        this.jugadores = jugadores;
    }

    public List<Estadisticas> getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(List<Estadisticas> estadisticas) {
        this.estadisticas = estadisticas;
    }
}
