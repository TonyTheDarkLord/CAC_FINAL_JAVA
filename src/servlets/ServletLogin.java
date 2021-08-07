package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ClienteDAO;
import dao.UsuarioDAO;
import entities.Cliente;
import entities.ProductoCarrito;
import entities.Usuario;

@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	HttpSession session;
	
	private static final long serialVersionUID = 1L;
       
    public ServletLogin() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession(true);
		
		int cantCarrito = 0;
		
		try {
			// CARGO EL HTML PARA MOSTRAR SI HAY CARRITO
			ArrayList<ProductoCarrito> carrito = new ArrayList<ProductoCarrito>();
			carrito = (ArrayList<ProductoCarrito>) session.getAttribute("carrito");
			
			//RECORRO EL ARRAY Y LO MUESTRO COMO TARJETAS
			for(ProductoCarrito prodC: carrito) {
				
				cantCarrito=cantCarrito+prodC.getCantidad();
			}
			
			request.setAttribute("carritoCant", cantCarrito);
			
		} catch(Exception e) {}
		
		if(request.getParameter("logout")==null) {
		
			if(request.getParameter("user")!="" && request.getParameter("pass")!="" && request.getParameter("user")!=null && request.getParameter("pass")!=null) {
	
				UsuarioDAO udao = new UsuarioDAO();
				Usuario usuario = new Usuario();
				
				usuario = udao.validarUsuario(request.getParameter("user").toString(), request.getParameter("pass").toString());
				
				if(usuario.getId()!= -1 && usuario.getEstado() == 1) {
					
					if(usuario.getTipo() != 0) {
						Cliente cli = new Cliente();
						ClienteDAO cdao = new ClienteDAO();
						cli = cdao.obtenerUnClienteXusu(usuario.getId());
						session.setAttribute("id_cliente", cli.getId());
					}
				
					
					session.setAttribute("id_usuario", usuario.getId());
					session.setAttribute("tipo_usuario", usuario.getTipo());
					
					if(request.getParameter("redirect")==null || request.getParameter("redirect").toString() == ""){
						response.sendRedirect("cuenta.jsp");
					} else {
						response.sendRedirect(request.getParameter("redirect").toString());
					}
				} else {
					if(request.getParameter("redirect")!=null || request.getParameter("redirect").toString() != ""){
						response.sendRedirect("login.jsp?Error=1&Redirect="+request.getParameter("redirect").toString());
					} else {
						response.sendRedirect("login.jsp?Error=1");
					}
				}
				
			} else {
				if(request.getParameter("redirect")!=null || request.getParameter("redirect").toString() != ""){
					response.sendRedirect("login.jsp?Error=1&Redirect="+request.getParameter("redirect").toString());
				} else {
					response.sendRedirect("login.jsp?Error=1");
				}
			}
			
		} else {
			
			session.setAttribute("id_cliente", null);
			session.setAttribute("id_usuario", null);
			session.setAttribute("tipo_usuario", null);
			response.sendRedirect("Index.jsp");
		}
	}

}
