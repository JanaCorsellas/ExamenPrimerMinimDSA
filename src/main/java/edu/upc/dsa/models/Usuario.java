package edu.upc.dsa.models;

public class Usuario {
    String id;
    String nombre;
    String apellido;
    Partida partidaActual;

    public Usuario(String id, String nombre, String apellido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.partidaActual = null; // Al principio el usuario no tiene partida activa
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Partida getPartidaActual() {
        return partidaActual;
    }

    public void setPartidaActual(Partida partidaActual) {
        this.partidaActual = partidaActual;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", partidaActual=" + partidaActual +
                '}';
    }
}
