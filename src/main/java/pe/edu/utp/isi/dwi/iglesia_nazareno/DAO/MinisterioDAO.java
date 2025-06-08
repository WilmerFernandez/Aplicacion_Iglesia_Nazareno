// pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.MinisterioDAO
package pe.edu.utp.isi.dwi.iglesia_nazareno.DAO;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Ministerio;
import java.util.List;
import java.sql.SQLException;


public interface MinisterioDAO {
    List<Ministerio> listarTodos() throws SQLException; 
   
}