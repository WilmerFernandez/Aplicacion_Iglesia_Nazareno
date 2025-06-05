package pe.edu.utp.isi.dwi.iglesia_nazareno.controller;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Usuario;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.UsuarioService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        usuarioService = new UsuarioService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Mostrar la página de login
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");

        Usuario user = usuarioService.autenticar(usuario, contrasena);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", user);

            // Verifica el rol del usuario y redirige al dashboard correspondiente
            String rol = user.getRol();  // Asumiendo que tienes un método getRol() en tu clase Usuario

            switch (rol) {
                case "pastor":
                    response.sendRedirect(request.getContextPath() + "/dashboardTesoreriaPastor.jsp");
                    break;
                case "tesorero":
                    response.sendRedirect(request.getContextPath() + "/dashboardTesoreria.jsp");
                    break;
                case "tesoreroJNI":
                    response.sendRedirect(request.getContextPath() + "/dashboardTesoreriaJNI.jsp");
                    break;
                case "tesoreroMNI":
                    response.sendRedirect(request.getContextPath() + "/dashboardTesoreriaMNI.jsp");
                    break;
                case "tesoreroDNI":
                    response.sendRedirect(request.getContextPath() + "/dashboardTesoreriaDNI.jsp");
                    break;
                case "secretario":
                    response.sendRedirect(request.getContextPath() + "/dashboardSecretaria.jsp");
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                    break;
            }

        } else {
            request.setAttribute("error", "Usuario o contraseña incorrectos.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

}
