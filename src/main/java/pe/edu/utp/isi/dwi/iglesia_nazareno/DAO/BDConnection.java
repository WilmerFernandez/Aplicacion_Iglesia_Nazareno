package pe.edu.utp.isi.dwi.iglesia_nazareno.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos MySQL.
 * Proporciona un método estático para obtener una conexión JDBC.
 * 
 * @author Wilmer Javier Fernandez Benavides
 * @version 1.0
 * @since 2025-05-29
 */
public class BDConnection {

    /**
     * URL de conexión JDBC al servidor MySQL.
     * Incluye el nombre de la base de datos y parámetros para evitar warnings.
     */
    private static final String URL = "jdbc:mysql://localhost:3306/bd_Nazareno?useSSL=false&serverTimezone=UTC";

    /**
     * Usuario para autenticación en la base de datos.
     * Cambiar según credenciales propias.
     */
    private static final String USER = "root";

    /**
     * Contraseña para autenticación en la base de datos.
     * Cambiar según credenciales propias.
     */
    private static final String PASSWORD = "admin123";

    /**
     * Obtiene una conexión abierta a la base de datos MySQL.
     * 
     * @return Connection objeto conexión activa a la base de datos
     * @throws SQLException si ocurre un error al obtener la conexión
     */
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Registrar driver JDBC MySQL (desde JDBC 4.0 no es obligatorio, pero es buena práctica)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión usando URL, usuario y contraseña
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            // Manejo de error si el driver JDBC no se encuentra en el classpath
            System.err.println("Error: Driver JDBC MySQL no encontrado.");
            e.printStackTrace();
        }
        return connection;
    }
}
