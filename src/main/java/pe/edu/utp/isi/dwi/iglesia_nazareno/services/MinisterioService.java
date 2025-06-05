package pe.edu.utp.isi.dwi.iglesia_nazareno.services;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Ministerio;
import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.MinisterioDAO; // Necesitas la interfaz DAO para Ministerio
import pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion.MinisterioDAOImpl; // Necesitas la implementación del DAO para Ministerio

import java.util.List;

/**
 * Servicio para gestionar las operaciones relacionadas con los Ministerios.
 * Actúa como una capa intermedia entre el Servlet y el DAO.
 */
public class MinisterioService {

    private MinisterioDAO ministerioDAO;

    // Constructor que inicializa el DAO
    public MinisterioService() {
        // Aquí instanciamos la implementación concreta de nuestro DAO
        this.ministerioDAO = new MinisterioDAOImpl();
    }

    /**
     * Obtiene y devuelve una lista de todos los ministerios disponibles.
     * @return Una lista de objetos Ministerio.
     * @throws Exception Si ocurre un error al listar los ministerios (por ejemplo, un error de base de datos).
     */
    public List<Ministerio> listarTodosMinisterios() throws Exception {
        // Simplemente delegamos la llamada al método listarTodos del DAO.
        // Aquí podrías añadir lógica de negocio adicional si fuera necesario antes de devolver la lista.
        return ministerioDAO.listarTodos();
    }

    // Puedes añadir otros métodos aquí si necesitas funcionalidades adicionales
    // para los ministerios, como insertar, actualizar, eliminar, o buscar por ID.

    // Ejemplo de un posible método adicional (que requeriría métodos en MinisterioDAO)
    /*
    public Ministerio obtenerMinisterioPorId(int id) throws Exception {
        return ministerioDAO.obtenerPorId(id);
    }
    */
}