package pe.edu.utp.isi.dwi.iglesia_nazareno.services;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Salida;
import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.SalidaDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion.SalidaDAOImpl; // Tu implementación del DAO

import java.util.List;
import java.sql.SQLException; // Para capturar SQLException del DAO

/**
 * Servicio para gestionar las operaciones de negocio relacionadas con las Salidas.
 */
public class SalidaService {

    private SalidaDAO salidaDAO;

    public SalidaService() {
        this.salidaDAO = new SalidaDAOImpl();
    }

    /**
     * Registra una nueva salida en la base de datos.
     * @param salida El objeto Salida a registrar.
     * @return true si la salida se registró correctamente, false en caso contrario.
     * @throws Exception Si ocurre un error durante el registro (por ejemplo, error de BD).
     */
    public boolean registrarSalida(Salida salida) throws Exception {
        try {
            // Aquí puedes añadir lógica de negocio adicional antes de insertar,
            // por ejemplo, validaciones más complejas.
            return salidaDAO.insertar(salida);
        } catch (SQLException e) {
            System.err.println("Error en el servicio al registrar salida: " + e.getMessage());
            // Podrías lanzar una excepción personalizada aquí, ej: new ServiceException("...", e);
            throw new Exception("Error al registrar la salida en la base de datos.", e);
        }
    }

    /**
     * Lista todas las salidas registradas.
     * @return Una lista de objetos Salida.
     * @throws Exception Si ocurre un error al listar las salidas.
     */
    public List<Salida> listarTodasSalidas() throws Exception {
        try {
            return salidaDAO.listarTodos();
        } catch (SQLException e) {
            System.err.println("Error en el servicio al listar salidas: " + e.getMessage());
            throw new Exception("Error al obtener la lista de salidas de la base de datos.", e);
        }
    }
}