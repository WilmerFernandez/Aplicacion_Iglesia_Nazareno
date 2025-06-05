package pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion;

import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.FeligresDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Feligres;
import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.BDConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeligresDAOImpl implements FeligresDAO {

    private static final String SQL_INSERT = "INSERT INTO Feligres (Nombre, Apellido, Fecha_Nacimiento, Estado, Telefono, Direccion) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT ID_Feligres, Nombre, Apellido, Fecha_Nacimiento, Estado, Telefono, Direccion, Fecha_Registro FROM Feligres";
    private static final String SQL_SELECT_BY_NAME = "SELECT ID_Feligres, Nombre, Apellido, Fecha_Nacimiento, Estado, Telefono, Direccion, Fecha_Registro FROM Feligres WHERE Nombre LIKE ?";

    @Override
    public boolean insertar(Feligres feligres) {
        boolean resultado = false;
        try (Connection con = BDConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {

            ps.setString(1, feligres.getNombre());
            ps.setString(2, feligres.getApellido());
            ps.setDate(3, Date.valueOf(feligres.getFechaNacimiento()));
            ps.setString(4, feligres.getEstado());
            ps.setString(5, feligres.getTelefono());
            ps.setString(6, feligres.getDireccion());
            // ¡¡¡IMPORTANTE!!! Elimina la línea siguiente:
            // ps.setTimestamp(7, Timestamp.valueOf(feligres.getFechaRegistro()));

            resultado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error insertando feligrés: " + e.getMessage());
            // Opcional: lanzar una excepción personalizada o loguear con más detalle
        }
        return resultado;
    }

    @Override
    public List<Feligres> listarTodos() {
        List<Feligres> lista = new ArrayList<>();
        try (Connection con = BDConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SQL_SELECT_ALL); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Feligres f = new Feligres();
                f.setId(rs.getInt("ID_Feligres"));
                f.setNombre(rs.getString("Nombre"));
                f.setApellido(rs.getString("Apellido"));
                f.setFechaNacimiento(rs.getDate("Fecha_Nacimiento").toLocalDate());
                f.setEstado(rs.getString("Estado"));
                f.setTelefono(rs.getString("Telefono"));
                f.setDireccion(rs.getString("Direccion"));
                f.setFechaRegistro(rs.getTimestamp("Fecha_Registro").toLocalDateTime());
                
                lista.add(f);
            }

        } catch (SQLException e) {
            System.err.println("Error listando feligreses: " + e.getMessage());
        }
        return lista;
    }
   

    @Override
    public List<Feligres> buscarPorNombre(String nombre) {
        List<Feligres> lista = new ArrayList<>();

        if (nombre == null) {
            nombre = ""; // Asegura que no sea null para el LIKE
        }

        try (Connection con = BDConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_NAME)) {

            ps.setString(1, "%" + nombre + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Feligres f = new Feligres();
                    f.setId(rs.getInt("ID_Feligres"));
                    f.setNombre(rs.getString("Nombre"));
                    f.setApellido(rs.getString("Apellido"));
                    f.setFechaNacimiento(rs.getDate("Fecha_Nacimiento").toLocalDate());
                    f.setEstado(rs.getString("Estado"));
                    f.setTelefono(rs.getString("Telefono"));
                    f.setDireccion(rs.getString("Direccion"));
                    f.setFechaRegistro(rs.getTimestamp("Fecha_Registro").toLocalDateTime());
                    lista.add(f);
                }
            } // El try-with-resources cerrará el ResultSet automáticamente

        } catch (SQLException e) {
            System.err.println("Error buscando feligrés por nombre: " + e.getMessage());
            // Opcional: lanzar una excepción personalizada o loguear con más detalle
        }
        return lista;
    }

}
