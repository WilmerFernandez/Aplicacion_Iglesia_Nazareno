package pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion;

import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.AsistenciaDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Asistencia;
import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.BDConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaDAOImpl implements AsistenciaDAO {

    private static final Logger logger = LoggerFactory.getLogger(AsistenciaDAOImpl.class);

    @Override
    public boolean registrarAsistencia(Asistencia asistencia) throws SQLException {
        String sql = "INSERT INTO Asistencia (Fecha, ID_Ministerio, Cantidad_Adultos, Cantidad_Jovenes, Cantidad_Adolescentes, Cantidad_Ninos, Registrado_Por) VALUES (?, ?, ?, ?, ?, ?, ?)";
        boolean registrado = false;

        try (Connection conn = BDConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, asistencia.getFecha());
            stmt.setInt(2, asistencia.getIdMinisterio());
            stmt.setInt(3, asistencia.getCantidadAdultos());
            stmt.setInt(4, asistencia.getCantidadJovenes());
            stmt.setInt(5, asistencia.getCantidadAdolescentes());
            stmt.setInt(6, asistencia.getCantidadNinos());
            stmt.setInt(7, asistencia.getRegistradoPor());

            int filasAfectadas = stmt.executeUpdate();
            registrado = filasAfectadas > 0;
            logger.info("Asistencia registrada exitosamente: {}", asistencia);

        } catch (SQLException e) {
            logger.error("Error al registrar asistencia: {}", ExceptionUtils.getStackTrace(e));
            throw e;
        }
        return registrado;
    }

    @Override
    public List<Asistencia> listarAsistencias() throws SQLException {
        List<Asistencia> asistencias = new ArrayList<>();

        String sql = "SELECT ID_Asistencia, Fecha, ID_Ministerio, Cantidad_Adultos, Cantidad_Jovenes, Cantidad_Adolescentes, Cantidad_Ninos, Registrado_Por FROM Asistencia ORDER BY Fecha DESC";

        logger.info("Iniciando la consulta para listar asistencias.");

        try (Connection conn = BDConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Asistencia asistencia = new Asistencia();
                asistencia.setIdAsistencia(rs.getInt("ID_Asistencia"));
                asistencia.setFecha(rs.getTimestamp("Fecha"));
                asistencia.setIdMinisterio(rs.getInt("ID_Ministerio"));
                asistencia.setCantidadAdultos(rs.getInt("Cantidad_Adultos"));
                asistencia.setCantidadJovenes(rs.getInt("Cantidad_Jovenes"));
                asistencia.setCantidadAdolescentes(rs.getInt("Cantidad_Adolescentes"));
                asistencia.setCantidadNinos(rs.getInt("Cantidad_Ninos"));
                asistencia.setRegistradoPor(rs.getInt("Registrado_Por"));
                asistencias.add(asistencia);
            }

            logger.info("Se han listado {} asistencias.", asistencias.size());
        } catch (SQLException e) {
            logger.error("Error al listar asistencias: {}", ExceptionUtils.getStackTrace(e));
            throw e;
        }

        return asistencias;
    }

    public List<Asistencia> listarAsistenciasPorMinisterio(int idMinisterio) throws SQLException {
        List<Asistencia> asistencias = new ArrayList<>();
        String sql = "SELECT ID_Asistencia, Fecha, ID_Ministerio, Cantidad_Adultos, Cantidad_Jovenes, Cantidad_Adolescentes, Cantidad_Ninos, Registrado_Por "
                + "FROM Asistencia WHERE ID_Ministerio = ? ORDER BY Fecha DESC";

        try (Connection conn = BDConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMinisterio);  // Establecer el parámetro de ID_Ministerio
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Asistencia asistencia = new Asistencia();
                    asistencia.setIdAsistencia(rs.getInt("ID_Asistencia"));
                    asistencia.setFecha(rs.getTimestamp("Fecha"));
                    asistencia.setIdMinisterio(rs.getInt("ID_Ministerio"));
                    asistencia.setCantidadAdultos(rs.getInt("Cantidad_Adultos"));
                    asistencia.setCantidadJovenes(rs.getInt("Cantidad_Jovenes"));
                    asistencia.setCantidadAdolescentes(rs.getInt("Cantidad_Adolescentes"));
                    asistencia.setCantidadNinos(rs.getInt("Cantidad_Ninos"));
                    asistencia.setRegistradoPor(rs.getInt("Registrado_Por"));
                    asistencias.add(asistencia);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al listar asistencias por ministerio: {}", ExceptionUtils.getStackTrace(e));
            throw e;
        }
        return asistencias;
    }

    public List<Asistencia> listarAsistenciasPorFecha(String fechaInicio, String fechaFin, int idMinisterio) throws SQLException {
        List<Asistencia> asistenciasPorFecha = new ArrayList<>();
        String sql = "SELECT ID_Asistencia, Fecha, ID_Ministerio, Cantidad_Adultos, Cantidad_Jovenes, Cantidad_Adolescentes, Cantidad_Ninos, Registrado_Por "
                + "FROM Asistencia WHERE Fecha BETWEEN ? AND ? AND ID_Ministerio = ? ORDER BY Fecha DESC";

        try (Connection conn = BDConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fechaInicio); // Establecer el parámetro de fecha de inicio
            stmt.setString(2, fechaFin);    // Establecer el parámetro de fecha de fin
            stmt.setInt(3, idMinisterio);   // Establecer el parámetro de ID_Ministerio

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Asistencia asistencia = new Asistencia();
                    asistencia.setIdAsistencia(rs.getInt("ID_Asistencia"));
                    asistencia.setFecha(rs.getTimestamp("Fecha"));
                    asistencia.setIdMinisterio(rs.getInt("ID_Ministerio"));
                    asistencia.setCantidadAdultos(rs.getInt("Cantidad_Adultos"));
                    asistencia.setCantidadJovenes(rs.getInt("Cantidad_Jovenes"));
                    asistencia.setCantidadAdolescentes(rs.getInt("Cantidad_Adolescentes"));
                    asistencia.setCantidadNinos(rs.getInt("Cantidad_Ninos"));
                    asistencia.setRegistradoPor(rs.getInt("Registrado_Por"));
                    asistenciasPorFecha.add(asistencia);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al listar asistencias por fecha y ministerio: {}", ExceptionUtils.getStackTrace(e));
            throw e;
        }

        return asistenciasPorFecha;
    }

}
