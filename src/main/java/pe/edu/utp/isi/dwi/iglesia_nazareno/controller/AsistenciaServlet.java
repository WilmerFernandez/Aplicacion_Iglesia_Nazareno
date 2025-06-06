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
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AsistenciaServlet", urlPatterns = {"/asistencia"})
public class AsistenciaServlet extends HttpServlet {

    private AsistenciaService asistenciaService;
    private Map<String, String> validJspPaths;

    @Override
    public void init() throws ServletException {
        super.init();
        asistenciaService = new AsistenciaService();

        validJspPaths = new HashMap<>();
   
        validJspPaths.put("registrarAsistencia", "/WEB-INF/vistas/registrarAsistencia.jsp");
        validJspPaths.put("registrarAsistenciaJNI", "/WEB-INF/vistas/registrarAsistenciaJNI.jsp");
        validJspPaths.put("registrarAsistenciaMNI", "/WEB-INF/vistas/registrarAsistenciaMNI.jsp");
        validJspPaths.put("registrarAsistenciaDNI", "/WEB-INF/vistas/registrarAsistenciaDNI.jsp");
        
        // Añador aquí cualquier otro JSP que uses para formularios.
    }

    private String getJspPath(String jspName) {
     
        return validJspPaths.getOrDefault(jspName, "/WEB-INF/vistas/registrarAsistencia.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestedForm = request.getParameter("form");
        String targetJsp = getJspPath(requestedForm != null ? requestedForm : "registrarAsistencia");

        request.getRequestDispatcher(targetJsp).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        boolean responseCommitted = false;

        String originatingJspName = request.getParameter("jspName");
        String targetJsp = getJspPath(originatingJspName);

        if ("registrar".equalsIgnoreCase(action)) {
            try {
                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");

                if (usuarioLogueado == null) {
                    request.getSession().setAttribute("error", "Debe iniciar sesión para registrar asistencia.");
                    response.sendRedirect(request.getContextPath() + "/login");
                    responseCommitted = true;
                    return;
                }

                int idMinisterio;
                String idMinisterioParam = request.getParameter("idMinisterioForm");

                if (idMinisterioParam != null && !idMinisterioParam.trim().isEmpty()) {
                    try {
                        idMinisterio = Integer.parseInt(idMinisterioParam);
                    } catch (NumberFormatException e) {
                        request.setAttribute("error", "Error: El ID del Ministerio no es un número válido.");
                        idMinisterio = 0;
                        System.err.println("NumberFormatException for idMinisterioForm: " + e.getMessage());
                    }
                } else {
                    request.setAttribute("error", "Error: El ID del Ministerio es requerido y no fue enviado.");
                    idMinisterio = 0;
                }

                if (idMinisterio <= 0) {
                    System.err.println("Intentó registrar con ID de Ministerio inválido o ausente: " + idMinisterioParam);
                } else {
                    int cantidadAdultos = Integer.parseInt(request.getParameter("cantidadAdultos"));
                    int cantidadJovenes = Integer.parseInt(request.getParameter("cantidadJovenes"));
                    int cantidadAdolescentes = Integer.parseInt(request.getParameter("cantidadAdolescentes"));
                    int cantidadNinos = Integer.parseInt(request.getParameter("cantidadNinos"));

                    int idUsuarioRegistrador = usuarioLogueado.getIdUsuario();

                    Asistencia asistencia = new Asistencia();
                    asistencia.setFecha(Timestamp.valueOf(LocalDateTime.now()));
                    asistencia.setIdMinisterio(idMinisterio); 
                    asistencia.setCantidadAdultos(cantidadAdultos);
                    asistencia.setCantidadJovenes(cantidadJovenes);
                    asistencia.setCantidadAdolescentes(cantidadAdolescentes);
                    asistencia.setCantidadNinos(cantidadNinos);
                    asistencia.setRegistradoPor(idUsuarioRegistrador);

                    boolean registrado = asistenciaService.registrarAsistencia(asistencia);

                    if (registrado) {
                        request.setAttribute("mensaje", "Asistencia registrada correctamente para el Ministerio ID: " + idMinisterio + ".");
                    } else {
                        request.setAttribute("error", "No se pudo registrar la asistencia. La base de datos no confirmó la inserción.");
                    }
                }

            } catch (NumberFormatException e) {
                request.setAttribute("error", "Error en el formato de las cantidades. Ingrese números válidos.");
                e.printStackTrace();
            } catch (Exception e) {
                request.setAttribute("error", "Ocurrió un error inesperado al registrar la asistencia: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción inválida para el registro de asistencia.");
            responseCommitted = true;
        }

        if (!responseCommitted) {
            request.getRequestDispatcher(targetJsp).forward(request, response);
        }
    }
}