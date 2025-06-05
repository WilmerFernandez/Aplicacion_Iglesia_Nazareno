package pe.edu.utp.isi.dwi.iglesia_nazareno.services;

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

    public ReporteFinanciero generarReporteFinancieroConsolidado() throws Exception {
        ReporteFinanciero reporte = new ReporteFinanciero();

        double totalDiezmos = diezmoDAO.obtenerTotalDiezmos();
        double totalOfrendasIglesia = ofrendaDAO.obtenerTotalOfrendasPorMinisterio(1); // ID 1 para "Iglesia"
        double totalSalidas = salidaDAO.obtenerTotalSalidas();

        reporte.setTotalDiezmos(totalDiezmos);
        reporte.setTotalOfrendasIglesia(totalOfrendasIglesia);
        reporte.setTotalSalidas(totalSalidas);
        reporte.setSaldoCaja(totalDiezmos + totalOfrendasIglesia - totalSalidas);

        return reporte;
    }
}