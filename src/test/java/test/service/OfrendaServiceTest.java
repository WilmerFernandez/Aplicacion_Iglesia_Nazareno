package test.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.OfrendaService;
import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.OfrendaDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Ofrenda;

public class OfrendaServiceTest {

    @Test
    public void testRegistrarOfrendaExitosamente() {
        // Crear un mock del DAO
        OfrendaDAO mockDAO = mock(OfrendaDAO.class);

        // Crear una ofrenda de prueba
        Ofrenda ofrenda = new Ofrenda();
        ofrenda.setFecha(LocalDate.now());
        ofrenda.setMonto(200.0);
        ofrenda.setIdMinisterio(1);
        ofrenda.setIdUsuarioRegistrador(2);

        // Simular el comportamiento del DAO
        when(mockDAO.insertar(ofrenda)).thenReturn(true);

        // Inyectar el mock en el servicio
        OfrendaService service = new OfrendaService(mockDAO);

        // Ejecutar el método y verificar
        boolean resultado = service.registrarOfrenda(ofrenda);
        assertTrue(resultado);
        verify(mockDAO).insertar(ofrenda);
    }

    @Test
    public void testCalcularTotalOfrendasPorMinisterio() {
        OfrendaDAO mockDAO = mock(OfrendaDAO.class);
        int idMinisterio = 1;

        // Simular que hay 450.0 en ofrendas del ministerio 1
        try {
            when(mockDAO.obtenerTotalOfrendasPorMinisterio(idMinisterio)).thenReturn(450.0);
        } catch (Exception e) {
            fail("No debería lanzar excepción");
        }

        OfrendaService service = new OfrendaService(mockDAO);

        double total = service.calcularTotalOfrendasPorMinisterio(idMinisterio);

        assertEquals(450.0, total);
        try {
            verify(mockDAO).obtenerTotalOfrendasPorMinisterio(idMinisterio);
        } catch (Exception e) {
            fail("No debería lanzar excepción");
        }
    }
}
