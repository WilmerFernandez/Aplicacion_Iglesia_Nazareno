<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8" />
        <title>Reporte Financiero</title>
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

            .summary-container {
                background: white;
                border-radius: 10px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                padding: 25px;
                margin-bottom: 20px;
            }

            .summary-grid {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
                gap: 20px;
                margin-top: 20px;
            }

            .summary-card {
                background: #f0f7ff;
                border-radius: 8px;
                padding: 15px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.05);
                border-left: 4px solid #4a6fa5;
            }

            .summary-card h4 {
                margin-top: 0;
                color: #2c5f9b;
                font-size: 1.1em;
            }

            .summary-value {
                font-size: 1.4em;
                font-weight: bold;
                color: #1a4f8a;
                margin: 10px 0;
            }

            .saldo-total {
                grid-column: 1 / -1;
                background: #e6f7ff;
                text-align: center;
                padding: 20px;
                border-radius: 8px;
                margin-top: 10px;
            }

            .saldo-total .summary-value {
                font-size: 1.8em;
                color: #007bff;
            }

            .tabs-container {
                background: white;
                border-radius: 10px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                overflow: hidden;
            }

            .tabs-header {
                display: flex;
                background: #f0f7ff;
                border-bottom: 1px solid #d0e3ff;
            }

            .tab-btn {
                padding: 12px 20px;
                background: none;
                border: none;
                cursor: pointer;
                font-weight: 600;
                color: #2c5f9b;
                transition: all 0.3s;
                position: relative;
            }

            .tab-btn.active {
                background: white;
                color: #1a4f8a;
            }

            .tab-btn.active:after {
                content: '';
                position: absolute;
                bottom: -1px;
                left: 0;
                right: 0;
                height: 3px;
                background: #4a6fa5;
            }

            .tab-content {
                display: none;
                padding: 20px;
            }

            .tab-content.active {
                display: block;
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

            .message {
                padding: 15px;
                border-radius: 5px;
                margin-bottom: 20px;
                text-align: center;
            }

            .error {
                background-color: #f8d7da;
                color: #721c24;
                border: 1px solid #f5c6cb;
            }

            .export-buttons {
                margin: 20px 0;
                display: flex;
                gap: 10px;
            }

            .export-btn {
                padding: 8px 15px;
                background-color: #4a6fa5;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-weight: bold;
                display: flex;
                align-items: center;
                gap: 5px;
            }

            .export-btn.excel {
                background-color: #217346;
            }

            .export-btn.pdf {
                background-color: #d33f3f;
            }
        </style>
    </head>
    <body>
        <div class="main-container">
            <div class="report-header">
                <h2>Reporte Financiero de la Iglesia</h2>
                <p>Resumen completo de ingresos y egresos</p>
            </div>

            <!-- Formulario de búsqueda por fechas -->
            <form method="post" action="${pageContext.request.contextPath}/reporte" class="summary-container" style="display: flex; gap: 20px; flex-wrap: wrap; align-items: center;">
                <div>
                    <label for="fechaInicio"><strong>Desde:</strong></label><br />
                    <input type="date" id="fechaInicio" name="fechaInicio" value="${fechaInicio}" style="padding: 8px; border-radius: 5px; border: 1px solid #ccc;">
                </div>

                <div>
                    <label for="fechaFin"><strong>Hasta:</strong></label><br />
                    <input type="date" id="fechaFin" name="fechaFin" value="${fechaFin}" style="padding: 8px; border-radius: 5px; border: 1px solid #ccc;">
                </div>

                <div style="margin-top: 18px;">
                    <button type="submit" class="export-btn" style="background-color: #1a4f8a;">🔍 Buscar por fechas</button>
                </div>

                <c:if test="${not empty fechaInicio && not empty fechaFin}">
                    <div style="margin-top: 18px;">
                        <a href="${pageContext.request.contextPath}/reporte" class="export-btn pdf">↩ Mostrar todo</a>
                    </div>
                </c:if>
            </form>



            <c:if test="${not empty error}">
                <div class="message error">${error}</div>
            </c:if>



            <!-- Resumen Financiero en la parte superior -->
            <div class="summary-container">
                <h3>Resumen General</h3>
                <div class="summary-grid">
                    <div class="summary-card">
                        <h4>Total Diezmos</h4>
                        <div class="summary-value currency">
                            S/. <fmt:formatNumber value="${resumenFinanciero.totalDiezmos}" 
                                              type="currency" currencySymbol="" minFractionDigits="2" maxFractionDigits="2" />
                        </div>
                    </div>

                    <div class="summary-card">
                        <h4>Total Ofrendas</h4>
                        <div class="summary-value currency">
                            S/. <fmt:formatNumber value="${resumenFinanciero.totalOfrendasIglesia}" 
                                              type="currency" currencySymbol="" minFractionDigits="2" maxFractionDigits="2" />
                        </div>
                    </div>

                    <div class="summary-card">
                        <h4>Total Salidas</h4>
                        <div class="summary-value currency">
                            S/. <fmt:formatNumber value="${resumenFinanciero.totalSalidas}" 
                                              type="currency" currencySymbol="" minFractionDigits="2" maxFractionDigits="2" />
                        </div>
                    </div>

                    <div class="export-buttons">
                        <form action="${pageContext.request.contextPath}/exportarExcel" method="post">
                            <c:if test="${not empty fechaInicio}">
                                <input type="hidden" name="fechaInicio" value="${fechaInicio}" />
                            </c:if>
                            <c:if test="${not empty fechaFin}">
                                <input type="hidden" name="fechaFin" value="${fechaFin}" />
                            </c:if>
                            <button type="submit" class="export-btn excel">
                                📊 Exportar a Excel
                            </button>
                        </form>

                        <form action="${pageContext.request.contextPath}/exportarPdf" method="post">
                            <c:if test="${not empty fechaInicio}">
                                <input type="hidden" name="fechaInicio" value="${fechaInicio}" />
                            </c:if>
                            <c:if test="${not empty fechaFin}">
                                <input type="hidden" name="fechaFin" value="${fechaFin}" />
                            </c:if>
                            <button type="submit" class="export-btn pdf">
                                📄 Exportar a PDF
                            </button>
                        </form>
                    </div>


                    <div class="saldo-total">
                        <h4>Saldo en Caja</h4>
                        <div class="summary-value currency">
                            S/. <fmt:formatNumber value="${resumenFinanciero.saldoCaja}" 
                                              type="currency" currencySymbol="" minFractionDigits="2" maxFractionDigits="2" />
                        </div>
                    </div>


                </div>
            </div>

            <!-- Pestañas para las listas detalladas -->
            <div class="tabs-container">
                <div class="tabs-header">
                    <button class="tab-btn active" onclick="openTab(event, 'diezmos')">Diezmos</button>
                    <button class="tab-btn" onclick="openTab(event, 'ofrendas')">Ofrendas</button>
                    <button class="tab-btn" onclick="openTab(event, 'salidas')">Salidas</button>
                </div>



                <!-- Contenido de pestaña Diezmos -->
                <div id="diezmos" class="tab-content active">
                    <h3>Listado de Diezmos</h3>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Feligrés</th>
                                <th>Fecha</th>
                                <th>Monto</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="diezmo" items="${listaDiezmos}">
                                <tr>
                                    <td>${diezmo.id}</td>
                                    <td>${diezmo.nombreFeligres}</td>
                                    <td><fmt:formatDate value="${diezmo.fechaAsUtilDate}" pattern="dd/MM/yyyy" /></td>
                                    <td class="currency">S/. <fmt:formatNumber value="${diezmo.monto}" 
                                                      type="currency" currencySymbol="" minFractionDigits="2" maxFractionDigits="2" /></td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty listaDiezmos}">
                                <tr><td colspan="4" class="empty-message">No hay diezmos registrados</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>

                <!-- Contenido de pestaña Ofrendas -->
                <div id="ofrendas" class="tab-content">
                    <h3>Listado de Ofrendas</h3>
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

                <!-- Contenido de pestaña Salidas -->
                <div id="salidas" class="tab-content">
                    <h3>Listado de Salidas</h3>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Fecha</th>
                                <th>Monto</th>
                                <th>Descripción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="salida" items="${listaSalidas}">
                                <tr>
                                    <td>${salida.idSalida}</td>
                                    <td><fmt:formatDate value="${salida.fechaAsUtilDate}" pattern="dd/MM/yyyy" /></td>
                                    <td class="currency">S/. <fmt:formatNumber value="${salida.monto}" 
                                                      type="currency" currencySymbol="" minFractionDigits="2" maxFractionDigits="2" /></td>
                                    <td>${salida.descripcion}</td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty listaSalidas}">
                                <tr><td colspan="4" class="empty-message">No hay salidas registradas</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <script>
            function openTab(evt, tabName) {
                // Oculta todos los contenidos de pestañas
                var tabContents = document.getElementsByClassName("tab-content");
                for (var i = 0; i < tabContents.length; i++) {
                    tabContents[i].classList.remove("active");
                }

                // Desactiva todos los botones de pestañas
                var tabButtons = document.getElementsByClassName("tab-btn");
                for (var i = 0; i < tabButtons.length; i++) {
                    tabButtons[i].classList.remove("active");
                }

                // Muestra la pestaña actual y activa el botón
                document.getElementById(tabName).classList.add("active");
                evt.currentTarget.classList.add("active");
            }
        </script>
    </body>
</html>