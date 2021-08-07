package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UsuarioDAO;
import entities.Usuario;

@WebServlet("/ServletCambioPassword")
public class ServletCambioPassword extends HttpServlet {
	HttpSession session;
	private static final long serialVersionUID = 1L;
       
    public ServletCambioPassword() {
        super();
        
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession(true);
		
		String contrasenaVieja = request.getParameter("old_pass");
		String contrasenaNueva = request.getParameter("new_pass");
		
		UsuarioDAO udao = new UsuarioDAO();
		Usuario usuario = new Usuario();
		
		usuario = udao.validarUsuarioXID(session.getAttribute("id_usuario").toString(), contrasenaVieja);
		
		if(usuario.getId()!= -1 ) {
			response.getWriter().append(udao.cambiarContrasenaUsuario(session.getAttribute("id_usuario").toString(),contrasenaNueva));
		} else {
			response.getWriter().append("error");
		}
		
		
	}

}
