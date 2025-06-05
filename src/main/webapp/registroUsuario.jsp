<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro de Usuario - Iglesia del Nazareno</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
        /* Variables CSS para colores y sombras */
        :root {
            --primary-color: #4CAF50;
            --primary-dark-color: #45a049;
            --background-color: #eef1f5;
            --container-bg-color: #ffffff;
            --text-color: #333;
            --border-color: #ccc;
            --success-color: #28a745;
            --error-color: #dc3545;
            --box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        body {
            font-family: 'Roboto', sans-serif;
            background-color: var(--background-color);
            margin: 0;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            box-sizing: border-box;
        }

        .container {
            width: 100%;
            max-width: 450px;
            background: var(--container-bg-color);
            padding: 30px;
            border-radius: 10px;
            box-shadow: var(--box-shadow);
            box-sizing: border-box;
        }

        h2 {
            text-align: center;
            color: var(--text-color);
            margin-bottom: 25px;
            font-weight: 700;
        }

        label {
            display: block;
            margin-top: 15px;
            margin-bottom: 5px;
            color: var(--text-color);
            font-size: 0.95em;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"],
        select {
            width: calc(100% - 20px); /* Ajuste para padding */
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid var(--border-color);
            border-radius: 5px;
            font-size: 1em;
            box-sizing: border-box; /* Incluye padding y border en el ancho */
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
        }

        input[type="text"]:focus,
        input[type="email"]:focus,
        input[type="password"]:focus,
        select:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 3px rgba(76, 175, 80, 0.2);
            outline: none;
        }

        .btn {
            margin-top: 20px;
            background-color: var(--primary-color);
            color: white;
            border: none;
            padding: 12px 20px;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
            font-size: 1.1em;
            font-weight: 700;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        .btn:hover {
            background-color: var(--primary-dark-color);
            transform: translateY(-2px);
        }

        .btn:active {
            transform: translateY(0);
        }

        .message {
            color: var(--success-color);
            background-color: #e6ffed;
            border: 1px solid var(--success-color);
            padding: 10px;
            border-radius: 5px;
            text-align: center;
            margin-bottom: 20px;
            font-weight: bold;
        }

        .error {
            color: var(--error-color);
            background-color: #ffe6e6;
            border: 1px solid var(--error-color);
            padding: 10px;
            border-radius: 5px;
            text-align: center;
            margin-bottom: 20px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Registrar Nuevo Usuario</h2>

    <%-- Mostrar mensaje de éxito --%>
    <c:if test="${not empty mensaje}">
        <p class="message">${mensaje}</p>
    </c:if>

    <%-- Mostrar mensaje de error --%>
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/usuario?action=registro" method="post">
        <%-- Cambié la URL para que vaya al servlet UsuarioController con acción registro --%>

        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" value="${param.nombre}" required autofocus/>

        <label for="apellido">Apellido:</label>
        <input type="text" id="apellido" name="apellido" value="${param.apellido}" required/>

        <label for="correo">Correo Electrónico:</label>
        <input type="email" id="correo" name="correo" value="${param.correo}" required/>

        <label for="usuario">Nombre de Usuario:</label>
        <input type="text" id="usuario" name="usuario" value="${param.usuario}" required/>

        <label for="contrasena">Contraseña:</label>
        <input type="password" id="contrasena" name="contrasena" required/>

        <label for="rol">Rol:</label>
        <select id="rol" name="rol" required>
            <option value="">--Seleccione un rol--</option>
            <option value="admin" <c:if test="${param.rol eq 'admin'}">selected</c:if>>Administrador</option>
            <option value="tesorero" <c:if test="${param.rol eq 'tesorero'}">selected</c:if>>Tesorero</option>
            <option value="tesoreroJNI" <c:if test="${param.rol eq 'tesoreroJNI'}">selected</c:if>>Tesorero JNI</option>
            <option value="tesoreroMNI" <c:if test="${param.rol eq 'tesoreroMNI'}">selected</c:if>>Tesorero MNI</option>
            <option value="tesoreroDNI" <c:if test="${param.rol eq 'tesoreroDNI'}">selected</c:if>>Tesorero DNI</option>
            <option value="pastor" <c:if test="${param.rol eq 'pastor'}">selected</c:if>>Pastor</option>
            <option value="secretario" <c:if test="${param.rol eq 'secretario'}">selected</c:if>>Secretario</option>
        </select>

        <button type="submit" class="btn">Registrar Usuario</button>
    </form>
</div>
</body>
</html>