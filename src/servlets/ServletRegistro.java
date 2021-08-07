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

@WebServlet("/ServletRegistro")
public class ServletRegistro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
	
    public ServletRegistro() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
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
		
		if(request.getParameter("registrar")!=null) {
			
			Usuario unuevo = new Usuario();
			UsuarioDAO udao = new UsuarioDAO();
			
			Cliente cnuevo = new Cliente();
			ClienteDAO cdao = new ClienteDAO();
			
			unuevo.setEstado(1);
			unuevo.setPass(request.getParameter("pass").toString());
			unuevo.setTipo(1);
			unuevo.setUsuario(request.getParameter("user").toString());
			
			int idU = udao.agregarUsuario(unuevo);
			
			cnuevo.setApellido(request.getParameter("apellido").toString());
			cnuevo.setNombre(request.getParameter("nombre").toString());
			cnuevo.setDni(Integer.parseInt(request.getParameter("dni").toString()));
			cnuevo.setId_usu(idU);
			cnuevo.setId_prov(Integer.parseInt(request.getParameter("provincia").toString()));
			cnuevo.setId_loc(Integer.parseInt(request.getParameter("localidad").toString()));
			cnuevo.setTelefono(request.getParameter("telefono").toString());
			
			cdao.agregarCliente(cnuevo);
			
			response.sendRedirect("login.jsp?Success=1");
			
		}
		
	}

}
