package pe.edu.utp.isi.dwi.iglesia_nazareno.controller;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.ReporteMinisterios;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.ReporteMinisteriosService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ReporteMinisteriosServlet")
public class ReporteMinisteriosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReporteMinisteriosService reporteService;

    public ReporteMinisteriosServlet() {
        super();
        this.reporteService = new ReporteMinisteriosService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idMinisterioStr = request.getParameter("idMinisterio");
        int idMinisterio = 0;
        String destinoJSP = "/WEB-INF/vistas/errorGenerico.jsp"; // JSP por defecto en caso de error

        try {
            if (idMinisterioStr != null && !idMinisterioStr.isEmpty()) {
                idMinisterio = Integer.parseInt(idMinisterioStr);

                // Validar que el ID del ministerio sea uno de los esperados
                if (idMinisterio < 1 || idMinisterio > 4) { // Asumiendo 1 para General, 2 JNI, 3 MNI, 4 DNI
                    throw new NumberFormatException("ID de ministerio fuera del rango esperado.");
                }

                ReporteMinisterios reporte = reporteService.generarReportePorMinisterio(idMinisterio);
                request.setAttribute("reporte", reporte);

                // Determinar a qué JSP redirigir basándose en el ID del ministerio
                switch (idMinisterio) {
                    case 1: // JNI
                        destinoJSP = "/WEB-INF/vistas/reporteIGLESIA.jsp";
                        break;
                    case 2: // JNI
                        destinoJSP = "/WEB-INF/vistas/reporteJNI.jsp";
                        break;
                    case 3: // MNI
                        destinoJSP = "/WEB-INF/vistas/reporteMNI.jsp";
                        break;
                    case 4: // DNI
                        destinoJSP = "/WEB-INF/vistas/reporteDNI.jsp";
                        break;
                   
                    default:
                        request.setAttribute("mensajeError", "Ministerio no reconocido.");
                        destinoJSP = "/WEB-INF/vistas/errorGenerico.jsp";
                        break;
                }
            } else {
                request.setAttribute("mensajeError", "El ID de ministerio no fue proporcionado.");
                destinoJSP = "/WEB-INF/vistas/errorGenerico.jsp"; // O a un JSP para seleccionar ministerio si es la primera vez
            }
        } catch (NumberFormatException e) {
            System.err.println("ID de Ministerio inválido: " + idMinisterioStr + " - " + e.getMessage());
            request.setAttribute("mensajeError", "El ID de ministerio proporcionado no es válido.");
        } catch (SQLException e) {
            System.err.println("Error de base de datos al generar el reporte del ministerio " + idMinisterio + ": " + e.getMessage());
            request.setAttribute("mensajeError", "Error al acceder a la base de datos para generar el reporte.");
        } catch (Exception e) {
            System.err.println("Error inesperado al procesar el reporte: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("mensajeError", "Ocurrió un error inesperado al generar el reporte.");
        } finally {
            request.getRequestDispatcher(destinoJSP).forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Para este caso de reportes, normalmente se usaría solo GET.
        // Si hay formularios en los reportes que envían datos, se manejarían aquí,
        // pero para la funcionalidad de reporte puro, doGet es suficiente.
        doGet(request, response);
    }
}