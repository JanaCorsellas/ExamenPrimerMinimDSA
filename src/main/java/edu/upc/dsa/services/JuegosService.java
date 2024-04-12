package edu.upc.dsa.services;


import edu.upc.dsa.JuegosManager;
import edu.upc.dsa.JuegosManagerImpl;
import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.Partida;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/juegos", description = "Endpoint to Juegos Service")
@Path("/juegos")
public class JuegosService {

    private JuegosManager jm;

    public JuegosService() {
        this.jm = JuegosManagerImpl.getInstance();
    }

    @POST
    @ApiOperation(value = "Crear un juego", notes = "Crear un juego nuevo")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Juego creado"),
            @ApiResponse(code = 500, message = "Error de validación")
    })
    @Path("/creacion")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearJuego(@PathParam("id") String id, @PathParam("descripcion") String descripcion, @PathParam("numeroNiveles") int numeroNiveles) {
        this.jm.crearJuego(id, descripcion, numeroNiveles);
        return Response.status(201).build();
    }

    @POST
    @ApiOperation(value = "Iniciar partida", notes = "Inicia una partida de un juego con un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitoso"),
            @ApiResponse(code = 404, message = "Usuario o juego no encontrado")
    })
    @Path("/iniciarPartida")
    @Produces(MediaType.APPLICATION_JSON)
    public Response iniciarPartida(@PathParam("idUsuario") String idUsuario, @PathParam("idJuego") String idJuego) {
        this.jm.iniciarPartida(idJuego, idUsuario);
        if (idUsuario != null & idJuego != null){
            return Response.status(201).build();
        }
        else{
            return Response.status(404).build();
        }
    }

    @GET
    @ApiOperation(value = "Consultar el nivel actual de un usuario", notes = "Obtiene el nivel actual de un usuario en un juego")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitoso"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    @Path("/nivel")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarNivelActual(@PathParam("idUsuario") String idUsuario) {
        String nivelActual  = this.jm.consultarNivelActual(idUsuario);
        if (nivelActual != null){
            return Response.status(201).entity(nivelActual).build();
        }
        else{
            return Response.status(404).build();
        }
    }

    @GET
    @ApiOperation(value = "Consultar puntuación usuario", notes = "Obtiene la puntuacion de un usuario en un juego")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitoso"),
            @ApiResponse(code = 404, message = "Usuario no tiene una partida activa")
    })
    @Path("/puntuacion")
    public Response consultarPuntuacionActual(@PathParam("idUsuario") String idUsuario) {
        int puntuacionActual = this.jm.consultarPuntuacionActual(idUsuario);
        if (puntuacionActual >= 0) return Response.status(201).entity(puntuacionActual).build();
        else {
            return Response.status(404).build();
        }
    }

    @POST
    @ApiOperation(value = "Subir de nivel", notes = "Sube de nivel a un usuario en un juego")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitoso"),
            @ApiResponse(code = 404, message = "Usuario no encontrado o no tiene una partida activa")
    })
    @Path("/subirNivel")
    public Response pasarDeNivel(@PathParam("idUsuario") String idUsuario, @PathParam("puntosConseguidos") int puntosConseguidos, @PathParam("fecha") String fechaCambioNivel) {
        // Lógica para pasar de nivel en el juego
        boolean usuarioEncontrado = jm.pasarDeNivel(idUsuario, puntosConseguidos, fechaCambioNivel);
        // Verificar si se encontró el usuario
        if (!usuarioEncontrado) {
            // Devolver una respuesta indicando que el usuario no ha sido encontrado (código 404)
            return Response.status(404).build();
        }
        // Devolver una respuesta HTTP indicando que el proceso fue exitoso (código 201)
        return Response.status(201).build();
    }

    @POST
    @ApiOperation(value = "Finalizar partida", notes = "Finalizamos el juego")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitoso"),
            @ApiResponse(code = 500, message = "Usuario no encontrado")

    })

    @Path("/finalizar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response finalizarPartida(@PathParam("idUsuario") String idUsuario) {
        boolean partidaFinalizada = jm.finalizarPartida(idUsuario);

        // Verificar si se ha finalizado la partida exitosamente
        if (partidaFinalizada) {
            // Devolver una respuesta indicando que la partida ha sido finalizada exitosamente (código 204)
            return Response.status(204).build();
        } else {
            // Devolver una respuesta indicando que el usuario no tiene una partida activa (código 404)
            return Response.status(404).build();
        }
    }
}