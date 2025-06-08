package pe.edu.utp.isi.dwi.iglesia_nazareno.services;

import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.FeligresDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion.FeligresDAOImpl;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Feligres;

import java.util.List;

public class FeligresService {

    private FeligresDAO feligresDAO;

    public FeligresService() {
        this.feligresDAO = new FeligresDAOImpl();
    }

    public boolean registrarFeligres(Feligres feligres) {
        return feligresDAO.insertar(feligres);
    }

    public List<Feligres> listarTodos() {
        return feligresDAO.listarTodos();
    }
    
    
    public List<Feligres> buscarFeligresesPorNombre(String nombre) {
        return feligresDAO.buscarPorNombre(nombre);
    }
}
