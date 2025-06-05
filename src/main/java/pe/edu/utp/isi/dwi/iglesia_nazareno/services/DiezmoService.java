package pe.edu.utp.isi.dwi.iglesia_nazareno.services;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Diezmo;
import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.DiezmoDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion.DiezmoDAOImpl; // Importa la implementación

import java.util.List;
import java.sql.SQLException;

/**
 * Servicio para gestionar Diezmos.
 */
public class DiezmoService {

    // Variable de instancia para el DAO
    private DiezmoDAO diezmoDAO;

    public DiezmoService() {
        // Inicialización de la instancia DAO
        this.diezmoDAO = new DiezmoDAOImpl();
    }

    /**
     * Registra un nuevo Diezmo.
     *
     * @param diezmo objeto Diezmo con datos a guardar
     * @return true si se guardó correctamente, false en otro caso
     */
    public boolean registrarDiezmo(Diezmo diezmo) {
        // Llamada real al DAO
        return diezmoDAO.insertar(diezmo);
    }
    
    /**
     * Obtiene todos los Diezmos.
     *
     * @return lista de Diezmo
     */
    public List<Diezmo> listarTodos() {
        return diezmoDAO.listarTodos();
    }
    
    public double calcularTotalDiezmos() throws Exception {
    try {
        return diezmoDAO.obtenerTotalDiezmos(); // Llama a un nuevo método en DiezmoDAO
    } catch (SQLException e) {
        throw new Exception("Error al calcular el total de los diezmos.", e);
    }
}
}
