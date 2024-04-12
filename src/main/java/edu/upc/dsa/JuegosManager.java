package edu.upc.dsa;

import java.util.List;

public interface JuegosManager {
    public void crearJuego(String id, String descripcion, int numeroNiveles);

    public void iniciarPartida(String idJuego, String idUsuario);

    public String consultarNivelActual(String idUsuario);

    public int consultarPuntuacionActual(String idUsuario);

    public boolean pasarDeNivel(String idUsuario, int puntosConseguidos, String fechaCambioNivel);

    public boolean finalizarPartida(String idUsuario);

    public List<String> consultarUsuariosPorPuntuacionDescendente(String idJuego);

    public List<String> consultarPartidasDeUsuario(String idUsuario);
}
