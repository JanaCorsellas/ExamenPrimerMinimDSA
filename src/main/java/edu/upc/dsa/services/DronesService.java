package edu.upc.dsa.services;

import edu.upc.dsa.DronesManager;
import edu.upc.dsa.DronesManagerImpl;

import edu.upc.dsa.models.Dron;
import edu.upc.dsa.models.Piloto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import io.swagger.annotations.Api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/drones", description = "Endpoint to Drones Service")
@Path("/drones")
public class DronesService {
    private DronesManager dm;

    public DronesService() {
        this.dm = DronesManagerImpl.getInstance();
    }

    @POST
    @ApiOperation(value = "Afegir nou dron", notes = "Afegirem un dron nou")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitós", response= Dron.class),
            @ApiResponse(code = 500, message = "Error de validació")

    })

    @Path("/afegirDron")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDron(Dron dron){
        //verifiquem si es proporciona un dron vàlid
        if (dron == null || dron.getId() == null || dron.getNombre() == null || dron.getFabricante() == null || dron.getModelo() == null) {
            return Response.status(500).entity("Error de validació: Informació de dron incompleta").build();
        }

        //agreguem el dron
        DronesManager manager = DronesManagerImpl.getInstance();
        manager.addDron(dron.getId(), dron.getNombre(), dron.getFabricante(), dron.getModelo());

        return Response.status(201).entity(dron).build();
    }

    @POST
    @ApiOperation(value = "Afegir nou pilot", notes = "Afegirem un pilot nou")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitós", response= Piloto.class),
            @ApiResponse(code = 500, message = "Error de validació")

    })

    @Path("/afegirPilot")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPiloto(Piloto piloto){
        //verifiquem si es proporciona un dron vàlid
        if (piloto == null || piloto.getId() == null || piloto.getNombre() == null || piloto.getApellido1() == null || piloto.getApellido2() == null) {
            return Response.status(500).entity("Error de validació: Informació de pilot incompleta").build();
        }

        //agreguem el pilot
        DronesManager manager = DronesManagerImpl.getInstance();
        manager.addPiloto(piloto.getId(), piloto.getNombre(), piloto.getApellido1(), piloto.getApellido2());

        return Response.status(201).entity(piloto).build();
    }

    @GET
    @ApiOperation(value = "Llistar drons per hores de vol descendent", notes = "Llistar tots els drons ordenats per hores de vol descendents")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exitós", response = Dron.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error intern del servidor")
    })
    @Path("/llistarDrons")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarDronesPorHorasDeVueloDescendente() {
        DronesManager manager = DronesManagerImpl.getInstance();

        try {
            // Obtener la lista de drones ordenados por horas de vuelo descendente
            List<Dron> dronesOrdenados = manager.listarDronesPorHorasDeVueloDescendente();

            // Devolver respuesta exitosa con la lista de drones
            return Response.status(200).entity(dronesOrdenados).build();
        } catch (Exception e) {
            // En caso de error, registrar el error y devolver respuesta de error interno del servidor
            //logger.error("Error al listar drones por horas de vuelo descendente", e);
            return Response.status(500).entity("Error intern del servidor").build();
        }
    }

    @GET
    @ApiOperation(value = "Llistar pilots per hores de vol descendent", notes = "Llistar tots els pilots ordenats per hores de vol descendents")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Èxit", response = Piloto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error intern del servidor")
    })
    @Path("/llistarPilots")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPilotosPorHorasDeVueloDescendente() {
        DronesManager manager = DronesManagerImpl.getInstance();

        try {
            // Obtener la lista de pilotos ordenados por horas de vuelo descendente
            List<Piloto> pilotosOrdenados = manager.listarPilotosPorHorasDeVueloDescendente();

            // Devolver respuesta exitosa con la lista de pilotos
            return Response.status(200).entity(pilotosOrdenados).build();
        } catch (Exception e) {
            // En caso de error, registrar el error y devolver respuesta de error interno del servidor
            //logger.error("Error al listar pilotos por horas de vuelo descendente", e);
            return Response.status(500).entity("Error intern del servidor").build();
        }
    }
}
