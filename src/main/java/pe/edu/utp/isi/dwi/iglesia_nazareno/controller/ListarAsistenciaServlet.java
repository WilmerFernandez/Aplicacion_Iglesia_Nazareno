package pe.edu.utp.isi.dwi.iglesia_nazareno.controller;

import pe.edu.utp.isi.dwi.iglesia_nazareno.services.AsistenciaService;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Asistencia;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/listarAsistencias")
public class ListarAsistenciaServlet extends HttpServlet {

    private AsistenciaService asistenciaService;

    @Override
    public void init() throws ServletException {
        super.init();
        asistenciaService = new AsistenciaService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Obtener el idMinisterio desde los parámetros de la solicitud (debe ser un valor de 1, 2, 3 o 4)
            String idMinisterioStr = request.getParameter("idMinisterio");
            int idMinisterio = idMinisterioStr != null ? Integer.parseInt(idMinisterioStr) : 1; // ID por defecto si no se pasa el parámetro

            // Obtener las fechas de inicio y fin
            String fechaInicio = request.getParameter("fechaInicio");
            String fechaFin = request.getParameter("fechaFin");

            // Filtrar las asistencias por ministerio y fechas
            List<Asistencia> asistencias = asistenciaService.listarAsistenciasPorFecha(fechaInicio, fechaFin, idMinisterio);

            // Calcular los promedios
            double[] promedios = asistenciaService.calcularPromedios(asistencias);
            double promedioAdultos = Math.round(promedios[0] * 100.0) / 100.0;
            double promedioJovenes = Math.round(promedios[1] * 100.0) / 100.0;
            double promedioAdolescentes = Math.round(promedios[2] * 100.0) / 100.0;
            double promedioNinos = Math.round(promedios[3] * 100.0) / 100.0;
            double promedioTotal = Math.round(promedios[4] * 100.0) / 100.0;

            // Pasar los datos al JSP
            request.setAttribute("asistencias", asistencias);
            request.setAttribute("promedioAdultos", promedioAdultos);
            request.setAttribute("promedioJovenes", promedioJovenes);
            request.setAttribute("promedioAdolescentes", promedioAdolescentes);
            request.setAttribute("promedioNinos", promedioNinos);
            request.setAttribute("promedioTotal", promedioTotal);
            request.setAttribute("idMinisterio", idMinisterio);  // Pasar el ID del ministerio al JSP

            // Enviar los datos al JSP para mostrar
            request.getRequestDispatcher("/asistenciasList.jsp").forward(request, response);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener las asistencias");
        }
    }
}
