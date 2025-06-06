<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <title>Registrar Ofrenda General</title> <%-- Título específico --%>
    <style>
        /* ... Tu CSS existente, lo puedes poner en un archivo CSS externo y enlazarlo para DRY ... */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #e6f7ff;
            margin: 0;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            min-height: 100vh;
        }

        .form-container {
            max-width: 450px;
            background: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            width: 100%;
        }

        h2 {
            margin-top: 0;
            color: #1a4f8a;
            text-align: center;
            margin-bottom: 25px;
            font-size: 1.8em;
            font-weight: 600;
        }

        label {
            display: block;
            margin-top: 18px;
            margin-bottom: 5px;
            font-weight: bold;
            color: #2c5f9b;
            font-size: 0.95em;
        }

        input[type="date"],
        input[type="number"] {
            width: calc(100% - 20px);
            padding: 12px 10px;
            margin-top: 5px;
            border: 1px solid #91c1ea;
            border-radius: 6px;
            box-sizing: border-box;
            font-size: 1em;
            color: #333;
            transition: border-color 0.3s ease;
        }

        input[type="date"]:focus,
        input[type="number"]:focus {
            border-color: #007bff;
            outline: none;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.4);
        }

        button[type="submit"] {
            margin-top: 30px;
            background-color: #007bff;
            color: white;
            border: none;
            padding: 15px;
            width: 100%;
            border-radius: 6px;
            cursor: pointer;
            font-size: 1.1em;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }

        button[type="submit"]:hover {
            background-color: #0056b3;
        }

        .message-container {
            min-height: 50px;
            margin-bottom: 20px;
            box-sizing: border-box;
        }

        .message {
            padding: 12px;
            border-radius: 5px;
            font-weight: bold;
            text-align: center;
        }

        .message.success {
            background-color: #d1ecf1;
            color: #0c5460;
            border: 1px solid #bee5eb;
        }

        .message.error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Registrar Ofrenda General</h2>

        <div class="message-container">
            <c:if test="${not empty mensaje}">
                <div class="message success">${mensaje}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="message error">${error}</div>
            </c:if>
        </div>

        <form action="${pageContext.request.contextPath}/ofrenda" method="post">
            <input type="hidden" name="action" value="registrar" />
            
            <%-- CAMBIOS CLAVE PARA OFRENDA GENERAL (IGLESIA): --%>
            <input type="hidden" name="idMinisterioForm" value="1" /> <%-- ID para Iglesia (General) --%>
            <input type="hidden" name="jspName" value="registrarOfrenda" /> <%-- El nombre del JSP --%>
            <%-- FIN CAMBIOS CLAVE --%>

            <label for="fecha">Fecha:</label>
            <input type="date" id="fecha" name="fecha" required />

            <label for="monto">Monto (S/.):</label>
            <input type="number" id="monto" name="monto" step="0.01" min="0" required />

            <button type="submit">Registrar Ofrenda</button>
        </form>
    </div>
</body>
</html>