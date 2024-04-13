package edu.upc.dsa;

import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;

import java.util.*;

public class DronesManagerImpl implements DronesManager{
    final static Logger logger = Logger.getLogger(DronesManagerImpl.class);
    private static DronesManager instance;
    protected List<Dron> drones;
    protected List<Piloto> pilotos;
    protected List<ReservaPlanVuelo> reservas;

    private DronesManagerImpl() {
        this.drones = new LinkedList<>();
        this.pilotos = new LinkedList<>();
        this.reservas = new LinkedList<>();
    }

    public static DronesManager getInstance() {
        if (instance==null) instance = new DronesManagerImpl();
        return instance;
    }

    @Override
    public void addDron(String id, String nombre, String fabricante, String modelo) {
        //verifiquem si ja existeix el dron
        logger.info("Vamos a añadir un dron con id " + id);
        for (Dron dron : drones) {
            if (dron.getId().equals(id)) {
                //si ja existeix un dron amb aquest id, missatge d'error i sortim
                logger.error("El dron con ID " + id + " ya existe.");
                return;
            }
        }

        //creem un nou dron i l'afegim a la llista
        Dron nuevoDron = new Dron(id, nombre, fabricante, modelo);
        drones.add(nuevoDron);
        logger.info("Dron añadido correctamente: " + nuevoDron);
    }


    @Override
    public void addPiloto(String id, String nombre, String apellido1, String apellido2) {
        logger.info("Vamos a añadir un piloto con id " + id);
        for (Piloto piloto : pilotos){
            if (piloto.getId().equals(id)){
                logger.error ("El piloto con ID " + id + " ya existe.");
                return;
          }
        }
        Piloto nuevoPiloto = new Piloto(id, nombre, apellido1, apellido2);
        pilotos.add(nuevoPiloto);
        logger.info("Piloto añadido correctamente: " + nuevoPiloto);
    }

    @Override
    public List<Dron> listarDronesPorHorasDeVueloDescendente() {
        logger.info("Listamos los drones por horas de vuelo descendente");
        List<Dron> dronesOrdenados = new ArrayList<>(drones);
        Comparator<Dron> comparadorAscendent = Comparator.comparingInt(Dron::getHorasVuelo);
        Collections.sort(dronesOrdenados, comparadorAscendent);
        Collections.reverse(dronesOrdenados);
        logger.info("Drones ordenados correctamente");
        return dronesOrdenados;
    }

    @Override
    public List<Piloto> listarPilotosPorHorasDeVueloDescendente() {
        logger.info("Listamos los pilotos por horas de vuelo descendente");
        List<Piloto> pilotosOrdenados = new ArrayList<>(pilotos);
        Comparator<Piloto> comparadorAscendent = Comparator.comparingInt(Piloto::getHorasVuelo);
        Collections.sort(pilotosOrdenados, comparadorAscendent);
        Collections.reverse(pilotosOrdenados);
        logger.info("Pilotos ordenados correctamente");
        return pilotosOrdenados;
    }

    @Override
    public void guardarDronEnAlmacen(String idDron) {
        logger.info("Guardando el dron con ID " + idDron + " en el almacén para tareas de mantenimiento.");

        //verifiquem si el dron ja està al magatzem
        for (Dron dron : drones) {
            if (dron.getId().equals(idDron)) {
                logger.error("El dron con ID " + idDron + " ya está en el almacén.");
                return;
            }
        }

        //agregar el dron al final de la llista (magatzem)
        Dron dron = obtenerDronPorId(idDron);
        drones.add(dron);

        logger.info("Dron con ID " + idDron + " guardado en el almacén correctamente.");
    }

    //mètode per obtenir un dron per la seva id
    public Dron obtenerDronPorId(String idDron) {
        for (Dron dron : drones) {
            if (dron.getId().equals(idDron)) {
                return dron;
            }
        }
        return null;
    }


    @Override
    public void repararDronEnAlmacen() {
        logger.info("Iniciando reparación de un dron en el almacén.");

        //verifiquem si hi ha drons al magatzem
        if (drones.isEmpty()) {
            logger.error("El almacén de drones está vacío. No hay drones para reparar.");
            return;
        }

        //trobem el dron més a prop de la porta (primer dron en la llista)
        Dron dronParaReparar = drones.get(0);

        //realitzar manteniment al dron
        dronParaReparar.setReparado(true);

        //eliminar el dron reparat del magatzem
        drones.remove(dronParaReparar);

        logger.info("Reparación completada para el dron con ID " + dronParaReparar.getId() +
                ". Dron eliminado del almacén y listo para ser utilizado.");
    }

