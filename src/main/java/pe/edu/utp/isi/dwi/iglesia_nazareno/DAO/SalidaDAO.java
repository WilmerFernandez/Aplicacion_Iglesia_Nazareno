package pe.edu.utp.isi.dwi.iglesia_nazareno.DAO;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Salida;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz para las operaciones de acceso a datos de la entidad Salida.
 */
public interface SalidaDAO {
    boolean insertar(Salida salida) throws SQLException;
    List<Salida> listarTodos() throws SQLException;
    // Puedes añadir más métodos como obtenerPorId, actualizar, eliminar si los necesitas
    double obtenerTotalSalidas() throws SQLException; // <-- NUEVO MÉTODO
}