<%@ page import="dao.Utilidades" %>
<%@ page import="entities.Producto" %>
<%@ page import="entities.Provincia" %>
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
    
	<title>Computar - Registro</title>
	<% 
	Utilidades utildao = new Utilidades();
	ArrayList<Provincia> provincias = new ArrayList<Provincia>();
	ArrayList<Provincia> localidades = new ArrayList<Provincia>();
	if(session.getAttribute("id_usuario")!=null && session.getAttribute("id_usuario")!=""){
			
		if(session.getAttribute("id_cli") != null){
		%>
    	<script>
    	location.href = "cuenta.jsp";
    	</script>
    	<%
		} else {
			%>
	    	<script>
	    	location.href = "admin.jsp";
	    	</script>
	    	<%
		}
	}
	String carritoCant = "0";
	try{
		%>
		<jsp:include page="/ServletRegistro" />
		<%
		carritoCant = request.getAttribute("carritoCant").toString();
	} catch (Exception e){}
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
	  <div class="row">
	    <div class="col-sm">
		    <div class="card">
			    <h5 class="card-header"><img class="my-lg-0" src="./imgs/logo.png" width="40px" height="40px">         Registrarse</h5>
				    <div class="card-body">
				      	<form id="registerForm" method="post" action="ServletRegistro">
				      	<input type="hidden"  name="registrar" id="registrar" value="registrar">
						  <div class="form-group">
						    <label for="InputEmail">Email</label>
						    <input type="email" class="form-control userReg" name="user" id="InputEmailRegistro" aria-describedby="emailHelp"  required oninvalid="this.setCustomValidity('Este campo no puede estar vacio.')" oninput="this.setCustomValidity('')" >
						  	<small class="text-muted">Usuario para ingresar a la tienda.</small>
						  </div>
						  <div class="form-group">
						    <label for="InputPassword">Contraseña</label>
						    <input type="password" class="form-control" name="pass" id="InputPassword" minlength="5"  required oninvalid="this.setCustomValidity('Este campo no puede estar vacio y como minimo debe tener 5 caracteres.')" oninput="this.setCustomValidity('')">
						  </div>
						  <div class="form-group">
						    <label for="InputPasswordC">Confirme Contraseña</label>
						    <input type="password" class="form-control passConfirm" name="passC" id="InputPasswordC" minlength="5"  required oninvalid="this.setCustomValidity('Las contraseñas no coinciden.')" oninput="this.setCustomValidity('')">
						  </div>
						  <div class="form-group">
						    <label for="InputNombre">Nombre</label>
						    <input type="text" class="form-control" name="nombre" id="InputNombre" aria-describedby=""  required oninvalid="this.setCustomValidity('Este campo no puede estar vacio.')" oninput="this.setCustomValidity('')" >
						  </div>
						  <div class="form-group">
						    <label for="InputApellido">Apellido</label>
						    <input type="text" class="form-control" name="apellido" id="InputApellido" aria-describedby=""  required oninvalid="this.setCustomValidity('Este campo no puede estar vacio.')" oninput="this.setCustomValidity('')" >
						  </div>
						  <div class="form-group">
						    <label for="InputDNI">DNI</label>
						    <input type="number" class="form-control" name="dni" id="InputDNI" aria-describedby=""  required oninvalid="this.setCustomValidity('Este campo no puede estar vacio.')" oninput="this.setCustomValidity('')" >
						  </div>
						  <div class="form-group">
						    <label for="InputTelefono">Telefono</label>
						    <input type="number" class="form-control" name="telefono" id="InputTelefono" aria-describedby=""  required oninvalid="this.setCustomValidity('Este campo no puede estar vacio.')" oninput="this.setCustomValidity('')" >
						  </div>
						  <div class="form-group">
						  	<label for="provincia">Provincia</label>
						  	<select name="provincia" class="custom-select d-block w-100 provincia required" id="provincia" required  oninvalid="this.setCustomValidity('Seleccione una opcion por favor.')" oninput="this.setCustomValidity('')">
								<option value="" selected>Elija una opcion</option>
								<% provincias = utildao.obtenerProvincias();
								for(Provincia prov:provincias) {
									%>
										<option value="<%=prov.getId()%>"><%=prov.getNombre()%></option>
									<%
								}%>
							</select>
						  </div>
						  <div class="form-group">
						  	<label for="localidad">Localidad</label>
						  	<select name="localidad" class="custom-select d-block w-100 localidad required" id="localidad" required disabled oninvalid="this.setCustomValidity('Seleccione una opcion por favor.')"  oninput="this.setCustomValidity('')">
								<option value="" selected>Elija una opcion</option>
							</select>
						  </div>
						  <h2><button type="submit" onclick="" class="btn btn-primary btn-lg btn-block">REGISTRARSE!</button></h2>
						  <br>
						</form>
						<br>
						<div class="alert alert-danger" role="alert" id="UserAlreadyInUse" style="display:none; width: 100%;justify-content: center;padding-top:10px">
							El usuario ya se encuentra registrado.
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
	<script src="./js/js.cookie-2.2.1.min.js" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script src="./js/utilidades.js"></script>
    
<%
if( request.getParameter("Error") != null ){
	
	if( request.getParameter("Error").toString().matches("1")){
	%>
	<script>
		document.getElementById("UserAlreadyInUse").style.display = "block";
	</script>
	<%
	} 
} else {
	%>
	<script>
		document.getElementById("UserAlreadyInUse").style.display = "none";
	</script>
	<%
}
if( request.getParameter("Redirect")!= null){
	%>
	<script>
	document.getElementById("redirect").value = "<%=request.getParameter("Redirect").toString()%>";
	</script>
	<%
}
%>
</body>
</html>