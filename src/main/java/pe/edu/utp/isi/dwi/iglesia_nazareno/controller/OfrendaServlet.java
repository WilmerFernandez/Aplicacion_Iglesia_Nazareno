package pe.edu.utp.isi.dwi.iglesia_nazareno.controller;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Ofrenda;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Ministerio;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Usuario;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.OfrendaService;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.MinisterioService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List; // Aunque no se use directamente para cargar el formulario, puede ser útil

@WebServlet(name = "OfrendaServlet", urlPatterns = {"/ofrenda"})
public class OfrendaServlet extends HttpServlet {

    private OfrendaService ofrendaService;
    private MinisterioService ministerioService; // Se mantiene por si en el futuro decides usarlo para algo más

    // Mapas para gestionar las rutas de los JSPs y sus IDs de ministerio asociados
    private Map<String, String> validJspPaths;
    private Map<String, Integer> jspNameToMinisterioId;

    @Override
    public void init() throws ServletException {
        super.init();
        ofrendaService = new OfrendaService();
        ministerioService = new MinisterioService();

        validJspPaths = new HashMap<>();
        jspNameToMinisterioId = new HashMap<>();

        // Definir rutas de JSPs para Ofrendas
        // ¡IMPORTANTE! Asegúrate de que estas rutas sean correctas para tu proyecto
        // Si tus JSPs están en /WEB-INF/vistas/, ajusta las rutas
        validJspPaths.put("registrarOfrenda", "/WEB-INF/vistas/registrarOfrenda.jsp");          // Iglesia (General)
        validJspPaths.put("registrarOfrendaJNI", "/WEB-INF/vistas/registrarOfrendaJNI.jsp");    // JNI
        validJspPaths.put("registrarOfrendaMNI", "/WEB-INF/vistas/registrarOfrendaMNI.jsp");    // MNI
        validJspPaths.put("registrarOfrendaDNI", "/WEB-INF/vistas/registrarOfrendaDNI.jsp");    // DNI

        // Asociar el nombre del JSP con el ID del Ministerio correspondiente
        // ¡Estos son los IDs que has proporcionado: IGLESIA ES 1, JNI 2, MNI 3, DNI 4
        jspNameToMinisterioId.put("registrarOfrenda", 1);    // Iglesia (General)
        jspNameToMinisterioId.put("registrarOfrendaJNI", 2);   // JNI
        jspNameToMinisterioId.put("registrarOfrendaMNI", 3);   // MNI
        jspNameToMinisterioId.put("registrarOfrendaDNI", 4);   // DNI
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener el nombre del JSP a cargar desde el parámetro 'form'
        String jspName = request.getParameter("form");
        String path = validJspPaths.get(jspName);

        if (path != null) {
            // Si el JSP es válido, lo despachamos
            request.getRequestDispatcher(path).forward(request, response);
        } else {
            // Si 'form' es nulo o no válido, se puede redirigir a un JSP por defecto o mostrar un error
            request.setAttribute("error", "Formulario de Ofrenda no especificado o inválido.");
            request.getRequestDispatcher("/WEB-INF/vistas/errorGenerico.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("registrar".equalsIgnoreCase(action)) {
            try {
                // Validación y parseo de fecha
                String fechaStr = request.getParameter("fecha");
                LocalDate fecha;
                if (fechaStr != null && !fechaStr.trim().isEmpty()) {
                    fecha = LocalDate.parse(fechaStr);
                } else {
                    throw new IllegalArgumentException("La fecha no puede estar vacía.");
                }

                // Validación y parseo de monto
                String montoStr = request.getParameter("monto");
                double monto;
                if (montoStr != null && !montoStr.trim().isEmpty()) {
                    monto = Double.parseDouble(montoStr);
                } else {
                    throw new IllegalArgumentException("El monto no puede estar vacío.");
                }

                // --- ¡Aquí está el cambio clave! ---
                // Obtener el ID de Ministerio desde el parámetro oculto del formulario
                String jspNameFromForm = request.getParameter("jspName");
                Integer idMinisterio = null;

                if (jspNameFromForm != null) {
                    idMinisterio = jspNameToMinisterioId.get(jspNameFromForm);
                }

                if (idMinisterio == null) {
                    throw new IllegalArgumentException("ID de Ministerio no encontrado para el formulario: " + jspNameFromForm);
                }

                // Obtener el usuario logueado desde la sesión
                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
                if (usuarioLogueado == null) {
                    throw new ServletException("No hay usuario logueado en la sesión. Inicie sesión para registrar una ofrenda.");
                }
                int idUsuarioRegistrador = usuarioLogueado.getIdUsuario();

                // Crear objeto Ofrenda y setear sus valores
                Ofrenda ofrenda = new Ofrenda();
                ofrenda.setFecha(fecha);
                ofrenda.setMonto(monto);
                ofrenda.setIdMinisterio(idMinisterio); // Usamos el ID dinámico
                ofrenda.setIdUsuarioRegistrador(idUsuarioRegistrador);

                // Registrar la ofrenda a través del servicio
                boolean registrado = ofrendaService.registrarOfrenda(ofrenda);

                // Preparar mensaje para la vista
                if (registrado) {
                    request.setAttribute("mensaje", "Ofrenda para " + getMinisterioName(idMinisterio) + " registrada correctamente.");
                } else {
                    request.setAttribute("error", "No se pudo registrar la ofrenda. La base de datos no confirmó la inserción.");
                }

            } catch (IllegalArgumentException e) { // Capturamos las excepciones de validación personalizadas
                request.setAttribute("error", "Error en los datos: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) { // Captura otras excepciones inesperadas
                request.setAttribute("error", "Ocurrió un error inesperado al registrar la ofrenda: " + e.getMessage());
                e.printStackTrace();
            }

            // Después de procesar el POST, se vuelve a cargar el JSP correcto con mensajes
            // Se debe obtener el jspName del formulario enviado
            String jspNameAfterPost = request.getParameter("jspName");
            String pathToForward = validJspPaths.get(jspNameAfterPost);

            if (pathToForward != null) {
                request.getRequestDispatcher(pathToForward).forward(request, response);
            } else {
                // Fallback si por alguna razón el jspName es inválido después del POST
                request.setAttribute("error", "Error interno: Formulario de ofrenda de retorno no válido.");
                request.getRequestDispatcher("/WEB-INF/vistas/errorGenerico.jsp").forward(request, response);
            }

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción POST inválida");
        }
    }

    // Método auxiliar para obtener el nombre del ministerio (opcional, para mensajes más amigables)
    private String getMinisterioName(int id) {
        // En un escenario real, esto se cargaría de la BD o de un mapa más robusto.
        // Por simplicidad, usamos un switch con los IDs que nos diste.
        switch (id) {
            case 1: return "Iglesia (General)";
            case 2: return "JNI";
            case 3: return "MNI";
            case 4: return "DNI";
            default: return "Desconocido";
        }
    }
}