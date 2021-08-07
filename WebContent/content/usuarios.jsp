<%@ page import="java.util.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="dao.Utilidades" %>
<%@ page import="dao.ClienteDAO" %>
<%@ page import="entities.Usuario" %>
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
    <link rel="stylesheet" href="./css/admin.css">
</head>

<%
String usuarios = "";
if(request.getAttribute("usuarios")!=null){
	usuarios = request.getAttribute("usuarios").toString();
} else {
	%>
	<jsp:include page="/ServletUsuarios" />
	<%
}
%>
<body>

<h1>Usuarios<button type="button" style="width:50px;height:50px;display: inline;" class="form-control btn-info" onClick="actualizarUsuarios()"><i class="fas fa-sync-alt"></i></button></h1>
<div class="container pt-2" id="tablaDiv" style="display:block;max-width: 100%;margin-top: -95px;" >
<h4 style=" padding-left: 50px; text-align: center; color: grey" id=""></h4>	
      		<div style="padding-top: 20px; padding-left: 0px; display: flex; justify-content: flex-end">	
      		
				<div style="width:835px"></div>
	      		<div class="form-group" style=" max-width: 150px;" id="paginacion">
	      		<label for="UsuariosMod" style="display:inline;">Mostrar</label>
		      		<select class="form-control" name="state" id="maxRows">
					  <option value="5">5</option>
					  <option value="10" selected>10</option>
					  <option value="15">15</option>
					  <option value="20">20</option>
					  <option value="50">50</option>
					  <option value="100">100</option>
					</select>
				</div>
			</div>
			<br>
        <div class="card mb-5" id="EspacioTabla">
             <div class="card-header"><h3 id="nombreTabla">Usuarios</h3></div>
            <input type="text" id="SearchUsuarios" class="form-control" onkeyup="buscaUsuarios()" placeholder="Buscar por DNI del cliente..">
            <div class="card-block p-0">
                <table class="table table-bordered table-sm m-0" id="tablaUsuarios">
                    <thead class="">
                        <tr>
                        	<th style="display:none">id</th>
                            <th style="width: 100px">DNI Cliente</th>
                            <th style="width: 150px">Nombre</th>
                            <th style="width: 150px">Apellido</th>
                            <th style="width: 119px">Telefono</th>
                            <th style="width: 130px">Provincia</th>
                            <th style="width: 155px">Localidad</th>
                            <th style="width: 160px">Usuario</th>
                            <th style="width: 160px">Contraseña</th>
                            <th style="width: 160px">Estado</th>
                            <th style="width: 160px">Accion</th>
                        </tr>
                    </thead>
                    <tbody id="tbody">
                    <%=usuarios%>
                    </tbody>
                </table>
            </div>
            <div class='pagination-container' >
				<nav>
				<ul class="pagination justify-content-end mt-3 mr-3">
				   <!--	Here the JS Function Will Add the Rows -->
				  </ul>
				</nav>
			</div>

			</div>
			<br>
  </div>
  <!--  MODAL -->
<div id="fsModal" class="modal animated bounceIn" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	  <div class="modal-dialog">
	    <div class="modal-content" style="width: 100%">
	      <div class="modal-header" style="justify-content: normal;"><img src="./imgs/logo.png" width="50px" height="50px" title="" style="margin-right:10px">
	        <h1 id="myModalLabel" class="modal-title">Detalle de Usuario</h1>
	      </div>
	      <div class="modal-body" style="">
	        <h4 id="causaModal">Usuarios</h4>
	        <hr>
	        <br>
	        <div id="contenidoDetalle"></div>
	      </div>
	      <div class="modal-footer">
	        <button class="btn btn-block btn-danger" data-dismiss="modal" style="text-align:center;" id="buttonModal">Cerrar</button>
	      </div>
	    </div>
	  </div>
	</div>
<!--  MODAL -->

<!--  MODAL CON CONFIRMACION -->
<div id="infoModal" class="modal animated bounceIn" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	  <div class="modal-dialog">
	    <div class="modal-content" style="width: 100%">
	      <div class="modal-header" style="justify-content: normal;"><img src="./imgs/logo.png" width="50px" height="50px" title="" style="margin-right:10px">
	        <h1 id="tituloModalInfo" class="modal-title"></h1>
	      </div>
	      <div class="modal-body" style="height:200px">
	        <h4 id="causaModalInfo"></h4>
	        <br>
	        <input type="hidden" id="idClienteParaAccion">
	        <div id="contenidoDetalle"></div>
	      </div>
	      <div class="modal-footer">
	        <button class="btn btn-block btn-danger" data-dismiss="modal" style="text-align:center;" id="buttonModalInfo">Cerrar</button>
	        <button class="btn btn-block btn-danger" data-dismiss="modal" style="text-align:center;margin-top:0" id="buttonModalAccion">CANCELAR</button>
	      </div>
	    </div>
	  </div>
	</div>
<!--  MODAL -->
  
  <div style="height:100%;" >
	</div>
	
<script src="js/bootstrap-select.min.js"></script>
<script src="js/admin.js"></script>

<script>
function cambia(){
	$("#fsModal").modal();
}
</script>	

	
        <!--<script src="js/cursos.js"></script>-->
        <script src="js/page.js"></script>
<script type="text/javascript">
	getPagination('#tablaUsuarios');
</script>


</body>
</html>