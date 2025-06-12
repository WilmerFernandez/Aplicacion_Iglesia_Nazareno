package pe.edu.utp.isi.dwi.iglesia_nazareno.services;

import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.AsistenciaDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion.AsistenciaDAOImpl;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Asistencia;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AsistenciaService {

    private AsistenciaDAO asistenciaDAO;

    public AsistenciaService() {
        this.asistenciaDAO = new AsistenciaDAOImpl(); // Instancia la implementación de la DAO
    }

    public boolean registrarAsistencia(Asistencia asistencia) {
        try {
            return asistenciaDAO.registrarAsistencia(asistencia);
        } catch (SQLException e) {
            System.err.println("Error en el servicio al registrar asistencia: " + e.getMessage());
            return false;
        }
    }

    public List<Asistencia> listarAsistencias() {
        try {
            return asistenciaDAO.listarAsistencias();
        } catch (SQLException e) {
            System.err.println("Error en el servicio al listar asistencias: " + e.getMessage());
            // Devuelve una lista vacía o null, dependiendo de tu manejo de errores
            return new ArrayList<>();
        }
    }
    
    public List<Asistencia> listarAsistenciasPorMinisterio(int idMinisterio) {
        try {
            return asistenciaDAO.listarAsistenciasPorMinisterio(idMinisterio);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Asistencia> listarAsistenciasPorFecha(String fechaInicio, String fechaFin, int idMinisterio) {
        try {
            List<Asistencia> asistenciasPorFecha = asistenciaDAO.listarAsistenciasPorFecha(fechaInicio, fechaFin, idMinisterio);
            return asistenciasPorFecha;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public double[] calcularPromedios(List<Asistencia> asistencias) {
        int totalAdultos = 0, totalJovenes = 0, totalAdolescentes = 0, totalNinos = 0;
        int totalAsistencias = asistencias.size();

        for (Asistencia asistencia : asistencias) {
            totalAdultos += asistencia.getCantidadAdultos();
            totalJovenes += asistencia.getCantidadJovenes();
            totalAdolescentes += asistencia.getCantidadAdolescentes();
            totalNinos += asistencia.getCantidadNinos();
        }

        double promedioAdultos = totalAsistencias > 0 ? (double) totalAdultos / totalAsistencias : 0;
        double promedioJovenes = totalAsistencias > 0 ? (double) totalJovenes / totalAsistencias : 0;
        double promedioAdolescentes = totalAsistencias > 0 ? (double) totalAdolescentes / totalAsistencias : 0;
        double promedioNinos = totalAsistencias > 0 ? (double) totalNinos / totalAsistencias : 0;
        
        // Promedio total de todos los grupos
        double promedioTotal = (promedioAdultos + promedioJovenes + promedioAdolescentes + promedioNinos) / 4;

        return new double[] {promedioAdultos, promedioJovenes, promedioAdolescentes, promedioNinos, promedioTotal};
    }

    

}