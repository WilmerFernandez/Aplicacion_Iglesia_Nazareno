package pe.edu.utp.isi.dwi.iglesia_nazareno.DAO;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Asistencia;
import java.sql.SQLException;
import java.util.List;

public interface AsistenciaDAO {
    /**
     * Registra una nueva asistencia en la base de datos.
     * @param asistencia Objeto Asistencia a registrar.
     * @return true si el registro fue exitoso, false en caso contrario.
     * @throws SQLException si ocurre un error de base de datos.
     */
    boolean registrarAsistencia(Asistencia asistencia) throws SQLException;

    /**
     * Obtiene una lista de todas las asistencias registradas.
     * @return Una lista de objetos Asistencia.
     * @throws SQLException si ocurre un error de base de datos.
     */
    List<Asistencia> listarAsistencias() throws SQLException;
    
    /**
     * Obtiene una lista de asistencias filtradas por ID_Ministerio.
     * @param idMinisterio El ID del ministerio.
     * @return Lista de asistencias correspondientes al ministerio.
     * @throws SQLException si ocurre un error de base de datos.
     */
    List<Asistencia> listarAsistenciasPorMinisterio(int idMinisterio) throws SQLException;

    /**
     * Obtiene una lista de asistencias dentro de un rango de fechas.
     * @param fechaInicio Fecha de inicio del rango.
     * @param fechaFin Fecha de fin del rango.
     * @return Lista de asistencias dentro del rango de fechas.
     * @throws SQLException si ocurre un error de base de datos.
     */
    List<Asistencia> listarAsistenciasPorFecha(String fechaInicio, String fechaFin, int idMinisterio) throws SQLException;

}