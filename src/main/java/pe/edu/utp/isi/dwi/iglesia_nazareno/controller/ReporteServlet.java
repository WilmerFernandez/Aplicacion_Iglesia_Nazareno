package pe.edu.utp.isi.dwi.iglesia_nazareno.controller;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Diezmo;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Ofrenda;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Salida;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.ReporteFinanciero;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.ReporteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ReporteServlet", urlPatterns = {"/reporte"})
public class ReporteServlet extends HttpServlet {

    private ReporteService reporteService;

    @Override
    public void init() throws ServletException {
        super.init();
        reporteService = new ReporteService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Reporte completo por defecto
            List<Diezmo> listaDiezmos = reporteService.listarTodosDiezmos();
            List<Ofrenda> listaOfrendas = reporteService.listarOfrendasPorMinisterio();
            List<Salida> listaSalidas = reporteService.listarSalidasPorMinisterio();
            ReporteFinanciero resumenFinanciero = reporteService.generarReporteFinancieroConsolidado();

            request.setAttribute("listaDiezmos", listaDiezmos);
            request.setAttribute("listaOfrendas", listaOfrendas);
            request.setAttribute("listaSalidas", listaSalidas);
            request.setAttribute("resumenFinanciero", resumenFinanciero);

            request.getRequestDispatcher("/reporteFinanciero.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", "Error al generar el reporte: " + e.getMessage());
            e.printStackTrace();
            request.getRequestDispatcher("/errorGenerico.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fechaInicio = request.getParameter("fechaInicio");
        String fechaFin = request.getParameter("fechaFin");

        try {
            // Validar fechas
            if (fechaInicio != null && fechaFin != null && !fechaInicio.isEmpty() && !fechaFin.isEmpty()) {
                List<Diezmo> listaDiezmos = reporteService.listarDiezmosPorFechas(fechaInicio, fechaFin);
                List<Ofrenda> listaOfrendas = reporteService.listarOfrendasPorFechas(fechaInicio, fechaFin);
                List<Salida> listaSalidas = reporteService.listarSalidasPorFechas(fechaInicio, fechaFin);
                ReporteFinanciero resumenFinanciero = reporteService.generarResumenPorFechas(fechaInicio, fechaFin);

                request.setAttribute("listaDiezmos", listaDiezmos);
                request.setAttribute("listaOfrendas", listaOfrendas);
                request.setAttribute("listaSalidas", listaSalidas);
                request.setAttribute("resumenFinanciero", resumenFinanciero);
                request.setAttribute("fechaInicio", fechaInicio);
                request.setAttribute("fechaFin", fechaFin);
            } else {
                request.setAttribute("error", "Debe seleccionar ambas fechas para filtrar.");
            }

        } catch (Exception e) {
            request.setAttribute("error", "Error al filtrar por fechas: " + e.getMessage());
            e.printStackTrace();
        }

        request.getRequestDispatcher("/reporteFinanciero.jsp").forward(request, response);
    }
}
