package pe.edu.utp.isi.dwi.iglesia_nazareno.controller;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Diezmo;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.ReporteFinanciero;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.ReporteService;
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Ofrenda;
import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Salida;

@WebServlet("/exportarExcel")
public class ExportarExcelServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReporteService reporteService = new ReporteService();

        try {
           
            List<Diezmo> diezmos = reporteService.listarTodosDiezmos();
            List<Ofrenda> ofrendas = reporteService.listarOfrendasPorMinisterio();
            List<Salida> salidas = reporteService.listarSalidasPorMinisterio();
            ReporteFinanciero reporte = reporteService.generarReporteFinancieroConsolidado();

            // Crear libro Excel
            Workbook workbook = new XSSFWorkbook();

            // Hoja 1: Listado de Diezmos
            crearHojaDiezmos(workbook, diezmos);

            // Hoja 2: Resumen Financiero
            crearHojaResumen(workbook, reporte);

            // Hoja 3: Listado de Ofrendas
            crearHojaOfrendas(workbook, ofrendas);

            // Hoja 4: Listado de Salidas
            crearHojaSalidas(workbook, salidas);

            // Configurar respuesta
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=reporte_financiero.xlsx");

            // Enviar archivo
            workbook.write(response.getOutputStream());
            workbook.close();

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al generar Excel: " + e.getMessage());
        }
    }

    // Métodos nuevos para ofrendas y salidas
    private void crearHojaOfrendas(Workbook workbook, List<Ofrenda> ofrendas) {
        Sheet sheet = workbook.createSheet("Ofrendas");

        // Crear encabezados
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Fecha");
        headerRow.createCell(2).setCellValue("Monto (S/.)");

        // Llenar datos
        int rowNum = 1;
        for (Ofrenda ofrenda : ofrendas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(ofrenda.getId());
            row.createCell(1).setCellValue(ofrenda.getFecha().toString());

            Cell montoCell = row.createCell(2);
            montoCell.setCellValue(ofrenda.getMonto());

            // Formato de moneda
            CellStyle currencyStyle = workbook.createCellStyle();
            currencyStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("#,##0.00"));
            montoCell.setCellStyle(currencyStyle);
        }

        // Autoajustar columnas
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void crearHojaSalidas(Workbook workbook, List<Salida> salidas) {
        Sheet sheet = workbook.createSheet("Salidas");

        // Crear encabezados
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Fecha");
        headerRow.createCell(2).setCellValue("Monto (S/.)");
        headerRow.createCell(3).setCellValue("Descripción");

        // Llenar datos
        int rowNum = 1;
        for (Salida salida : salidas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(salida.getIdSalida());
            row.createCell(1).setCellValue(salida.getFecha().toString());

            Cell montoCell = row.createCell(2);
            montoCell.setCellValue(salida.getMonto());

            // Formato de moneda
            CellStyle currencyStyle = workbook.createCellStyle();
            currencyStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("#,##0.00"));
            montoCell.setCellStyle(currencyStyle);

            row.createCell(3).setCellValue(salida.getDescripcion());
        }

        // Autoajustar columnas
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void crearHojaDiezmos(Workbook workbook, List<Diezmo> diezmos) {
        Sheet sheet = workbook.createSheet("Diezmos");

        // Estilo para encabezados
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // Crear encabezados
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Feligrés", "Fecha", "Monto (S/.)"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Crear estilo para moneda
        CellStyle currencyStyle = workbook.createCellStyle();
        short currencyFormat = (short) BuiltinFormats.getBuiltinFormat("#,##0.00");
        currencyStyle.setDataFormat(currencyFormat);

        // Llenar datos
        int rowNum = 1;
        for (Diezmo diezmo : diezmos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(diezmo.getId());
            row.createCell(1).setCellValue(diezmo.getNombreFeligres());
            row.createCell(2).setCellValue(diezmo.getFecha().toString());

            Cell montoCell = row.createCell(3);
            montoCell.setCellValue(diezmo.getMonto());
            montoCell.setCellStyle(currencyStyle); // Aplicar estilo de moneda
        }

        // Autoajustar columnas
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void crearHojaResumen(Workbook workbook, ReporteFinanciero reporte) {
        Sheet sheet = workbook.createSheet("Resumen Financiero");

        // Estilos
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // Estilo para moneda normal
        CellStyle currencyStyle = workbook.createCellStyle();
        short currencyFormat = (short) BuiltinFormats.getBuiltinFormat("#,##0.00");
        currencyStyle.setDataFormat(currencyFormat);

        // Estilo para moneda en negrita
        CellStyle boldCurrencyStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        boldCurrencyStyle.setFont(boldFont);
        boldCurrencyStyle.setDataFormat(currencyFormat); // Usar el mismo formato numérico

        // Encabezados
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Concepto");
        headerRow.createCell(1).setCellValue("Monto (S/.)");

        // Aplicar estilos a encabezados
        headerRow.getCell(0).setCellStyle(headerStyle);
        headerRow.getCell(1).setCellStyle(headerStyle);

        // Datos del reporte
        int rowNum = 1;
        crearFilaResumen(sheet, rowNum++, "Total Diezmos", reporte.getTotalDiezmos(), currencyStyle);
        crearFilaResumen(sheet, rowNum++, "Total Ofrendas Iglesia", reporte.getTotalOfrendasIglesia(), currencyStyle);
        crearFilaResumen(sheet, rowNum++, "Total Salidas", reporte.getTotalSalidas(), currencyStyle);

        // Fila de saldo
        Row saldoRow = sheet.createRow(rowNum);
        saldoRow.createCell(0).setCellValue("Saldo en Caja");
        Cell saldoCell = saldoRow.createCell(1);
        saldoCell.setCellValue(reporte.getSaldoCaja());
        saldoCell.setCellStyle(boldCurrencyStyle);

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

}
