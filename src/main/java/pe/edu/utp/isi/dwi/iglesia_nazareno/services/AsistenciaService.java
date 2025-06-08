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
}