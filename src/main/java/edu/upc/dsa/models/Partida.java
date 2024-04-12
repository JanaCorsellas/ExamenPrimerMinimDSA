package edu.upc.dsa.models;

public class Partida {
    String idJuego;
    String idUsuario;
    int nivelActual;
    int puntuacion;
    String fechaInicio;
    String fechaFin;

    public Partida(String idJuego, String idUsuario, int nivelActual, int puntuacion, String fechaInicio) {
        this.idJuego = idJuego;
        this.idUsuario = idUsuario;
        this.nivelActual = nivelActual;
        this.puntuacion = puntuacion;
        this.fechaInicio = fechaInicio;
    }

    // Getters y setters

    public String getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(String idJuego) {
        this.idJuego = idJuego;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public void setNivelActual(int nivelActual) {
        this.nivelActual = nivelActual;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "Partida{" +
                "idJuego='" + idJuego + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                ", nivelActual=" + nivelActual +
                ", puntuacion=" + puntuacion +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }
}
