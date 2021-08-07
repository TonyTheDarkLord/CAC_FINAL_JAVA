package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CategoriaDAO;
import dao.ProductoDAO;
import entities.Categoria;
import entities.Producto;
import entities.ProductoCarrito;

@WebServlet("/ServletCarrito")
public class ServletCarrito extends HttpServlet {
	HttpSession session;
	private static final long serialVersionUID = 1L;
       

    public ServletCarrito() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}


	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession(true);
		
		ArrayList<ProductoCarrito> carrito = new ArrayList<ProductoCarrito>();
		String htmlCarrito = "";
		String accion = "";
		int cantCarrito = 0;
		
		try {
		
			accion = request.getParameter("action");

			if(accion.matches("remover") && request.getParameter("id_prod")!=null) {
				
				int pos=0;
			    int ubicacion = 0;
				carrito = (ArrayList<ProductoCarrito>) session.getAttribute("carrito");
				
				for(ProductoCarrito prodC:carrito) {
					
					if(prodC.getId() == Integer.parseInt(request.getParameter("id_prod"))) {
						ubicacion = pos;
					}
					pos++;
				}
				
				carrito.remove(ubicacion);
				session.setAttribute("carrito", carrito);
			}
		
		} catch(Exception e){}
		
		try {
			
			accion = request.getParameter("action");
			
			Producto prod = new Producto();
			ProductoDAO pdao = new ProductoDAO();
			
			// BUSCO QUE EXISTA EL PRODUCTO
			prod = pdao.obtenerUnProducto(Integer.parseInt(request.getParameter("id_prod")));

			if(accion.matches("aumentar") && request.getParameter("id_prod")!=null) {
				
				carrito = (ArrayList<ProductoCarrito>) session.getAttribute("carrito");
				
				for(ProductoCarrito prodC:carrito) {
					if(prodC.getId() == Integer.parseInt(request.getParameter("id_prod"))) {
						if(prodC.getCantidad() < prod.getStock()) {
							prodC.setCantidad(prodC.getCantidad()+1);
						}
					}
				}
				
				session.setAttribute("carrito", carrito);
				
			} else if(accion.matches("disminuir") && request.getParameter("id_prod")!=null) {
				
				int pos=0;
			    
				carrito = (ArrayList<ProductoCarrito>) session.getAttribute("carrito");
				
				for(ProductoCarrito prodC:carrito) {
					
					if(prodC.getId() == Integer.parseInt(request.getParameter("id_prod"))) {
						if(prodC.getCantidad() > 1) {
							prodC.setCantidad(prodC.getCantidad()-1);
						} else if(prodC.getCantidad() == 1) {
							carrito.remove(pos);
						}
					}
					pos++;
				}
				session.setAttribute("carrito", carrito);
			}
		
		} catch(Exception e){}
		
		if(request.getParameter("id_prod")!=null && request.getParameter("cant")!=null && accion.matches("agregar")) {
			System.out.println(accion);
			Producto prod = new Producto();
			ProductoDAO pdao = new ProductoDAO();
			
			// BUSCO QUE EXISTA EL PRODUCTO
			prod = pdao.obtenerUnProducto(Integer.parseInt(request.getParameter("id_prod")));

			if(prod.getId() == -1) {
				// SI NO EXISTE, VA AL INDEX
				RequestDispatcher rd = request.getRequestDispatcher("Index.jsp");
				rd.forward(request, response);
			} else {
				
				// PRODUCTO EXISTE
				try {
					int nuevaCant = 0;
					
					// BUSCO SI YA HAY UN CARRITO
					carrito = (ArrayList<ProductoCarrito>) session.getAttribute("carrito");
					
					// SI HAY RECORRO PARA BUSCAR SI EL PRODUCTO YA HABIA SIDO CARGADO
					for(ProductoCarrito prodC:carrito) {
						if(prodC.getId() == prod.getId()) {
							// SI LO ENCUENTRO CAMBIO LA CANTIDAD EN EL ARRAY INTERNO
							nuevaCant = Integer.parseInt(request.getParameter("cant")) + prodC.getCantidad();
							prodC.setCantidad(nuevaCant);
						}
					}
					
					// SI NO LO ENCONTRE, Y YA HAY CARRITO, TENGO QUE CARGARLO
					if (nuevaCant==0) {
						ProductoCarrito pca = new ProductoCarrito();
						
						// CREO UN PRODUCTO DE CARRITO NUEVO Y LO CARGO
						pca.setCantidad(Integer.parseInt(request.getParameter("cant")));
						pca.setId(prod.getId());
						carrito.add(pca);
					}
				}
				catch(Exception e){
					System.out.println(e);
					// SI ROMPIO PORQUE NO EXISTIA EL CARRITO, LO AGREGO AL ARRAY INTERNO
					ProductoCarrito pca = new ProductoCarrito();
					carrito = new ArrayList<ProductoCarrito>();
					
					pca.setCantidad(Integer.parseInt(request.getParameter("cant")));
					pca.setId(prod.getId());
					carrito.add(pca);
				}
				
				// CARGO EL ARRAY INTERNO AL CARRITO DE LA SESION
				session.setAttribute("carrito", carrito);
			}
			
		}
		
		try {
			// CARGO EL HTML PARA MOSTRAR SI HAY CARRITO
			Producto prod = new Producto();
			ProductoDAO pdao = new ProductoDAO();
			
			carrito = (ArrayList<ProductoCarrito>) session.getAttribute("carrito");
			
			double total = 0;
			
			carrito.get(0);
			
			//RECORRO EL ARRAY Y LO MUESTRO COMO TARJETAS
			for(ProductoCarrito prodC: carrito) {
				prod = pdao.obtenerUnProducto(prodC.getId());

				htmlCarrito+="<div class=\"card-body card-prod-carrito\" id=\"cardcarritoprod"+prodC.getId()+"\">";
				htmlCarrito+="<div class=\"container\">";
				htmlCarrito+="<div class=\"row\">";
				htmlCarrito+="<div class=\"col\">";
				htmlCarrito+="<img style=\"max-width:200px\" src=\""+prod.getURLImagen()+"\">";
				htmlCarrito+="</div>";
				htmlCarrito+="<div class=\"col-6\">";
				htmlCarrito+="<span class=\"row\">"+prod.getNombre()+"</span>";
				htmlCarrito+="<input type=\"hidden\" id=\"precioProd"+prodC.getId()+"\" value=\""+prod.getPrecio()+"\">";
				htmlCarrito+="<input type=\"hidden\" name=\"totalProdValues\" id=\"totalProdValue"+prodC.getId()+"\" value=\""+prod.getPrecio()*prodC.getCantidad()+"\">";
				htmlCarrito+="<br>";
				htmlCarrito+="<div class=\"btn-group\" role=\"group\">";
				htmlCarrito+="<button type=\"button\" onClick=\"cambiocantCarrito("+prodC.getId()+",'disminuir')\" id=\"btndisminuir"+prodC.getId()+"\" class=\"btn btn-secondary\" style=\"width:40px\">-</button>";
				htmlCarrito+="<button type=\"button\" id=\"cantidad"+prodC.getId()+"\" class=\"btn btn-secondary\" style=\"min-width:40px\" disabled>"+prodC.getCantidad()+"</button>";
				htmlCarrito+="<button type=\"button\" onClick=\"cambiocantCarrito("+prodC.getId()+",'aumentar')\" id=\"btnaumentar"+prodC.getId()+"\" class=\"btn btn-secondary\" style=\"width:40px\">+</button>";
				htmlCarrito+="</div>";
				htmlCarrito+="</div>";
				htmlCarrito+="<div id=\"totalProd"+prodC.getId()+"\" class=\"col\" style=\"text-align: right\">";
				cantCarrito+=prodC.getCantidad();
				total = total + (prod.getPrecio()*prodC.getCantidad());
				htmlCarrito+="$ "+ String.format(Locale.ROOT, "%.2f", prod.getPrecio()*prodC.getCantidad()) + " &nbsp ";
				htmlCarrito+="<button type=\"button\" onClick=\"removerprodCarrito("+prodC.getId()+")\" class=\"btn btn-secondary\" style=\"width:40px\"><i class=\"fas fa-trash-alt\"></i></button>";
				htmlCarrito+="</div>";
				htmlCarrito+="</div>";
				htmlCarrito+="</div>";
				htmlCarrito+="</div>";
				

				
			}
			htmlCarrito+="<br>";
			htmlCarrito+="<hr>";
			htmlCarrito+="<div style=\"text-align: right;\"><h3 id=\"totalCarrito\">Total $ "+String.format(Locale.ROOT, "%.2f", total)+"</h3></div>";
			htmlCarrito+="</div>";
			htmlCarrito+="<div class=\"row justify-content-md-center\"><button id=\"botonInicioCompra\" type=\"button\" class=\"btn btn-primary btn-lg btn-block\" onClick=\"location.href = 'pago.jsp';\" style=\"max-width:20%\">Iniciar Compra <i class=\"fas fa-money-bill-wave-alt\"></i></button></div>";
			
		}catch(Exception e){
			// SI PINCHO POR EL CARRITO VACIO MUESTRO CARRITO VACIO.
			htmlCarrito = "<div style=\"text-align:center\">";
			htmlCarrito += "<h2>El carrito de compras está vacio</h2>";
			htmlCarrito += "<br>";
			htmlCarrito += "<h3><a href=\"Index.jsp\">Volver a la tienda <i class=\"fas fa-shopping-basket\"></i></a></h3>";
			htmlCarrito += "</div>";
		}
		
		ArrayList<Categoria> todasCats = new ArrayList<Categoria>();
		CategoriaDAO daoCat = new CategoriaDAO();
		todasCats = daoCat.obtenerCategorias();
		String categorias ="<ul class=\"dropdown-menu dropdown-menu-dark\" aria-labelledby=\"navbarDropdown\">";
		
		for(Categoria categoria: todasCats) {
			
			categorias+="<li><a class=\"dropdown-item\" href=\"catalogo.jsp?idCat="+ categoria.getId() +"\" >" + categoria.getDescripcion() + "</a></li>";
			
		}
		
		categorias+="</ul>";
		
		request.setAttribute("carritoCant", cantCarrito);
		request.setAttribute("categorias", categorias);
		request.setAttribute("carrito", htmlCarrito);
		
		RequestDispatcher rd = request.getRequestDispatcher("carrito.jsp");
		rd.forward(request, response);
	}

}
