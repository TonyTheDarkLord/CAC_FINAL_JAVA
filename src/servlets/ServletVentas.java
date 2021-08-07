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

import dao.ProductoDAO;
import dao.Utilidades;
import dao.VentasDAO;
import entities.DetallesVenta;
import entities.EstadoEnvio;
import entities.EstadoPago;
import entities.Producto;
import entities.TipoVenta;
import entities.Venta;

@WebServlet("/ServletVentas")
public class ServletVentas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServletVentas() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<DetallesVenta> prodsVenta = new ArrayList<DetallesVenta>();
		ArrayList<Venta> ventas = new ArrayList<Venta>();
		ArrayList<TipoVenta> tiposVenta = new ArrayList<TipoVenta>();
		ArrayList<EstadoEnvio> estadosEnvio = new ArrayList<EstadoEnvio>();
		ArrayList<EstadoPago> estadosPago = new ArrayList<EstadoPago>();
		//ArrayList<EmpresaEnvio> empresasEnvio = new ArrayList<EmpresaEnvio>();
		VentasDAO vdao = new VentasDAO();
		Utilidades util = new Utilidades();
		
		String htmlModalVenta = "";
		int cantCarrito = 0;
		tiposVenta = util.obtenerTiposVenta();
		estadosEnvio = util.obtenerEstadosEnvio();
		estadosPago = util.obtenerEstadosPago();
		//empresasEnvio = util.obtenerEmpresasEnvios();
		
		String htmlTablaVentas = "";
		
		if(request.getParameter("id_venta")!=null && request.getParameter("accion")==null) {
			
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// CARGO PAGINA NORMAL
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		Producto prod = new Producto();
		ProductoDAO pdao = new ProductoDAO();
		
		prodsVenta = util.obtenerProductosVenta(Integer.parseInt(request.getParameter("id_venta").toString()));
		
		double total = 0;
		
		//RECORRO EL ARRAY PARA EL MODAL
		for(DetallesVenta prodC: prodsVenta) {
			
			cantCarrito=cantCarrito+prodC.getCantidad();
			
			prod = pdao.obtenerUnProducto(prodC.getId_prod());

			htmlModalVenta+="<div class=\"card-body card-prod-pago\" id=\"cardcarritoprod"+prodC.getId_prod()+"\" style=\"padding: 0;\">";
			htmlModalVenta+="<div class=\"container\">";
			htmlModalVenta+="<div class=\"row\">";
			htmlModalVenta+="<div class=\"col\">";
			htmlModalVenta+="<img style=\"max-width:85px\" src=\""+prod.getURLImagen()+"\">";
			htmlModalVenta+="</div>";
			htmlModalVenta+="<div class=\"col-6\">";
			htmlModalVenta+="<span class=\"row\">" + prodC.getCantidad() + " x " + prod.getNombre() + "</span>";
			htmlModalVenta+="<div class=\"col-6\">";
			//htmlCarrito+="<button type=\"button\" id=\"cantidad"+prodC.getId()+"\" class=\"btn btn-secondary\" style=\"min-width:40px\" disabled>"+prodC.getCantidad()+"</button>";
			htmlModalVenta+="</div>";
			htmlModalVenta+="</div>";
			htmlModalVenta+="<div class=\"col\" style=\"text-align: right;\">";
			total = total + (prod.getPrecio()*prodC.getCantidad());
			htmlModalVenta+="$ "+String.format(Locale.ROOT, "%.2f", prod.getPrecio()*prodC.getCantidad());
			htmlModalVenta+="</div>";
			htmlModalVenta+="</div>";
			htmlModalVenta+="</div>";
			htmlModalVenta+="</div>";

			
		}
		
		htmlModalVenta+="<br>";
		htmlModalVenta+="<hr>";
		htmlModalVenta+="<div style=\"text-align: right;\"><h3>Total $ "+String.format(Locale.ROOT, "%.2f", total)+"</h3></div>";
		
		response.getWriter().append(htmlModalVenta);

						
		} else if(request.getParameter("id_venta")!=null && request.getParameter("accion")!=null) {
			
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// ACCIONES SOBRE LA TABLA
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			if(request.getParameter("accion").matches("eliminar")) {
				
				int resultado = 0;
				
				resultado = vdao.eliminarVenta(Integer.parseInt(request.getParameter("id_venta").toString()));
				
				
				if(resultado== 1) {
					
					// REDIRIJO PARA QUE VUELVA A CARGAR LA TABLA SIN LA QUE ELIMINE
					request.setAttribute("ventas", null);
					request.setAttribute("accion", null);
					request.setAttribute("id_venta", null);
					response.getWriter().append("Venta Cancelada.");
					
				} else {
					
					response.getWriter().append("No se puede cancelar la Venta.");
				}
		
			} else if(request.getParameter("accion").matches("modificar")) {
					
					int resultado = 0;
					
					resultado = vdao.modificarVenta(Integer.parseInt(request.getParameter("id_venta").toString()),request.getParameter("direccion").toString(),Integer.parseInt(request.getParameter("estado_envio").toString()),Integer.parseInt(request.getParameter("estado_pago").toString()));
					
					if(resultado== 1) {
						
						// REDIRIJO PARA QUE VUELVA A CARGAR LA TABLA SIN LA QUE ELIMINE
						request.setAttribute("ventas", null);
						request.setAttribute("accion", null);
						request.setAttribute("id_venta", null);
						response.getWriter().append("Venta Modificada.");
						
					} else {
						
						response.getWriter().append("Hubo un error al modificiar la venta.");
					}

					
				}
			
			
		} else {

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// CARGO PAGINA NORMAL
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
		ventas = vdao.ObtenerTodasLasVentas();
		
		for(Venta v: ventas) {

			htmlTablaVentas += "<tr><td>#" +v.getId() + "</td><td>" +v.getDni() + "</td><td>" +v.getNombre_cli() + "</td><td>" +v.getApellido_cli() + "</td><td><input type=\"date\" class=\"form-control\" placeholder=\"-\" value=\"" +v.getFecha() + "\" disabled></input></td>";
		    
			
			// PARA EL ESTADO DEL PAGO
			if(v.getEstado_envio()==3) {
				htmlTablaVentas += "<td><select class=\"form-control\" id=\"estadoPagoSelect" + v.getId() + "\" disabled>";
			} else {
				htmlTablaVentas += "<td><select class=\"form-control\" id=\"estadoPagoSelect" + v.getId() + "\" >";
			}
			for(EstadoPago ep: estadosPago) {
				
				if(ep.getId() == v.getEstado_pago()) {
				
					htmlTablaVentas += "<option value=\""+ ep.getId() +"\" selected>"+ep.getDetalle()+"</option>";
					
				} else {
					htmlTablaVentas += "<option value=\""+ ep.getId() +"\">"+ep.getDetalle()+"</option>";
				}
			}
			
			htmlTablaVentas += "</select></td>";
			
			// PARA EL TIPO DEL PAGO
			
			htmlTablaVentas += "<td><select class=\"form-control\" id=\"tipoVentaSelect" + v.getId() + "\" disabled>";
						
			for(TipoVenta tv: tiposVenta) {
				
				if(tv.getId() == v.getTipo_venta()) {
				
					htmlTablaVentas += "<option value=\""+ tv.getId() +"\" selected>"+tv.getDetalle()+"</option>";
					
				} else {
					htmlTablaVentas += "<option value=\""+ tv.getId() +"\">"+tv.getDetalle()+"</option>";
				}
			}
						
			htmlTablaVentas += "</select></td>";
						
			if(v.getEstado_envio() == 3) {
				htmlTablaVentas += "<td><input type=\"text\" id=\"direccion"+v.getId()+"\" class=\"form-control\" placeholder=\" - \" value=\"" +v.getDireccion() + "\" disabled></input></td>";
			} else {
				htmlTablaVentas += "<td><input type=\"text\" id=\"direccion"+v.getId()+"\" class=\"form-control\" placeholder=\" - \" value=\"" +v.getDireccion() + "\" ></input></td>";
			}
			
		    // PARA EL ENVIO
			
 			if(v.getEstado_envio()==3) {
 				htmlTablaVentas += "<td><select class=\"form-control\" id=\"estadoEnvioSelect" + v.getId() + "\" disabled>";
 			} else {
 				htmlTablaVentas += "<td><select class=\"form-control\" id=\"estadoEnvioSelect" + v.getId() + "\">";
 			}
 						
 			for(EstadoEnvio ee: estadosEnvio) {
 				
 				if(ee.getId() == v.getEstado_envio()) {
 				
 					htmlTablaVentas += "<option value=\""+ ee.getId() +"\" selected>"+ee.getDetalle()+"</option>";
 					
 				} else {
 					htmlTablaVentas += "<option value=\""+ ee.getId() +"\">"+ee.getDetalle()+"</option>";
 				}
 			}
 			
 			htmlTablaVentas += "</select></td>";
 			
 			/*if(v.getEstado_envio()==3) {
 				htmlTablaVentas += "<td><select class=\"form-control\" id=\"tipoEnvioSelect" + v.getId() + "\" disabled>";
 			} else {
 				htmlTablaVentas += "<td><select class=\"form-control\" id=\"tipoEnvioSelect" + v.getId() + "\">";
 			}
 			
 			for(EmpresaEnvio ee: empresasEnvio) {
 				
 				if(ee.getId() == v.getEmpresa_envio()) {
 				
 					htmlTablaVentas += "<option value=\""+ ee.getId() +"\" selected>"+ee.getNombre()+", $"+ee.getPrecio()+"</option>";
 					
 				} else {
 					htmlTablaVentas += "<option value=\""+ ee.getId() +"\">"+ee.getNombre()+", $"+ee.getPrecio()+"</option>";
 				}
 			}
 			
 			htmlTablaVentas += "</select></td>";*/
 			
		    htmlTablaVentas += "<td><button type=\"button\" onclick=\"obtenerDetalleDeVenta("+v.getId()+")\" class=\"form-control btn-info\">" + "Detalles #"  +v.getId() +"</button></td>";
		    if(v.getEstado_envio() == 3) {
		    	htmlTablaVentas += "<td><button type=\"button\" onclick=\"cancelarVenta("+v.getId()+")\" class=\"form-control btn-secondary\" disabled>" + "Cancelar"  +"</button></td>";
		    	htmlTablaVentas += "<td><button type=\"button\" class=\"btn-secondary form-control\" id=\"GuardaCambios\" onclick=\"guardarCambiosVenta("+v.getId()+")\" disabled>Guardar</button></td></tr>";
		    } else {
		    	htmlTablaVentas += "<td><button type=\"button\" onclick=\"cancelarVenta("+v.getId()+")\" class=\"form-control btn-danger\">" + "Cancelar"  +"</button></td>";
		    	htmlTablaVentas += "<td><button type=\"button\" class=\"btn-success form-control\" id=\"GuardaCambios\" onclick=\"guardarCambiosVenta("+v.getId()+")\">Guardar</button></td></tr>";
		    }
		    
		    
		}
		
		request.setAttribute("ventas", htmlTablaVentas);
		RequestDispatcher rd = request.getRequestDispatcher("content/ventas.jsp");
		rd.forward(request, response);
		//response.sendRedirect("/content/ventas.jsp");
		}
	}

}
