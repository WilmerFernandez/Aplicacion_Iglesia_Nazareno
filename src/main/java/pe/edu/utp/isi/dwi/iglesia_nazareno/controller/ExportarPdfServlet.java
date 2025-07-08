package pe.edu.utp.isi.dwi.iglesia_nazareno.controller;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Diezmo;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Ofrenda;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Salida;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.ReporteFinanciero;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.ReporteService;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/exportarPdf")
public class ExportarPdfServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReporteService reporteService = new ReporteService();

        String inicio = request.getParameter("fechaInicio");
        String fin = request.getParameter("fechaFin");

        try (PDDocument document = new PDDocument()) {
            List<Diezmo> diezmos;
            List<Ofrenda> ofrendas;
            List<Salida> salidas;
            ReporteFinanciero reporte;

            if (inicio != null && fin != null && !inicio.isEmpty() && !fin.isEmpty()) {
                diezmos = reporteService.listarDiezmosPorFechas(inicio, fin);
                ofrendas = reporteService.listarOfrendasPorFechas(inicio, fin);
                salidas = reporteService.listarSalidasPorFechas(inicio, fin);
                reporte = reporteService.generarResumenPorFechas(inicio, fin);
            } else {
                diezmos = reporteService.listarTodosDiezmos();
                ofrendas = reporteService.listarOfrendasPorMinisterio();
                salidas = reporteService.listarSalidasPorMinisterio();
                reporte = reporteService.generarReporteFinancieroConsolidado();
            }

            // Crear primera página
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
            escribirTexto(contentStream, "Reporte Financiero - Iglesia Nazareno", 50, 750);

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
            escribirTexto(contentStream, "Resumen Consolidado", 50, 700);

            contentStream.setFont(PDType1Font.HELVETICA, 12);
            escribirTexto(contentStream, String.format("Total Diezmos: S/. %.2f", reporte.getTotalDiezmos()), 50, 675);
            escribirTexto(contentStream, String.format("Total Ofrendas: S/. %.2f", reporte.getTotalOfrendasIglesia()), 50, 650);
            escribirTexto(contentStream, String.format("Total Salidas: S/. %.2f", reporte.getTotalSalidas()), 50, 625);

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
            escribirTexto(contentStream, String.format("Saldo en Caja: S/. %.2f", reporte.getSaldoCaja()), 50, 590);

            // Detalle de Diezmos
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
            escribirTexto(contentStream, "Detalle de Diezmos", 50, 550);

            contentStream.setFont(PDType1Font.HELVETICA, 10);
            int yPosition = 525;
            for (Diezmo diezmo : diezmos) {
                String linea = String.format("%d - %s - S/. %.2f - %s",
                        diezmo.getId(),
                        diezmo.getNombreFeligres(),
                        diezmo.getMonto(),
                        diezmo.getFecha().toString());

                escribirTexto(contentStream, linea, 50, yPosition);
                yPosition -= 15;

                if (yPosition < 50) {
                    contentStream.close();
                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    yPosition = 750;
                }
            }

            // Detalle de Ofrendas
            contentStream.close();
            page = new PDPage();
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
            escribirTexto(contentStream, "Detalle de Ofrendas", 50, 750);

            contentStream.setFont(PDType1Font.HELVETICA, 10);
            yPosition = 725;
            for (Ofrenda ofrenda : ofrendas) {
                String linea = String.format("%d - S/. %.2f - %s",
                        ofrenda.getId(),
                        ofrenda.getMonto(),
                        ofrenda.getFecha().toString());

                escribirTexto(contentStream, linea, 50, yPosition);
                yPosition -= 15;

                if (yPosition < 50) {
                    contentStream.close();
                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    yPosition = 750;
                }
            }

            // Detalle de Salidas
            contentStream.close();
            page = new PDPage();
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
            escribirTexto(contentStream, "Detalle de Salidas", 50, 750);

            contentStream.setFont(PDType1Font.HELVETICA, 10);
            yPosition = 725;
            for (Salida salida : salidas) {
                String linea = String.format("%d - S/. %.2f - %s - %s",
                        salida.getIdSalida(),
                        salida.getMonto(),
                        salida.getFecha().toString(),
                        salida.getDescripcion());

                escribirTexto(contentStream, linea, 50, yPosition);
                yPosition -= 15;

                if (yPosition < 50) {
                    contentStream.close();
                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    yPosition = 750;
                }
            }

            contentStream.close();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=reporte_financiero.pdf");
            document.save(response.getOutputStream());

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al generar PDF: " + e.getMessage());
        }
    }

    private void escribirTexto(PDPageContentStream contentStream, String texto, float x, float y) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(texto);
        contentStream.endText();
    }
}
