package pe.edu.utp.isi.dwi.iglesia_nazareno.DAO;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Feligres;
import java.util.List;

public interface FeligresDAO {
    boolean insertar(Feligres feligres);
    List<Feligres> listarTodos();

    List<Feligres> buscarPorNombre(String nombre); 
}
