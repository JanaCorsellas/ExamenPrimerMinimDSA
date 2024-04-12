package edu.upc.dsa;

import edu.upc.dsa.models.Dron;
import edu.upc.dsa.models.Piloto;
import edu.upc.dsa.models.ReservaPlanVuelo;

import java.util.List;

public interface DronesManager {
    public void addDron (String id, String nombre, String fabricante, String modelo);
    public void addPiloto(String id, String nombre, String apellido1, String apellido2);
    public List<Dron> listarDronesPorHorasDeVueloDescendente();
    public List<Piloto> listarPilotosPorHorasDeVueloDescendente();
    public void guardarDronEnAlmacen(String idDron);
    public void repararDronEnAlmacen();
    public void añadirReservaPlanVuelo(String idDron, String idPiloto, String fecha, int duracion, String posicionInicio, String posicionDestino);
    public List<ReservaPlanVuelo> listarReservasPorPiloto(String idPiloto);
    public List<ReservaPlanVuelo> listarReservasPorDron(String idDron);
    public Dron obtenerDronPorId(String idDron);
}
