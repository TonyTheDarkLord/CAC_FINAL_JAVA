<%@ page import="java.util.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="dao.CompraDAO" %>
<%@ page import="dao.Utilidades" %>
<%@ page import="dao.ClienteDAO" %>
<%@ page import="dao.ProductoDAO" %>
<%@ page import="entities.Compra" %>
<%@ page import="entities.DetallesVenta" %>
<%@ page import="entities.Usuario" %>
<%@ page import="entities.Producto" %>
<%@ page import="entities.Cliente" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
    <link rel="stylesheet" href="./css/tienda.css">
    
    
	<title>Computar - Mi Cuenta</title>
	<%
	int id_cli;
	ArrayList<Compra> compras = new ArrayList<Compra>();
	ArrayList<DetallesVenta> detallesV = new ArrayList<DetallesVenta>();
	CompraDAO cdao = new CompraDAO();
	ClienteDAO clidao = new ClienteDAO();
	Cliente cliente = new Cliente();
	Utilidades detalles = new Utilidades();
	
    if(session.getAttribute("id_usuario")!=null){
    	
    	if(Integer.parseInt(session.getAttribute("tipo_usuario").toString()) !=0){
    	
	    	id_cli = Integer.parseInt(session.getAttribute("id_cliente").toString());
	    	compras=cdao.obtenerComprasXCliente(id_cli);
	    	cliente = clidao.obtenerUnCliente(id_cli);
    	} else {
    		%>
        	<script>
        	location.href = "admin.jsp";
        	</script>
        	<%
    	}
    	
    } else {
    	%>
    	<script>
    	location.href = "login.jsp?Error=2";
    	</script>
    	<%
    }
    %>
	
	<%
	String categorias = null;
	String carritoCant = "0";
	if(request.getAttribute("categorias")!= null && request.getAttribute("categorias")!= ""){
		categorias = request.getAttribute("categorias").toString();
		carritoCant = request.getAttribute("carritoCant").toString();
	}else {
		%>
		<jsp:include page="/ServletCuenta" />
		<%
	}
	%>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	  <a href="Index.jsp"><img src="./imgs/logo.png" width="50px" height="50px"></a>
	  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	    <span class="navbar-toggler-icon"></span>
	  </button>
	
	  <div class="collapse navbar-collapse" id="navbarSupportedContent">
	    <ul class="navbar-nav mr-auto">
	      <li class="nav-item">
	        <a class="nav-link" href="catalogo.jsp">Productos</a>
	      </li>
	      <li class="nav-item dropdown">
	        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	          Categorias
	        </a>
	        <%= categorias %>
	    </ul>
	    <!-- <form class="form-inline my-2 my-lg-0"> -->
	    <div class="form-inline my-2 my-lg-0" style="margin-right: 40px;">
	      <input class="form-control mr-sm-2" type="search" placeholder="Buscar" aria-label="Buscar" name="busqueda" id="busqueda">
	      <button class="btn btn-success my-2 my-sm-0" type="button" onClick="busqueda()">Buscar</button>
	    </div>
	    <!-- </form> -->
	    <div style="position:absolute;right: 48px;"><a href="carrito.jsp" style="position:relative;">
	    <%if(Integer.parseInt(carritoCant) > 0) {
	    	%>
	    	<span style="position:absolute;top: -24px;left: -5px;" id="cantBadge" class="badge"><%=carritoCant %></span>
	     	<%
	     }
	     %>
	    <i style="position:absolute;left: -28px;top: -13px;font-size: x-large;" class="fas fa-shopping-cart"></i></a></div>
        <a href="cuenta.jsp"><i class="fas fa-user" style="font-size: x-large;padding-left:7px"></i></a>
	  </div>
	</nav>
	<br>
	<br>
	<div class="container">
	  <div class="row" style="display:block">
		    <div class="card">
			    <div class="container">
			    <div style="padding: 1.25rem;">
			    	<h3>Mi cuenta</h3>
			    	<hr width="100%">
			    	</div>
			    	<div style="display:flex;padding: 1.25rem;">
			    	<div>
					  <i class="fas fa-user" style="font-size:6rem"></i>
					</div>
					<div>
					  <h3 class="card-title" style="padding-left: 30px;"><%=cliente.getApellido()%>, <%=cliente.getNombre()%></h3>
					  <span style="padding-left: 30px;"><a onclick="cambiarPass()" href="javascript:void(0);">Cambio de contraseña</a></span><br>
					  <span style="padding-left: 30px;"><a href="login.jsp?logout=exit">Salir de la cuenta</a></span>
					  </div>
					</div>
				  <div class="card-body">
				    <hr width="100%">
				    <br>
				    <h3>Mis ultimas compras</h3>
				    <hr width="100%">
				    <% 
				    for(Compra compra:compras){
				    %>
				    <div class="accordion" id="accordion<%=compra.getId()%>">
					  <div class="card">
					    <div class="card-header" id="heading<%=compra.getId()%>">
					      <h2 class="mb-0">
					        <button class="btn btn-link btn-block text-left" type="button" data-toggle="collapse" data-target="#collapse<%=compra.getId()%>" aria-expanded="false" aria-controls="collapse<%=compra.getId()%>" style="text-decoration:none">
					          Orden #<%=compra.getId()%>
					        </button>
					      </h2>
					    </div>
					    <div id="collapse<%=compra.getId()%>" class="collapse" aria-labelledby="heading<%=compra.getId()%>" data-parent="#accordion<%=compra.getId()%>">
						<div class="card-body" style="padding: 1.25rem;height:auto;">
							<div style="padding-left: 1.25rem;padding-right: 1.25rem;">
								<span class="row">Pago:  <em> <%=detalles.obtenerEstadoPago(compra.getId())%></em></span>
								<br>
								<span class="row">Envio: <%=detalles.obtenerEstadoEnvio(compra.getId())%></span>
								<span class="row">Direccion de envio: <%=detalles.obtenerDireccionEnvio(compra.getId())%></span>
							</div>
	                     </div>
	                     <hr width="100%">
	                     <% 
				    	detallesV = detalles.obtenerProductosVenta(compra.getId());
				    	for(DetallesVenta d:detallesV){
				    		
				    		ProductoDAO pdao = new ProductoDAO();
				    		Producto prod = new Producto();
				    		prod = pdao.obtenerUnProducto(d.getId_prod());
				    	%>
					      <div class="card-body" style="padding: 0;">
					        <div class="container">
							  <div class="row" style="padding: 1.25rem;">
							    <div class="col">
							      <img style="max-width:200px" src="<%=prod.getURLImagen()%>">
							    </div>
							    <div class="col-6">
							      <span class="row"><h5><%=d.getCantidad()%></h5> x <%=prod.getNombre() %> </span>
							      <br>
							      <span class="row">Precio Unitario: $ <%=String.format(Locale.ROOT, "%.2f", d.getPrecio())%></span>
							      
							    </div>
							    <div class="col" style="text-align: right;">
							     $  <%=String.format(Locale.ROOT, "%.2f", d.getPrecio()*d.getCantidad())%>
							    </div>
							  </div>
							</div>
							
					      <hr width="100%" style="margin:0">
					      </div>
					    
					    <%
					    }
				    	%>
				    	
				    	</div>
				    	<div class="card-header" style="display:flex !important">
					      <div class="p-2 w-100 bd-highlight"><h2>TOTAL COMPRA</h2></div>
					      <div class="p-2 flex-shrink-1 bd-highlight"><h2>$<%= String.format(Locale.ROOT, "%.2f", compra.getTotal())%></h2></div>
					    
					    </div>
					    </div>
					    </div>
					    
					    <hr style="margin-top:0" width="100%">
				    	<%
				    	}%>
					  </div>
					    </div>
				  
			  </div>
		
	    </div>
	  </div>
	</div>
	<br>
	<br>
	<footer class="footer mt-auto py-3">
	<hr width="100%">
	  <div class="container">
	    	<h5>Computar tecnologia de punta</h5>
         	<p>Desde 1987 acercando los mejores productos al publico mas exigente.</p>
	  </div>
	</footer>
	

	<script src="https://code.jquery.com/jquery-3.5.1.min.js" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.18/dist/sweetalert2.all.min.js"></script>
    <script src="./js/utilidades.js"></script>
<script>
$('#busqueda').keyup(function (e) {
    if (e.keyCode === 13) {
       busqueda()
    }
  });
</script>
<%
if( request.getParameter("compraRealizada")!= null){
	%>
	<script>
	$("#collapse"+<%=request.getParameter("compraRealizada").toString()%>).addClass("show");
	</script>
	<%
}
%>
<%
if( request.getParameter("Transferencia")!= null){
	%>
	<script>
	Swal.fire(
			  'Compra realizada con exito!',
			  'A continuacion se detallan los datos para el pago. <br> CBU 9898989898989898 <br> Banco Santander Rio <br> CUIT 23-39707040-9 <br> Propietario Juan Pablo Toniolo',
			  'success'
			)
	</script>
	<%
}
%>
</body>
</html>