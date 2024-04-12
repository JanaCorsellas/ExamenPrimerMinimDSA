package edu.upc.dsa.models;

public class Juego {
    String id;
    String descripcion;
    int numeroNiveles;

    public Juego(String id, String descripcion, int numeroNiveles) {
        this.id = id;
        this.descripcion = descripcion;
        this.numeroNiveles = numeroNiveles;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumeroNiveles() {
        return numeroNiveles;
    }

    public void setNumeroNiveles(int numeroNiveles) {
        this.numeroNiveles = numeroNiveles;
    }

    @Override
    public String toString() {
        return "Juego{" +
                "id='" + id + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", numeroNiveles=" + numeroNiveles +
                '}';
    }
}
