package pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion;

import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.AsistenciaDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Asistencia;
import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.BDConnection; // ¡Asegúrate de que esta importación sea correcta!

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaDAOImpl implements AsistenciaDAO {

    @Override
    public boolean registrarAsistencia(Asistencia asistencia) throws SQLException {
        // SQL corregido para usar los nombres de columnas de tu tabla
        String sql = "INSERT INTO Asistencia (Fecha, ID_Ministerio, Cantidad_Adultos, Cantidad_Jovenes, Cantidad_Adolescentes, Cantidad_Ninos, Registrado_Por) VALUES (?, ?, ?, ?, ?, ?, ?)";
        boolean registrado = false;

        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, asistencia.getFecha());
            stmt.setInt(2, asistencia.getIdMinisterio());
            stmt.setInt(3, asistencia.getCantidadAdultos());
            stmt.setInt(4, asistencia.getCantidadJovenes());
            stmt.setInt(5, asistencia.getCantidadAdolescentes());
            stmt.setInt(6, asistencia.getCantidadNinos());
            stmt.setInt(7, asistencia.getRegistradoPor());

            int filasAfectadas = stmt.executeUpdate();
            registrado = filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar asistencia: " + e.getMessage());
            throw e; // Relanza la excepción para que sea manejada en capas superiores
        }
        return registrado;
    }

    @Override
    public List<Asistencia> listarAsistencias() throws SQLException {
        List<Asistencia> asistencias = new ArrayList<>();
        // SQL corregido para usar los nombres de columnas de tu tabla
        String sql = "SELECT ID_Asistencia, Fecha, ID_Ministerio, Cantidad_Adultos, Cantidad_Jovenes, Cantidad_Adolescentes, Cantidad_Ninos, Registrado_Por FROM Asistencia ORDER BY Fecha DESC";

        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Asistencia asistencia = new Asistencia();
                // Getters por nombre de columna exacto
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
        } catch (SQLException e) {
            System.err.println("Error al listar asistencias: " + e.getMessage());
            throw e;
        }
        return asistencias;
    }
}