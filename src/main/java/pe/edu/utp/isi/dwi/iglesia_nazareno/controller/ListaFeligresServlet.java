package pe.edu.utp.isi.dwi.iglesia_nazareno.controller;

import pe.edu.utp.isi.dwi.iglesia_nazareno.services.FeligresService;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Feligres;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.Date; // Importación para java.sql.Date

import java.time.LocalDateTime;

@WebServlet("/listaFeligreses")
public class ListaFeligresServlet extends HttpServlet {

    private FeligresService feligresService;

    @Override
    public void init() throws ServletException {
        feligresService = new FeligresService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombreBusqueda = request.getParameter("nombreBusqueda");
        List<Feligres> listaFeligresesOriginal;

        if (nombreBusqueda != null && !nombreBusqueda.trim().isEmpty()) {
            listaFeligresesOriginal = feligresService.buscarFeligresesPorNombre(nombreBusqueda);
            request.setAttribute("nombreBusqueda", nombreBusqueda);
        } else {
            listaFeligresesOriginal = feligresService.listarTodos();
        }

        List<FeligresDisplayDTO> listaFeligresesParaJSP = listaFeligresesOriginal.stream()
                .map(FeligresDisplayDTO::new)
                .collect(Collectors.toList());

        request.setAttribute("listaFeligreses", listaFeligresesParaJSP);

        request.getRequestDispatcher("/listadoFeligres.jsp").forward(request, response);
    }

    public static class FeligresDisplayDTO {

        private int id;
        private String nombre;
        private String apellido;
        private java.util.Date fechaNacimiento; // Sigue siendo java.util.Date aquí, lo cual aún requiere conversión desde LocalDate del modelo
        private String estado;
        private String telefono;
        private String direccion; // Campo 'direccion'
        private LocalDateTime fechaRegistro;

        // Constructor actualizado
        public FeligresDisplayDTO(Feligres feligres) {
            this.id = feligres.getId();
            this.nombre = feligres.getNombre();
            this.apellido = feligres.getApellido();
            // Recordatorio: esta línea podría dar un error de compilación
            // si feligres.getFechaNacimiento() devuelve LocalDate y no java.sql.Date
            this.fechaNacimiento = Date.valueOf(feligres.getFechaNacimiento());
            this.estado = feligres.getEstado();
            this.telefono = feligres.getTelefono();
            this.direccion = feligres.getDireccion();
            this.fechaRegistro = feligres.getFechaRegistro();
        }

        // Getters
        public int getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        public String getApellido() {
            return apellido;
        }

        public java.util.Date getFechaNacimiento() {
            return fechaNacimiento;
        }

        public String getEstado() {
            return estado;
        }

        public String getTelefono() {
            return telefono;
        }

        public String getDireccion() {
            return direccion;
        } // ¡CORREGIDO AQUÍ!

        public LocalDateTime getFechaRegistro() {
            return fechaRegistro;
        }
    }

}
