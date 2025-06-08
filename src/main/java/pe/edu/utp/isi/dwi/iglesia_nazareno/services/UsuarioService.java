package pe.edu.utp.isi.dwi.iglesia_nazareno.services;

import pe.edu.utp.isi.dwi.iglesia_nazareno.DAO.UsuarioDAO;
import pe.edu.utp.isi.dwi.iglesia_nazareno.implementacion.UsuarioDAOImpl;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Usuario;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Clase de servicio para la gestión de usuarios.
 * Encapsula la lógica de negocio, validaciones y seguridad.
 */
public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    /**
     * Constructor que inicializa el DAO.
     */
    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    /**
     * Registra un nuevo usuario.
     * Hashea la contraseña antes de guardar.
     *
     * @param usuario Objeto Usuario con datos a registrar.
     * @return true si el registro fue exitoso, false en caso contrario.
     */
    public boolean registrarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getContrasena() == null) {
            throw new IllegalArgumentException("Usuario o contraseña no pueden ser nulos");
        }

        
        if (usuarioDAO.buscarPorUsuario(usuario.getUsuario()) != null) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }

        // Hashear contraseña usando SHA-256 de Guava (mejor usar bcrypt en producción)
        String hashedPassword = Hashing.sha256()
                .hashString(usuario.getContrasena(), StandardCharsets.UTF_8)
                .toString();

        usuario.setContrasena(hashedPassword);
        usuario.setEstado("activo");

        return usuarioDAO.insertar(usuario);
    }

    /**
     * Autentica a un usuario verificando usuario y contraseña.
     *
     * @param nombreUsuario nombre de usuario (login)
     * @param contrasena contraseña en texto plano
     * @return Usuario autenticado o null si falla
     */
    public Usuario autenticar(String nombreUsuario, String contrasena) {
        Usuario usuario = usuarioDAO.buscarPorUsuario(nombreUsuario);
        if (usuario != null) {
            String hashedInput = Hashing.sha256()
                    .hashString(contrasena, StandardCharsets.UTF_8)
                    .toString();
            if (usuario.getContrasena().equals(hashedInput) && "activo".equalsIgnoreCase(usuario.getEstado())) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Obtiene la lista de todos los usuarios registrados.
     *
     * @return lista de usuarios
     */
    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listarTodos();
    }

    // Aqui se agrega más métodos de negocio como actualizar usuario, cambiar estado, etc.
}
