package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProductoDAO;
import dao.VentasDAO;
import entities.Producto;
import entities.ProductoCarrito;
import entities.Venta;

@WebServlet("/ServletFinalizarCompra")
public class ServletFinalizarCompra extends HttpServlet {
	HttpSession session;
	private static final long serialVersionUID = 1L;
       
    public ServletFinalizarCompra() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession(true);
		int id_venta = 0;
		
		ArrayList<ProductoCarrito> carrito = new ArrayList<ProductoCarrito>();
		
		VentasDAO vdao = new VentasDAO();
		Venta venta = new Venta();
		ProductoDAO pdao = new ProductoDAO();
		Producto producto = new Producto();
		
		if(request.getParameter("accion").toString().matches("add")) {
			
			
			if(request.getParameter("tipo").toString().matches("1")) {
				
				if(!(request.getParameter("nombre").toString().matches("")) && request.getParameter("nombre")!=null && !(request.getParameter("apellido").toString().matches("")) && request.getParameter("apellido")!=null && !(request.getParameter("direccion").toString().matches("")) && request.getParameter("direccion")!=null && !(request.getParameter("cp").toString().matches("")) && request.getParameter("cp")!=null && !(request.getParameter("provincia").toString().matches("")) && request.getParameter("provincia")!=null && !(request.getParameter("localidad").toString().matches("")) && request.getParameter("localidad")!=null) {
					
					carrito = (ArrayList<ProductoCarrito>) session.getAttribute("carrito");
					
					double total = 0;
					int nroDetalle = 0;
					
					for(ProductoCarrito prodC:carrito) {
						producto = pdao.obtenerUnProducto(prodC.getId());
						total = total  + (producto.getPrecio()*prodC.getCantidad());
					}
					
					
					venta.setId_cli(Integer.parseInt(session.getAttribute("id_cliente").toString()));
					venta.setDireccion(request.getParameter("direccion").toString());
					venta.setCp(Integer.parseInt(request.getParameter("cp").toString()));
					venta.setId_loc(Integer.parseInt(request.getParameter("localidad").toString()));
					//venta.setNro_tarjeta(request.getParameter("numeroT").toString());
					//venta.setNombre_tarjeta(request.getParameter("nombreT").toString());
					//venta.setDni(Integer.parseInt(request.getParameter("dniT").toString()));
					venta.setTotal(total);
					venta.setEstado_envio(1);
					venta.setEstado_pago(1);
					venta.setTipo_venta(1);
					
					vdao.cargarVenta(venta);
					
					id_venta = vdao.obtenerUltimaVenta(Integer.parseInt(session.getAttribute("id_cliente").toString()));
					
					for(ProductoCarrito prodC:carrito) {
						nroDetalle++;
						producto = pdao.obtenerUnProducto(prodC.getId());
						vdao.agregarDetalle(nroDetalle,id_venta,prodC.getId(),producto.getPrecio(),prodC.getCantidad());
					}
					session.setAttribute("carrito", null);
					response.getWriter().append("success,cuenta.jsp?Transferencia=true&compraRealizada="+id_venta);
				}
			
			} else {
				
				if(!(request.getParameter("nombre").toString().matches("")) && request.getParameter("nombre")!=null && !(request.getParameter("apellido").toString().matches("")) && request.getParameter("apellido")!=null && !(request.getParameter("direccion").toString().matches("")) && request.getParameter("direccion")!=null && !(request.getParameter("cp").toString().matches("")) && request.getParameter("cp")!=null && !(request.getParameter("provincia").toString().matches("")) && request.getParameter("provincia")!=null && !(request.getParameter("localidad").toString().matches("")) && request.getParameter("localidad")!=null && !(request.getParameter("numeroT").toString().matches("")) && request.getParameter("numeroT")!=null && !(request.getParameter("nombreT").toString().matches("")) && request.getParameter("nombreT")!=null) {
					
					/// FALTA PASAR CARRITO
					session.setAttribute("carrito", null);
					response.getWriter().append("success,cuenta.jsp?compraRealizada="+id_venta);
				}
			}

		}
	}

}
