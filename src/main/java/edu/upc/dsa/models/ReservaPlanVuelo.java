package edu.upc.dsa.models;

public class ReservaPlanVuelo {
    String id; //la id de la reserva
    String idDron;
    String idPiloto;
    String fecha;
    int duracion;
    String posicionInicio;
    String posicionDestino;

    // Constructor
    public ReservaPlanVuelo(String idDron, String idPiloto, String fecha, int duracion, String posicionInicio, String posicionDestino) {
        //this.id = id;
        this.idDron = idDron;
        this.idPiloto = idPiloto;
        this.fecha = fecha;
        this.duracion = duracion;
        this.posicionInicio = posicionInicio;
        this.posicionDestino = posicionDestino;
    }

    // Getters y setters
    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getIdDron() {
        return idDron;
    }

    public void setIdDron(String idDron) {
        this.idDron = idDron;
    }

    public String getIdPiloto() {
        return idPiloto;
    }

    public void setIdPiloto(String idPiloto) {
        this.idPiloto = idPiloto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getPosicionInicio() {
        return posicionInicio;
    }

    public void setPosicionInicio(String posicionInicio) {
        this.posicionInicio = posicionInicio;
    }

    public String getPosicionDestino() {
        return posicionDestino;
    }

    public void setPosicionDestino(String posicionDestino) {
        this.posicionDestino = posicionDestino;
    }

    @Override
    public String toString() {
        return "ReservaPlanVuelo{" +
                "idDron='" + idDron + '\'' +
                ", idPiloto='" + idPiloto + '\'' +
                ", fecha='" + fecha + '\'' +
                ", duracion=" + duracion +
                ", posicionInicio='" + posicionInicio + '\'' +
                ", posicionDestino='" + posicionDestino + '\'' +
                '}';
    }

}
