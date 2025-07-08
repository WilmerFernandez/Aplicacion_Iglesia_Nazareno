package pe.edu.utp.isi.dwi.iglesia_nazareno.DAO;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Diezmo;
import java.util.List;
import java.sql.SQLException;

/**
 * Interfaz para operaciones CRUD sobre Diezmo.
 */
public interface DiezmoDAO {

    /**
     * Inserta un nuevo diezmo en la base de datos.
     *
     * @param diezmo objeto Diezmo a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    boolean insertar(Diezmo diezmo);

    /**
     * Obtiene la lista de diezmos registrados.
     *
     * @return lista de Diezmo
     */
    List<Diezmo> listarTodos();

    double obtenerTotalDiezmos() throws SQLException;
    
    
    
    // Agregado para búsqueda por fechas
    List<Diezmo> listarPorFechas(String inicio, String fin) throws SQLException;
    double obtenerTotalPorFechas(String inicio, String fin) throws SQLException;
}
