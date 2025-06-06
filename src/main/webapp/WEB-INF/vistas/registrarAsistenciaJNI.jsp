<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="asistencia-form">
    <h2><i class="fas fa-users"></i> Registrar Asistencia JNI</h2>

    <div id="messageDisplayArea">
        <% String mensaje = (String) request.getAttribute("mensaje");
           String error = (String) request.getAttribute("error");
           if (mensaje != null) { %>
            <div class="message-success">✓ <%= mensaje %></div>
        <% } else if (error != null) { %>
            <div class="message-error">✗ <%= error %></div>
        <% } %>
    </div>

    <form action="<%= request.getContextPath()%>/asistencia" method="post">
        <input type="hidden" name="action" value="registrar">
        <input type="hidden" name="idMinisterioForm" value="2">
        <input type="hidden" name="jspName" value="registrarAsistencia">

        <div class="form-row">
            <div class="form-group">
                <label>Adultos</label>
                <input type="number" name="cantidadAdultos" min="0" required>
                <div class="input-description">Mayores de 30 años</div>
            </div>
            <div class="form-group">
                <label>Jóvenes</label>
                <input type="number" name="cantidadJovenes" min="0" required>
                <div class="input-description">18 a 30 años</div>
            </div>
        </div>
        
        <div class="form-row">
            <div class="form-group">
                <label>Adolescentes</label>
                <input type="number" name="cantidadAdolescentes" min="0" required>
                <div class="input-description">12 a 17 años</div>
            </div>
            <div class="form-group">
                <label>Niños</label>
                <input type="number" name="cantidadNinos" min="0" required>
                <div class="input-description">Menores de 12 años</div>
            </div>
        </div>

        <button type="submit" class="btn">Registrar Asistencia</button>
    </form>
</div>

<style>
.asistencia-form {
    background: #fff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    max-width: 500px;
    margin: 20px auto;
    font-family: 'Open Sans', sans-serif;
}
.asistencia-form h2 {
    text-align: center;
    margin: 0 0 20px;
    color: #333;
}
.form-row {
    display: flex;
    gap: 15px;
    margin-bottom: 15px;
}
.form-group {
    flex: 1;
}
.form-group label {
    display: block;
    margin-bottom: 5px;
    font-weight: 600;
}
.form-group input {
    width: 100%;
    padding: 8px;
    border: 1px solid #ddd;
    border-radius: 4px;
}
.input-description {
    font-size: 12px;
    color: #666;
    margin-top: 3px;
}
.btn {
    width: 100%;
    padding: 10px;
    background: #2196f3;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    margin-top: 10px;
}
.message-success, .message-error {
    padding: 10px;
    margin-bottom: 15px;
    border-radius: 4px;
    text-align: center;
}
.message-success {
    background: #d4edda;
    color: #155724;
}
.message-error {
    background: #f8d7da;
    color: #721c24;
}
</style>