package pe.edu.utp.isi.dwi.iglesia_nazareno.services;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Ministerio;
import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.MinisterioDAO; 
import pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion.MinisterioDAOImpl; 

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
        return ministerioDAO.listarTodos();
    }

    
}