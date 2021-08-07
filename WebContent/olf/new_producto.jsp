<%@ page import="dao.ProductoDAO" %>
<%@ page import="dao.MarcaDAO" %>
<%@ page import="dao.CategoriaDAO" %>
<%@ page import="entities.Producto" %>
<%@ page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
    <link rel="stylesheet" href="./css/tienda.css">
    
    
	<title>Computar - Productos</title>
	<%
		
		String categoria = null;
		String marca = null;
		String categorias = null;
		Producto prod = new Producto();
		ArrayList<Producto> prodsRel = new ArrayList<Producto>();
	
		 if (request.getParameter("id") != null && request.getParameter("id") != ""){
			 
			 if(request.getAttribute("categorias")!= null && request.getAttribute("categorias")!= ""){
			 
				 prod = (Producto) request.getAttribute("prod");
				 prodsRel =  (ArrayList<Producto>) request.getAttribute("prodsRel");
				 categoria = request.getAttribute("categoria").toString();
				 marca = request.getAttribute("marca").toString();
				 categorias = request.getAttribute("categorias").toString();
				 
			 }else{
				 %>
					<jsp:include page="/ServletProducto" />
				<%
			 }
		 }else{
			 %>
		    	<script>
		    	location.href = "new_index.jsp";
		    	</script>
	    	<%
		 }
	%>
</head>
<body>
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
	      <button class="btn btn-outline-success my-2 my-sm-0" type="button" onClick="busqueda()">Buscar</button>
	    </div>
	    <!-- </form> -->
	    <a href="carrito.jsp"><i class="fas fa-shopping-cart" style="font-size: x-large;padding-left:7px"></i></a>
        <a href="cuenta.jsp"><i class="fas fa-user" style="font-size: x-large;padding-left:7px"></i></a>
	  </div>
	</nav>
	
	<br>
	<br>
	
	<div class="container">
		<div class="card">
			<div class="container-fliud">
				<div class="wrapper row" style="padding:20px">
					<div class="col-md-6">
						<div>
							<img style="width: 100%;height: 100%;" src="<%=prod.getURLImagen()%>"/>
						</div>
						
					</div>
					<div class="col-md-6">
						<h3><span><%= prod.getNombre()%></span></h3>
						<hr>
						<div style="padding-bottom: 10px;padding-top: 10px">
							<h5><span><a href="catalogo.jsp?idMarca=<%= prod.getIdMarca() %>"><%= marca %></a></span></h5>
						</div>
						<div style="padding-bottom: 10px;">
							<h3><span>$ <%= prod.getPrecio() %></span></h3>
						</div>
						<div style="padding-bottom: 50px;">
							<h5><span>Cantidad:
							<%	if(prod.getStock()!=0){%>
					        	<select>
					        	<%
						        for(int i = 1; i <= prod.getStock(); ++i){%>
							          <option value="<%= i%>"><%= i%></option>
							          <%}%>
					        	</select>
					        	<%
					        } else {
					        	 %>
					        	 	<select disabled>
						          	<option value="0"></option>
						          	</select>
						          <%
					        }
					        %></span></h5>
				        </div>
						<div>
							<button class="btn btn-primary btn-lg btn-block" type="button">Comprar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<br>
	<div class="container">
		<div class="card">
			<div class="container-fliud">
				<div class="wrapper row">
					<div class="col-md-12" style="padding:25px">
						<h3><span>Descripcion</span></h3>
						<hr>
						<p><%= prod.getDescripcion()%></p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
	  	<div class="row">
		   <div class="col">
		    </div>
		    <%
        		if(prodsRel.size()>1){
        	%>
		    <div class="col-12">
			    <br>
			    <br>
		    	<h3>Productos relacionados</h3>
		    	<br>
		    	<div class="card-deck">
		    	
		    	<%
	          		for(Producto rel: prodsRel){
	          			%>
	          			     <div class="card">
						        <img src="<%= rel.getURLImagen() %>" class="card-img-top" style="width: 100%;height: 10vw;object-fit: fit;">
						        <div class="card-body">
							      <h5><a href="window.location='producto.jsp?id=<%= rel.getId() %>'"><%= rel.getNombre() %></a></h5>
							      <p class="card-text">$ <%= rel.getPrecio() %></p>
							    </div>
							    <div class="card-footer">
							      <button type="button" onclick="window.location='producto.jsp?id=<%= rel.getId() %>'" class="btn btn-primary btn-lg btn-block">Ver Producto</button>
							    </div>
						  	</div>
	          			
	          			<%
	          		}
	         	%>
		    	</div>
		    	<%
	          		}	         
	         	%>
		    <div class="col">
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