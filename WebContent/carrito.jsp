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
	String carritoCant = "0";
	String categorias = null;
	if(request.getAttribute("categorias")!= null && request.getAttribute("categorias")!= ""){
		categorias = request.getAttribute("categorias").toString();
		carritoCant = request.getAttribute("carritoCant").toString();
	}else {
		%>
		<jsp:include page="/ServletCarrito" />
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
	    <div class="form-inline my-2 my-lg-0">
	      <input class="form-control mr-sm-2" type="search" placeholder="Buscar" aria-label="Buscar" name="busqueda" id="busqueda">
	      <button class="btn btn-success my-2 my-sm-0" type="button" onClick="busqueda()">Buscar</button>
	    </div>
	    <!-- </form> -->
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
			    	<h3>Mi Carrito <button id="cantidadEnCarro" type="button" class="btn btn-secondary" style="min-width:40px" value="<%=carritoCant %>" disabled><%=carritoCant %></button></h3>
			    	<hr width="100%">
			    	</div>
				  <div id="carritodata" class="card-body">
				  <%=request.getAttribute("carrito")%>
				    <br>
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
</body>
</html>