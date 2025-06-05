package pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion;

import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.OfrendaDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Ofrenda;
import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.BDConnection; // Asegúrate de que esta clase de conexión esté funcionando correctamente

import java.sql.*;
import java.time.LocalDateTime; // Necesario para convertir LocalDate a Timestamp
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC para OfrendaDAO.
 */
public class OfrendaDAOImpl implements OfrendaDAO {

    // Se modificó la consulta para incluir ID_Ministerio y Registrado_Por
    private static final String SQL_INSERT = "INSERT INTO Ofrenda (Fecha, Monto, ID_Ministerio, Registrado_Por) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT ID_Ofrenda, Fecha, Monto, ID_Ministerio, Registrado_Por FROM Ofrenda"; // Especifica las columnas para mayor claridad
    private static final String SQL_SUM_BY_MINISTERIO = "SELECT SUM(Monto) AS Total FROM Ofrenda WHERE ID_Ministerio = ?"; // <-- NUEVA CONSULTA

    @Override
    public boolean insertar(Ofrenda ofrenda) {
        boolean resultado = false;
        try (Connection con = BDConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {

            // 1. Convertir LocalDate a Timestamp para la columna 'Fecha' (TIMESTAMP en DB)
            // Asume que quieres el inicio del día para la fecha de la ofrenda.
            LocalDateTime localDateTime = ofrenda.getFecha().atStartOfDay();
            ps.setTimestamp(1, Timestamp.valueOf(localDateTime));

            // 2. Setear el Monto
            ps.setDouble(2, ofrenda.getMonto());

            // 3. Setear el ID_Ministerio
            ps.setInt(3, ofrenda.getIdMinisterio());

            // 4. Setear el Registrado_Por (ID_Usuario)
            ps.setInt(4, ofrenda.getIdUsuarioRegistrador());

            resultado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error insertando ofrenda: " + e.getMessage());
            // Opcional: lanzar una excepción más específica o registrar en un log
        }
        return resultado;
    }

    @Override
    public List<Ofrenda> listarTodos() {
        List<Ofrenda> lista = new ArrayList<>();
        try (Connection con = BDConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SQL_SELECT_ALL); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ofrenda o = new Ofrenda();
                // 1. Obtener ID_Ofrenda (asegúrate que el nombre de la columna sea exacto)
                o.setId(rs.getInt("ID_Ofrenda"));

                // 2. Obtener Fecha (TIMESTAMP de DB a LocalDate de Java)
                Timestamp dbTimestamp = rs.getTimestamp("Fecha");
                if (dbTimestamp != null) {
                    o.setFecha(dbTimestamp.toLocalDateTime().toLocalDate());
                }

                // 3. Obtener Monto
                o.setMonto(rs.getDouble("Monto"));

                // 4. Obtener ID_Ministerio
                o.setIdMinisterio(rs.getInt("ID_Ministerio"));

                // 5. Obtener Registrado_Por
                o.setIdUsuarioRegistrador(rs.getInt("Registrado_Por"));

                lista.add(o);
            }

        } catch (SQLException e) {
            System.err.println("Error listando ofrendas: " + e.getMessage());
            // Opcional: lanzar una excepción más específica o registrar en un log
        }
        return lista;
    }

    @Override
    public double obtenerTotalOfrendasPorMinisterio(int idMinisterio) throws SQLException { // <-- IMPLEMENTACIÓN DEL NUEVO MÉTODO
        double total = 0.0;
        try (Connection con = BDConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SQL_SUM_BY_MINISTERIO)) {
            ps.setInt(1, idMinisterio);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getDouble("Total");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener total de ofrendas por ministerio: " + e.getMessage());
            throw e;
        }
        return total;
    }
}
