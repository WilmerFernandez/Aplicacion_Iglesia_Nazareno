package pe.edu.utp.isi.dwi.iglesia_nazareno.controller;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Salida;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Usuario;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.SalidaService;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.DiezmoService;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.OfrendaService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "SalidaServlet", urlPatterns = {"/salida"})
public class SalidaServlet extends HttpServlet {

    private SalidaService salidaService;
    private DiezmoService diezmoService;
    private OfrendaService ofrendaService;

    private Map<String, String> validJspPaths;
    private Map<String, Integer> jspNameToMinisterioId;

    @Override
    public void init() throws ServletException {
        super.init();
        salidaService = new SalidaService();
        diezmoService = new DiezmoService();
        ofrendaService = new OfrendaService();

        validJspPaths = new HashMap<>();
        jspNameToMinisterioId = new HashMap<>();

        // Definir rutas de JSPs
        validJspPaths.put("registrarSalida", "/WEB-INF/vistas/registrarSalida.jsp");
        validJspPaths.put("registrarSalidaJNI", "/WEB-INF/vistas/registrarSalidaJNI.jsp");
        validJspPaths.put("registrarSalidaMNI", "/WEB-INF/vistas/registrarSalidaMNI.jsp");
        validJspPaths.put("registrarSalidaDNI", "/WEB-INF/vistas/registrarSalidaDNI.jsp");

        // **************** IMPORTANTE: AÑADIR LAS ASOCIACIONES DE JSP A ID DE MINISTERIO ****************
        jspNameToMinisterioId.put("registrarSalida", 1);   
        jspNameToMinisterioId.put("registrarSalidaJNI", 2);
        jspNameToMinisterioId.put("registrarSalidaMNI", 3);  
        jspNameToMinisterioId.put("registrarSalidaDNI", 4);  
          
        
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String requestedForm = request.getParameter("form");
        
        String targetJsp;
        int idMinisterioActual = 1; // Default a ID 1 (Iglesia) si no se especifica o no se encuentra
        if (requestedForm != null && validJspPaths.containsKey(requestedForm)) {
            targetJsp = validJspPaths.get(requestedForm);
            // Si el requestedForm tiene una asociación de ministerio, úsala.
            if (jspNameToMinisterioId.containsKey(requestedForm)) {
                idMinisterioActual = jspNameToMinisterioId.get(requestedForm);
            }
        } else {
            targetJsp = validJspPaths.get("registrarSalida"); // JSP por defecto
            // Usar el ID del JSP por defecto si existe en el mapa
            if (jspNameToMinisterioId.containsKey("registrarSalida")) {
                idMinisterioActual = jspNameToMinisterioId.get("registrarSalida");
            }
        }
        request.setAttribute("idMinisterioActual", idMinisterioActual);
        request.getRequestDispatcher(targetJsp).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        // Este jspNameAfterPost es CRÍTICO y debe venir CORRECTAMENTE de cada JSP
        String jspNameAfterPost = request.getParameter("jspName"); 

        // Si jspNameAfterPost es nulo (ej. el campo oculto no se envió), o no se encuentra en el mapa,
        // se usará 1 (Iglesia) como ID por defecto.
        int idMinisterioAsociado = jspNameToMinisterioId.getOrDefault(jspNameAfterPost, 1);

        if ("registrar".equalsIgnoreCase(action)) {
            try {
                String fechaStr = request.getParameter("fecha");
                LocalDate fecha;
                if (fechaStr != null && !fechaStr.trim().isEmpty()) {
                    fecha = LocalDate.parse(fechaStr);
                } else {
                    throw new IllegalArgumentException("La fecha no puede estar vacía.");
                }

                String montoStr = request.getParameter("monto");
                double montoSalida;
                if (montoStr != null && !montoStr.trim().isEmpty()) {
                    montoSalida = Double.parseDouble(montoStr);
                } else {
                    throw new IllegalArgumentException("El monto no puede estar vacío.");
                }

                String descripcion = request.getParameter("descripcion");
                if (descripcion == null || descripcion.trim().isEmpty()) {
                    descripcion = "";
                }

                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
                if (usuarioLogueado == null) {
                    throw new ServletException("No hay usuario logueado en la sesión. Inicie sesión para registrar una salida.");
                }
                int idUsuarioRegistrador = usuarioLogueado.getIdUsuario();

                // LÓGICA DE FONDOS AJUSTADA POR MINISTERIO
                // Para esto, necesitarías el 'calcularTotalDiezmosPorMinisterio' y 'calcularTotalOfrendasPorMinisterio'
                // que habíamos discutido antes.
                // Si aún no los tienes implementados o los Diezmos son globales, ajusta aquí.
                // Ejemplo con lógica global para diezmos y por ministerio para ofrendas (como tu código original):
                double totalDiezmos = diezmoService.calcularTotalDiezmos(); // Suma todos los diezmos (si son globales)
                double ofrendasDelMinisterio = ofrendaService.calcularTotalOfrendasPorMinisterio(idMinisterioAsociado); // Ofrendas del ministerio específico
                double ingresosDisponibles = totalDiezmos + ofrendasDelMinisterio; // Combina ambos

                // Si Diezmos también son por ministerio, sería:
                // double diezmosDelMinisterio = diezmoService.calcularTotalDiezmosPorMinisterio(idMinisterioAsociado);
                // double ingresosDisponibles = diezmosDelMinisterio + ofrendasDelMinisterio;


                if (montoSalida > ingresosDisponibles) {
                    request.setAttribute("error", "Fondos insuficientes. El monto de la salida (S/. " +
                                        String.format("%.2f", montoSalida) + ") excede los ingresos disponibles (S/. " +
                                        String.format("%.2f", ingresosDisponibles) + ").");
                    request.setAttribute("idMinisterioActual", idMinisterioAsociado); 
                    String targetJspOnError = validJspPaths.get(jspNameAfterPost != null ? jspNameAfterPost : "registrarSalida");
                    request.getRequestDispatcher(targetJspOnError).forward(request, response);
                    return;
                }

                Salida salida = new Salida();
                salida.setFecha(fecha);
                salida.setMonto(montoSalida);
                salida.setDescripcion(descripcion);
                salida.setIdMinisterio(idMinisterioAsociado); // ¡Aquí se usa el ID del ministerio correcto!
                salida.setRegistradoPor(idUsuarioRegistrador);

                boolean registrado = salidaService.registrarSalida(salida);

                if (registrado) {
                    request.setAttribute("mensaje", "Salida registrada correctamente");
                } else {
                    request.setAttribute("error", "No se pudo registrar la salida. La base de datos no confirmó la inserción.");
                }

            } catch (IllegalArgumentException e) {
                request.setAttribute("error", "Error en los datos: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                request.setAttribute("error", "Ocurrió un error inesperado al registrar la salida: " + e.getMessage());
                e.printStackTrace();
            }

            request.setAttribute("idMinisterioActual", idMinisterioAsociado);
            String targetJspAfterPost = validJspPaths.get(jspNameAfterPost != null ? jspNameAfterPost : "registrarSalida");
            request.getRequestDispatcher(targetJspAfterPost).forward(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción POST inválida");
        }
    }
}