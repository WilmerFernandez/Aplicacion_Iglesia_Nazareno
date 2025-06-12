<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Reporte Financiero: JNI</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            .section-title {
                background-color: #f8f9fa;
                padding: 10px;
                border-bottom: 1px solid #dee2e6;
                margin-top: 20px;
            }
            .total-summary {
                font-size: 1.2em;
                font-weight: bold;
                margin-top: 20px;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
                background-color: #e9ecef;
            }
        </style>
    </head>
    <body>
        <div class="container mt-5 text-center">
            <h1 class="mb-4">Reporte Financiero para: ${reporte.nombreMinisterio}</h1>

            <div class="total-summary text-center mb-5">
                <p>Total Entradas: <fmt:formatNumber value="${reporte.totalEntradas}" type="currency" currencySymbol="S/." maxFractionDigits="2"/></p>
                <p>Total Salidas: <fmt:formatNumber value="${reporte.totalSalidas}" type="currency" currencySymbol="S/." maxFractionDigits="2"/></p>
                <p class="h4 text-primary">Total en Caja: <fmt:formatNumber value="${reporte.totalEnCaja}" type="currency" currencySymbol="S/." maxFractionDigits="2"/></p>
            </div>

            <div class="export-buttons">
                <form action="${pageContext.request.contextPath}/exportarReporteMinisterioExcel" method="get" style="display: inline;">
                    <input type="hidden" name="idMinisterio" value="2"> <%-- ID 2 para JNI --%>
                    <button type="submit" class="btn btn-info">Exportar Reporte JNI Excel</button>
                </form>
                <form action="${pageContext.request.contextPath}/exportarReporteMinisterioPdf" method="get" style="display: inline;">
                    <input type="hidden" name="idMinisterio" value="2"> <%-- ID 2 para JNI --%>
                    <button type="submit" class="btn btn-warning">Exportar Reporte JNI PDF</button>
                </form>
            </div>

            <h3 class="section-title">Detalle de Entradas (Ofrendas)</h3>
            <c:choose>
                <c:when test="${not empty reporte.entradas}">
                    <table class="table table-striped table-hover">
                        <thead class="thead-dark">
                            <tr>
                                <th>ID Ofrenda</th>
                                <th>Fecha</th>
                                <th>Monto</th>
                                    <%-- <th>Registrado Por (ID Usuario)</th>--%>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ofrenda" items="${reporte.entradas}">
                                <tr>
                                    <td>${ofrenda.id}</td>
                                    <td><fmt:formatDate value="${ofrenda.fechaAsUtilDate}" pattern="dd/MM/yyyy"/></td>
                                    <td><fmt:formatNumber value="${ofrenda.monto}" type="currency" currencySymbol="S/." maxFractionDigits="2"/></td>
                                    <%--  <td>${ofrenda.idUsuarioRegistrador}</td> --%>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-info">No hay entradas (ofrendas) registradas para este ministerio.</div>
                </c:otherwise>
            </c:choose>

            <h3 class="section-title">Detalle de Salidas</h3>
            <c:choose>
                <c:when test="${not empty reporte.salidas}">
                    <table class="table table-striped table-hover">
                        <thead class="thead-dark">
                            <tr>
                                <th>ID Salida</th>
                                <th>Fecha</th>
                                <th>Monto</th>
                                <th>Descripción</th>

                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="salida" items="${reporte.salidas}">
                                <tr>
                                    <td>${salida.idSalida}</td>
                                    <td><fmt:formatDate value="${salida.fechaAsUtilDate}" pattern="dd/MM/yyyy"/></td>
                                    <td><fmt:formatNumber value="${salida.monto}" type="currency" currencySymbol="S/." maxFractionDigits="2"/></td>
                                    <td>${salida.descripcion}</td>

                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-info">No hay salidas registradas para este ministerio.</div>
                </c:otherwise>
            </c:choose>


        </div>
    </body>
</html>