    @Override
    public void añadirReservaPlanVuelo(String idDron, String idPiloto, String fecha, int duracion, String posicionInicio, String posicionDestino) {
        logger.info("Añadiendo reserva de plan de vuelo para dron " + idDron + " y piloto " + idPiloto);

        //verifiquem si el dron està disponible pel vol
        Dron dron = null;
        for (Dron d : drones) {
            if (d.getId().equals(idDron)) {
                dron = d;
                break;
            }
        }
        if (dron == null) {
            logger.error("El dron con ID " + idDron + " no está registrado.");
            return;
        }
        //si el boolean de reparado del dron no està true, vol dir que el dron està al magatzem
        if (!dron.isReparado()) {
            logger.error("El dron con ID " + idDron + " no está operativo, está en el almacén para ser reparado.");
            return;
        }
        /*if (dron.isEnAlmacen()) {
            logger.error("El dron con ID " + idDron + " está en el almacén para mantenimiento.");
            return;
        }*/

        //verifiquem si el pilot està disponible
        Piloto piloto = null;
        for (Piloto p : pilotos) {
            if (p.getId().equals(idPiloto)) {
                piloto = p;
                break;
            }
        }
        if (piloto == null) {
            logger.error("El piloto con ID " + idPiloto + " no está registrado.");
            return;
        }
        //observem si el pilot ja té algun vol assignat o no
        if (piloto.isEnVuelo()) {
            logger.error("El piloto con ID " + idPiloto + " ya tiene un plan de vuelo asignado.");
            return;
        }

        //mirem si hi ha conflictes d'horari pel dron
        for (ReservaPlanVuelo reserva : reservas) {
            if (reserva.getIdDron().equals(idDron) && reserva.getFecha().equals(fecha)) {
                logger.error("El dron con ID " + idDron + " ya tiene una reserva en la fecha " + fecha + ".");
                return;
            }
        }

        //mirem si hi ha conflictes d'horari pel pilot
        for (ReservaPlanVuelo reserva : reservas) {
            if (reserva.getIdPiloto().equals(idPiloto) && reserva.getFecha().equals(fecha)) {
                logger.error("El piloto con ID " + idPiloto + " ya tiene una reserva en la fecha " + fecha + ".");
                return;
            }
        }

        //creem la reserva i l'afegim a la llista de reserves
        ReservaPlanVuelo nuevaReserva = new ReservaPlanVuelo(idDron, idPiloto, fecha, duracion, posicionInicio, posicionDestino);
        reservas.add(nuevaReserva);
        logger.info("Reserva de plan de vuelo añadida correctamente: " + nuevaReserva);
    }

    @Override
    public List<ReservaPlanVuelo> listarReservasPorPiloto(String idPiloto) {
        logger.info("Listando reservas para el piloto con ID: " + idPiloto);
        List<ReservaPlanVuelo> reservasPorPiloto = new ArrayList<>();
        for (ReservaPlanVuelo reserva : reservas) {
            if (reserva.getIdPiloto().equals(idPiloto)) {
                reservasPorPiloto.add(reserva);
            }
        }
        if (reservasPorPiloto.isEmpty()) {
            logger.info("No se encontraron reservas para el piloto con ID: " + idPiloto);
        } else {
            logger.info("Reservas encontradas para el piloto con ID: " + idPiloto);
            for (ReservaPlanVuelo reserva : reservasPorPiloto) {
                logger.info("Reserva: " + reserva);
            }
        }
        return reservasPorPiloto;
    }

    @Override
    public List<ReservaPlanVuelo> listarReservasPorDron(String idDron) {
        logger.info("Listando reservas para el dron con ID: " + idDron);
        List<ReservaPlanVuelo> reservasPorDron = new ArrayList<>();
        for (ReservaPlanVuelo reserva : reservas) {
            if (reserva.getIdDron().equals(idDron)) {
                reservasPorDron.add(reserva);
            }
        }
        if (reservasPorDron.isEmpty()) {
            logger.info("No se encontraron reservas para el dron con ID: " + idDron);
        } else {
            logger.info("Reservas encontradas para el dron con ID: " + idDron);
            for (ReservaPlanVuelo reserva : reservasPorDron) {
                logger.info("Reserva: " + reserva);
            }
        }
        return reservasPorDron;
    }
}
