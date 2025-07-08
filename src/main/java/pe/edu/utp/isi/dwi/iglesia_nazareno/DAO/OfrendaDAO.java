package pe.edu.utp.isi.dwi.iglesia_nazareno.DAO;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Ofrenda;
import java.util.List;
import java.sql.SQLException;

/**
 * Interface para operaciones CRUD sobre Ofrenda.
 */
public interface OfrendaDAO {

    /**
     * Inserta una nueva ofrenda.
     *
     * @param ofrenda Objeto Ofrenda a insertar.
     * @return true si inserta correctamente, false en caso contrario.
     */
    boolean insertar(Ofrenda ofrenda);

    /**
     * Lista todas las ofrendas registradas.
     *
     * @return Lista de Ofrenda.
     */
    List<Ofrenda> listarTodos();

    List<Ofrenda> listarOfrendasPorMinisterio(int idMinisterio) throws SQLException;

    double obtenerTotalOfrendasPorMinisterio(int idMinisterio) throws SQLException;

    
    
    List<Ofrenda> listarPorFechas(String inicio, String fin, int idMinisterio) throws SQLException;

    double obtenerTotalPorFechas(String inicio, String fin, int idMinisterio) throws SQLException;


}
