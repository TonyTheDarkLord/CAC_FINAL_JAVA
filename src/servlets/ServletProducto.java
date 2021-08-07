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
import dao.MarcaDAO;
import dao.ProductoDAO;
import entities.Categoria;
import entities.Producto;
import entities.ProductoCarrito;

@WebServlet("/ServletProducto")
public class ServletProducto extends HttpServlet {
	HttpSession session;
	private static final long serialVersionUID = 1L;
       
    public ServletProducto() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession(true);
		
		ArrayList<ProductoCarrito> carrito = new ArrayList<ProductoCarrito>();
		int cantCarrito = 0;
		String nombreCategoria = null;
		String nombreMarca = null;
		Producto prod = new Producto();
		ArrayList<Producto> prodsRel = new ArrayList<Producto>();
		
		ProductoDAO pdao = new ProductoDAO();
		MarcaDAO mdao = new MarcaDAO();
		CategoriaDAO cdao = new CategoriaDAO();
		 
		prod = pdao.obtenerUnProducto(Integer.parseInt(request.getParameter("id")));
		
		//System.out.println(prod.getId());
		
		if(prod.getId() == -1) {
			RequestDispatcher rd = request.getRequestDispatcher("ServletBuscar?Error=1");
			rd.forward(request, response);
		}else {
			
			try {
				int nuevaCant = 0;
				
				// BUSCO SI YA HAY UN CARRITO
				carrito = (ArrayList<ProductoCarrito>) session.getAttribute("carrito");
				for(ProductoCarrito prodC:carrito) {
					cantCarrito=cantCarrito+prodC.getCantidad();
					if(prodC.getId() == prod.getId()) {
						// SI LO ENCUENTRO CAMBIO LA CANTIDAD DE STOCK
						nuevaCant = prod.getStock() - prodC.getCantidad();
						prod.setStock(nuevaCant);
					}
				}
			}
			catch(Exception e) {}
			
			prodsRel = pdao.obtenerProductosRel(prod);
			nombreCategoria = cdao.obtenerNombreCat(prod.getIdCat());
			nombreMarca = mdao.obtenerNombreMarca(prod.getIdMarca());
	
			ArrayList<Categoria> todasCats = new ArrayList<Categoria>();
			CategoriaDAO daoCat = new CategoriaDAO();
			todasCats = daoCat.obtenerCategorias();
			String categorias ="<ul class=\"dropdown-menu dropdown-menu-dark\" aria-labelledby=\"navbarDropdown\">";
			
			for(Categoria categoria: todasCats) {
				
				categorias+="<li><a class=\"dropdown-item\" href=\"catalogo.jsp?idCat="+ categoria.getId() +"\" >" + categoria.getDescripcion() + "</a></li>";
				
			}
			
			categorias+="</ul>";
			
			request.setAttribute("categorias", categorias);
			request.setAttribute("marca", nombreMarca);
			request.setAttribute("categoria", nombreCategoria);
			request.setAttribute("prodsRel", prodsRel);
			request.setAttribute("prod", prod);
			request.setAttribute("carritoCant", cantCarrito);
			
			RequestDispatcher rd = request.getRequestDispatcher("producto.jsp");
			rd.forward(request, response);
		}
	}

}
