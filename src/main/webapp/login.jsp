<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Iglesia Nazareno</title>
    <style>
        :root {
            --color-primary: #4a6fa5;
            --color-secondary: #166d67;
            --color-danger: #e74c3c;
            --color-light: #f8f9fa;
            --color-dark: #343a40;
        }
        
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f7fa;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 20px;
            background-image: url('https://img.freepik.com/fotos-premium/pao-e-vinho-com-livro-aberto-biblico-e-trigo-maduro-no-fundo-em-close-up-christian-bi_1347512-3928.jpg?ga=GA1.1.2066011695.1748986392&semt=ais_hybrid&w=740');
            background-size: cover;
            background-position: center;
            background-blend-mode: overlay;
            background-color: rgba(0, 0, 0, 0.5);
        }
        
        .login-container {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
            overflow: hidden;
            animation: fadeIn 0.5s ease-out;
        }
        
        .login-header {
            background-color: var(--color-primary);
            color: white;
            padding: 25px;
            text-align: center;
        }
        
        .login-header h2 {
            font-size: 1.8rem;
            margin-bottom: 5px;
        }
        
        .login-header p {
            font-size: 0.9rem;
            opacity: 0.9;
        }
        
        .login-body {
            padding: 25px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: var(--color-dark);
        }
        
        .form-control {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 1rem;
            transition: border 0.3s;
        }
        
        .form-control:focus {
            outline: none;
            border-color: var(--color-primary);
            box-shadow: 0 0 0 3px rgba(74, 111, 165, 0.2);
        }
        
        .btn {
            display: inline-block;
            padding: 12px 20px;
            background-color: var(--color-primary);
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            width: 100%;
        }
        
        .btn:hover {
            background-color: #3a5a80;
            transform: translateY(-2px);
        }
        
        .alert {
            padding: 12px;
            border-radius: 5px;
            margin-bottom: 20px;
            font-size: 0.9rem;
        }
        
        .alert-danger {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        
        .login-footer {
            text-align: center;
            padding: 15px;
            font-size: 0.85rem;
            color: #666;
            border-top: 1px solid #eee;
        }
        
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }
        
        @media (max-width: 480px) {
            .login-container {
                max-width: 100%;
            }
            
            .login-header, .login-body {
                padding: 20px;
            }
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="login-header">
            <h2>Iglesia Nazareno</h2>
            <p>Sistema de Tesorería</p>
        </div>
        
        <div class="login-body">
            <c:if test="${not empty error}">
                <div class="alert alert-danger">
                    ${error}
                </div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="form-group">
                    <label for="usuario">Usuario</label>
                    <input type="text" id="usuario" name="usuario" class="form-control" placeholder="Ingrese su usuario" required autofocus>
                </div>
                
                <div class="form-group">
                    <label for="contrasena">Contraseña</label>
                    <input type="password" id="contrasena" name="contrasena" class="form-control" placeholder="Ingrese su contraseña" required>
                </div>
                
                <button type="submit" class="btn">Iniciar Sesión</button>
            </form>
        </div>
        
        <div class="login-footer">
            © ${java.time.Year.now().getValue()} Iglesia Nazareno. Todos los derechos reservados.
        </div>
    </div>
</body>
</html>