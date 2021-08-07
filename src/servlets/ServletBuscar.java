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

import entities.Categoria;
import entities.Marca;
import entities.Producto;
import entities.ProductoCarrito;
import dao.CategoriaDAO;
import dao.MarcaDAO;
import dao.ProductoDAO;

@WebServlet("/ServletBuscar")
public class ServletBuscar extends HttpServlet {
	HttpSession session;
	private static final long serialVersionUID = 1L;
       
    public ServletBuscar() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doPost(request,response);
		
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		session = request.getSession(true);
		
		String consulta = "";
		int cantCarrito = 0;
		boolean cat=false;
		boolean mar=false;
		boolean ord=false;
		boolean MaxP=false;
		boolean MinP=false;
		
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

		if(request.getParameter("idCat")!=null && !(request.getParameter("idCat").matches(""))) {
			
			consulta+=" WHERE id_cat_prod =" + request.getParameter("idCat");
			cat=true;
		}
		if(request.getParameter("idMarca")!=null && !(request.getParameter("idMarca").matches(""))) {

			if(cat==true || mar==true || ord==true || MaxP==true || MinP==true) {
				consulta+=" AND id_marca_prod =" + request.getParameter("idMarca");
			} else {
				consulta+=" WHERE id_marca_prod =" + request.getParameter("idMarca");
			}
			mar=true;
		}
		if(request.getParameter("MinP")!=null && !(request.getParameter("MinP").matches(""))) {

			if(cat==true || mar==true || ord==true || MaxP==true || MinP==true) {
				consulta+=" AND precio_unitario_prod >=" + request.getParameter("MinP");
			} else {
				consulta+=" WHERE precio_unitario_prod >=" + request.getParameter("MinP");
			}
			MinP=true;
		}
		if(request.getParameter("MaxP")!=null && !(request.getParameter("MaxP").matches(""))) {

			if(cat==true || mar==true || ord==true || MaxP==true || MinP==true) {
				consulta+=" AND precio_unitario_prod <=" + request.getParameter("MaxP");
			} else {
				consulta+=" WHERE precio_unitario_prod <=" + request.getParameter("MaxP");
			}
			MaxP=true;
		}
		if(request.getParameter("busqueda")!=null && !(request.getParameter("busqueda").matches(""))) {
			
			if(cat==true || mar==true || ord==true || MaxP==true || MinP==true) {
				
				consulta+=" AND nombre_prod LIKE '%" + request.getParameter("busqueda") + "%'";
			
			} else {
				consulta+=" WHERE nombre_prod LIKE '%" + request.getParameter("busqueda") + "%'";
			}
		}
		if(request.getParameter("Ord")!=null && !(request.getParameter("Ord").matches("")) && !(request.getParameter("Ord").matches("-1"))) {

			if(request.getParameter("Ord").matches("fechaMe")){
				consulta+=" ORDER BY fecha_ingreso_prod ASC";
			} else if(request.getParameter("Ord").matches("fechaMa")){
				consulta+=" ORDER BY fecha_ingreso_prod DESC";
			} else if(request.getParameter("Ord").matches("precioMe")){
				consulta+=" ORDER BY precio_unitario_prod ASC";
			} else if(request.getParameter("Ord").matches("precioMa")){
				consulta+=" ORDER BY precio_unitario_prod DESC";
			}
			ord=true;
			
		}
		
		ArrayList<Producto> catalogoProds = new ArrayList<Producto>();
		ProductoDAO daoProd = new ProductoDAO();
		System.out.println(consulta);
		catalogoProds = daoProd.obtenerCatalogo(consulta);
		String catalogo ="";
		
