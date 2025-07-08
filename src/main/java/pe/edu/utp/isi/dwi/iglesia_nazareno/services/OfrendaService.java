package pe.edu.utp.isi.dwi.iglesia_nazareno.services;

import java.sql.SQLException;
import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.OfrendaDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion.OfrendaDAOImpl;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Ofrenda;

import java.util.List;

public class OfrendaService {

    private OfrendaDAO ofrendaDAO;

    public OfrendaService() {
        this.ofrendaDAO = new OfrendaDAOImpl();
    }
    
     // Constructor para pruebas
    public OfrendaService(OfrendaDAO ofrendaDAO) {
        this.ofrendaDAO = ofrendaDAO;
    }

    public boolean registrarOfrenda(Ofrenda ofrenda) {
        return ofrendaDAO.insertar(ofrenda);
    }

    public List<Ofrenda> listarTodos() {
        return ofrendaDAO.listarTodos();
    }
    
    public double calcularTotalOfrendasPorMinisterio(int idMinisterio) {
        try {
            return ofrendaDAO.obtenerTotalOfrendasPorMinisterio(idMinisterio);
        } catch (SQLException e) {
            System.err.println("Error en servicio al calcular total de ofrendas por ministerio: " + e.getMessage());
            return 0.0;
        }
    }
}
