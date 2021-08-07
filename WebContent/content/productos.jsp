<%@ page import="java.util.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="dao.CompraDAO" %>
<%@ page import="dao.Utilidades" %>
<%@ page import="dao.ClienteDAO" %>
<%@ page import="dao.ProductoDAO" %>
<%@ page import="entities.Compra" %>
<%@ page import="entities.DatosVenta" %>
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
    <link rel="stylesheet" href="./css/admin.css">
</head>

<%
String productos = "";
if(request.getAttribute("productos")!=null){
	productos = request.getAttribute("productos").toString();
} else {
	%>
	<jsp:include page="/ServletProductos" />
	<%
}
%>
<body>

<h1>Productos</h1>
<div class="container pt-2" id="tablaDiv" style="display:block;max-width: 100%;margin-top: -95px;" >
<h4 style=" padding-left: 50px; text-align: center; color: grey" id=""></h4>
      		<div style="padding-top: 20px; padding-left: 0px; display: flex; justify-content: flex-end">	
				<div style="width:835px"></div>
				<button type="button" class="btn btn-success btn-lg" onClick="addProducto()" style="margin-right: 41%;width: 400px;">Añadir Producto</button>
	      		<div class="form-group" style=" max-width: 150px;" id="paginacion">
	      		<label for="ProductosMod" style="display:inline;">Mostrar</label>
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
             <div class="card-header"><h3 id="nombreTabla">Productos</h3></div>
            <input type="text" id="SearchProductos" class="form-control" onkeyup="buscaProductos()" placeholder="Buscar...">
            <div class="card-block p-0">
                <table class="table table-bordered table-sm m-0" id="tablaProductos">
                    <thead class="">
                        <tr>
                            <th style="width: 200px">Nombre</th>
                            <th style="width: 200px">Descripcion</th>
                            <th style="width: 110px">Precio Unitario($)</th>
                            <th style="width: 60px">Stock</th>
                            <th style="width: 300px">URL Imagen</th>
                            <th style="width: 115px">Estado</th>
                            <th style="width: 130px">Categoria</th>
                            <th style="width: 130px">Marca</th>
                            <th style="width: 60px">Guardar Cambios</th>
                        </tr>
                    </thead>
                    <tbody id="tbody">
                    <%=productos%>
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
  
<!--  MODAL CON CONFIRMACION -->
	<div id="infoModal" class="modal animated bounceIn" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	  <div class="modal-dialog">
	    <div class="modal-content" style="width: 100%">
	      <div class="modal-header" style="justify-content: normal;"><img src="./imgs/logo.png" width="50px" height="50px" title="" style="margin-right:10px">
	        <h1 id="tituloModalInfo" class="modal-title"></h1>
	      </div>
	      <div class="modal-body">
	        <h4 id="causaModalInfo"></h4>
	        <br>
	        <input type="hidden" id="idProductoParaAccion">
	        <div class="alert alert-danger" role="alert" id="blankFields" style="display:none; width: 100%;justify-content: center;padding-top:10px">
				Complete todos los campos.
			</div>
	      </div>
	      <div class="modal-footer">
	        <button class="btn btn-block btn-danger" data-dismiss="modal" style="text-align:center;" id="buttonModalInfo">Cerrar</button>
	        <button class="btn btn-block btn-danger" style="text-align:center;margin-top:0" id="buttonModalAccion">CANCELAR</button>
	      </div>
	    </div>
	  </div>
	</div>
<!--  MODAL -->
<!--  MODAL NUEVA CAT Y MARCA -->
	<div id="nuevoModal" class="modal animated bounceIn" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	  <div class="modal-dialog">
	    <div class="modal-content" style="width: 100%">
	      <div class="modal-header" style="justify-content: normal;"><img src="./imgs/logo.png" width="50px" height="50px" title="" style="margin-right:10px">
	        <h1 id="tituloModalNuevo" class="modal-title"></h1>
	      </div>
	      <div class="modal-body">
	        <h4 id="causaModalNuevo"></h4>
	        <br>
	        <div id="contenidoDetalleNuevo"></div>
	        <input type="hidden" id="tipoNuevo">
	        <div class="alert alert-danger" role="alert" id="blankFieldsNuevo" style="display:none; width: 100%;justify-content: center;padding-top:10px">
				Complete con un Nombre.
			</div>
	      </div>
	      <div class="modal-footer">
	        <button class="btn btn-block btn-danger" data-dismiss="modal" style="text-align:center;" id="buttonModalNuevo">Cerrar</button>
	        <button class="btn btn-block btn-info" style="text-align:center;margin-top:0" id="buttonModalAccionNuevo">Agregar</button>
	      </div>
	    </div>
	  </div>
	</div>
<!--  MODAL -->

  <div style="height:100%;" >
	</div>
	
<script src="js/bootstrap-select.min.js"></script>
<script src="js/admin.js"></script>


<script src="js/page.js"></script>
<script type="text/javascript">
	getPagination('#tablaProductos');
</script>


</body>
</html>