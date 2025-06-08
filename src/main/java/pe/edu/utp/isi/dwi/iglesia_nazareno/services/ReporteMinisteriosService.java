package pe.edu.utp.isi.dwi.iglesia_nazareno.services;

import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.OfrendaDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.SalidaDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion.OfrendaDAOImpl;
import pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion.SalidaDAOImpl;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.ReporteMinisterios;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Ofrenda;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Salida;

import java.sql.SQLException;
import java.util.List;

public class ReporteMinisteriosService {

    private OfrendaDAO ofrendaDAO;
    private SalidaDAO salidaDAO;
    

    public ReporteMinisteriosService() {
        this.ofrendaDAO = new OfrendaDAOImpl();
        this.salidaDAO = new SalidaDAOImpl();
        
    }

    // Método para obtener el nombre del ministerio (podrías tener una tabla de ministerios y un DAO para ello)
    
    private String obtenerNombreMinisterio(int idMinisterio) {
        switch (idMinisterio) {
            case 2: return "JNI (Jóvenes Nazarenos Internacional)";
            case 3: return "MNI (Ministerios Nazarenos Internacional)";
            case 4: return "DNI (Discipulado Nazareno Internacional)";
            default: return "Ministerio Desconocido";
        }
    }

    public ReporteMinisterios generarReportePorMinisterio(int idMinisterio) throws SQLException {
        ReporteMinisterios reporte = new ReporteMinisterios();
        reporte.setIdMinisterio(idMinisterio);
        reporte.setNombreMinisterio(obtenerNombreMinisterio(idMinisterio));

        // Obtener entradas (Ofrendas)
        List<Ofrenda> entradas = ofrendaDAO.listarOfrendasPorMinisterio(idMinisterio);
        reporte.setEntradas(entradas);

        // Calcular total de entradas
        double totalEntradas = ofrendaDAO.obtenerTotalOfrendasPorMinisterio(idMinisterio);
        reporte.setTotalEntradas(totalEntradas);

        // Obtener salidas (Salidas)
        List<Salida> salidas = salidaDAO.listarSalidasPorMinisterio(idMinisterio);
        reporte.setSalidas(salidas);

        // Calcular total de salidas
        double totalSalidas = salidaDAO.obtenerTotalSalidasPorMinisterio(idMinisterio);
        reporte.setTotalSalidas(totalSalidas);

        // Calcular total en caja
        reporte.setTotalEnCaja(totalEntradas - totalSalidas);

        return reporte;
    }
}