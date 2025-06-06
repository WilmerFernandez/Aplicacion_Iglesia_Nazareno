<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Este JSP es un fragmento, no un documento HTML completo. --%>

<div class="form-container">
    <h2><i class="fas fa-users"></i> Registrar Asistencia</h2>

    <%-- **INICIO: CÓDIGO PARA MOSTRAR MENSAJES DE ÉXITO/ERROR** --%>
    <%
        String mensaje = (String) request.getAttribute("mensaje");
        String error = (String) request.getAttribute("error");
        if (mensaje != null) {
    %>
    <div class="message-success">
        <%= mensaje%>
    </div>
    <%
    } else if (error != null) {
    %>
    <div class="message-error">
        <%= error%>
    </div>
    <%
        }
    %>
    <%-- **FIN: CÓDIGO PARA MOSTRAR MENSAJES DE ÉXITO/ERROR** --%>

    <form action="<%= request.getContextPath()%>/asistencia" method="post">
        <input type="hidden" name="action" value="registrar">

        <div class="form-group">
            <label for="cantidadAdultos">Cantidad de Adultos:</label>
            <input type="number" id="cantidadAdultos" name="cantidadAdultos" min="0" required>
        </div>

        <div class="form-group">
            <label for="cantidadJovenes">Cantidad de Jóvenes:</label>
            <input type="number" id="cantidadJovenes" name="cantidadJovenes" min="0" required>
        </div>

        <div class="form-group">
            <label for="cantidadAdolescentes">Cantidad de Adolescentes:</label>
            <input type="number" id="cantidadAdolescentes" name="cantidadAdolescentes" min="0" required>
        </div>

        <div class="form-group">
            <label for="cantidadNinos">Cantidad de Niños:</label>
            <input type="number" id="cantidadNinos" name="cantidadNinos" min="0" required>
        </div>

        <button type="submit" class="btn btn-primary">Registrar Asistencia</button>
    </form>
</div>

<style>
    /* Estilos específicos para este formulario, puedes fusionarlos con tus estilos globales */
    .form-container {
        background-color: #ffffff;
        padding: 30px;
        border-radius: 8px;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        max-width: 600px;
        margin: 30px auto;
    }

    .form-container h2 {
        text-align: center;
        color: #333;
        margin-bottom: 25px;
        font-size: 26px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-family: 'Arial', sans-serif;
    }

    .form-container h2 i {
        margin-right: 10px;
        color: var(--primary-color, #2196f3);
    }

    .form-group {
        margin-bottom: 20px;
        font-family: 'Arial', sans-serif; /* Puedes cambiar 'Arial' por la fuente que desees */
    }


    .form-group label {
        display: block;
        margin-bottom: 8px;
        font-weight: bold;
        color: #555;
    }

    .form-group input[type="number"] {
        width: 100%;
        padding: 12px;
        border: 1px solid #ccc;
        border-radius: 5px;
        font-size: 16px;
        box-sizing: border-box;
    }

    .btn {
        display: block;
        width: 100%;
        padding: 15px;
        border: none;
        border-radius: 5px;
        font-size: 18px;
        font-weight: bold;
        cursor: pointer;
        transition: background-color 0.3s ease;
        text-align: center;
    }

    .btn-primary {
        background-color: var(--primary-color, #2196f3);
        color: white;
    }

    .btn-primary:hover {
        background-color: var(--primary-dark, #1976d2);
    }

    /* Estilos para los mensajes de éxito/error (puedes copiarlos de tu dashboard.jsp) */
    .message-success {
        background-color: #d4edda;
        color: #155724;
        border: 1px solid #c3e6cb;
        padding: 12px;
        margin-bottom: 20px;
        border-radius: 5px;
        text-align: center;
        font-weight: bold;
    }
    .message-error {
        background-color: #f8d7da;
        color: #721c24;
        border: 1px solid #f5c6cb;
        padding: 12px;
        margin-bottom: 20px;
        border-radius: 5px;
        text-align: center;
        font-weight: bold;
    }
</style>