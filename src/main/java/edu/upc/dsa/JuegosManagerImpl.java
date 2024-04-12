package edu.upc.dsa;

import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.Partida;
import org.apache.log4j.Logger;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class JuegosManagerImpl implements JuegosManager{
    final static Logger logger = Logger.getLogger(JuegosManagerImpl.class);
    private static JuegosManager instance;
    protected List<Juego> juegos;
    protected List<Usuario> usuarios;
    protected List<Partida> partidas;

    private JuegosManagerImpl() {
        this.juegos = new LinkedList<>();
        this.usuarios = new LinkedList<>();
        this.partidas = new LinkedList<>();
    }

    public static JuegosManager getInstance() {
        if (instance==null) instance = new JuegosManagerImpl();
        return instance;
    }

    /*public int size() {
        int ret = this.juegos.size();
        logger.info("size " + ret);

        return ret;
    }*/

    @Override
    public void crearJuego(String id, String descripcion, int numeroNiveles){
        logger.info("Creando juego con ID: " + id);

        // Verificar si ya existe un juego con el mismo ID
        boolean existe = false;
        for (Juego juego : juegos) {
            if (juego.getId().equals(id)) {
                existe = true;
                break;
            }
        }

        // Si no existe, agregar el nuevo juego
        if (!existe) {
            Juego juego = new Juego(id, descripcion, numeroNiveles);
            juegos.add(juego);
            logger.info("Juego creado correctamente");
        } else {
            logger.error("El juego con ID " + id + " ya existe.");
        }
    }

    @Override
    public void iniciarPartida(String idJuego, String idUsuario){
        logger.info("Iniciando partida para el usuario " + idUsuario + " en el juego " + idJuego);

        // Verificar si el juego existe
        Juego juego = buscarJuegoPorId(idJuego);
        if (juego == null) {
            logger.error("El juego con ID " + idJuego + " no existe.");
            return;
        }

        // Obtener el número de niveles del juego
        int numeroNiveles = juego.getNumeroNiveles();

        // Verificar si el usuario ya tiene una partida activa
        for (Partida partida : partidas) {
            if (partida.getIdUsuario().equals(idUsuario)) {
                logger.error("El usuario " + idUsuario + " ya tiene una partida activa.");
                return;
            }
        }

        // Crear una nueva partida y agregar al usuario
        //Partida partida = new Partida(idUsuario, juego);
        Partida partida = new Partida(idJuego, idUsuario, 1, 50, obtenerFechaActual());
        //partida.setPuntuacion(50); // Establecer 50 puntos iniciales
        partidas.add(partida);

        logger.info("Partida iniciada para el usuario " + idUsuario + " en el juego " + idJuego);
    }

    @Override
    public String consultarNivelActual(String idUsuario) {
        logger.info("Consultando nivel actual para el usuario " + idUsuario);

        // Buscar la partida activa del usuario
        for (Partida partida : partidas) {
            if (partida.getIdUsuario().equals(idUsuario)) {
                // Si se encuentra la partida, retornar el nivel actual
                return "El usuario " + idUsuario + " está en el nivel " + partida.getNivelActual() +
                        " de la partida con ID " + partida.getIdJuego();
            }
        }
        // Si no se encuentra una partida activa para el usuario, mostrar mensaje de error
        logger.error("El usuario " + idUsuario + " no tiene una partida activa.");
        return "El usuario " + idUsuario + " no tiene una partida activa.";
    }

    @Override
    public int consultarPuntuacionActual(String idUsuario) {
        logger.info("Consultando puntuación actual para el usuario " + idUsuario);

        // Buscar la partida activa del usuario
        for (Partida partida : partidas) {
            if (partida.getIdUsuario().equals(idUsuario)) {
                // Si se encuentra la partida, retornar la puntuación actual
                return partida.getPuntuacion();
            }
        }
        // Si no se encuentra una partida activa para el usuario, mostrar mensaje de error
        logger.error("El usuario " + idUsuario + " no tiene una partida activa.");
        return -1; // O algún valor que indique que no se encontró la puntuación
    }

    @Override
    public boolean pasarDeNivel(String idUsuario, int puntosConseguidos, String fechaCambioNivel) {
        logger.info("Pasando de nivel para el usuario " + idUsuario);

        // Buscar la partida activa del usuario
        Partida partidaActiva = null;
        for (Partida partida : partidas) {
            if (partida.getIdUsuario().equals(idUsuario)) {
                partidaActiva = partida;
                break;
            }
        }

        // Verificar si se encontró la partida activa
        if (partidaActiva == null) {
            logger.error("El usuario " + idUsuario + " no tiene una partida activa.");
            return false;
        }

        // Obtener el nivel actual y la puntuación acumulada
        int nivelActual = partidaActiva.getNivelActual();
        int puntuacionAcumulada = partidaActiva.getPuntuacion();

        // Calcular el nuevo nivel
        int nuevoNivel = nivelActual + (puntosConseguidos / 100);

        // Obtener el número de niveles del juego
        Juego juego = buscarJuegoPorId(partidaActiva.getIdJuego());
        int numeroNiveles = juego.getNumeroNiveles();

        // Actualizar el nivel actual y la puntuación acumulada
        partidaActiva.setNivelActual(nuevoNivel);
        partidaActiva.setPuntuacion(puntuacionAcumulada + puntosConseguidos);

        // Verificar si el nuevo nivel es el último
        if (nuevoNivel >= numeroNiveles) {
            // Incrementar la puntuación acumulada en 100 puntos
            partidaActiva.setPuntuacion(puntuacionAcumulada + puntosConseguidos + 100);
            // Finalizar la partida
            finalizarPartida(partidaActiva.getIdUsuario());
        } else {
            // Actualizar el nivel actual y la puntuación acumulada
            partidaActiva.setNivelActual(nuevoNivel);
            partidaActiva.setPuntuacion(puntuacionAcumulada + puntosConseguidos);
        }

        // Actualizar la fecha de cambio de nivel
        partidaActiva.setFechaFin(fechaCambioNivel);

        logger.info("Nivel actualizado para el usuario " + idUsuario);
        return false;
    }

    @Override
    public boolean finalizarPartida(String idUsuario) {
        logger.info("Finalizando partida para el usuario " + idUsuario);

        // Buscar la partida activa del usuario
        Partida partidaActiva = null;
        for (Partida partida : partidas) {
            if (partida.getIdUsuario().equals(idUsuario)) {
                partidaActiva = partida;
                break;
            }
        }

        // Verificar si se encontró la partida activa
        if (partidaActiva == null) {
            logger.error("El usuario " + idUsuario + " no tiene una partida activa.");
            return false;
        }

        // Finalizar la partida eliminándola de la lista de partidas activas
        partidas.remove(partidaActiva);

        logger.info("Partida finalizada para el usuario " + idUsuario);

        return false;
    }

    @Override
    public List<String> consultarUsuariosPorPuntuacionDescendente(String idJuego) {
        logger.info("Consultando usuarios por puntuación descendente para el juego " + idJuego);

        // Buscar todas las partidas asociadas al juego
        List<Partida> partidasJuego = new ArrayList<>();
        for (Partida partida : partidas) {
            if (partida.getIdJuego().equals(idJuego)) {
                partidasJuego.add(partida);
            }
        }

        // Calcular la puntuación total de cada usuario
        Map<String, Integer> puntuacionUsuarios = new HashMap<>();
        for (Partida partida : partidasJuego) {
            String idUsuario = partida.getIdUsuario();
            int puntuacionPartida = partida.getPuntuacion();
            puntuacionUsuarios.put(idUsuario, puntuacionUsuarios.getOrDefault(idUsuario, 0) + puntuacionPartida);
        }

        // Ordenar los usuarios por puntuación descendente
        List<String> usuariosOrdenados = new ArrayList<>(puntuacionUsuarios.keySet());
        usuariosOrdenados.sort((idUsuario1, idUsuario2) -> {
            int puntuacionUsuario1 = puntuacionUsuarios.get(idUsuario1);
            int puntuacionUsuario2 = puntuacionUsuarios.get(idUsuario2);
            return Integer.compare(puntuacionUsuario2, puntuacionUsuario1);
        });

        logger.info("Usuarios ordenados por puntuación descendente: " + usuariosOrdenados);
        return usuariosOrdenados;

    }

    @Override
    public List<String> consultarPartidasDeUsuario(String idUsuario) {
        logger.info("Consultando partidas del usuario " + idUsuario);

        List<String> partidasUsuario = new ArrayList<>();
        for (Partida partida : partidas) {
            if (partida.getIdUsuario().equals(idUsuario)) {
                partidasUsuario.add(partida.getIdJuego());
            }
        }

        logger.info("Partidas del usuario " + idUsuario + ": " + partidasUsuario);
        return partidasUsuario;
    }

    public Juego buscarJuegoPorId(String idJuego) {
        for (Juego juego : juegos) {
            if (juego.getId().equals(idJuego)) {
                return juego;
            }
        }
        return null; // Si no se encuentra el juego con el ID especificado
    }

    public static String obtenerFechaActual() {
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha en el formato deseado (por ejemplo, "dd/MM/yyyy")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaActual.format(formatter);

        return fechaFormateada;

    }
}
