package pe.edu.utp.isi.dwi.iglesia_nazareno.controller;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Ofrenda;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.ReporteMinisterios;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Salida;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.ReporteMinisteriosService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/exportarReporteMinisterioExcel")
public class ExportarReporteMinisterioExcelServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReporteMinisteriosService reporteMinisteriosService = new ReporteMinisteriosService();
        String idMinisterioParam = request.getParameter("idMinisterio");

        if (idMinisterioParam == null || idMinisterioParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro 'idMinisterio' es requerido.");
            return;
        }

        try {
            int idMinisterio = Integer.parseInt(idMinisterioParam);
            ReporteMinisterios reporte = reporteMinisteriosService.generarReportePorMinisterio(idMinisterio);

            Workbook workbook = new XSSFWorkbook();

            // Estilos
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            CellStyle currencyStyle = workbook.createCellStyle();
            short currencyFormat = (short) BuiltinFormats.getBuiltinFormat("#,##0.00");
            currencyStyle.setDataFormat(currencyFormat);

            // Hoja 1: Resumen del Ministerio
            crearHojaResumenMinisterio(workbook, reporte, headerStyle, currencyStyle);

            // Hoja 2: Detalle de Ofrendas (Entradas)
            crearHojaOfrendasMinisterio(workbook, reporte.getEntradas(), reporte.getNombreMinisterio(), headerStyle, currencyStyle);

            // Hoja 3: Detalle de Salidas
            crearHojaSalidasMinisterio(workbook, reporte.getSalidas(), reporte.getNombreMinisterio(), headerStyle, currencyStyle);

            // Configurar respuesta
            String nombreArchivo = "reporte_" + reporte.getNombreMinisterio().replace(" ", "_").replace("(", "").replace(")", "") + ".xlsx";
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");

            // Enviar archivo
            workbook.write(response.getOutputStream());
            workbook.close();

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de Ministerio inválido.");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al generar Excel para reporte de ministerio: " + e.getMessage());
            e.printStackTrace(); // Para depuración
        }
    }

    private void crearHojaResumenMinisterio(Workbook workbook, ReporteMinisterios reporte, CellStyle headerStyle, CellStyle currencyStyle) {
        Sheet sheet = workbook.createSheet("Resumen " + reporte.getNombreMinisterio());

        // Título del reporte
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Reporte Financiero del " + reporte.getNombreMinisterio());
        titleCell.setCellStyle(headerStyle);

        // Encabezados
        Row headerRow = sheet.createRow(2);
        headerRow.createCell(0).setCellValue("Concepto");
        headerRow.createCell(1).setCellValue("Monto (S/.)");
        headerRow.getCell(0).setCellStyle(headerStyle);
        headerRow.getCell(1).setCellStyle(headerStyle);

        int rowNum = 3;
        crearFilaResumen(sheet, rowNum++, "Total Entradas", reporte.getTotalEntradas(), currencyStyle);
        crearFilaResumen(sheet, rowNum++, "Total Salidas", reporte.getTotalSalidas(), currencyStyle);

        // Fila de saldo
        Row saldoRow = sheet.createRow(rowNum);
        saldoRow.createCell(0).setCellValue("Total en Caja");
        Cell saldoCell = saldoRow.createCell(1);
        saldoCell.setCellValue(reporte.getTotalEnCaja());
        saldoCell.setCellStyle(currencyStyle); // Podrías usar un estilo en negrita aquí si lo deseas

        // Autoajustar columnas
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
    }

    private void crearFilaResumen(Sheet sheet, int rowNum, String concepto, double monto, CellStyle style) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(concepto);
        Cell montoCell = row.createCell(1);
        montoCell.setCellValue(monto);
        montoCell.setCellStyle(style);
    }

    private void crearHojaOfrendasMinisterio(Workbook workbook, List<Ofrenda> ofrendas, String nombreMinisterio, CellStyle headerStyle, CellStyle currencyStyle) {
        Sheet sheet = workbook.createSheet("Ofrendas " + nombreMinisterio);

        // Crear encabezados
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Fecha", "Monto (S/.)", "Registrado Por"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Llenar datos
        int rowNum = 1;
        for (Ofrenda ofrenda : ofrendas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(ofrenda.getId());
            row.createCell(1).setCellValue(ofrenda.getFecha().toString());
            Cell montoCell = row.createCell(2);
            montoCell.setCellValue(ofrenda.getMonto());
            montoCell.setCellStyle(currencyStyle);
            row.createCell(3).setCellValue(ofrenda.getNombreUsuarioRegistrador()); // Asumiendo que Ofrenda tiene este campo
        }

        // Autoajustar columnas
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void crearHojaSalidasMinisterio(Workbook workbook, List<Salida> salidas, String nombreMinisterio, CellStyle headerStyle, CellStyle currencyStyle) {
        Sheet sheet = workbook.createSheet("Salidas " + nombreMinisterio);

        // Crear encabezados
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Fecha", "Monto (S/.)", "Descripción", "Registrado Por"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Llenar datos
        int rowNum = 1;
        for (Salida salida : salidas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(salida.getIdSalida());
            row.createCell(1).setCellValue(salida.getFecha().toString());
            Cell montoCell = row.createCell(2);
            montoCell.setCellValue(salida.getMonto());
            montoCell.setCellStyle(currencyStyle);
            row.createCell(3).setCellValue(salida.getDescripcion());
            row.createCell(4).setCellValue(salida.getNombreUsuarioRegistrador()); // Asumiendo que Salida tiene este campo
        }

        // Autoajustar columnas
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }
}