package edu.upc.dsa;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by juan on 16/11/16.
 */
public class DronesServiceTest {
    DronesManager dm;

    @Before
    public void setUp() throws Exception {
        this.dm = DronesManagerImpl.getInstance();
    }
    @Test
    public void testaddDron() throws Exception {
        dm.addDron("1", "dron1", "fabricante1", "modelo1");
        assertNotNull(dm.obtenerDronPorId("1"));
    }

    @Test
    public void testListarDronesPorHorasDeVueloDescendente() {
        dm.addDron("1", "Dron1", "Fabricante1", "Modelo1");
        dm.addDron("2", "Dron2", "Fabricante2", "Modelo2");
        dm.addDron("3", "Dron3", "Fabricante3", "Modelo3");

        //agreguem hores de vol per observar si s'ordena
        dm.obtenerDronPorId("1").setHorasVuelo(10);
        dm.obtenerDronPorId("2").setHorasVuelo(5);
        dm.obtenerDronPorId("3").setHorasVuelo(20);
        assertEquals(3, dm.listarDronesPorHorasDeVueloDescendente().size());
        assertEquals("3", dm.listarDronesPorHorasDeVueloDescendente().get(0).getId());
        assertEquals("1", dm.listarDronesPorHorasDeVueloDescendente().get(1).getId());
        assertEquals("2", dm.listarDronesPorHorasDeVueloDescendente().get(2).getId());
    }

}