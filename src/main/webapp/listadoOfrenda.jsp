<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8" />
        <title>Lista de Ofrendas</title>
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f5f9fc;
                margin: 0;
                padding: 20px;
                min-height: 100vh;
            }

            .main-container {
                max-width: 1200px;
                margin: 0 auto;
                display: flex;
                flex-direction: column;
                gap: 20px;
            }

            .report-header {
                text-align: center;
                margin-bottom: 20px;
            }

            h2 {
                color: #1a4f8a;
                margin: 0;
                font-size: 2em;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 15px;
            }

            th, td {
                border: 1px solid #e0e0e0;
                padding: 12px;
                text-align: left;
            }

            th {
                background-color: #f2f7fc;
                color: #333;
                font-weight: 600;
            }

            tr:nth-child(even) {
                background-color: #f9f9f9;
            }

            .empty-message {
                text-align: center;
                padding: 20px;
                color: #666;
                font-style: italic;
            }

            .currency {
                font-family: 'Segoe UI', Tahoma;
            }
        </style>
    </head>
    <body>
        <div class="main-container">
            <div class="report-header">
                <h2>Lista de Ofrendas</h2>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Fecha</th>
                        <th>Monto</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="ofrenda" items="${listaOfrendas}">
                        <tr>
                            <td>${ofrenda.id}</td>
                            <td><fmt:formatDate value="${ofrenda.fechaAsUtilDate}" pattern="dd/MM/yyyy" /></td>
                            <td class="currency">S/. <fmt:formatNumber value="${ofrenda.monto}" 
                                                      type="currency" currencySymbol="" minFractionDigits="2" maxFractionDigits="2" /></td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty listaOfrendas}">
                        <tr><td colspan="3" class="empty-message">No hay ofrendas registradas</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </body>
</html>
