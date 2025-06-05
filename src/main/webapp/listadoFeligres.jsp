<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8" />
        <title>Lista de Feligreses</title>
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

            /* --- NUEVOS ESTILOS PARA LA BÚSQUEDA --- */
            .search-form {
                background-color: #ffffff;
                padding: 15px 20px;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.05);
                display: flex;
                gap: 10px;
                align-items: center;
                margin-bottom: 20px; /* Espacio antes de la tabla */
            }

            .search-form label {
                font-weight: 600;
                color: #333;
            }

            .search-form input[type="text"] {
                flex-grow: 1; /* Permite que el input ocupe el espacio disponible */
                padding: 10px 12px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 1em;
            }

            .search-form button {
                padding: 10px 15px;
                background-color: #4CAF50; /* Color verde para el botón Buscar */
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 1em;
                transition: background-color 0.3s ease;
            }

            .search-form button:hover {
                background-color: #45a049;
            }
            
            /* Estilo para el botón Limpiar */
            .search-form button.clear-button {
                background-color: #f44336; /* Color rojo para Limpiar */
            }

            .search-form button.clear-button:hover {
                background-color: #da190b;
            }
            /* --- FIN NUEVOS ESTILOS --- */


            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 15px;
                background-color: #ffffff; /* Fondo blanco para la tabla */
                box-shadow: 0 2px 4px rgba(0,0,0,0.05);
                border-radius: 8px;
                overflow: hidden; /* Para que los bordes redondeados se apliquen bien */
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
        </style>
    </head>
    <body>
    <div class="main-container">
        <div class="report-header">
            <h2>Lista de Feligreses</h2>
        </div>

        <div class="search-form">
            <form action="listaFeligreses" method="get" style="display: flex; gap: 10px; width: 100%;">
                <label for="nombreBusqueda">Buscar por Nombre:</label>
                <input type="text" id="nombreBusqueda" name="nombreBusqueda" 
                       value="${not empty nombreBusqueda ? nombreBusqueda : ''}" 
                       placeholder="Ingrese el nombre a buscar"/>
                <button type="submit">Buscar</button>
                <c:if test="${not empty nombreBusqueda}">
                     <button type="button" class="clear-button" onclick="window.location.href='listaFeligreses'">Limpiar</button>
                </c:if>
            </form>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Fecha de Nacimiento</th>
                    <th>Estado</th>
                    <th>Telefono</th> <!-- Nuevo encabezado para Telefono -->
                    <th>Direccion</th> <!-- Nuevo encabezado para Direccion -->
                
                    
                </tr>
            </thead>
            <tbody>
                <c:forEach var="feligres" items="${listaFeligreses}">
                    <tr>
                        <td>${feligres.id}</td>
                        <td>${feligres.nombre}</td>
                        <td>${feligres.apellido}</td>
                        <td><fmt:formatDate value="${feligres.fechaNacimiento}" pattern="dd/MM/yyyy" /></td>
                        <td>${feligres.estado}</td>
                        <td>${feligres.telefono}</td> <!-- Mostrar el teléfono -->
                        <td>${feligres.direccion}</td> <!-- Mostrar la dirección -->
                    
                     
                    </tr>
                </c:forEach>
                <c:if test="${empty listaFeligreses}">
                    <tr><td colspan="8" class="empty-message">No se encontraron feligreses con el nombre especificado.</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>

</html>
