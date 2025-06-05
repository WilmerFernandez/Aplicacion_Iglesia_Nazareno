package pe.edu.utp.isi.dwi.iglesia_nazareno.controller;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Feligres;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.FeligresService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
// import java.time.LocalDateTime; // Ya no es necesario si la BD maneja Fecha_Registro

@WebServlet(name = "FeligresServlet", urlPatterns = {"/feligres"})
public class FeligresServlet extends HttpServlet {

    private FeligresService feligresService;

    @Override
    public void init() throws ServletException {
        super.init();
        feligresService = new FeligresService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            LocalDate fechaNacimiento = LocalDate.parse(request.getParameter("fechaNacimiento"));
            String estado = request.getParameter("estado");
            String telefono = request.getParameter("telefono");   // <-- Obtener Telefono
            String direccion = request.getParameter("direccion"); // <-- Obtener Direccion

            Feligres f = new Feligres();
            f.setNombre(nombre);
            f.setApellido(apellido);
            f.setFechaNacimiento(fechaNacimiento);
            f.setEstado(estado);
            f.setTelefono(telefono);     // <-- Setear Telefono
            f.setDireccion(direccion);   // <-- Setear Direccion
            // No necesitas setear f.setFechaRegistro() aquí,
            // ya que la base de datos se encarga automáticamente.

            boolean registrado = feligresService.registrarFeligres(f);

            if (registrado) {
                request.setAttribute("mensaje", "Feligres registrado exitosamente.");
            } else {
                request.setAttribute("error", "No se pudo registrar el feligres. Verifique los datos o la conexión a la base de datos.");
            }

        } catch (Exception e) {
            // Es buena práctica loguear la excepción completa para depuración
            e.printStackTrace(); // Esto imprimirá el stack trace en la consola del servidor
            request.setAttribute("error", "Error interno al procesar el registro: " + e.getMessage());
        }

        request.getRequestDispatcher("/registroFeligres.jsp").forward(request, response);
    }
}