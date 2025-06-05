package pe.edu.utp.isi.dwi.iglesia_nazareno.DAO;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Usuario;
import java.util.List;

/**
 * Interfaz para definir operaciones CRUD de Usuario.
 */
public interface UsuarioDAO {
    
    /**
     * Inserta un nuevo usuario en la base de datos.
     * @param usuario Objeto Usuario con datos a insertar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    boolean insertar(Usuario usuario);

    /**
     * Actualiza datos de un usuario existente.
     * @param usuario Objeto Usuario con datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    boolean actualizar(Usuario usuario);

    /**
     * Elimina un usuario por su ID.
     * @param idUsuario ID del usuario a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    boolean eliminar(int idUsuario);

    /**
     * Busca un usuario por su ID.
     * @param idUsuario ID del usuario a buscar.
     * @return Usuario encontrado o null si no existe.
     */
    Usuario buscarPorId(int idUsuario);

    /**
     * Busca un usuario por su nombre de usuario (login).
     * @param usuario Nombre de usuario.
     * @return Usuario encontrado o null si no existe.
     */
    Usuario buscarPorUsuario(String usuario);

    /**
     * Obtiene la lista de todos los usuarios registrados.
     * @return Lista de usuarios.
     */
    List<Usuario> listarTodos();
}
