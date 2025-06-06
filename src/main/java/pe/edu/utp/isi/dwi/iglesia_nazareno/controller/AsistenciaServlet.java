package pe.edu.utp.isi.dwi.iglesia_nazareno.controller;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Asistencia;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Usuario;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.AsistenciaService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet(name = "AsistenciaServlet", urlPatterns = {"/asistencia"})
public class AsistenciaServlet extends HttpServlet {

    private AsistenciaService asistenciaService;
    // Define la ruta del JSP una sola vez para evitar errores de escritura
    private final String ASISTENCIA_FORM_JSP = "registrarAsistencia.jsp"; 
    // ^^^ AJUSTA ESTA RUTA si tu JSP del formulario no está ahí ^^^

    @Override
    public void init() throws ServletException {
        super.init();
        asistenciaService = new AsistenciaService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Simplemente reenvía al JSP del formulario.
        request.getRequestDispatcher(ASISTENCIA_FORM_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // Esta bandera nos ayudará a saber si ya hemos redirigido/enviado una respuesta
        boolean responseCommitted = false; 

        if ("registrar".equalsIgnoreCase(action)) {
            try {
                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");

                if (usuarioLogueado == null) {
                    // Si el usuario no está logueado, establece un error en la sesión
                    // y redirige al login. Esto COMPROMETE LA RESPUESTA.
                    request.getSession().setAttribute("error", "Debe iniciar sesión para registrar asistencia.");
                    response.sendRedirect(request.getContextPath() + "/login");
                    responseCommitted = true; // Indicamos que la respuesta ya fue enviada
                    return; // ¡IMPORTANTE! Detiene la ejecución aquí para evitar el forward
                }

                // Solo si el usuario está logueado, procedemos a parsear y registrar
                int cantidadAdultos = Integer.parseInt(request.getParameter("cantidadAdultos"));
                int cantidadJovenes = Integer.parseInt(request.getParameter("cantidadJovenes"));
                int cantidadAdolescentes = Integer.parseInt(request.getParameter("cantidadAdolescentes"));
                int cantidadNinos = Integer.parseInt(request.getParameter("cantidadNinos"));

                int idUsuarioRegistrador = usuarioLogueado.getIdUsuario();

                Asistencia asistencia = new Asistencia();
                asistencia.setFecha(Timestamp.valueOf(LocalDateTime.now()));
                asistencia.setIdMinisterio(1); // Fija id_ministerio a 1
                asistencia.setCantidadAdultos(cantidadAdultos);
                asistencia.setCantidadJovenes(cantidadJovenes);
                asistencia.setCantidadAdolescentes(cantidadAdolescentes);
                asistencia.setCantidadNinos(cantidadNinos);
                asistencia.setRegistradoPor(idUsuarioRegistrador);

                boolean registrado = asistenciaService.registrarAsistencia(asistencia);

                if (registrado) {
                    request.setAttribute("mensaje", "Asistencia registrada correctamente.");
                } else {
                    request.setAttribute("error", "No se pudo registrar la asistencia. La base de datos no confirmó la inserción.");
                }

            } catch (NumberFormatException e) {
                request.setAttribute("error", "Error en el formato de las cantidades. Ingrese números válidos.");
                e.printStackTrace();
            } catch (Exception e) {
                request.setAttribute("error", "Ocurrió un error inesperado al registrar la asistencia: " + e.getMessage());
                e.printStackTrace();
            } 
            // El bloque 'finally' se ha eliminado.
            // El forward se hará solo si la respuesta no ha sido comprometida aún.
        } else {
            // Si la acción es inválida, envía un error. Esto también COMPROMETE LA RESPUESTA.
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción inválida para el registro de asistencia.");
            responseCommitted = true; // Indicamos que la respuesta ya fue enviada
        }

        // Este es el punto clave: solo hacemos forward si la respuesta no ha sido enviada (redirigida/error)
        if (!responseCommitted) {
            request.getRequestDispatcher(ASISTENCIA_FORM_JSP).forward(request, response);
        }
    }
}