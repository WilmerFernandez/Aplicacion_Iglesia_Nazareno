package pe.edu.utp.isi.dwi.iglesia_nazareno.services;

import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.OfrendaDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion.OfrendaDAOImpl;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Ofrenda;

import java.util.List;

public class OfrendaService {

    private OfrendaDAO ofrendaDAO;

    public OfrendaService() {
        this.ofrendaDAO = new OfrendaDAOImpl();
    }

    public boolean registrarOfrenda(Ofrenda ofrenda) {
        return ofrendaDAO.insertar(ofrenda);
    }

    public List<Ofrenda> listarTodos() {
        return ofrendaDAO.listarTodos();
    }
}
