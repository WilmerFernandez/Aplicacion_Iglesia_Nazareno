package pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion;

import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.DiezmoDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Diezmo;
import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.BDConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC de DiezmoDAO.
 */
public class DiezmoDAOImpl implements DiezmoDAO {

    private static final String SQL_INSERT = "INSERT INTO diezmo (ID_Feligres, Fecha, Monto, Registrado_Por) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL
            = "SELECT d.ID_Diezmo, d.ID_Feligres, f.Nombre, f.Apellido, d.Fecha, d.Monto, d.Registrado_Por "
            + "FROM Diezmo d "
            + "JOIN Feligres f ON d.ID_Feligres = f.ID_Feligres";

    @Override
    public boolean insertar(Diezmo diezmo) {
        boolean resultado = false;
        try (Connection con = BDConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {

            System.out.println("Intentando insertar Diezmo:");
            System.out.println("  ID Feligres: " + diezmo.getIdFeligres());
            System.out.println("  Fecha: " + diezmo.getFecha());
            System.out.println("  Monto: " + diezmo.getMonto());
            System.out.println("  ID Registrado Por: " + diezmo.getIdRegistradoPor());
            // Se elimina la impresión del valor de ofrenda

            ps.setInt(1, diezmo.getIdFeligres());
            ps.setDate(2, Date.valueOf(diezmo.getFecha()));
            ps.setDouble(3, diezmo.getMonto());
            ps.setInt(4, diezmo.getIdRegistradoPor());

            // Se elimina el código para el manejo de la ofrenda
            int filasAfectadas = ps.executeUpdate();
            resultado = filasAfectadas > 0;

            if (resultado) {
                System.out.println("Diezmo insertado correctamente. Filas afectadas: " + filasAfectadas);
            } else {
                System.out.println("No se insertó el diezmo. Filas afectadas: " + filasAfectadas + ". Puede que haya una restricción o error.");
            }

        } catch (SQLException e) {
            System.err.println("Error SQL insertando diezmo: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado al insertar diezmo: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public List<Diezmo> listarTodos() {
        List<Diezmo> lista = new ArrayList<>();
        try (Connection con = BDConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SQL_SELECT_ALL); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Diezmo d = new Diezmo();
                d.setId(rs.getInt("ID_Diezmo"));
                d.setIdFeligres(rs.getInt("ID_Feligres"));
                d.setNombreFeligres(rs.getString("Nombre") + " " + rs.getString("Apellido")); // Asegúrate de que tu modelo `Diezmo` tenga este campo o método
                d.setFecha(rs.getDate("Fecha").toLocalDate());
                d.setMonto(rs.getDouble("Monto"));
                d.setIdRegistradoPor(rs.getInt("Registrado_Por"));
                lista.add(d);
            }
        } catch (SQLException e) {
            System.err.println("Error SQL listando diezmos: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // En DiezmoDAOImpl.java
    @Override
    public double obtenerTotalDiezmos() throws SQLException {
        double total = 0.0;
        String sql = "SELECT SUM(Monto) AS Total FROM Diezmo";
        try (Connection con = BDConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getDouble("Total");
            }
        }
        return total;
    }
}
