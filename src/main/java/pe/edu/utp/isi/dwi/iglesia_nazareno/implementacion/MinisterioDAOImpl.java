// pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion.MinisterioDAOImpl
package pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion;

import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.MinisterioDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Ministerio;
import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.BDConnection; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp; 

public class MinisterioDAOImpl implements MinisterioDAO {

    private static final String SQL_SELECT_ALL = "SELECT ID_Ministerio, Nombre_Ministerio, Fecha_Registro FROM Ministerio";

    @Override
    public List<Ministerio> listarTodos() throws SQLException {
        List<Ministerio> ministerios = new ArrayList<>();
        try (Connection con = BDConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ministerio ministerio = new Ministerio();
                ministerio.setIdMinisterio(rs.getInt("ID_Ministerio"));
                ministerio.setNombreMinisterio(rs.getString("Nombre_Ministerio"));

                
                Timestamp fechaRegistroDb = rs.getTimestamp("Fecha_Registro");
                ministerio.setFechaRegistro(fechaRegistroDb); 

                ministerios.add(ministerio);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar ministerios: " + e.getMessage());
            throw e; // Relanza la excepción para que el servicio la capture
        }
        return ministerios;
    }

   
}