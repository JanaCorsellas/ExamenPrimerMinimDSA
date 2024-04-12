package edu.upc.dsa.models;

public class Dron {
    String id;
    String nombre;
    String fabricante;
    String modelo;
    int horasVuelo;
    boolean reparado;

    public Dron() {}

    // Constructor
    public Dron(String id, String nombre, String fabricante, String modelo) {
        this.id = id;
        this.nombre = nombre;
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.horasVuelo = 0;
        this.reparado = false;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getHorasVuelo() {
        return horasVuelo;
    }

    public void setHorasVuelo(int horasVuelo) {
        this.horasVuelo = horasVuelo;
    }

    public boolean isReparado() {
        return reparado;
    }

    public void setReparado(boolean reparado) {
        this.reparado = reparado;
    }

    @Override
    public String toString() {
        return "Dron{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", modelo='" + modelo + '\'' +
                ", horasVuelo=" + horasVuelo +
                '}';
    }
}
