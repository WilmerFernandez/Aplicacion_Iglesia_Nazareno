<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Asistencias - Ministerio ${idMinisterio}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
            color: #333;
        }
        h1, h3 {
            color: #0056b3;
        }
        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-bottom: 20px;
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            align-items: center;
        }
        form label {
            font-weight: bold;
        }
        form input[type="date"],
        form button {
            padding: 10px;
            border-radius: 4px;
            border: 1px solid #ddd;
            font-size: 16px;
        }
        form button {
            background-color: #007bff;
            color: white;
            cursor: pointer;
            border: none;
            transition: background-color 0.3s ease;
        }
        form button:hover {
            background-color: #0056b3;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: #fff;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            border-radius: 8px;
            overflow: hidden; /* Ensures rounded corners apply to content */
        }
        table th, table td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        table th {
            background-color: #e9e9e9;
            font-weight: bold;
            color: #555;
        }
        table tbody tr:hover {
            background-color: #f1f1f1;
        }
        table tbody tr:last-child td {
            border-bottom: none;
        }
        .message {
            padding: 10px;
            background-color: #ffe08a;
            border: 1px solid #ffcc00;
            border-radius: 5px;
            margin-bottom: 15px;
            color: #5c4000;
        }
    </style>
</head>
<body>
    <h1>Lista de Asistencias de MNI  </h1>

    <p>Aquí puedes ver y filtrar las asistencias registradas de Misiones Nazarenas Internacional</p>

    <form action="listarAsistencias" method="get">
        <input type="hidden" name="idMinisterio" value="${idMinisterio != null ? idMinisterio : 3}"/>

        <label for="fechaInicio">Fecha de Inicio:</label>
        <input type="date" id="fechaInicio" name="fechaInicio" value="${param.fechaInicio}" required/>

        <label for="fechaFin">Fecha de Fin:</label>
        <input type="date" id="fechaFin" name="fechaFin" value="${param.fechaFin}" required/>

        <button type="submit">Filtrar Asistencias</button>
    </form>

    <hr/>

    <h2>Promedios de Asistencias por Rango de Fechas</h2>
    <c:if test="${promedioAdultos == null && promedioJovenes == null && promedioAdolescentes == null && promedioNinos == null && promedioTotal == null}">
        <p class="message">No se han calculado promedios. Por favor, filtra las asistencias para ver los promedios.</p>
    </c:if>
    <c:if test="${promedioTotal != null}">
        <table>
            <thead>
                <tr>
                    <th>Promedio Adultos</th>
                    <th>Promedio Jóvenes</th>
                    <th>Promedio Adolescentes</th>
                    <th>Promedio Niños</th>
                    <th>Promedio Total</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><c:out value="${promedioAdultos != null ? promedioAdultos : 'N/A'}"/></td>
                    <td><c:out value="${promedioJovenes != null ? promedioJovenes : 'N/A'}"/></td>
                    <td><c:out value="${promedioAdolescentes != null ? promedioAdolescentes : 'N/A'}"/></td>
                    <td><c:out value="${promedioNinos != null ? promedioNinos : 'N/A'}"/></td>
                    <td><c:out value="${promedioTotal != null ? promedioTotal : 'N/A'}"/></td>
                </tr>
            </tbody>
        </table>
    </c:if>

    <hr/>

    <h2>Detalle de Asistencias ${param.fechaInicio != null ? 'del ' : ''}${param.fechaInicio} ${param.fechaFin != null && param.fechaInicio != null ? 'al ' : ''} ${param.fechaFin}</h2>

    <c:if test="${empty asistencias}">
        <p class="message">No hay asistencias registradas para el rango de fechas seleccionado o para este ministerio.</p>
    </c:if>

    <c:if test="${not empty asistencias}">
        <table>
            <thead>
                <tr>
                    <th>ID Asistencia</th>
                    <th>Fecha</th>
                    <th>Adultos</th>
                    <th>Jóvenes</th>
                    <th>Adolescentes</th>
                    <th>Niños</th>
                    <th>Total Asistentes</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="asistencia" items="${asistencias}">
                    <tr>
                        <td>${asistencia.idAsistencia}</td>
                        <td>${asistencia.fecha}</td>
                        <td>${asistencia.cantidadAdultos}</td>
                        <td>${asistencia.cantidadJovenes}</td>
                        <td>${asistencia.cantidadAdolescentes}</td>
                        <td>${asistencia.cantidadNinos}</td>
                        <td>
                            ${asistencia.cantidadAdultos + asistencia.cantidadJovenes +
                              asistencia.cantidadAdolescentes + asistencia.cantidadNinos}
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>