<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <title>Registrar Diezmo</title>
    <style>
        /* Estilos generales del cuerpo */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; /* Fuente más moderna */
            background-color: #e9f2f7; /* Un color de fondo suave */
            margin: 0;
            padding: 20px;
            display: flex; /* Para centrar el contenedor */
            justify-content: center;
            align-items: flex-start; /* Alinea al inicio verticalmente */
            min-height: 100vh; /* Ocupa al menos toda la altura de la ventana */
        }

        /* Contenedor principal del formulario */
        .form-container {
            max-width: 450px; /* Ancho un poco mayor */
            background: #ffffff; /* Fondo blanco para el contenedor */
            padding: 30px; /* Más padding */
            border-radius: 10px; /* Bordes más redondeados */
            box-shadow: 0 4px 15px rgba(0,0,0,0.1); /* Sombra más pronunciada */
            width: 100%; /* Ocupa todo el ancho disponible hasta el max-width */
        }

        /* Título del formulario */
        h2 {
            margin-top: 0;
            color: #2c3e50;
            text-align: center;
            margin-bottom: 25px; /* Espacio debajo del título */
            font-size: 1.8em; /* Tamaño de fuente más grande */
            font-weight: 600; /* Un poco más de grosor */
        }

        /* Estilos para las etiquetas de los campos */
        label {
            display: block; /* Cada etiqueta en su propia línea */
            margin-top: 18px; /* Más espacio superior */
            margin-bottom: 5px; /* Espacio entre etiqueta y campo */
            font-weight: bold;
            color: #34495e; /* Color de texto más oscuro */
            font-size: 0.95em; /* Tamaño de fuente ligeramente más pequeño */
        }

        /* Estilos para campos de entrada y select */
        input[type="date"],
        input[type="number"],
        select {
            width: calc(100% - 20px); /* Ancho completo menos padding */
            padding: 12px 10px; /* Más padding interno */
            margin-top: 5px;
            border: 1px solid #c9d8e3; /* Borde más suave */
            border-radius: 6px; /* Bordes redondeados */
            box-sizing: border-box; /* Incluye padding y borde en el ancho */
            font-size: 1em; /* Tamaño de fuente legible */
            color: #333;
            transition: border-color 0.3s ease; /* Transición suave al enfocar */
        }

        input[type="date"]:focus,
        input[type="number"]:focus,
        select:focus {
            border-color: #3498db; /* Borde azul al enfocar */
            outline: none; /* Elimina el contorno por defecto */
            box-shadow: 0 0 5px rgba(52, 152, 219, 0.4); /* Sombra suave al enfocar */
        }

        /* Estilos para el botón de enviar */
        button[type="submit"] {
            margin-top: 30px; /* Más espacio superior */
            background-color: #28a745; /* Un verde más vibrante */
            color: white;
            border: none;
            padding: 15px; /* Más padding para un botón más grande */
            width: 100%;
            border-radius: 6px;
            cursor: pointer;
            font-size: 1.1em; /* Tamaño de fuente más grande */
            font-weight: bold;
            transition: background-color 0.3s ease; /* Transición suave al pasar el mouse */
        }

        button[type="submit"]:hover {
            background-color: #218838; /* Verde más oscuro al pasar el mouse */
        }

        /* Estilos para los mensajes de éxito/error */
        .message {
            margin-top: 20px;
            padding: 12px;
            border-radius: 5px;
            font-weight: bold;
            text-align: center;
        }

        .message.success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
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
        <h2>Registrar Nuevo Diezmo</h2>

        <c:if test="${not empty mensaje}">
            <div class="message success">${mensaje}</div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="message error">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/diezmo" method="post">
            <input type="hidden" name="action" value="registrar" />

            <label for="idFeligres">Feligrés:</label>
            <select id="idFeligres" name="idFeligres" required>
                <option value="">-- Seleccione un feligrés --</option>
                <c:forEach var="f" items="${listaFeligres}">
                    <option value="${f.id}">${f.nombre} ${f.apellido}</option>
                </c:forEach>
            </select>

            <label for="fecha">Fecha:</label>
            <input type="date" id="fecha" name="fecha" required />

            <label for="monto">Monto (S/.):</label>
            <input type="number" id="monto" name="monto" step="0.01" min="0" required />

            <button type="submit">Registrar Diezmo</button>
        </form>
    </div>
</body>
</html>