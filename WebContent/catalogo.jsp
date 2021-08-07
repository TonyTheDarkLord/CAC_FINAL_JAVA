<%@ page import="dao.ProductoDAO" %>
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
    
    
	<title>Computar - CAC Proyecto Final</title>
	<%	
	String catalogo = null;
	String categorias = null;
	String marcas = null;
	String categoriasFiltros = null;
	String carritoCant = "0";
	if(request.getAttribute("catalogo")!=null){
		catalogo = request.getAttribute("catalogo").toString();
		categorias = request.getAttribute("categorias").toString();
		marcas = request.getAttribute("marcas").toString();
		categoriasFiltros = request.getAttribute("categoriasFiltros").toString();
		carritoCant = request.getAttribute("carritoCant").toString();
	} else {
		%>
		<jsp:include page="/ServletBuscar" />
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
	    <%if(request.getParameter("busqueda")!=null){%>
	    	<input class="form-control mr-sm-2" type="search" placeholder="Buscar" aria-label="Buscar" name="busqueda" id="busqueda" value="<%=request.getParameter("busqueda")%>">
	    <%} else { %>
	      <input class="form-control mr-sm-2" type="search" placeholder="Buscar" aria-label="Buscar" name="busqueda" id="busqueda">
	      <%} %>
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
	<div class="container" style="max-width: 100%;">
	  <div class="row row-cols-1 row-cols-md-3">
	    <div class="col">
		   	 <ul id="ulFiltros">
			  	<h4>Filtros<a href="catalogo.jsp"><i class="fas fa-eraser" style="font-size: x-large;padding-left:7px"></i></a></h4>
			  	<hr style="padding:0">
			  	<li>
			         <h4>Categorias</h4>
		         </li>
		         <li>
		         	<%=categoriasFiltros %>
		         </li>
		         <br>
			  	<li>
			         <h4>Marcas</h4>
		         </li>
		         <li>
		         	<%=marcas %>
		         </li>
		         <br>
		         <li>
			         <h4>Precio</h4>
		         </li>
		         <li>
			         <form action="catalogo.jsp" id="filtrosBusquedaForm">
			         		<%	
			         		//LOGICA DE FILTROS ACA
			         		if(request.getParameter("idMarca")!=null){
			         			%>
			         			<input type="hidden" value="<%=request.getParameter("idMarca")%>" id="idMarca" name="idMarca">
			         			<%
			         		}else {%>
		         				<input type="hidden" value="" id="idMarca" name="idMarca">
		         			<%}
			         		if(request.getParameter("idCat")!=null){%>
			         			<input type="hidden" value="<%=request.getParameter("idCat")%>" id="idCat" name="idCat">
			         			
			         		<%} else {%>
			         			<input type="hidden" value="" id="idCat" name="idCat">
			         			<%}%>
			         		<%
			         		if(request.getParameter("busqueda")!=null){
			         			%>
			         			<input type="hidden" value="<%=request.getParameter("busqueda")%>" name="busqueda">
			         			<%
			         		}%>
			         		<%
			         		if(request.getParameter("MinP")!=null){
			         			%>
			         			<input type="number" style="width:180px" class="form-control mr-sm-2" placeholder="Minimo" value="<%=request.getParameter("MinP")%>" name="MinP"><div style="width:10px; height:10px"></div>
			         			<%
			         		} else {
			         			%>
			         			<input type="number" style="width:180px" class="form-control mr-sm-2" placeholder="Minimo" name="MinP"><div style="width:10px; height:10px"></div>
			         			<%
			         		}
			         		if(request.getParameter("MaxP")!=null){
			         			%>
			         			<input type="number" style="width:180px" class="form-control mr-sm-2" placeholder="Maximo" value="<%=request.getParameter("MaxP")%>" name="MaxP"><div style="width:10px; height:10px"></div>
			         			<%
			         		} else {
			         			%>
			         			<input type="number" style="width:180px" class="form-control mr-sm-2" placeholder="Minimo" name="MaxP"><div style="width:10px; height:10px"></div>
			         			<%
			         		}
			         		 %>
			         		<select class="form-control" style="width:180px" id="Ord" name="Ord">
				         		<option value="-1" selected>Ordenar por...</option>
				         		<option value="precioMe">Menor precio</option>
				         		<option value="precioMa">Mayor precio</option>
				         		<option value="fechaMe">Mas viejo primero</option>
				         		<option value="fechaMa">Mas nuevo primero</option>
			         		</select>
			         		<div style="width:10px; height:10px"></div>
			         		<%
			         		if(request.getParameter("Ord")!=null && request.getParameter("Ord")!="-1"){
			         			%>
			         			<script>document.getElementById('Ord').value = "<%=request.getParameter("Ord")%>"</script>
			         			<%
			         		}%>
			         		
				          <button type="submit" style="width:180px" class="btn btn-primary my-2 my-sm-0" style="height:39px">Filtrar</button>
			         </form>
		         </li>
	          </ul>
	    </div>
	    <div class="col-8">
	    	<div style="display:flex">
	    	<h4 style="padding-left:8px">Productos  | <h4 id="ubicacionTree"></h4> </h4>
	    	</div>
	    	<script>
		    var lugar ="";
		    var paso = false;
		    if(document.getElementById('idCat').value != ""){
		    	paso=true;
		    	lugar+= '\xa0' + document.getElementById('linkCat'+document.getElementById('idCat').value).innerHTML
		    }
		    if(paso == true){
		    	if(document.getElementById('idMarca').value != ""){
			    	lugar+= ">";
			    	lugar+= document.getElementById('linkMarca'+document.getElementById('idMarca').value).innerHTML
		    	}
		    } else {
		    	if(document.getElementById('idMarca').value != ""){
		    		lugar+= '\xa0' + document.getElementById('linkMarca'+document.getElementById('idMarca').value).innerHTML
		    	}
		    }
		    if(lugar!=""){
		    	document.getElementById('ubicacionTree').innerHTML =lugar
		    }
		    </script>
	    	<hr style="margin-bottom:0">
	    	<div class="row row-cols-1 row-cols-md-3" style="margin-top: -9px;">
	    	 <%
	    	 String catalogovacio = "";
	    	 catalogovacio+="<div style=\"text-align:center;padding-top: 100px;padding-left: 10%;\">";
	    	 catalogovacio+="<h2>NO SE ENCONTRARON PRODUCTOS PARA LA BUSQUEDA ESPECIFICADA</h2>";
	    	 catalogovacio+="<h3><a href=\"catalogo.jsp\">Volver a la tienda <i class=\"fas fa-shopping-basket\"></i></a></h3>";
	    	 catalogovacio+="</div>";
	    	 
	    	 if(request.getAttribute("catalogo").toString().matches(catalogovacio)){ %>
					<script>document.getElementById("ulFiltros").style.display = "none"</script>
				<% }%>
				<%=catalogo %>
			</div>
		</div>
	    <div class="col">
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