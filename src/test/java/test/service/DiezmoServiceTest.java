package test.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import pe.edu.utp.isi.dwi.iglesia_nazareno.services.DiezmoService;
import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.DiezmoDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Diezmo;

public class DiezmoServiceTest {

    @Test
    public void testRegistrarDiezmoExitoso() {
        // Crear el mock del DAO
        DiezmoDAO mockDAO = mock(DiezmoDAO.class);

        // Crear un diezmo de prueba
        Diezmo diezmo = new Diezmo();
        diezmo.setIdFeligres(1);
        diezmo.setFecha(LocalDate.now());
        diezmo.setMonto(100.0);
        diezmo.setIdRegistradoPor(2);

        // Configurar el comportamiento simulado del mock
        when(mockDAO.insertar(diezmo)).thenReturn(true);

        // Usar el mock en el servicio
        DiezmoService service = new DiezmoService(mockDAO);

        // Ejecutar el método
        boolean resultado = service.registrarDiezmo(diezmo);

        // Verificar
        assertTrue(resultado);
        verify(mockDAO).insertar(diezmo);
    }

    @Test
    public void testCalcularTotalDiezmos() throws Exception {
        // Crear el mock del DAO
        DiezmoDAO mockDAO = mock(DiezmoDAO.class);

        // Simular el resultado del método
        when(mockDAO.obtenerTotalDiezmos()).thenReturn(500.0);

        // Usar el mock en el servicio
        DiezmoService service = new DiezmoService(mockDAO);

        // Ejecutar el método
        double total = service.calcularTotalDiezmos();

        // Verificar
        assertEquals(500.0, total);
        verify(mockDAO).obtenerTotalDiezmos();
    }
}
