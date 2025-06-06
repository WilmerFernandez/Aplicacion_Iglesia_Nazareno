<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard Tesorería - Iglesia</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f7fa;
            color: #333;
        }
        
        #saludo {
            background-color: #4a6fa5;
            color: white;
            padding: 15px 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        
        #menu {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
            flex-wrap: wrap;
        }
        
        button {
            padding: 10px 15px;
            background-color: #4a6fa5;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
            transition: all 0.3s ease;
            box-shadow: 0 2px 3px rgba(0,0,0,0.1);
        }
        
        button:hover {
            background-color: #3a5a80;
            transform: translateY(-2px);
            box-shadow: 0 4px 6px rgba(0,0,0,0.15);
        }
        
        .btn-logout {
            background-color: #e74c3c !important;
            margin-left: auto;
        }
        
        .btn-logout:hover {
            background-color: #c0392b !important;
        }
        
        #contenido {
            width: 100%;
            height: 600px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: white;
            box-shadow: 0 3px 10px rgba(0,0,0,0.08);
        }
        
        .user-info {
            font-size: 1.1em;
        }
        
        .user-role {
            font-size: 0.9em;
            opacity: 0.9;
            margin-top: 5px;
        }
        
        .active-btn {
            background-color: #2c4a76 !important;
        }
    </style>
</head>
<body>

    <div id="saludo">
        <div class="user-info">
            <c:choose>
                <c:when test="${not empty sessionScope.usuarioLogueado}">
                    👋 Bienvenido, <strong>${sessionScope.usuarioLogueado.nombre}</strong>
                </c:when>
                <c:otherwise>
                    👋 Bienvenido, Invitado
                </c:otherwise>
            </c:choose>
        </div>
        <c:if test="${not empty sessionScope.usuarioLogueado}">
            <div class="user-role">
                Rol: ${sessionScope.usuarioLogueado.rol}
            </div>
        </c:if>
    </div>

    <div id="menu">
        <button onclick="cargar('diezmo', this)">💰 Registrar Diezmo</button>
        <button onclick="cargar('ofrenda', this)">💵 Registrar Ofrenda</button>
        <button onclick="cargar('salida?form=registrarSalida', this)">💸 Registrar Salidas</button>
        <button onclick="cargar('reporte', this)">📊 Reportes Financieros</button>
        <button class="btn-logout" onclick="cerrarSesion()">🚪 Cerrar sesión</button>
    </div>

    <iframe id="contenido" src="" frameborder="0"></iframe>

    <script>
        function cargar(ruta, boton) {
            document.getElementById('contenido').src = ruta;
            document.querySelectorAll('button').forEach(btn => {
                btn.classList.remove('active-btn');
            });
            boton.classList.add('active-btn');
        }
        
        function cerrarSesion() {
            if(confirm('¿Está seguro que desea cerrar la sesión?')) {
                window.location.href = 'logout'; // Ajusta esta URL
            }
        }
        
        window.onload = function() {
            document.querySelector('button').click();
        };
    </script>
</body>
</html>