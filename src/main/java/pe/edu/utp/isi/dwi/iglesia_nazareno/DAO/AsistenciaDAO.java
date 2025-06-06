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

    // Puedes añadir más métodos como obtener por ID, actualizar, eliminar, etc., si los necesitas.
}