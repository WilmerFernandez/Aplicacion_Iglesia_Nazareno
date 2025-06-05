package pe.edu.utp.isi.dwi.iglesia_nazareno.filter;

import pe.edu.utp.isi.dwi.iglesia_nazareno.model.Usuario;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/dashboardTesoreria", "/diezmo", "/ofrenda", "/salida"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        Usuario user = (Usuario) req.getSession().getAttribute("usuarioLogueado");

        if (user == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        chain.doFilter(request, response);
    }
}
