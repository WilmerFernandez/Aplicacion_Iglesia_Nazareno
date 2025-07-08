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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ReporteService reporteService = new ReporteService();
        String inicio = request.getParameter("fechaInicio");
        String fin = request.getParameter("fechaFin");

        try {
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

            Workbook workbook = new XSSFWorkbook();
            crearHojaDiezmos(workbook, diezmos);
            crearHojaResumen(workbook, reporte);
            crearHojaOfrendas(workbook, ofrendas);
            crearHojaSalidas(workbook, salidas);

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=reporte_financiero.xlsx");

            workbook.write(response.getOutputStream());
            workbook.close();

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al generar Excel: " + e.getMessage());
        }
    }

    private void crearHojaDiezmos(Workbook workbook, List<Diezmo> diezmos) {
        Sheet sheet = workbook.createSheet("Diezmos");

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Feligrés", "Fecha", "Monto (S/.)"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        CellStyle currencyStyle = workbook.createCellStyle();
        short currencyFormat = (short) BuiltinFormats.getBuiltinFormat("#,##0.00");
        currencyStyle.setDataFormat(currencyFormat);

        int rowNum = 1;
        for (Diezmo diezmo : diezmos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(diezmo.getId());
            row.createCell(1).setCellValue(diezmo.getNombreFeligres());
            row.createCell(2).setCellValue(diezmo.getFecha().toString());

            Cell montoCell = row.createCell(3);
            montoCell.setCellValue(diezmo.getMonto());
            montoCell.setCellStyle(currencyStyle);
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void crearHojaResumen(Workbook workbook, ReporteFinanciero reporte) {
        Sheet sheet = workbook.createSheet("Resumen Financiero");

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        CellStyle currencyStyle = workbook.createCellStyle();
        short currencyFormat = (short) BuiltinFormats.getBuiltinFormat("#,##0.00");
        currencyStyle.setDataFormat(currencyFormat);

        CellStyle boldCurrencyStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        boldCurrencyStyle.setFont(boldFont);
        boldCurrencyStyle.setDataFormat(currencyFormat);

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Concepto");
        headerRow.createCell(1).setCellValue("Monto (S/.)");
        headerRow.getCell(0).setCellStyle(headerStyle);
        headerRow.getCell(1).setCellStyle(headerStyle);

        int rowNum = 1;
        crearFilaResumen(sheet, rowNum++, "Total Diezmos", reporte.getTotalDiezmos(), currencyStyle);
        crearFilaResumen(sheet, rowNum++, "Total Ofrendas Iglesia", reporte.getTotalOfrendasIglesia(), currencyStyle);
        crearFilaResumen(sheet, rowNum++, "Total Salidas", reporte.getTotalSalidas(), currencyStyle);

        Row saldoRow = sheet.createRow(rowNum);
        saldoRow.createCell(0).setCellValue("Saldo en Caja");
        Cell saldoCell = saldoRow.createCell(1);
        saldoCell.setCellValue(reporte.getSaldoCaja());
        saldoCell.setCellStyle(boldCurrencyStyle);

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

    private void crearHojaOfrendas(Workbook workbook, List<Ofrenda> ofrendas) {
        Sheet sheet = workbook.createSheet("Ofrendas");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Fecha");
        headerRow.createCell(2).setCellValue("Monto (S/.)");

        int rowNum = 1;
        for (Ofrenda ofrenda : ofrendas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(ofrenda.getId());
            row.createCell(1).setCellValue(ofrenda.getFecha().toString());

            Cell montoCell = row.createCell(2);
            montoCell.setCellValue(ofrenda.getMonto());

            CellStyle currencyStyle = workbook.createCellStyle();
            currencyStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("#,##0.00"));
            montoCell.setCellStyle(currencyStyle);
        }

        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void crearHojaSalidas(Workbook workbook, List<Salida> salidas) {
        Sheet sheet = workbook.createSheet("Salidas");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Fecha");
        headerRow.createCell(2).setCellValue("Monto (S/.)");
        headerRow.createCell(3).setCellValue("Descripción");

        int rowNum = 1;
        for (Salida salida : salidas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(salida.getIdSalida());
            row.createCell(1).setCellValue(salida.getFecha().toString());

            Cell montoCell = row.createCell(2);
            montoCell.setCellValue(salida.getMonto());

            CellStyle currencyStyle = workbook.createCellStyle();
            currencyStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("#,##0.00"));
            montoCell.setCellStyle(currencyStyle);

            row.createCell(3).setCellValue(salida.getDescripcion());
        }

        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }
    }
}
