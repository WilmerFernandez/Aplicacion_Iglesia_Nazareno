<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registro de Feligrés</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }

        .container {
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 400px;
            max-width: 90%;
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        .message {
            text-align: center;
            margin-bottom: 15px;
            padding: 10px;
            border-radius: 5px;
        }

        .message.success {
            color: green;
            background-color: #e6ffe6;
            border: 1px solid green;
        }

        .message.error {
            color: red;
            background-color: #ffe6e6;
            border: 1px solid red;
            /* Cuidado: en tu CSS original, 'error' no tenía un border,
               lo he añadido para que sea similar a 'success' */
        }

        .form-row {
            display: flex;
            flex-wrap: wrap;
            gap: 15px; /* Space between items in a row */
            margin-bottom: 15px;
        }

        .form-group {
            flex: 1; /* Allows items to grow and shrink */
            min-width: 180px; /* Minimum width before wrapping */
        }

        label {
            display: block;
            margin-bottom: 5px;
            color: #555;
            font-weight: bold;
        }

        input[type="text"],
        input[type="date"],
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box; /* Include padding and border in the element's total width and height */
            font-size: 16px;
        }

        button[type="submit"] {
            background-color: #40e0d0; /* Turquoise blue */
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 18px;
            transition: background-color 0.3s ease;
            width: 100%;
            margin-top: 20px;
        }

        button[type="submit"]:hover {
            background-color: #20b2aa; /* Slightly darker turquoise on hover */
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Registrar Nuevo Feligrés</h2>

    <c:if test="${not empty mensaje}">
        <p class="message success">${mensaje}</p>
    </c:if>

    <c:if test="${not empty error}">
        <p class="message error">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/feligres" method="post">
        <div class="form-row">
            <div class="form-group">
                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" required/>
            </div>
            <div class="form-group">
                <label for="apellido">Apellido:</label>
                <input type="text" id="apellido" name="apellido" required/>
            </div>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label for="fechaNacimiento">Fecha de Nacimiento:</label>
                <input type="date" id="fechaNacimiento" name="fechaNacimiento" required/>
            </div>
            <div class="form-group">
                <label for="estado">Estado:</label>
                <select id="estado" name="estado" required>
                    <option value="activo">Activo</option>
                    <option value="inactivo">Inactivo</option>
                </select>
            </div>
        </div>

        <%-- AÑADIDO: Campos Telefono y Direccion --%>
        <div class="form-row">
            <div class="form-group">
                <label for="telefono">Teléfono:</label>
                <input type="text" id="telefono" name="telefono" required/>
            </div>
            <div class="form-group">
                <label for="direccion">Dirección:</label>
                <input type="text" id="direccion" name="direccion" required/>
            </div>
        </div>

      
         
      

        <button type="submit">Registrar</button>
    </form>
</div>
</body>
</html>