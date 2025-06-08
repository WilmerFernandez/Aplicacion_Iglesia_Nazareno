package pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion;

import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.SalidaDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Salida;
import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.BDConnection; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp; 
import java.time.LocalDateTime; 
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC para SalidaDAO.
 */
public class SalidaDAOImpl implements SalidaDAO {

    
    private static final String SQL_INSERT = "INSERT INTO Salida (Fecha, Monto, Descripcion, ID_Ministerio, Registrado_Por) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT ID_Salida, Fecha, Monto, Descripcion, ID_Ministerio, Registrado_Por FROM Salida";
    private static final String SQL_SELECT_BY_MINISTERIO = "SELECT ID_Salida, Fecha, Monto, Descripcion, ID_Ministerio, Registrado_Por FROM Salida WHERE ID_Ministerio = ?";
    private static final String SQL_SUM_ALL = "SELECT SUM(Monto) AS Total FROM Salida"; 
    private static final String SQL_SUM_BY_MINISTERIO = "SELECT SUM(monto) AS TotalSalidas FROM Salida WHERE ID_Ministerio = ?";

    @Override
    public boolean insertar(Salida salida) throws SQLException {
        boolean resultado = false;
        try (Connection con = BDConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {

           
            LocalDateTime localDateTime = salida.getFecha().atStartOfDay(); 
            ps.setTimestamp(1, Timestamp.valueOf(localDateTime));
            ps.setDouble(2, salida.getMonto());
            ps.setString(3, salida.getDescripcion());
            ps.setInt(4, salida.getIdMinisterio());
            ps.setInt(5, salida.getRegistradoPor());

            resultado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error insertando salida: " + e.getMessage());
            throw e; 
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
    public List<Salida> listarSalidasPorMinisterio(int idMinisterio) throws SQLException {
        List<Salida> lista = new ArrayList<>();
        try (Connection con = BDConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_MINISTERIO)) {
            ps.setInt(1, idMinisterio);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Salida s = new Salida();
                    s.setIdSalida(rs.getInt("ID_Salida"));
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
            }
        } catch (SQLException e) {
            System.err.println("Error listando salidas por ministerio " + idMinisterio + ": " + e.getMessage());
            throw e;
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
    
    
    @Override
    public double obtenerTotalSalidasPorMinisterio(int idMinisterio) throws SQLException {
        double total = 0.0;
        try (Connection con = BDConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_SUM_BY_MINISTERIO)) {

            ps.setInt(1, idMinisterio);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getDouble("TotalSalidas");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener total de ofrendas para ministerio " + idMinisterio + ": " + e.getMessage());
            throw e;
        }
        return total;
    }
    
    
}

