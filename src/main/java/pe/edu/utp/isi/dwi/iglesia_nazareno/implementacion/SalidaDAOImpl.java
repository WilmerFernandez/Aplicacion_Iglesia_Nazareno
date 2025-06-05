package pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion;

import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.SalidaDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Salida;
import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.BDConnection; // Tu clase de conexión a la BD

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp; // Para manejar las columnas TIMESTAMP de la DB
import java.time.LocalDateTime; // Para convertir LocalDate a LocalDateTime y luego a Timestamp
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC para SalidaDAO.
 */
public class SalidaDAOImpl implements SalidaDAO {

    // Asegúrate de que los nombres de las columnas coincidan exactamente con tu script de tabla
    private static final String SQL_INSERT = "INSERT INTO Salida (Fecha, Monto, Descripcion, ID_Ministerio, Registrado_Por) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT ID_Salida, Fecha, Monto, Descripcion, ID_Ministerio, Registrado_Por FROM Salida";
    private static final String SQL_SUM_ALL = "SELECT SUM(Monto) AS Total FROM Salida"; // <-- NUEVA CONSULTA

    @Override
    public boolean insertar(Salida salida) throws SQLException {
        boolean resultado = false;
        try (Connection con = BDConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {

            // Convertir LocalDate a Timestamp para la DB
            LocalDateTime localDateTime = salida.getFecha().atStartOfDay(); // O .atTime(LocalTime.now()) si necesitas la hora actual
            ps.setTimestamp(1, Timestamp.valueOf(localDateTime));
            ps.setDouble(2, salida.getMonto());
            ps.setString(3, salida.getDescripcion());
            ps.setInt(4, salida.getIdMinisterio());
            ps.setInt(5, salida.getRegistradoPor());

            resultado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error insertando salida: " + e.getMessage());
            throw e; // Relanzar la excepción para que sea manejada por el servicio
        }
        return resultado;
    }

    @Override
    public List<Salida> listarTodos() throws SQLException {
        List<Salida> lista = new ArrayList<>();
        try (Connection con = BDConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SQL_SELECT_ALL); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Salida s = new Salida();
                s.setIdSalida(rs.getInt("ID_Salida"));

                // Convertir Timestamp de DB a LocalDate para el modelo
                Timestamp dbTimestamp = rs.getTimestamp("Fecha");
                if (dbTimestamp != null) {
                    s.setFecha(dbTimestamp.toLocalDateTime().toLocalDate());
                }

                s.setMonto(rs.getDouble("Monto"));
                s.setDescripcion(rs.getString("Descripcion"));
                s.setIdMinisterio(rs.getInt("ID_Ministerio"));
                s.setRegistradoPor(rs.getInt("Registrado_Por"));
                lista.add(s);
            }

        } catch (SQLException e) {
            System.err.println("Error listando salidas: " + e.getMessage());
            throw e; // Relanzar la excepción
        }
        return lista;
    }

    @Override
    public double obtenerTotalSalidas() throws SQLException { // <-- IMPLEMENTACIÓN DEL NUEVO MÉTODO
        double total = 0.0;
        try (Connection con = BDConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SQL_SUM_ALL); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getDouble("Total");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener total de salidas: " + e.getMessage());
            throw e;
        }
        return total;
    }
}
