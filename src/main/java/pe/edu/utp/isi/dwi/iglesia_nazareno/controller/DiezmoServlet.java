package pe.edu.utp.isi.dwi.iglesia_nazareno.controller;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Diezmo;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Feligres;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Usuario;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.DiezmoService;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.FeligresService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "DiezmoServlet", urlPatterns = {"/diezmo"})
public class DiezmoServlet extends HttpServlet {

    private DiezmoService diezmoService;
    private FeligresService feligresService;

    @Override
    public void init() throws ServletException {
        super.init();
        diezmoService = new DiezmoService();
        feligresService = new FeligresService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener lista de feligreses para el formulario
        List<Feligres> feligreses = feligresService.listarTodos();
        request.setAttribute("listaFeligres", feligreses);

        // Enviar a JSP para mostrar el formulario y mensajes
        request.getRequestDispatcher("/registrarDiezmo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("registrar".equalsIgnoreCase(action)) {
            try {
                // Parseo de parámetros recibidos
                int idFeligres = Integer.parseInt(request.getParameter("idFeligres"));
                LocalDate fecha = LocalDate.parse(request.getParameter("fecha"));
                double monto = Double.parseDouble(request.getParameter("monto"));

                // Obtener usuario logueado desde sesión
                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
                if (usuarioLogueado == null) {
                    throw new ServletException("No hay usuario logueado en la sesión.");
                }
                int idUsuarioRegistrador = usuarioLogueado.getIdUsuario();

                // Crear objeto Diezmo y setear valores
                Diezmo diezmo = new Diezmo();
                diezmo.setIdFeligres(idFeligres);
                diezmo.setFecha(fecha);
                diezmo.setMonto(monto);
                diezmo.setIdRegistradoPor(idUsuarioRegistrador);

                // Registrar diezmo en base de datos
                boolean registrado = diezmoService.registrarDiezmo(diezmo);

                // Preparar mensaje para la vista
                if (registrado) {
                    request.setAttribute("mensaje", "Diezmo registrado correctamente.");
                } else {
                    request.setAttribute("error", "No se pudo registrar el diezmo. La base de datos no confirmó la inserción.");
                }

            } catch (NumberFormatException e) {
                request.setAttribute("error", "Error en el formato de los datos (ID de Feligrés o Monto). " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                request.setAttribute("error", "Ocurrió un error inesperado al registrar el diezmo: " + e.getMessage());
                e.printStackTrace();
            }

            // Volver a mostrar el formulario con mensajes
            doGet(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción inválida");
        }
    }
}
