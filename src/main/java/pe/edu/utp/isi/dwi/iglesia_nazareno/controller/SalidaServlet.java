package pe.edu.utp.isi.dwi.iglesia_nazareno.controller;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Salida;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Usuario;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.SalidaService;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.DiezmoService; // Importa el DiezmoService
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "SalidaServlet", urlPatterns = {"/salida"})
public class SalidaServlet extends HttpServlet {

    private SalidaService salidaService;
    private DiezmoService diezmoService; // Agrega el servicio para diezmos

    @Override
    public void init() throws ServletException {
        super.init();
        salidaService = new SalidaService();
        diezmoService = new DiezmoService(); // Inicializa el servicio de diezmos
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Ya no necesitamos obtener la lista de ministerios, ya que el ID es fijo.
        request.getRequestDispatcher("/registrarSalida.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("registrar".equalsIgnoreCase(action)) {
            try {
                // Validar y parsear Fecha
                String fechaStr = request.getParameter("fecha");
                LocalDate fecha;
                if (fechaStr != null && !fechaStr.trim().isEmpty()) {
                    fecha = LocalDate.parse(fechaStr);
                } else {
                    throw new IllegalArgumentException("La fecha no puede estar vacía.");
                }

                // Validar y parsear Monto
                String montoStr = request.getParameter("monto");
                double monto;
                if (montoStr != null && !montoStr.trim().isEmpty()) {
                    monto = Double.parseDouble(montoStr);
                } else {
                    throw new IllegalArgumentException("El monto no puede estar vacío.");
                }

                // Obtener Descripcion
                String descripcion = request.getParameter("descripcion");
                if (descripcion == null || descripcion.trim().isEmpty()) {
                    descripcion = ""; // O un valor por defecto si lo prefieres
                }

                // Obtener usuario logueado de la sesión
                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
                if (usuarioLogueado == null) {
                    throw new ServletException("No hay usuario logueado en la sesión. Inicie sesión para registrar una salida.");
                }
                int idUsuarioRegistrador = usuarioLogueado.getIdUsuario();

                // Calcular el total de los diezmos
                double totalDiezmos = diezmoService.calcularTotalDiezmos(); // Implementa este método en DiezmoService

                // El ID del ministerio siempre será 1 (Iglesia) + el total de los diezmos
                int idMinisterio = 1; // Ministerio Iglesia
                monto += totalDiezmos; // Suma el total de los diezmos al monto de la salida

                // Crear objeto Salida y setear valores
                Salida salida = new Salida();
                salida.setFecha(fecha);
                salida.setMonto(monto);
                salida.setDescripcion(descripcion);
                salida.setIdMinisterio(idMinisterio);
                salida.setRegistradoPor(idUsuarioRegistrador);

                // Registrar salida en base de datos
                boolean registrado = salidaService.registrarSalida(salida);

                // Preparar mensaje para la vista
                if (registrado) {
                    request.setAttribute("mensaje", "Salida registrada correctamente.");
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

            // Volver a cargar el formulario con mensajes
            doGet(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción POST inválida");
        }
    }
}