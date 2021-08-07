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

import dao.ClienteDAO;
import dao.ProductoDAO;
import dao.Utilidades;
import entities.Cliente;
import entities.DatosVenta;
import entities.Producto;
import entities.ProductoCarrito;
import entities.Provincia;

@WebServlet("/ServletPayment")
public class ServletPayment extends HttpServlet {
	HttpSession session;
	private static final long serialVersionUID = 1L;
       
    public ServletPayment() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession(true);
		
		ArrayList<ProductoCarrito> carrito = new ArrayList<ProductoCarrito>();
		ArrayList<Provincia> provincias = new ArrayList<Provincia>();
		ArrayList<Provincia> localidades = new ArrayList<Provincia>();
		Utilidades utildao = new Utilidades();
		DatosVenta ultimaCompra = new DatosVenta();
		boolean guardo = false;
		String htmlCarrito = "";
		String variable = "";
		String htmlFormulario = "";
		int cantCarrito = 0;
		
		ClienteDAO cdao = new ClienteDAO();
		Cliente cliente = new Cliente();
		
		if(session.getAttribute("id_cliente")!=null) {
		
			cliente = cdao.obtenerUnCliente(Integer.parseInt((session.getAttribute("id_cliente").toString())));
			
			if(cliente.isGuardar()) {
				ultimaCompra = utildao.obtenerDatosUltimaVenta(cliente.getId());
				guardo = true;
			}
				
				htmlFormulario+="<div style=\"margin: 0px;padding-top:10px\">";
				htmlFormulario+="<h4 class=\"mb-3\">Informacion de envio</h4><hr>";
				htmlFormulario+="<form action=\"ServletFinalizarCompra\" class=\"needs-validation\">";
				htmlFormulario+="<div class=\"alert alert-danger\" role=\"alert\" id=\"blankFields\" style=\"display:none; width: 100%;justify-content: center;padding-top:10px\">Revise que los campos esten correctos.</div>";
				htmlFormulario+="<div class=\"row\">";
				htmlFormulario+="<div class=\"col-md-6 mb-3\">";
				htmlFormulario+="<label for=\"firstName\">Nombre</label>";
				variable = "";
				if(guardo) {variable = cliente.getNombre();}
				htmlFormulario+="<input name=\"firstName\" type=\"text\" class=\"form-control\" id=\"firstName\" placeholder=\"\" value=\"" + variable +"\" required>";
				htmlFormulario+="<div class=\"invalid-feedback\">El nombre es requerido.</div>";
				htmlFormulario+="</div>";
				htmlFormulario+="<div class=\"col-md-6 mb-3\">";
				htmlFormulario+="<label for=\"lastName\">Apellido</label>";
				variable = "";
				if(guardo) {variable = cliente.getApellido();}
				htmlFormulario+="<input name=\"lastName\" type=\"text\" class=\"form-control\" id=\"lastName\" placeholder=\"\" value=\""+ variable +"\" required>";
				htmlFormulario+="<div class=\"invalid-feedback\">El Apellido es requerido.</div>";
				htmlFormulario+="</div>";
				htmlFormulario+="</div>";
				htmlFormulario+="<div class=\"mb-3\">";
				htmlFormulario+="<label for=\"address\">Direccion</label>";
				htmlFormulario+="<input name=\"address\" type=\"text\" class=\"form-control\" id=\"address\" placeholder=\"1234 Main St\" required>";
				htmlFormulario+="<div class=\"invalid-feedback\">";
				htmlFormulario+="Ingrese una direccion.";
				htmlFormulario+="</div>";
				htmlFormulario+="</div>";
				htmlFormulario+="<div class=\"mb-3\">";
				//htmlFormulario+="<label for=\"address2\">Address 2 <span class=\"text-muted\">(Optional)</span></label>";
				//htmlFormulario+="<input type=\"text\" class=\"form-control\" id=\"address2\" placeholder=\"Apartment or suite\">";
				htmlFormulario+="<div class=\"row\"><div class=\"col-md-5 mb-3\"><label for=\"country\">Provincia</label>";
				htmlFormulario+="<select name=\"provincia\" class=\"custom-select d-block w-100 provincia required\" id=\"provincia\" required>";
				htmlFormulario+="<option value=\"-1\" disabled=\"disabled\" selected>Elija una opcion</option>";
				provincias = utildao.obtenerProvincias();
				int idProv = -1;
				for(Provincia prov:provincias) {
					if(guardo) {
						idProv = utildao.obtenerIdProvSelect(ultimaCompra.getId_loc());
					}
					if(prov.getId()==idProv) {
						htmlFormulario+="<option value=\""+prov.getId()+"\" selected>"+prov.getNombre()+"</option>";
					} else {
						htmlFormulario+="<option value=\""+prov.getId()+"\">"+prov.getNombre()+"</option>";
					}
				}
				htmlFormulario+="</select>";
				htmlFormulario+="<div class=\"invalid-feedback\">Seleccione una provincia valida.</div></div>";
				htmlFormulario+="<div class=\"col-md-4 mb-3\">";
				htmlFormulario+="<label for=\"state\">Localidad</label>";
				if(idProv!=-1) {
					htmlFormulario+="<select class=\"custom-select d-block w-100 required\" id=\"localidad\" required>";
					htmlFormulario+="<option value=\"-1\" disabled=\"disabled\" selected>Elija una opcion</option>";
					localidades = utildao.obtenerLocalidadesXProv(idProv);
					for(Provincia loc:localidades) {
						if(guardo) {
							htmlFormulario+="<option value=\""+loc.getId()+"\" selected>"+loc.getNombre()+"</option>";
						}
						if(loc.getId()==idProv) {
							
						} else {
							htmlFormulario+="<option value=\""+loc.getId()+"\">"+loc.getNombre()+"</option>";
						}
					}
				} else {
					htmlFormulario+="<select name=\"localidad\" class=\"custom-select d-block w-100\" id=\"localidad\" required disabled>";
					htmlFormulario+="<option value=\"-1\">Elija una opcion</option>";
				}
				htmlFormulario+="</select>";
				htmlFormulario+="<div class=\"invalid-feedback\">Elija una localidad valida.</div></div>";
				variable = "";
				if(guardo) {variable = Integer.toString(ultimaCompra.getCp());}
				htmlFormulario+="<div class=\"col-md-3 mb-3\"><label for=\"zip\">Codigo Postal</label><input name=\"zip\" type=\"text\" class=\"form-control\" id=\"zip\" value=\""+variable+"\" placeholder=\"\" required><div class=\"invalid-feedback\">El CP no puede estar vacio.</div></div></div>";
				//htmlFormulario+="<hr class=\"mb-4\"><div class=\"custom-control custom-checkbox\"><input type=\"checkbox\" class=\"custom-control-input\" id=\"same-address\"><label class=\"custom-control-label\" for=\"same-address\">Shipping address is the same as my billing address</label></div>";
				//htmlFormulario+="<div class=\"custom-control custom-checkbox\"><input type=\"checkbox\" class=\"custom-control-input\" id=\"save-info\"><label class=\"custom-control-label\" for=\"save-info\">Save this information for next time</label></div>";
				htmlFormulario+="<hr class=\"mb-4\"><h4 class=\"mb-3\">Pago</h4><hr>";
				htmlFormulario+="<div class=\"d-block my-3\">";
				htmlFormulario+="<div class=\"alert alert-danger\" role=\"alert\" id=\"blankPago\" style=\"display:none; width: 100%;justify-content: center;padding-top:10px\">Seleccione un metodo de pago.</div>";
				htmlFormulario+="<div class=\"custom-control custom-radio\"><input onchange=\"mostrarInfoPago()\" id=\"tarjeta\" name=\"metodopago\" type=\"radio\" class=\"custom-control-input\" required><label class=\"custom-control-label\" for=\"tarjeta\">Tarjeta</label></div>";
				htmlFormulario+="<div class=\"custom-control custom-radio\"><input onchange=\"mostrarInfoPago()\" id=\"efectivo\" name=\"metodopago\" type=\"radio\" class=\"custom-control-input\" required><label class=\"custom-control-label\" for=\"efectivo\">Efectivo/Transferencia</label></div></div>";
				htmlFormulario+="<div id=\"rowTarjetaDatos\" class=\"row\" style=\"display:none\">";
			    htmlFormulario+="<div class=\"col-md-6 mb-3\"><label for=\"cc-name\">Nombre y Apellido</label><input type=\"text\" class=\"form-control\" id=\"cc-name\" placeholder=\"\" required><small class=\"text-muted\">Nombre completo como figura en el plastico.</small><div class=\"invalid-feedback\">Este dato no puede estar vacio.</div></div>";
				htmlFormulario+="<div class=\"col-md-6 mb-3\"><label for=\"cc-number\">Numero de la tarjeta</label><input type=\"text\" class=\"form-control\" id=\"cc-number\" placeholder=\"\" required><div class=\"invalid-feedback\">Este dato no puede estar vacio.</div></div></div>";
				htmlFormulario+="<div id=\"rowTransferencia\" class=\"row\" style=\"display:none\">";
			    htmlFormulario+="<div style=\"margin-left: 16px;\"><span>Al finalizar la compra se le presentaran los datos para realizar el pago.</span></div>";
				htmlFormulario+="</div>";
				htmlFormulario+="<div id=\"rowTarjeta\" class=\"row\" style=\"display:none\">";
				htmlFormulario+="<div class=\"col-md-3 mb-3\"><label for=\"cc-expiration\">Vencimiento</label><input type=\"text\" class=\"form-control\" name=\"cc-expiration\" id=\"cc-expiration\" placeholder=\"\" required><div class=\"invalid-feedback\">Este dato no puede estar vacio.</div></div>";
				htmlFormulario+="<div class=\"col-md-3 mb-3\"><label for=\"cc-cvv\">Codigo</label><input type=\"text\" class=\"form-control\" name=\"cc-cvv\" id=\"cc-cvv\" placeholder=\"\" required><div class=\"invalid-feedback\">Security code required</div></div></div>";
				htmlFormulario+="<hr class=\"mb-4\"><button class=\"btn btn-primary btn-lg btn-block\" onclick=\"realizarCompra()\" type=\"button\">Realizar compra</button></form>";
				htmlFormulario+="</div>";
				htmlFormulario+="</div>";
				
		} else {
			
			htmlFormulario+="<div style=\"margin: 0px;padding-top:10px\">";
			htmlFormulario+="<h4 class=\"mb-3\">Informacion de envio</h4><hr class=\"mb-4\">";
			htmlFormulario+="<form class=\"needs-validation\">";
			htmlFormulario+="<div class=\"alert alert-danger\" role=\"alert\" id=\"blankFields\" style=\"display:none; width: 100%;justify-content: center;padding-top:10px\">Revise que los campos esten correctos.</div>";
			htmlFormulario+="<div class=\"row\">";
			htmlFormulario+="<div class=\"col-md-6 mb-3\">";
			htmlFormulario+="<label for=\"firstName\">Nombre</label>";
			htmlFormulario+="<input type=\"text\" class=\"form-control\" id=\"firstName\" placeholder=\"\" value=\"\" required>";
			htmlFormulario+="<div class=\"invalid-feedback\">El nombre es requerido.</div>";
			htmlFormulario+="</div>";
			htmlFormulario+="<div class=\"col-md-6 mb-3\">";
			htmlFormulario+="<label for=\"lastName\">Apellido</label>";
			htmlFormulario+="<input type=\"text\" class=\"form-control\" id=\"lastName\" placeholder=\"\" value=\"\" required>";
			htmlFormulario+="<div class=\"invalid-feedback\">El Apellido es requerido.</div>";
			htmlFormulario+="</div>";
			htmlFormulario+="</div>";
			htmlFormulario+="<div class=\"mb-3\">";
			htmlFormulario+="<label for=\"address\">Direccion</label>";
			htmlFormulario+="<input type=\"text\" class=\"form-control\" id=\"address\" placeholder=\"1234 Main St\" required>";
			htmlFormulario+="<div class=\"invalid-feedback\">";
			htmlFormulario+="Ingrese una direccion.";
			htmlFormulario+="</div>";
			htmlFormulario+="</div>";
			htmlFormulario+="<div class=\"mb-3\">";
			//htmlFormulario+="<label for=\"address2\">Address 2 <span class=\"text-muted\">(Optional)</span></label>";
			//htmlFormulario+="<input type=\"text\" class=\"form-control\" id=\"address2\" placeholder=\"Apartment or suite\">";
			htmlFormulario+="<div class=\"row\"><div class=\"col-md-5 mb-3\"><label for=\"country\">Provincia</label>";
			htmlFormulario+="<select class=\"custom-select d-block w-100 provincia\" id=\"provincia\" required>";
			htmlFormulario+="<option value=\"-1\" selected>Elija una opcion</option>";
			provincias = utildao.obtenerProvincias();
			for(Provincia prov:provincias) {
					htmlFormulario+="<option value=\""+prov.getId()+"\">"+prov.getNombre()+"</option>";
			}
			htmlFormulario+="</select>";
			htmlFormulario+="<div class=\"invalid-feedback\">Seleccione una provincia valida.</div></div>";
			htmlFormulario+="<div class=\"col-md-4 mb-3\">";
			htmlFormulario+="<label for=\"state\">Localidad</label>";
			htmlFormulario+="<select class=\"custom-select d-block w-100\" id=\"localidad\" required disabled>";
			htmlFormulario+="<option value=\"-1\">Elija una opcion</option>";
			htmlFormulario+="</select>";
			htmlFormulario+="<div class=\"invalid-feedback\">Elija una localidad valida.</div></div>";
			htmlFormulario+="<div class=\"col-md-3 mb-3\"><label for=\"zip\">Codigo Postal</label><input type=\"text\" class=\"form-control\" id=\"zip\" value=\"\" placeholder=\"\" required=\"\"><div class=\"invalid-feedback\">El CP no puede estar vacio.</div></div></div>";
			//htmlFormulario+="<hr class=\"mb-4\"><div class=\"custom-control custom-checkbox\"><input type=\"checkbox\" class=\"custom-control-input\" id=\"same-address\"><label class=\"custom-control-label\" for=\"same-address\">Shipping address is the same as my billing address</label></div>";
			//htmlFormulario+="<div class=\"custom-control custom-checkbox\"><input type=\"checkbox\" class=\"custom-control-input\" id=\"save-info\"><label class=\"custom-control-label\" for=\"save-info\">Save this information for next time</label></div>";
			htmlFormulario+="<hr class=\"mb-4\"><h4 class=\"mb-3\">Pago</h4><hr>";
			htmlFormulario+="<div class=\"d-block my-3\">";
			htmlFormulario+="<div class=\"custom-control custom-radio\"><input onchange=\"mostrarInfoPago()\" id=\"tarjeta\" name=\"paymentMethod\" type=\"radio\" class=\"custom-control-input\" required=\"\"><label class=\"custom-control-label\" for=\"tarjeta\">Tarjeta</label></div>";
			htmlFormulario+="<div class=\"custom-control custom-radio\"><input onchange=\"mostrarInfoPago()\" id=\"efectivo\" name=\"paymentMethod\" type=\"radio\" class=\"custom-control-input\" required=\"\"><label class=\"custom-control-label\" for=\"efectivo\">Efectivo/Transferencia</label></div></div>";
			htmlFormulario+="<div class=\"row\" style=\"display:none\">";
		    htmlFormulario+="<div class=\"col-md-6 mb-3\"><label for=\"cc-name\">Nombre y Apellido</label><input type=\"text\" class=\"form-control\" id=\"cc-name\" placeholder=\"\" required=\"\"><small class=\"text-muted\">Nombre completo como figura en el plastico.</small><div class=\"invalid-feedback\">Este dato no puede estar vacio.</div></div>";
			htmlFormulario+="<div class=\"col-md-6 mb-3\"><label for=\"cc-number\">Numero de la tarjeta</label><input type=\"text\" class=\"form-control\" id=\"cc-number\" placeholder=\"\" required=\"\"><div class=\"invalid-feedback\">Este dato no puede estar vacio.</div></div></div>";
			htmlFormulario+="<div class=\"row\">";
		    htmlFormulario+="<div style=\"margin-left: 16px;\"><span>Al finalizar la compra se le presentaran los datos para realizar el pago.</span></div>";
			htmlFormulario+="</div>";
			htmlFormulario+="<div class=\"row\" style=\"display:none\">";
			htmlFormulario+="<div class=\"col-md-3 mb-3\"><label for=\"cc-expiration\">Vencimiento</label><input type=\"text\" class=\"form-control\" id=\"cc-expiration\" placeholder=\"\" required=\"\"><div class=\"invalid-feedback\">Este dato no puede estar vacio.</div></div>";
			htmlFormulario+="<div class=\"col-md-3 mb-3\"><label for=\"cc-cvv\">Codigo</label><input type=\"text\" class=\"form-control\" id=\"cc-cvv\" placeholder=\"\" required=\"\"><div class=\"invalid-feedback\">Security code required</div></div></div>";
			htmlFormulario+="<hr class=\"mb-4\"><button class=\"btn btn-primary btn-lg btn-block\" onclick=\"realizarCompra()\" type=\"button\">Realizar compra</button></form>";
			htmlFormulario+="</div>";
			htmlFormulario+="<hr><h4 class=\"mb-3\">Crear Cuenta</h4><hr>";
			htmlFormulario+="<form class=\"needs-validation\" novalidate=\"\">";
			htmlFormulario+="<div class=\"row\" style=\"display:none\">";
			htmlFormulario+="<div class=\"col-md-6 mb-3\">";
			htmlFormulario+="<label for=\"firstName\">Nombre</label>";
			htmlFormulario+="<input type=\"text\" class=\"form-control\" id=\"firstName\" placeholder=\"\" value=\"\" required=\"\">";
			htmlFormulario+="<div class=\"invalid-feedback\">El nombre es requerido.</div>";
			htmlFormulario+="</div>";
			htmlFormulario+="<div class=\"col-md-6 mb-3\">";
			htmlFormulario+="<label for=\"lastName\">Apellido</label>";
			htmlFormulario+="<input type=\"text\" class=\"form-control\" id=\"lastName\" placeholder=\"\" value=\"\" required=\"\">";
			htmlFormulario+="<div class=\"invalid-feedback\">El Apellido es requerido.</div>";
			htmlFormulario+="</div>";
			htmlFormulario+="</div>";
			htmlFormulario+="</div>";
			
		}
		
