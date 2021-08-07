package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CategoriaDAO;
import dao.MarcaDAO;
import dao.ProductoDAO;
import dao.UsuarioDAO;
import dao.Utilidades;
import entities.Categoria;
import entities.Marca;
import entities.Producto;
import entities.ProductoCarrito;
import entities.Provincia;

@WebServlet("/ServletAuxiliar")
public class ServletAuxiliar extends HttpServlet {
	HttpSession session;
	private static final long serialVersionUID = 1L;
       
    public ServletAuxiliar() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession(true);
		
		ArrayList<ProductoCarrito> carrito = new ArrayList<ProductoCarrito>();
		ProductoDAO pdao = new ProductoDAO();
		UsuarioDAO udao = new UsuarioDAO();
		
		if(request.getParameter("tipoSolicitud")!=null) {
			
			if(request.getParameter("tipoSolicitud").toString().matches("maxStock") && request.getParameter("id_prod")!=null) {
				int maxStock = pdao.obtenerStock(Integer.parseInt(request.getParameter("id_prod")));
				response.getWriter().append(Integer.toString(maxStock));
			}
			
			if(request.getParameter("tipoSolicitud").toString().matches("carritoOnlyTotal")) {

					// CARGO EL HTML PARA MOSTRAR SI HAY CARRITO
					Producto prod = new Producto();
					
					carrito = (ArrayList<ProductoCarrito>) session.getAttribute("carrito");
					
					double total = 0;
					
					//RECORRO EL ARRAY Y LO MUESTRO COMO TARJETAS
					for(ProductoCarrito prodC: carrito) {
						prod = pdao.obtenerUnProducto(prodC.getId());

						total = total + (prod.getPrecio()*prodC.getCantidad());
					}
					response.getWriter().append(Double.toString(total));
			}
			
			if(request.getParameter("tipoSolicitud").toString().matches("listarLoc")) {

				// CARGO EL HTML PARA MOSTRAR SI HAY CARRITO
				ArrayList<Provincia> localidades = new ArrayList<Provincia>();
				Utilidades utildao = new Utilidades();
				String html = "";
				html+="<option value=\"\" disabled=\"disabled\" selected>Elija una opcion</option>";
						
				localidades = utildao.obtenerLocalidadesXProv(Integer.parseInt(request.getParameter("id_prov")));
				for(Provincia loc:localidades) {
					html+="<option value=\""+loc.getId()+"\">"+loc.getNombre()+"</option>";
				}
				response.setCharacterEncoding("UTF-8");
				response.getWriter().append(html);
		}
			if(request.getParameter("tipoSolicitud").toString().matches("checkuser")) {

				int filas = 0;
				
				filas = udao.checkUser(request.getParameter("user").toString());
				if(filas == 0) {
					response.getWriter().append("success");
				}else {
					response.getWriter().append("error");
				}
		}
			if(request.getParameter("tipoSolicitud").toString().matches("obtenerCategoriasProd")){	
				ArrayList<Categoria> categorias = new ArrayList<Categoria>();
				
				CategoriaDAO cdao = new CategoriaDAO();
				
				categorias = cdao.obtenerCategorias();
				
				String htmlSelectCategorias = "<option value=\"-1\" selected>Seleccione una Opcion...</option>";
				
				for(Categoria c: categorias) {
					htmlSelectCategorias += "<option value=\""+c.getId()+"\" >"+c.getDescripcion()+"</option>";
				}
				
				response.getWriter().append(htmlSelectCategorias);
			}
			if(request.getParameter("tipoSolicitud").toString().matches("obtenerMarcasProd")){
				ArrayList<Marca> marcas = new ArrayList<Marca>();
				MarcaDAO mdao = new MarcaDAO();
				marcas = mdao.obtenerMarcas();
				
				String htmlSelectMarcas = "<option value=\"-1\" selected>Seleccione una Opcion...</option>";
				
				for(Marca m: marcas) {
					htmlSelectMarcas += "<option value=\""+m.getId()+"\" >"+m.getDescripcion()+"</option>";
				}
				
				response.getWriter().append(htmlSelectMarcas);
			}
			
		}
	}

}
