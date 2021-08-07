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
    
	<title>Computar - Iniciar Sesion</title>
	<% 
	if(request.getParameter("logout")!=null){
		%>
		<jsp:include page="/ServletLogin" />
		<%
	} else {
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
	}
	String carritoCant = "0";
	try{
		%>
		<jsp:include page="/ServletLogin" />
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
	    </div>
	    <div class="col-sm">
		    <div class="card">
			    <h5 class="card-header"><img class="my-lg-0" src="./imgs/logo.png" width="40px" height="40px">         Iniciar Sesion</h5>
				    <div class="card-body">
				      	<form id="loginForm" method="post" action="ServletLogin">
				      	<input type="hidden" name="redirect" id="redirect">
						  <div class="form-group">
						    <label for="InputEmail">Email</label>
						    <input type="email" class="form-control" name="user" id="InputEmail" aria-describedby="emailHelp"  required oninvalid="this.setCustomValidity('Este campo no puede estar vacio.')" oninput="this.setCustomValidity('')" >
						  </div>
						  <div class="form-group">
						    <label for="InputPassword">Contraseña</label>
						    <input type="password" class="form-control" name="pass" id="InputPassword"  required oninvalid="this.setCustomValidity('Este campo no puede estar vacio.')" oninput="this.setCustomValidity('')">
						  </div>
						  <div class="form-group form-check">
						    <input type="checkbox" class="form-check-input" name="Check" id="Check">
						    <label class="form-check-label" for="Check">Recordar usuario</label>
						  </div>
						  <button type="submit" onclick="saveuser()" class="btn btn-primary">Entrar</button>
						  <br>
						</form>
						<br>
						<div class="alert alert-danger" role="alert" id="PassIncorrecta" style="display:none; width: 100%;justify-content: center;padding-top:10px">
							Usuario/Contraseña Incorrectos.
						  </div>
					    <div class="alert alert-danger" role="alert" id="LoginRequested" style="display:none; width: 100%;justify-content: center;padding-top:10px">
						    Acceda para continuar.
					    </div>
					</div>
					<hr width="100%" style="margin:0">
					<h5 class="card-header">Registrese</h5>
					<div class="card-body">
						<form method="post" action="">
							<div class="form-group">
								<button type="button" onClick="location.href = 'registro.jsp';" class="btn btn-primary btn-lg btn-block">Registrarse</button>
							</div>
						</form>
					</div>
				</div>
		    </div>
	    <div class="col-sm">
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
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.18/dist/sweetalert2.all.min.js"></script>
    <script src="./js/utilidades.js"></script>
    <script src="./js/login.js"></script>
    
<%

if( request.getParameter("Error") != null ){
	
	if( request.getParameter("Error").toString().matches("1")){
	%>
	<script>
		document.getElementById("PassIncorrecta").style.display = "block";
	</script>
	<%
	} 
	
	if( request.getParameter("Error").toString().matches("2")){
		%>
		<script>
			document.getElementById("LoginRequested").style.display = "block";
		</script>
		<%
	}
	
}
if( request.getParameter("Redirect")!= null){
	%>
	<script>
	document.getElementById("redirect").value = "<%=request.getParameter("Redirect").toString()%>";
	</script>
	<%
}
if( request.getParameter("Success")!= null){
	if( request.getParameter("Success").toString().matches("1")){
		%>
		<script>
		document.getElementById("LoginRequested").style.display = "none";
			Swal.fire(
				  'Usted se registro correctamente!',
				  'Ahora puede acceder al sitio.',
				  'success'
				)
		</script>
		<%
	}
}
%>
<script>
if(Cookies.get('user')!="undefined" && Cookies.get('user')!="" && Cookies.get('user')!=null){
	document.getElementById("Check").checked = true
	document.getElementById("InputEmail").value = Cookies.get('user')
}
</script>
<script>
$('#busqueda').keyup(function (e) {
    if (e.keyCode === 13) {
       busqueda()
    }
  });
</script>
</body>
</html>