		try {
			// CARGO EL HTML PARA MOSTRAR SI HAY CARRITO
			Producto prod = new Producto();
			ProductoDAO pdao = new ProductoDAO();
			
			carrito = (ArrayList<ProductoCarrito>) session.getAttribute("carrito");
			
			carrito.get(0);
			double total = 0;
			
			//RECORRO EL ARRAY Y LO MUESTRO COMO TARJETAS
			for(ProductoCarrito prodC: carrito) {
				
				cantCarrito=cantCarrito+prodC.getCantidad();
				
				prod = pdao.obtenerUnProducto(prodC.getId());

				htmlCarrito+="<div class=\"card-body card-prod-pago\" id=\"cardcarritoprod"+prodC.getId()+"\" style=\"padding: 0;\">";
				htmlCarrito+="<div class=\"container\">";
				htmlCarrito+="<div class=\"row\">";
				htmlCarrito+="<div class=\"col\">";
				htmlCarrito+="<img style=\"max-width:85px\" src=\""+prod.getURLImagen()+"\">";
				htmlCarrito+="</div>";
				htmlCarrito+="<div class=\"col-6\">";
				htmlCarrito+="<span class=\"row\">" + prodC.getCantidad() + " x " + prod.getNombre() + "</span>";
				htmlCarrito+="<div class=\"col-6\">";
				//htmlCarrito+="<button type=\"button\" id=\"cantidad"+prodC.getId()+"\" class=\"btn btn-secondary\" style=\"min-width:40px\" disabled>"+prodC.getCantidad()+"</button>";
				htmlCarrito+="</div>";
				htmlCarrito+="</div>";
				htmlCarrito+="<div class=\"col\" style=\"text-align: right;\">";
				total = total + (prod.getPrecio()*prodC.getCantidad());
				htmlCarrito+="$ "+String.format(Locale.ROOT, "%.2f", prod.getPrecio()*prodC.getCantidad());
				htmlCarrito+="</div>";
				htmlCarrito+="</div>";
				htmlCarrito+="</div>";
				htmlCarrito+="</div>";

				
			}
			
			htmlCarrito+="<br>";
			htmlCarrito+="<hr>";
			htmlCarrito+="<div style=\"text-align: right;\"><h3>Total $ "+String.format(Locale.ROOT, "%.2f", total)+"</h3></div>";
			
			request.setAttribute("carrito", htmlCarrito);
			request.setAttribute("carritoCant", cantCarrito);
			request.setAttribute("formulario", htmlFormulario);
			
			RequestDispatcher rd = request.getRequestDispatcher("pago.jsp");
			rd.forward(request, response);

		}catch(Exception e){
			System.out.println(e);
			response.sendRedirect("Index.jsp");
		}
		
		
	}

}
