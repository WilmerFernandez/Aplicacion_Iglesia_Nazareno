package pe.edu.utp.isi.dwi.iglesia_nazareno.controller;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Ofrenda;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Ministerio;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Usuario;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.OfrendaService;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.MinisterioService; // Necesario si mantienes el doGet para listar Ministerios, aunque no lo uses en el formulario

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "OfrendaServletDNI", urlPatterns = {"/ofrendaDNI"})
public class OfrendaServletDNI extends HttpServlet {

    private OfrendaService ofrendaService;
    private MinisterioService ministerioService; // Se mantiene por si en el futuro decides usarlo para algo más

    @Override
    public void init() throws ServletException {
        super.init();
        ofrendaService = new OfrendaService();
        ministerioService = new MinisterioService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Aunque el JSP ya no necesita la lista de ministerios para un dropdown,
            // mantenemos la lógica por si quieres usar 'listaMinisterios' para otra cosa
            // o si en el futuro decides reintroducir la selección.
            // Si solo quieres cargar el formulario sin datos adicionales, puedes simplificar esto.
            List<Ministerio> ministerios = ministerioService.listarTodosMinisterios();
            request.setAttribute("listaMinisterios", ministerios);

            // Enviar a la JSP para mostrar el formulario de registro de ofrendas
            request.getRequestDispatcher("/registrarOfrendaDNI.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", "Error al cargar recursos para el formulario: " + e.getMessage());
            e.printStackTrace();
            request.getRequestDispatcher("/errorGenerico.jsp").forward(request, response);
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
                // Establecer el ID de Ministerio fijo a 1 (para "Iglesia")
                int idMinisterio = 4; // Establece directamente el ID 4

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
                ofrenda.setIdMinisterio(idMinisterio); // Usamos el ID fijo
                ofrenda.setIdUsuarioRegistrador(idUsuarioRegistrador);

                // Registrar la ofrenda a través del servicio
                boolean registrado = ofrendaService.registrarOfrenda(ofrenda);

                // Preparar mensaje para la vista
                if (registrado) {
                    request.setAttribute("mensaje", "Ofrenda registrada correctamente.");
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

            // Después de procesar el POST, se vuelve a llamar al doGet para recargar el formulario con mensajes
            doGet(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción POST inválida");
        }
    }
}
