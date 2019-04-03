package com.example.venadosdefinitivo.models.jugadores;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JugadoresList {

    @SerializedName("forwards")
    private List<Jugadores> forwards;
    @SerializedName("centers")
    private List<Jugadores> centers;
    @SerializedName("defenses")
    private List<Jugadores> defenses;
    @SerializedName("goalkeepers")
    private List<Jugadores> goalkeepers;
    @SerializedName("coaches")
    private List<Jugadores> coaches;

    public List<Jugadores> getForwards() {
        return forwards;
    }

    public void setForwards(List<Jugadores> forwards) {
        this.forwards = forwards;
    }

    public List<Jugadores> getCenters() {
        return centers;
    }

    public void setCenters(List<Jugadores> centers) {
        this.centers = centers;
    }

    public List<Jugadores> getDefenses() {
        return defenses;
    }

    public void setDefenses(List<Jugadores> defenses) {
        this.defenses = defenses;
    }

    public List<Jugadores> getGoalkeepers() {
        return goalkeepers;
    }

    public void setGoalkeepers(List<Jugadores> goalkeepers) {
        this.goalkeepers = goalkeepers;
    }

    public List<Jugadores> getCoaches() {
        return coaches;
    }

    public void setCoaches(List<Jugadores> coaches) {
        this.coaches = coaches;
    }
}