		if(!(catalogoProds.isEmpty())) {
		
			for(Producto prod: catalogoProds) {
				
				catalogo+="<div class=\"col-lg-3 col-md-4 col-sm-6 col-xs-12\"  style=\"min-width:300px;padding:25px\">";
				catalogo+="<div class=\"card h-100\">";
				catalogo+="<img src=\""+prod.getURLImagen()+"\" class=\"card-img-top\" style=\"width: 100%; height: 10vw;\" title=\"\">";
				catalogo+="<div class=\"card-body\">";
				catalogo+="<h5 class=\"card-title\"><a href=\"producto.jsp?id="+prod.getId()+"\">"+prod.getNombre()+"</a></h5>";
				catalogo+="<p class=\"card-text\">$ "+String.format(Locale.ROOT, "%.2f", prod.getPrecio())+"</p>";
				catalogo+="</div>";
				catalogo+="<div class=\"card-footer\">";
				catalogo+="<button type=\"button\" onclick=\"window.location='producto.jsp?id="+prod.getId()+"'\" class=\"btn btn-primary btn-lg btn-block\">Ver Producto</button>";
				catalogo+="</div>";
				catalogo+="</div>";
				catalogo+="</div>";
				
				
			}
		}else{
			catalogo+="<div style=\"text-align:center;padding-top: 100px;padding-left: 10%;\">";
			catalogo+="<h2>NO SE ENCONTRARON PRODUCTOS PARA LA BUSQUEDA ESPECIFICADA</h2>";
			catalogo+="<h3><a href=\"catalogo.jsp\">Volver a la tienda <i class=\"fas fa-shopping-basket\"></i></a></h3>";
			catalogo+="</div>";
		}
		
		ArrayList<Marca> todasMarcas = new ArrayList<Marca>();
		MarcaDAO daoMarca = new MarcaDAO();
		String busquedaText = "";
		if(request.getParameter("busqueda")!=null && !(request.getParameter("busqueda").matches(""))){busquedaText=request.getParameter("busqueda").toString();};
		
		if(request.getParameter("idCat")!=null && !(request.getParameter("idCat").matches(""))) {
			todasMarcas = daoMarca.obtenerMarcasXCategoria(request.getParameter("idCat"),busquedaText);
		} else {
			todasMarcas = daoMarca.obtenerMarcas();
		}
		
		String marcas ="";
		
		for(Marca marca: todasMarcas) {
			
			//marcas+="<li><a href=\"catalogo.jsp?idMarca="+ marca.getId() +"\" >" + marca.getDescripcion() + "</a></li>";
			marcas+="<li><a id=\"linkMarca"+ marca.getId() +"\" onClick=\"busquedaConFiltrosMarca("+ marca.getId() +")\" href=\"#\" >" + marca.getDescripcion() + "</a></li>";

		}
		
		ArrayList<Categoria> todasCats = new ArrayList<Categoria>();
		CategoriaDAO daoCat = new CategoriaDAO();
		todasCats = daoCat.obtenerCategorias();
		String categorias ="<ul class=\"dropdown-menu dropdown-menu-dark\" aria-labelledby=\"navbarDropdown\">";
		String categoriasFiltros ="";
		
		for(Categoria categoria: todasCats) {
			
			categorias+="<li><a class=\"dropdown-item\" href=\"catalogo.jsp?idCat="+ categoria.getId() +"\" >" + categoria.getDescripcion() + "</a></li>";
			categoriasFiltros+="<li><a id=\"linkCat"+ categoria.getId() +"\" onClick=\"busquedaConFiltrosCat("+ categoria.getId() +")\" href=\"#\" >" + categoria.getDescripcion() + "</a></li>";
		}
		
		categorias+="</ul>";
		
		if(request.getParameter("busqueda")!=null && !(request.getParameter("busqueda").matches(""))) {
			request.setAttribute("busqueda", request.getParameter("busqueda"));
		}
		if(request.getParameter("Ord")!=null && !(request.getParameter("Ord").matches("")) && !(request.getParameter("Ord").matches("-1"))) {
			request.setAttribute("Ord", request.getParameter("Ord"));
		}
		request.setAttribute("categoriasFiltros", categoriasFiltros);
		request.setAttribute("categorias", categorias);
		request.setAttribute("carritoCant", cantCarrito);
		request.setAttribute("catalogo", catalogo);
		request.setAttribute("marcas", marcas);
		
		RequestDispatcher rd = request.getRequestDispatcher("catalogo.jsp");
		rd.forward(request, response);
	}

}
