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
	int cantCarrito = 0;
	String carrito = null;
	String formulario = null;
	
	if(request.getAttribute("carrito")!= null && request.getAttribute("carrito")!= ""){
		
		if(session.getAttribute("id_usuario")!=null && session.getAttribute("id_usuario")!=""){
			
			carrito = request.getAttribute("carrito").toString();
			formulario = request.getAttribute("formulario").toString();
			cantCarrito = Integer.parseInt(request.getAttribute("carritoCant").toString());
		
		} else {
			%>
			<script>
		    	location.href = "login.jsp?Redirect=pago.jsp";
		    </script>
			<%
		}
		
	}else {
		%>
		<jsp:include page="/ServletPayment" />
		<%
	}
	if(cantCarrito == 0){
			%>
			<script>
		    	location.href = "Index.jsp";
		    </script>
			<%
		}
	 %>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	  <a href="Index.jsp"><img src="./imgs/logo.png" width="50px" height="50px"></a>
	</nav>
	<br>
	<br>
	<div class="container">
	  <div class="row">
	    <div class="col-6">
	    <a href="carrito.jsp"><i class="fas fa-arrow-left"></i> Volver al carrito</a>
	      <%= formulario %>
	    </div>
	    <div class="col-6">
	    <div class="card">
  			<div class="card-body">
  			<h3>Productos <button type="button" class="btn btn-secondary" style="min-width:40px" value="<%=cantCarrito %>" disabled><%=cantCarrito %></button></h3>
  			<hr>
	      		<%= carrito %>
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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js" crossorigin="anonymous"></script>
    <script src="./js/utilidades.js"></script>
	<script>
	$('#busqueda').keyup(function (e) {
	    if (e.keyCode === 13) {
	       busqueda()
	    }
	  });
	$('#firstName').keyup(function (e) {
	       if(document.getElementById("firstName").value==""){
	    	   $("#firstName").addClass("red");
	       } else {
	    	   $("#firstName").removeClass("red");
	       }
	  });
	$('#lastName').keyup(function (e) {
	       if(document.getElementById("lastName").value==""){
	    	   $("#lastName").addClass("red");
	       } else {
	    	   $("#lastName").removeClass("red");
	       }
	  });
	$('#address').keyup(function (e) {
	       if(document.getElementById("address").value==""){
	    	   $("#address").addClass("red");
	       } else {
	    	   $("#address").removeClass("red");
	       }
	  });
	$('#zip').keyup(function (e) {
	       if(document.getElementById("zip").value==""){
	    	   $("#zip").addClass("red");
	       } else {
	    	   $("#zip").removeClass("red");
	       }
	  });
	$('#cc-name').keyup(function (e) {
	       if(document.getElementById("cc-name").value==""){
	    	   $("#cc-name").addClass("red");
	       } else {
	    	   $("#cc-name").removeClass("red");
	       }
	  });
	$('#cc-number').keyup(function (e) {
	       if(document.getElementById("cc-number").value==""){
	    	   $("#cc-number").addClass("red");
	       } else {
	    	   $("#cc-number").removeClass("red");
	       }
	  });
	$('#cc-cvv').keyup(function (e) {
	       if(document.getElementById("cc-cvv").value==""){
	    	   $("#cc-cvv").addClass("red");
	       } else {
	    	   $("#cc-cvv").removeClass("red");
	       }
	  });
	$('#cc-expiration').keyup(function (e) {
	       if(document.getElementById("cc-expiration").value==""){
	    	   $("#cc-expiration").addClass("red");
	       } else {
	    	   $("#cc-expiration").removeClass("red");
	       }
	  });
	</script>
</body>
</html>