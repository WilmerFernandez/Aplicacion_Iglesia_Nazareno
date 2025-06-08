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
            // Listar todos los diezmos, ofrendas y salidas
            List<Diezmo> listaDiezmos = reporteService.listarTodosDiezmos();
            List<Ofrenda> listaOfrendas = reporteService.listarOfrendasPorMinisterio();
            List<Salida> listaSalidas = reporteService.listarSalidasPorMinisterio();

            // Generar el reporte consolidado (totales y saldo)
            ReporteFinanciero resumenFinanciero = reporteService.generarReporteFinancieroConsolidado();

            // Poner los datos en el request para la JSP
            request.setAttribute("listaDiezmos", listaDiezmos);
            request.setAttribute("listaOfrendas", listaOfrendas);
            request.setAttribute("listaSalidas", listaSalidas);
            request.setAttribute("resumenFinanciero", resumenFinanciero);

            // Redirigir a la JSP del reporte
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
        doGet(request, response); 
    }
}
