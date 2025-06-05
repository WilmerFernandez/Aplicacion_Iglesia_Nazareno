package pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion;

import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.UsuarioDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Usuario;
import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.BDConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz UsuarioDAO usando JDBC.
 */
public class UsuarioDAOImpl implements UsuarioDAO {

    private static final String SQL_INSERT = "INSERT INTO Usuario (Nombre, Apellido, Correo, Usuario, Contrasena, Rol, Estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE Usuario SET Nombre = ?, Apellido = ?, Correo = ?, Usuario = ?, Contrasena = ?, Rol = ?, Estado = ? WHERE ID_Usuario = ?";
    private static final String SQL_DELETE = "DELETE FROM Usuario WHERE ID_Usuario = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM Usuario WHERE ID_Usuario = ?";
    private static final String SQL_SELECT_BY_USUARIO = "SELECT * FROM Usuario WHERE Usuario = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM Usuario";

    @Override
    public boolean insertar(Usuario usuario) {
        boolean resultado = false;
        try (Connection con = BDConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getUsuario());
            ps.setString(5, usuario.getContrasena()); // Debe estar hasheada
            ps.setString(6, usuario.getRol());
            ps.setString(7, usuario.getEstado());

            resultado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar Usuario: " + e.getMessage());
        }
        return resultado;
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        boolean resultado = false;
        try (Connection con = BDConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getUsuario());
            ps.setString(5, usuario.getContrasena()); // Debe estar hasheada
            ps.setString(6, usuario.getRol());
            ps.setString(7, usuario.getEstado());
            ps.setInt(8, usuario.getIdUsuario());

            resultado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar Usuario: " + e.getMessage());
        }
        return resultado;
    }

    @Override
    public boolean eliminar(int idUsuario) {
        boolean resultado = false;
        try (Connection con = BDConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, idUsuario);
            resultado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar Usuario: " + e.getMessage());
        }
        return resultado;
    }

    @Override
    public Usuario buscarPorId(int idUsuario) {
        Usuario usuario = null;
        try (Connection con = BDConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_ID)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = mapearUsuario(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar Usuario por ID: " + e.getMessage());
        }
        return usuario;
    }

    @Override
    public Usuario buscarPorUsuario(String usuarioLogin) {
        Usuario usuario = null;
        try (Connection con = BDConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_USUARIO)) {

            ps.setString(1, usuarioLogin);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = mapearUsuario(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar Usuario por login: " + e.getMessage());
        }
        return usuario;
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        try (Connection con = BDConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = mapearUsuario(rs);
                lista.add(usuario);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar Usuarios: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Mapea un ResultSet a un objeto Usuario.
     * 
     * @param rs ResultSet de consulta SQL
     * @return Usuario objeto con datos del ResultSet
     * @throws SQLException en caso de error en acceso a datos
     */
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("ID_Usuario"));
        usuario.setNombre(rs.getString("Nombre"));
        usuario.setApellido(rs.getString("Apellido"));
        usuario.setCorreo(rs.getString("Correo"));
        usuario.setUsuario(rs.getString("Usuario"));
        usuario.setContrasena(rs.getString("Contrasena"));
        usuario.setRol(rs.getString("Rol"));
        usuario.setEstado(rs.getString("Estado"));
        return usuario;
    }
}
