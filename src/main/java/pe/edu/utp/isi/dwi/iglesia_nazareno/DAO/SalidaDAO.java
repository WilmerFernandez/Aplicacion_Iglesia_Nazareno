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
    
    /**
     * Lista todas las salidas registradas para un ministerio específico.
     * @param idMinisterio El ID del ministerio.
     * @return Lista de Salidas del ministerio.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    List<Salida> listarSalidasPorMinisterio(int idMinisterio) throws SQLException; // <-- NUEVO MÉTODO
    
    
    double obtenerTotalSalidas() throws SQLException; 
    
// Nuevo método para sumar ofrendas por ministerio
    double obtenerTotalSalidasPorMinisterio(int idMinisterio) throws SQLException;
}