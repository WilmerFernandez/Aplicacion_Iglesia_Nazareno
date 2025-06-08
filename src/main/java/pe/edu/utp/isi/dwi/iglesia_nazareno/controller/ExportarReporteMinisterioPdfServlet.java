package pe.edu.utp.isi.dwi.iglesia_nazareno.controller;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Ofrenda;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.ReporteMinisterios;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Salida;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.ReporteMinisteriosService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font; 
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;

@WebServlet("/exportarReporteMinisterioPdf")
public class ExportarReporteMinisterioPdfServlet extends HttpServlet {

    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("0.00");

   
    private PDType0Font fontRegular;
    private PDType0Font fontBold;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Cargar la fuente DejaVuSans.ttf
            InputStream fontStreamRegular = getServletContext().getResourceAsStream("/WEB-INF/fonts/DejaVuSans.ttf");
            if (fontStreamRegular == null) {
                throw new ServletException("ERROR: No se encontró el archivo de fuente: /WEB-INF/fonts/DejaVuSans.ttf");
            }
            fontRegular = PDType0Font.load(new PDDocument(), fontStreamRegular, true); // true para incrustar la fuente (recomendado)

            // Cargar la fuente DejaVuSans-Bold.ttf
            InputStream fontStreamBold = getServletContext().getResourceAsStream("/WEB-INF/fonts/DejaVuSans-Bold.ttf");
            if (fontStreamBold == null) {
                System.err.println("ADVERTENCIA: No se encontró el archivo de fuente: /WEB-INF/fonts/DejaVuSans-Bold.ttf. Usando DejaVuSans.ttf para texto en negrita.");
                fontBold = fontRegular; // Fallback a la fuente regular si no se encuentra la negrita
            } else {
                fontBold = PDType0Font.load(new PDDocument(), fontStreamBold, true);
            }

        } catch (IOException e) {
            // Loguear el error y lanzar una excepción de servlet para que Tomcat lo capture
            System.err.println("Error al cargar las fuentes para PDFBox: " + e.getMessage());
            e.printStackTrace(); // Imprimir la pila de llamadas para depuración
            throw new ServletException("Error de inicialización: No se pudieron cargar las fuentes del PDF.", e);
        }
    }

  
    @Override
    public void destroy() {
        super.destroy();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReporteMinisteriosService reporteMinisteriosService = new ReporteMinisteriosService();
        String idMinisterioParam = request.getParameter("idMinisterio");

        if (idMinisterioParam == null || idMinisterioParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro 'idMinisterio' es requerido.");
            return;
        }

        try (PDDocument document = new PDDocument()) {
            int idMinisterio = Integer.parseInt(idMinisterioParam);
            ReporteMinisterios reporte = reporteMinisteriosService.generarReportePorMinisterio(idMinisterio);

            float margin = 50;
            float yStart = PDRectangle.A4.getHeight() - margin;
            float tableWidth = PDRectangle.A4.getWidth() - 2 * margin;
            float rowHeight = 20;
            float cellMargin = 5;

            // ***** HOJA DE RESUMEN *****
            PDPage resumenPage = new PDPage(PDRectangle.A4);
            document.addPage(resumenPage);
            PDPageContentStream resumenContentStream = new PDPageContentStream(document, resumenPage);
            float yPositionResumen = yStart;

            // Usar fontBold para los títulos
            resumenContentStream.setFont(fontBold, 18);
            escribirTexto(resumenContentStream, "Reporte Financiero del " + (reporte.getNombreMinisterio() != null ? reporte.getNombreMinisterio() : "Ministerio Desconocido"), margin, yPositionResumen);
            yPositionResumen -= 40;

            resumenContentStream.setFont(fontBold, 14);
            escribirTexto(resumenContentStream, "Resumen General", margin, yPositionResumen);
            yPositionResumen -= 25;

            resumenContentStream.setFont(fontRegular, 12);
            escribirTexto(resumenContentStream, String.format("Total Entradas: S/. %s", CURRENCY_FORMAT.format(reporte.getTotalEntradas())), margin, yPositionResumen);
            yPositionResumen -= 20;
            escribirTexto(resumenContentStream, String.format("Total Salidas: S/. %s", CURRENCY_FORMAT.format(reporte.getTotalSalidas())), margin, yPositionResumen);
            yPositionResumen -= 30;

            resumenContentStream.setFont(fontBold, 14);
            escribirTexto(resumenContentStream, String.format("Total en Caja: S/. %s", CURRENCY_FORMAT.format(reporte.getTotalEnCaja())), margin, yPositionResumen);
            resumenContentStream.close();


            // ***** HOJA DE OFRENDAS (ENTRADAS) *****
            if (reporte.getEntradas() != null && !reporte.getEntradas().isEmpty()) {
                PDPage ofrendasPage = new PDPage(PDRectangle.A4);
                document.addPage(ofrendasPage);
                PDPageContentStream ofrendasContentStream = new PDPageContentStream(document, ofrendasPage);
                float yPositionOfrendas = yStart;

                ofrendasContentStream.setFont(fontBold, 16);
                escribirTexto(ofrendasContentStream, "Detalle de Ofrendas (" + (reporte.getNombreMinisterio() != null ? reporte.getNombreMinisterio() : "Ministerio Desconocido") + ")", margin, yPositionOfrendas);
                yPositionOfrendas -= 30;

                float[] ofrendasColWidths = {0.15f * tableWidth, 0.25f * tableWidth, 0.20f * tableWidth, 0.40f * tableWidth};
                String[] ofrendasHeaders = {"ID", "Fecha", "Monto (S/.)", "Registrado Por"};

                yPositionOfrendas = dibujarEncabezadosTabla(ofrendasContentStream, yPositionOfrendas, margin, rowHeight, cellMargin, ofrendasColWidths, ofrendasHeaders);
                ofrendasContentStream.setFont(fontRegular, 10);

                for (Ofrenda ofrenda : reporte.getEntradas()) {
                    if (yPositionOfrendas < margin + rowHeight) {
                        ofrendasContentStream.close();
                        ofrendasPage = new PDPage(PDRectangle.A4);
                        document.addPage(ofrendasPage);
                        ofrendasContentStream = new PDPageContentStream(document, ofrendasPage);
                        yPositionOfrendas = yStart;
                        ofrendasContentStream.setFont(fontBold, 14);
                        escribirTexto(ofrendasContentStream, "Detalle de Ofrendas (" + (reporte.getNombreMinisterio() != null ? reporte.getNombreMinisterio() : "Ministerio Desconocido") + ") - Continuación", margin, yPositionOfrendas);
                        yPositionOfrendas -= 30;
                        ofrendasContentStream.setFont(fontBold, 10);
                        yPositionOfrendas = dibujarEncabezadosTabla(ofrendasContentStream, yPositionOfrendas, margin, rowHeight, cellMargin, ofrendasColWidths, ofrendasHeaders);
                        ofrendasContentStream.setFont(fontRegular, 10);
                    }

                    String fechaOfrenda = ofrenda.getFecha() != null ? ofrenda.getFecha().toString() : "";
                    String nombreUsuarioOfrenda = ofrenda.getNombreUsuarioRegistrador() != null ? ofrenda.getNombreUsuarioRegistrador() : "";

                    String[] rowData = {
                        String.valueOf(ofrenda.getId()),
                        fechaOfrenda,
                        CURRENCY_FORMAT.format(ofrenda.getMonto()),
                        nombreUsuarioOfrenda
                    };
                    dibujarFilaTabla(ofrendasContentStream, yPositionOfrendas, margin, rowHeight, cellMargin, ofrendasColWidths, rowData);
                    yPositionOfrendas -= rowHeight;
                }
                ofrendasContentStream.close();
            }


            // ***** HOJA DE SALIDAS *****
            if (reporte.getSalidas() != null && !reporte.getSalidas().isEmpty()) {
                PDPage salidasPage = new PDPage(PDRectangle.A4);
                document.addPage(salidasPage);
                PDPageContentStream salidasContentStream = new PDPageContentStream(document, salidasPage);
                float yPositionSalidas = yStart;

                salidasContentStream.setFont(fontBold, 16);
                escribirTexto(salidasContentStream, "Detalle de Salidas (" + (reporte.getNombreMinisterio() != null ? reporte.getNombreMinisterio() : "Ministerio Desconocido") + ")", margin, yPositionSalidas);
                yPositionSalidas -= 30;

                float[] salidasColWidths = {0.10f * tableWidth, 0.20f * tableWidth, 0.15f * tableWidth, 0.35f * tableWidth, 0.20f * tableWidth};
                String[] salidasHeaders = {"ID", "Fecha", "Monto (S/.)", "Descripción", "Registrado Por"};

                yPositionSalidas = dibujarEncabezadosTabla(salidasContentStream, yPositionSalidas, margin, rowHeight, cellMargin, salidasColWidths, salidasHeaders);
                salidasContentStream.setFont(fontRegular, 10);

                for (Salida salida : reporte.getSalidas()) {
                    if (yPositionSalidas < margin + rowHeight) {
                        salidasContentStream.close();
                        salidasPage = new PDPage(PDRectangle.A4);
                        document.addPage(salidasPage);
                        salidasContentStream = new PDPageContentStream(document, salidasPage);
                        yPositionSalidas = yStart;
                        salidasContentStream.setFont(fontBold, 14);
                        escribirTexto(salidasContentStream, "Detalle de Salidas (" + (reporte.getNombreMinisterio() != null ? reporte.getNombreMinisterio() : "Ministerio Desconocido") + ") - Continuación", margin, yPositionSalidas);
                        yPositionSalidas -= 30;
                        salidasContentStream.setFont(fontBold, 10);
                        yPositionSalidas = dibujarEncabezadosTabla(salidasContentStream, yPositionSalidas, margin, rowHeight, cellMargin, salidasColWidths, salidasHeaders);
                        salidasContentStream.setFont(fontRegular, 10);
                    }

                    String fechaSalida = salida.getFecha() != null ? salida.getFecha().toString() : "";
                    String descripcionSalida = salida.getDescripcion() != null ? salida.getDescripcion() : "";
                    String nombreUsuarioSalida = salida.getNombreUsuarioRegistrador() != null ? salida.getNombreUsuarioRegistrador() : "";

                    String[] rowData = {
                        String.valueOf(salida.getIdSalida()),
                        fechaSalida,
                        CURRENCY_FORMAT.format(salida.getMonto()),
                        descripcionSalida,
                        nombreUsuarioSalida
                    };
                    dibujarFilaTabla(salidasContentStream, yPositionSalidas, margin, rowHeight, cellMargin, salidasColWidths, rowData);
                    yPositionSalidas -= rowHeight;
                }
                salidasContentStream.close();
            }

            // Configurar respuesta
            String nombreArchivo = "reporte_" + (reporte.getNombreMinisterio() != null ? reporte.getNombreMinisterio().replace(" ", "_").replace("(", "").replace(")", "") : "desconocido") + ".pdf";
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");
            document.save(response.getOutputStream());

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de Ministerio inválido.");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al generar PDF para reporte de ministerio: " + e.getMessage());
            e.printStackTrace(); // Para depuración
        }
    }

    private void escribirTexto(PDPageContentStream contentStream, String texto, float x, float y) throws IOException {
        if (texto == null) {
            texto = "";
        }
       
        texto = texto.replace("\r", "");
        texto = texto.replace("\n", " "); 

        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(texto);
        contentStream.endText();
    }

    private float dibujarEncabezadosTabla(PDPageContentStream contentStream, float yPosition, float margin, float rowHeight, float cellMargin, float[] colWidths, String[] headers) throws IOException {
        contentStream.setFont(fontBold, 10);
        contentStream.setNonStrokingColor(0.85f, 0.85f, 0.85f);

        float currentX = margin;
        for(float width : colWidths) {
            contentStream.addRect(currentX, yPosition - rowHeight, width, rowHeight);
            currentX += width;
        }
        contentStream.fill();

        contentStream.setNonStrokingColor(0, 0, 0);

        currentX = margin + cellMargin;
        for (int i = 0; i < headers.length; i++) {
            escribirTexto(contentStream, headers[i], currentX, yPosition - rowHeight + cellMargin);
            currentX += colWidths[i];
        }
        contentStream.setFont(fontRegular, 10); 
        return yPosition - rowHeight;
    }

    private void dibujarFilaTabla(PDPageContentStream contentStream, float yPosition, float margin, float rowHeight, float cellMargin, float[] colWidths, String[] rowData) throws IOException {
        float currentX = margin + cellMargin;
        for (int i = 0; i < rowData.length; i++) {
            String cellText = rowData[i] != null ? rowData[i] : "";
            escribirTexto(contentStream, cellText, currentX, yPosition - rowHeight + cellMargin);
            currentX += colWidths[i];
        }
    }
}