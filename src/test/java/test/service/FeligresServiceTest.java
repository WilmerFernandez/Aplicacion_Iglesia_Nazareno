package test.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.FeligresDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Feligres;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.FeligresService;

public class FeligresServiceTest {

    @Test
    public void testRegistrarFeligresExitoso() {
        // Arrange
        FeligresDAO mockDAO = mock(FeligresDAO.class);

        Feligres feligres = new Feligres();
        feligres.setNombre("Pedro");
        feligres.setApellido("Gómez");
        feligres.setFechaNacimiento(LocalDate.of(1990, 5, 15));
        feligres.setEstado("Activo");
        feligres.setTelefono("987654321");
        feligres.setDireccion("Av. Lima 123");
        feligres.setFechaRegistro(LocalDateTime.now());

        when(mockDAO.insertar(feligres)).thenReturn(true);

        FeligresService service = new FeligresService(mockDAO);

        // Act
        boolean resultado = service.registrarFeligres(feligres);

        // Assert
        assertTrue(resultado);
        verify(mockDAO).insertar(feligres);
    }

    @Test
    public void testBuscarFeligresesPorNombre() {
        // Arrange
        FeligresDAO mockDAO = mock(FeligresDAO.class);

        Feligres f1 = new Feligres();
        f1.setNombre("María");

        Feligres f2 = new Feligres();
        f2.setNombre("María Teresa");

        when(mockDAO.buscarPorNombre("María")).thenReturn(Arrays.asList(f1, f2));

        FeligresService service = new FeligresService(mockDAO);

        // Act
        List<Feligres> resultados = service.buscarFeligresesPorNombre("María");

        // Assert
        assertEquals(2, resultados.size());
        assertEquals("María", resultados.get(0).getNombre());
        assertEquals("María Teresa", resultados.get(1).getNombre());
        verify(mockDAO).buscarPorNombre("María");
    }
}
