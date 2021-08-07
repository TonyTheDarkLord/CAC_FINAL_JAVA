package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CategoriaDAO;
import entities.Categoria;
import entities.ProductoCarrito;

@WebServlet("/ServletIndex")
public class ServletIndex extends HttpServlet {
	HttpSession session;
	private static final long serialVersionUID = 1L;
       
    public ServletIndex() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession(true);
		ArrayList<Categoria> todasCats = new ArrayList<Categoria>();
		CategoriaDAO daoCat = new CategoriaDAO();
		todasCats = daoCat.obtenerCategorias();
		int cantCarrito = 0;
		
		try {
			// CARGO EL HTML PARA MOSTRAR SI HAY CARRITO
			ArrayList<ProductoCarrito> carrito = new ArrayList<ProductoCarrito>();
			carrito = (ArrayList<ProductoCarrito>) session.getAttribute("carrito");
			
			//RECORRO EL ARRAY Y LO MUESTRO COMO TARJETAS
			for(ProductoCarrito prodC: carrito) {
				
				cantCarrito=cantCarrito+prodC.getCantidad();
			}
		} catch(Exception e) {}
		
		String categorias ="<ul class=\"dropdown-menu dropdown-menu-dark\" aria-labelledby=\"navbarDropdown\">";
		
		for(Categoria categoria: todasCats) {
			
			categorias+="<li><a class=\"dropdown-item\" href=\"catalogo.jsp?idCat="+ categoria.getId() +"\" >" + categoria.getDescripcion() + "</a></li>";
			
		}
		
		categorias+="</ul>";
		
		request.setAttribute("categorias", categorias);
		request.setAttribute("carritoCant", cantCarrito);
		
		RequestDispatcher rd = request.getRequestDispatcher("Index.jsp");
		rd.forward(request, response);
	}

}
