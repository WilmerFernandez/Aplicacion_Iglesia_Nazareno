package pe.edu.utp.isi.dwi.iglesia_nazareno.services;

import java.sql.SQLException;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Diezmo;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Ofrenda;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Salida;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.ReporteFinanciero; // Nuevo modelo
import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.DiezmoDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.OfrendaDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.SalidaDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion.DiezmoDAOImpl;
import pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion.OfrendaDAOImpl;
import pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion.SalidaDAOImpl;

import java.util.List;

public class ReporteService {

    private DiezmoDAO diezmoDAO;
    private OfrendaDAO ofrendaDAO;
    private SalidaDAO salidaDAO;

    public ReporteService() {
        this.diezmoDAO = new DiezmoDAOImpl();
        this.ofrendaDAO = new OfrendaDAOImpl();
        this.salidaDAO = new SalidaDAOImpl();
    }

    public List<Diezmo> listarTodosDiezmos() throws Exception {
        return diezmoDAO.listarTodos();
    }

    public List<Ofrenda> listarTodasOfrendas() throws Exception {
        return ofrendaDAO.listarTodos();
    }

    public List<Salida> listarTodasSalidas() throws Exception {
        return salidaDAO.listarTodos();
    }
    
    
    public List<Salida> listarSalidasPorMinisterio() throws SQLException {
        int idMinisterio = 1;  // ID del ministerio hardcodeado
        try {
            // Llamamos al método del DAO para obtener las salidas para el ministerio con id 1
            return salidaDAO.listarSalidasPorMinisterio(idMinisterio);
        } catch (SQLException e) {
            // Manejo de excepciones
            System.err.println("Error en el servicio al listar salidas para el ministerio con ID " + idMinisterio + ": " + e.getMessage());
            throw e;  // Propagamos la excepción
        }
    }
    
    public List<Ofrenda> listarOfrendasPorMinisterio() throws SQLException {
        int idMinisterio = 1;  // ID del ministerio hardcodeado
        try {
            // Llamamos al método del DAO para obtener las ofrendas para el ministerio con id 1
            return ofrendaDAO.listarOfrendasPorMinisterio(idMinisterio);
        } catch (SQLException e) {
            // Manejo de excepciones
            System.err.println("Error en el servicio al listar ofrendas para el ministerio con ID " + idMinisterio + ": " + e.getMessage());
            throw e;  // Propagamos la excepción
        }
    }
    
    
    public ReporteFinanciero generarReporteFinancieroConsolidado() throws Exception {
        ReporteFinanciero reporte = new ReporteFinanciero();

        double totalDiezmos = diezmoDAO.obtenerTotalDiezmos();
        double totalOfrendasIglesia = ofrendaDAO.obtenerTotalOfrendasPorMinisterio(1); // ID 1 para "Iglesia"
        double totalSalidas = salidaDAO.obtenerTotalSalidasPorMinisterio(1); // ID 1 para "Iglesia"

        reporte.setTotalDiezmos(totalDiezmos);
        reporte.setTotalOfrendasIglesia(totalOfrendasIglesia);
        reporte.setTotalSalidas(totalSalidas);
        reporte.setSaldoCaja(totalDiezmos + totalOfrendasIglesia - totalSalidas);

        return reporte;
    }
}