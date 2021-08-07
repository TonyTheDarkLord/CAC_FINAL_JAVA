package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoriaDAO;
import dao.MarcaDAO;
import dao.ProductoDAO;
import entities.Categoria;
import entities.Marca;
import entities.Producto;

@WebServlet("/ServletProductos")
public class ServletProductos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServletProductos() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Producto> productos = new ArrayList<Producto>();
		ArrayList<Categoria> categorias = new ArrayList<Categoria>();
		ArrayList<Marca> marcas = new ArrayList<Marca>();
		
		ProductoDAO pdao = new ProductoDAO();
		CategoriaDAO cdao = new CategoriaDAO();
		MarcaDAO mdao = new MarcaDAO();
		
		String htmlTablaProductos = "";
		
		if(request.getParameter("accion")!=null && request.getParameter("nombre")!=null) {
			
			if(request.getParameter("accion").matches("categoria")) {
				cdao.cargarCategoria(request.getParameter("nombre").toString());
			}
			if(request.getParameter("accion").matches("marca")) {
				mdao.cargarMarca(request.getParameter("nombre").toString());
			}
			if(request.getParameter("accion").matches("agregar")) {
				
				
				Producto pm = new Producto();
				
				pm.setNombre((request.getParameter("nombre").toString()));
				pm.setDescripcion((request.getParameter("descripcion").toString()));
				pm.setPrecio(Double.parseDouble((request.getParameter("precio").toString())));
				pm.setStock(Integer.parseInt((request.getParameter("stock").toString())));
				pm.setURLImagen((request.getParameter("urlimg").toString()));
				pm.setIdCat(Integer.parseInt((request.getParameter("categoria").toString())));
				pm.setIdMarca(Integer.parseInt((request.getParameter("marca").toString())));
				pm.setEstado(1);
				
				int resultado = pdao.agregarProducto(pm);
				if(resultado == 1) {
					response.getWriter().append("Producto Agregado correctamente");
				} else {
					response.getWriter().append("Error al Agregar el Producto");
				}
				
			}
		}
		
		if(request.getParameter("id_producto")!=null && request.getParameter("accion")!=null) {
			
			if(request.getParameter("accion").matches("modificar")) {
				
				Producto pm = new Producto();
				
				pm.setId(Integer.parseInt((request.getParameter("id_producto").toString())));
				pm.setNombre((request.getParameter("nombre").toString()));
				pm.setDescripcion((request.getParameter("descripcion").toString()));
				pm.setPrecio(Double.parseDouble((request.getParameter("precio").toString())));
				pm.setStock(Integer.parseInt((request.getParameter("stock").toString())));
				pm.setURLImagen((request.getParameter("urlimg").toString()));
				pm.setIdCat(Integer.parseInt((request.getParameter("categoria").toString())));
				pm.setIdMarca(Integer.parseInt((request.getParameter("marca").toString())));
				pm.setEstado(Integer.parseInt((request.getParameter("estado").toString())));
				
				int resultado = pdao.modificarProducto(pm);
				if(resultado == 1) {
					response.getWriter().append("Modificado correctamente");
				} else {
					response.getWriter().append("Error al modificar Producto");
				}
			}
			

		} else {

				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				// CARGO PAGINA NORMAL
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					
				productos = pdao.ObtenerTodasLosProductos();
				categorias = cdao.obtenerCategorias();
				marcas = mdao.obtenerMarcas();
				
				for(Producto p: productos) {

					htmlTablaProductos += "<tr><td><input type=\"text\" id=\"nombreProd" + p.getId() + "\" class=\"form-control\" placeholder=\"-\" value=\"" +p.getNombre() + "\"></input></td><td><input type=\"text\" id=\"descripcionProd" + p.getId() + "\" class=\"form-control\" placeholder=\"-\" value=\"" +p.getDescripcion() + "\"></input></td><td><input type=\"number\" id=\"precioProd" + p.getId() + "\" class=\"form-control\" placeholder=\"-\" value=\"" +p.getPrecio() + "\"></input></td><td><input type=\"number\" id=\"stockProd" + p.getId() + "\" class=\"form-control\" placeholder=\"-\" value=\"" +p.getStock() + "\"></input></td>";
					htmlTablaProductos += "<td><input type=\"text\" class=\"form-control\" id=\"urlProd" + p.getId() + "\" placeholder=\"-\" value=\"" +p.getURLImagen() + "\"></input></td>";
					
					// ESTADO DEL PRODUCTO
					
					htmlTablaProductos += "<td><select class=\"form-control\" id=\"estadoProdSelect" + p.getId() + "\">";
					if(p.getEstado() == 1) {
					
						htmlTablaProductos += "<option value=\"1\" selected>Habilitado</option>";
						htmlTablaProductos += "<option value=\"0\">Deshabilitado</option>";
						
					} else {
						htmlTablaProductos += "<option value=\"0\" selected>Deshabilitado</option>";
						htmlTablaProductos += "<option value=\"1\">Habilitado</option>";
					}
							
					htmlTablaProductos += "</select></td>";
								
					
					// CATEGORIAS
					
					htmlTablaProductos += "<td><select class=\"form-control\" id=\"categoriaProdSelect" + p.getId() + "\">";
					
					for(Categoria c: categorias) {

						if(p.getIdCat() == c.getId()) {
							htmlTablaProductos += "<option value=\""+c.getId()+"\" selected>"+c.getDescripcion()+"</option>";
						} else {
							htmlTablaProductos += "<option value=\""+c.getId()+"\" >"+c.getDescripcion()+"</option>";
						}
						
					}
					
					htmlTablaProductos += "</select></td>";
					
					// MARCAS
					
					htmlTablaProductos += "<td><select class=\"form-control\" id=\"marcaProdSelect" + p.getId() + "\">";
					for(Marca m: marcas) {

						if(p.getIdMarca() == m.getId()) {
							htmlTablaProductos += "<option value=\""+m.getId()+"\" selected>"+m.getDescripcion()+"</option>";
						} else {
							htmlTablaProductos += "<option value=\""+m.getId()+"\" >"+m.getDescripcion()+"</option>";
						}
						
					}
					
					htmlTablaProductos += "</select></td>";
					
					htmlTablaProductos += "<td><button type=\"button\" class=\"btn-success form-control\" id=\"GuardaCambios\" onClick=\"guardarCambiosProducto("+p.getId()+")\">Guardar</button></td></tr>";
				    
				    
				}
				
				request.setAttribute("productos", htmlTablaProductos);
				RequestDispatcher rd = request.getRequestDispatcher("content/productos.jsp");
				rd.forward(request, response);
			}
		}
	

}
