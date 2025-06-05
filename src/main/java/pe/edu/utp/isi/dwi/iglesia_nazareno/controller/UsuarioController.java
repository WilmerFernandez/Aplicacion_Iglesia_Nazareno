package pe.edu.utp.isi.dwi.iglesia_nazareno.controller;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Usuario;
import pe.edu.utp.isi.dwi.iglesia_nazareno.services.UsuarioService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet para gestionar operaciones relacionadas con Usuarios:
 * registro y autenticación.
 */
@WebServlet(name = "UsuarioController", urlPatterns = {"/usuario"})
public class UsuarioController extends HttpServlet {

    private UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        super.init();
        usuarioService = new UsuarioService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("registro".equalsIgnoreCase(action)) {
            // JSP en la raíz Web Pages
            request.getRequestDispatcher("/registroUsuario.jsp").forward(request, response);
        } else if ("login".equalsIgnoreCase(action)) {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/usuario?action=login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("registro".equalsIgnoreCase(action)) {
            registrarUsuario(request, response);
        } else if ("login".equalsIgnoreCase(action)) {
            autenticarUsuario(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/usuario?action=login");
        }
    }

    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String correo = request.getParameter("correo");
            String usuario = request.getParameter("usuario");
            String contrasena = request.getParameter("contrasena");
            String rol = request.getParameter("rol");

            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setApellido(apellido);
            nuevoUsuario.setCorreo(correo);
            nuevoUsuario.setUsuario(usuario);
            nuevoUsuario.setContrasena(contrasena);
            nuevoUsuario.setRol(rol);
            nuevoUsuario.setEstado("activo");

            boolean registrado = usuarioService.registrarUsuario(nuevoUsuario);

            if (registrado) {
                request.setAttribute("mensaje", "Registro exitoso, por favor inicie sesión.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "No se pudo registrar el usuario.");
                request.getRequestDispatcher("/registroUsuario.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            request.setAttribute("error", "Error: " + ex.getMessage());
            request.getRequestDispatcher("/registroUsuario.jsp").forward(request, response);
        }
    }

    private void autenticarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuarioLogin = request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");

        Usuario usuario = usuarioService.autenticar(usuarioLogin, contrasena);

        if (usuario != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", usuario);
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            request.setAttribute("error", "Usuario o contraseña incorrectos.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
