// TODO VENTA
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function actualizarVentas(){
	$('#content').load('content/ventas.jsp');
}

function buscaVentas(){
	 // Declare variables 
	  var input, filter, table, tr, td, i, txtValue;
	  input = document.getElementById("SearchVentas");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("tablaVentas");
	  tr = table.getElementsByTagName("tr");
	
	  // Loop through all table rows, and hide those who don't match the search query
	  for (i = 0; i < tr.length; i++) {
		  //FALTA MAXIMO MAXROWS, OSEA GIRO HASTA TOCAR EL MAXIMO DE ROWS SELECCIONADO
		td = tr[i].getElementsByTagName("td")[2];
	    if (td) {
	      txtValue = td.textContent || td.innerText || $(td).find("input").val();
	      if (txtValue.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    }
	  }

	input = document.getElementById("SearchVentas");
	filter = input.value.toUpperCase();
	  if(filter == null || filter == ""){
		  getPagination('#tablaVentas');
		  var ele = document.getElementsByClassName('page-item');
		  for (var i = 0; i < ele.length; i++ ) {
		      ele[i].style.display = "block";
		  }
	  } else {
		  var ele = document.getElementsByClassName('page-item');
		  for (var i = 0; i < ele.length; i++ ) {
		      ele[i].style.display = "none";
		  }
	  }
}

function obtenerDetalleDeVenta(id){
	
	$.ajax({url:'./ServletVentas',
	         type:'POST',
			 data:{id_venta : id},
			 success : function(responseData){
					document.getElementById("contenidoDetalle").innerHTML = responseData;
					$("#fsModal").modal();
			 }
	});
	
	
}

function cancelarVenta(id){
	
	document.getElementById("buttonModalAccion").style.display = "block";
	document.getElementById("tituloModalInfo").innerHTML = "CANCELAR VENTA";
	document.getElementById("causaModalInfo").innerHTML = "Seguro que desea cancelar la Venta #" +id+  " ? Esta accion es irreversible.";
	document.getElementById("idVentaParaAccion").value = id;
	$("#buttonModalAccion").removeClass("btn-info");
	$("#buttonModalAccion").addClass("btn-danger");
	document.getElementById("buttonModalAccion").innerHTML = "CANCELAR";
	document.getElementById("buttonModalAccion").outerHTML = document.getElementById("buttonModalAccion").outerHTML;
	document.getElementById("buttonModalAccion").addEventListener('click',function ()
	{
    confirmaCancelacionVenta();
    }); 

	$("#infoModal").modal();
	
}

function confirmaCancelacionVenta(){
		$.ajax({url:'./ServletVentas',
		         type:'POST',
				 data:{id_venta : document.getElementById("idVentaParaAccion").value, accion : "eliminar"},
				 success : function(responseData){
					    document.getElementById("tituloModalInfo").innerHTML = responseData;
						document.getElementById("causaModalInfo").innerHTML = "";
						document.getElementById("buttonModalAccion").style.display = "none";
						document.getElementById("buttonModalAccion").outerHTML = document.getElementById("buttonModalAccion").outerHTML;
						document.getElementById("buttonModalInfo").addEventListener('click',function ()
						{
					   	 $('#content').load('content/ventas.jsp');
					    }); 
						$("#infoModal").modal();
				 }
		})
	
};

function guardarCambiosVenta(id){
	
	document.getElementById("buttonModalAccion").style.display = "block";
	document.getElementById("tituloModalInfo").innerHTML = "Modificar Venta";
	document.getElementById("causaModalInfo").innerHTML = "Seguro que desea guardar los cambios realizados a la Venta #" +id+  " ?";
	document.getElementById("idVentaParaAccion").value = id;
	$("#buttonModalAccion").removeClass("btn-danger");
	$("#buttonModalAccion").addClass("btn-info");
	document.getElementById("buttonModalAccion").innerHTML = "Modificar";
	document.getElementById("buttonModalAccion").outerHTML = document.getElementById("buttonModalAccion").outerHTML;
	document.getElementById("buttonModalAccion").addEventListener('click',function ()
	{
    confirmaGuardarCambiosVenta();
    }); 

	$("#infoModal").modal();
	
}

function confirmaGuardarCambiosVenta(){
	var id = document.getElementById("idVentaParaAccion").value;
		$.ajax({url:'./ServletVentas',
		         type:'POST',
				 data:{id_venta : document.getElementById("idVentaParaAccion").value, accion : "modificar", estado_envio : document.getElementById("estadoEnvioSelect"+id).value, estado_pago : document.getElementById("estadoPagoSelect"+id).value, direccion : document.getElementById("direccion"+id).value},
				 success : function(responseData){
					    document.getElementById("tituloModalInfo").innerHTML = responseData;
						document.getElementById("causaModalInfo").innerHTML = "";
						document.getElementById("buttonModalAccion").style.display = "none";
						document.getElementById("buttonModalAccion").outerHTML = document.getElementById("buttonModalAccion").outerHTML;
						document.getElementById("buttonModalInfo").addEventListener('click',function ()
						{
					   	 $('#content').load('content/ventas.jsp');
					    }); 
						$("#infoModal").modal();
				 }
		})
	
};

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// TODO PRODUCTOS
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function addProducto(){
	
	document.getElementById("blankFields").style.display = "none"
	document.getElementById("causaModalInfo").innerHTML = "<span>Nombre:</span><input id=\"nombreNP\" type=\"text\" class=\"form-control\" placeholder=\"\"><span>Descripcion:</span><input id=\"descripcionNP\" type=\"text\" class=\"form-control\" placeholder=\"\"><span>Precio:</span><input id=\"precioNP\" type=\"number\" class=\"form-control\" placeholder=\"\"><span>Stock:</span><input id=\"stockNP\" type=\"number\" class=\"form-control\" placeholder=\"\"><span>Imagen del producto(URL):</span><input id=\"urlimgNP\" type=\"text\" class=\"form-control\" placeholder=\"\"><span>Categoria:</span><div style=\"display: flex\"><select id=\"selectNewProdCategoria\" style=\"margin-right:15px\" class=\"form-control\"><option value=\"-1\" selected>Seleccione una opcion...</option></select><button onClick=\"guardarNuevaX(\'categoria\')\" class=\"btn btn-info\" style=\"width: 40px;\"><i class=\"fas fa-plus\"></i></button></div><span>Marca:</span><div style=\"display: flex\"><select id=\"selectNewProdMarca\"  style=\"margin-right:15px\" class=\"form-control\"><option value=\"-1\" selected>Seleccione una opcion...</option></select><button onClick=\"guardarNuevaX(\'marca\')\" class=\"btn btn-info\" style=\"width: 40px;\"><i class=\"fas fa-plus\"></i></button></div>";

	
	$.ajax({url:'./ServletAuxiliar',
		         type:'POST',
				 data:{tipoSolicitud : "obtenerMarcasProd"},
				 success : function(responseData){
					    document.getElementById("selectNewProdMarca").innerHTML = responseData;
				 }
		})
		
	$.ajax({url:'./ServletAuxiliar',
		         type:'POST',
				 data:{tipoSolicitud : "obtenerCategoriasProd"},
				 success : function(responseData){
					    document.getElementById("selectNewProdCategoria").innerHTML = responseData;
				 }
		})
	
	document.getElementById("buttonModalAccion").style.display = "block";
	document.getElementById("tituloModalInfo").innerHTML = "Agregar Producto";
	$("#buttonModalAccion").removeClass("btn-danger");
	$("#buttonModalAccion").addClass("btn-info");
	$('#buttonModalAccion').removeAttr('data-dismiss');
	document.getElementById("buttonModalAccion").innerHTML = "Agregar";
	document.getElementById("buttonModalAccion").outerHTML = document.getElementById("buttonModalAccion").outerHTML;
	document.getElementById("buttonModalAccion").addEventListener('click',function ()
	{
    	cargarProducto();
    }); 

	$("#infoModal").modal();
	
}

function cargarProducto(){
	
	let nombre = document.getElementById("nombreNP").value
	let descripcion = document.getElementById("descripcionNP").value
	let precio = document.getElementById("precioNP").value
	let stock = document.getElementById("stockNP").value
	let urlimg = document.getElementById("urlimgNP").value
	let categoria = document.getElementById("selectNewProdCategoria").value
	let marca = document.getElementById("selectNewProdMarca").value
	
	if(nombre!="" && descripcion!="" && precio!="" && stock!="" && urlimg!="" && categoria!="-1" && marca!="-1"){
		
		document.getElementById("blankFields").style.display = "none"
		$("#infoModal").modal();
		$.ajax({url:'./ServletProductos',
		         type:'POST',
				 data:{accion : "agregar", nombre : document.getElementById("nombreNP").value, descripcion : document.getElementById("descripcionNP").value, precio : document.getElementById("precioNP").value, stock : document.getElementById("stockNP").value, urlimg : document.getElementById("urlimgNP").value, categoria : document.getElementById("selectNewProdCategoria").value, marca : document.getElementById("selectNewProdMarca").value},
				 success : function(responseData){
					    document.getElementById("tituloModalInfo").innerHTML = "Producto Cargado Correctamente";
						document.getElementById("causaModalInfo").innerHTML = "";
						document.getElementById("buttonModalAccion").style.display = "none";
						document.getElementById("buttonModalAccion").outerHTML = document.getElementById("buttonModalAccion").outerHTML;
						document.getElementById("buttonModalInfo").addEventListener('click',function ()
						{
					   	 	$('#content').load('content/productos.jsp');
					    }); 
						$("#infoModal").modal();
				 }
		})
		
		
	} else {
		
		document.getElementById("blankFields").style.display = "block"
		
	}
	
}

function guardarCambiosProducto(id){
	
	document.getElementById("causaModalInfo").innerHTML ="";
	
	let nombre = document.getElementById("nombreProd"+id)
	let descripcion = document.getElementById("descripcionProd"+id)
	let precio = document.getElementById("precioProd"+id)
	let stock = document.getElementById("stockProd"+id)
	let urlimg = document.getElementById("urlProd"+id)
	let categoria = document.getElementById("categoriaProdSelect"+id)
	let marca = document.getElementById("marcaProdSelect"+id)
	
	if(nombre!=null && descripcion!=null && precio!=null && stock!=null && urlimg!=null && nombre.value!="" && descripcion.value!="" && precio.value!="" && stock.value!="" && urlimg.value!=""){
		
		document.getElementById("blankFields").style.display = "none"
		document.getElementById("buttonModalAccion").style.display = "block";
		document.getElementById("tituloModalInfo").innerHTML = "Modificar Producto";
		document.getElementById("causaModalInfo").innerHTML = "Seguro que desea guardar los cambios realizados al producto?";
		document.getElementById("idProductoParaAccion").value = id;
		$("#buttonModalAccion").removeClass("btn-danger");
		$("#buttonModalAccion").addClass("btn-info");
		document.getElementById("buttonModalAccion").innerHTML = "Modificar";
		document.getElementById("buttonModalAccion").outerHTML = document.getElementById("buttonModalAccion").outerHTML;
		$('#buttonModalAccion').attr("data-dismiss","modal");
		document.getElementById("buttonModalAccion").addEventListener('click',function ()
		{
	    confirmaGuardarCambiosProducto();
	    }); 
		
	} else {
		
		document.getElementById("tituloModalInfo").innerHTML = "Modificar Producto";
		document.getElementById("causaModalInfo").innerHTML = "Error";
		document.getElementById("buttonModalAccion").innerHTML = "Modificar";
		document.getElementById("buttonModalAccion").outerHTML = document.getElementById("buttonModalAccion").outerHTML;
		document.getElementById("buttonModalAccion").style.display = "none";
		document.getElementById("blankFields").style.display = "block"
		
	}

	$("#infoModal").modal();
	
}

function confirmaGuardarCambiosProducto(){
	var id = document.getElementById("idProductoParaAccion").value;
	
		$.ajax({url:'./ServletProductos',
		         type:'POST',
				 data:{id_producto : document.getElementById("idProductoParaAccion").value, accion : "modificar", nombre : document.getElementById("nombreProd"+id).value, descripcion : document.getElementById("descripcionProd"+id).value, precio : document.getElementById("precioProd"+id).value, stock : document.getElementById("stockProd"+id).value, urlimg : document.getElementById("urlProd"+id).value, categoria : document.getElementById("categoriaProdSelect"+id).value, marca : document.getElementById("marcaProdSelect"+id).value, estado : document.getElementById("estadoProdSelect"+id).value},
				 success : function(responseData){
					    document.getElementById("tituloModalInfo").innerHTML = responseData;
						document.getElementById("causaModalInfo").innerHTML = "";
						document.getElementById("buttonModalAccion").style.display = "none";
						document.getElementById("buttonModalAccion").outerHTML = document.getElementById("buttonModalAccion").outerHTML;
						document.getElementById("buttonModalInfo").addEventListener('click',function ()
						{
					   	 $('#content').load('content/productos.jsp');
					    }); 
						$("#infoModal").modal();
				 }
		})
	
};

function guardarNuevaX(tipo){
	document.getElementById("tipoNuevo").value = tipo;
	
	document.getElementById("tituloModalNuevo").innerHTML = "Nueva " + tipo;
	document.getElementById("causaModalNuevo").innerHTML = "<span>Nombre:</span><input id=\"nombreN\" type=\"text\" class=\"form-control\" placeholder=\"\">"
	document.getElementById("buttonModalAccionNuevo").outerHTML = document.getElementById("buttonModalAccionNuevo").outerHTML;
	document.getElementById("buttonModalAccionNuevo").style.display = "block";
	document.getElementById("buttonModalAccionNuevo").addEventListener('click',function (){
   	 guardarNuevaMarcaCat();
    }); 
	document.getElementById("buttonModalNuevo").outerHTML = document.getElementById("buttonModalNuevo").outerHTML;
	document.getElementById("buttonModalNuevo").addEventListener('click',function (){
   	 
	 $("#nuevoModal").modal('toggle');
	 $("#infoModal").modal('toggle');
    }); 
	
	$("#nuevoModal").modal('toggle');
	$("#infoModal").modal('toggle');
};

function guardarNuevas(tipo){
	$.ajax({url:'./ServletProductos',
		         type:'POST',
				 data:{accion : tipo, nombre : document.getElementById("nombreN").value},
				 success : function(){
				 }
		})
}

function llenarMarcas(){
	$.ajax({url:'./ServletAuxiliar',
			         type:'POST',
					 data:{tipoSolicitud : "obtenerMarcasProd"},
					 success : function(responseData){
						    document.getElementById("selectNewProdMarca").innerHTML = responseData;
					 }
			})
}

function llenarCategorias(){
	$.ajax({url:'./ServletAuxiliar',
				         type:'POST',
						 data:{tipoSolicitud : "obtenerCategoriasProd"},
						 success : function(responseData){
							    document.getElementById("selectNewProdCategoria").innerHTML = responseData;
						 }
				})
}

function guardarNuevaMarcaCat(){
	var tipo = document.getElementById("tipoNuevo").value;
	var nombre = document.getElementById("nombreN")
	
	if(nombre.value!=""){
		
		guardarNuevas(tipo);
		document.getElementById("blankFieldsNuevo").style.display = "none"
		$("#nuevoModal").modal('toggle');
		$("#infoModal").modal('toggle');
		
	} else {
		
		document.getElementById("blankFieldsNuevo").style.display = "block"
		
	}
	
	llenarMarcas();
	llenarCategorias();
		
};

function buscaProductos(){
	  // Declare variables 
	  var input, filter, table, tr, td, i, txtValue;
	  input = document.getElementById("SearchProductos");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("tablaProductos");
	  tr = table.getElementsByTagName("tr");
	
	  // Loop through all table rows, and hide those who don't match the search query
	  for (i = 0; i < tr.length; i++) {
		  //FALTA MAXIMO MAXROWS, OSEA GIRO HASTA TOCAR EL MAXIMO DE ROWS SELECCIONADO
		td = tr[i].getElementsByTagName("td")[1];
	    if (td) {
	      txtValue = td.textContent || td.innerText || $(td).find("input").val();
	      if (txtValue.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    }
	  }

	input = document.getElementById("SearchProductos");
	filter = input.value.toUpperCase();
	  if(filter == null || filter == ""){
		  getPagination('#tablaProductos');
		  var ele = document.getElementsByClassName('page-item');
		  for (var i = 0; i < ele.length; i++ ) {
		      ele[i].style.display = "block";
		  }
	  } else {
		  var ele = document.getElementsByClassName('page-item');
		  for (var i = 0; i < ele.length; i++ ) {
		      ele[i].style.display = "none";
		  }
	  }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// USUARIOS
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function actualizarUsuarios(){
	$('#content').load('content/usuarios.jsp');
}

$(".provinciaU").change(function() {
	
	let id = $(this).closest('tr').find("td")[1].innerText
	
	$.post("./ServletAuxiliar", {id_prov : document.getElementById('provinciaSelect'+id).value, tipoSolicitud : "listarLoc"}, function(responseText) {
					document.getElementById('localidadSelect'+id).innerHTML = responseText;
					document.getElementById('localidadSelect'+id).disabled = false;
	         }
	       );

});

function buscaUsuarios(){
	 // Declare variables 
	  var input, filter, table, tr, td, i, txtValue;
	  input = document.getElementById("SearchUsuarios");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("tablaUsuarios");
	  tr = table.getElementsByTagName("tr");
	
	  // Loop through all table rows, and hide those who don't match the search query
	  for (i = 0; i < tr.length; i++) {
		  //FALTA MAXIMO MAXROWS, OSEA GIRO HASTA TOCAR EL MAXIMO DE ROWS SELECCIONADO
		td = tr[i].getElementsByTagName("td")[2];
	    if (td) {
	      txtValue = td.textContent || td.innerText || $(td).find("input").val();
	      if (txtValue.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    }
	  }

	input = document.getElementById("SearchUsuarios");
	filter = input.value.toUpperCase();
	  if(filter == null || filter == ""){
		  getPagination('#tablaUsuarios');
		  var ele = document.getElementsByClassName('page-item');
		  for (var i = 0; i < ele.length; i++ ) {
		      ele[i].style.display = "block";
		  }
	  } else {
		  var ele = document.getElementsByClassName('page-item');
		  for (var i = 0; i < ele.length; i++ ) {
		      ele[i].style.display = "none";
		  }
	  }
}

function guardarCambiosUsuario(id){
	
	document.getElementById("buttonModalAccion").style.display = "block";
	document.getElementById("tituloModalInfo").innerHTML = "Modificar Usuario";
	document.getElementById("causaModalInfo").innerHTML = "Seguro que desea guardar los cambios realizados?";
	document.getElementById("idClienteParaAccion").value = id;
	$("#buttonModalAccion").removeClass("btn-danger");
	$("#buttonModalAccion").addClass("btn-info");
	document.getElementById("buttonModalAccion").innerHTML = "Modificar";
	document.getElementById("buttonModalAccion").outerHTML = document.getElementById("buttonModalAccion").outerHTML;
	document.getElementById("buttonModalAccion").addEventListener('click',function ()
	{
    confirmaGuardarCambiosUsuario();
    }); 

	$("#infoModal").modal();
	
}

function confirmaGuardarCambiosUsuario(){
	var id = document.getElementById("idClienteParaAccion").value;
		$.ajax({url:'./ServletUsuarios',
		         type:'POST',
				 data:{id_cli : document.getElementById("idClienteParaAccion").value, accion : "modificar", dni : document.getElementById("dniCli"+id).value, nombre : document.getElementById("nombreCli"+id).value, apellido : document.getElementById("apellidoCli"+id).value, telefono : document.getElementById("telefonoCli"+id).value, provincia : document.getElementById("provinciaSelect"+id).value, localidad : document.getElementById("localidadSelect"+id).value, usuario : document.getElementById("usuarioCli"+id).value, contrasena : document.getElementById("passCli"+id).value, estado : document.getElementById("estadoSelect"+id).value},
				 success : function(responseData){
					    document.getElementById("tituloModalInfo").innerHTML = responseData;
						document.getElementById("causaModalInfo").innerHTML = "";
						document.getElementById("buttonModalAccion").style.display = "none";
						document.getElementById("buttonModalAccion").outerHTML = document.getElementById("buttonModalAccion").outerHTML;
						document.getElementById("buttonModalInfo").addEventListener('click',function ()
						{
					   	 $('#content').load('content/usuarios.jsp');
					    }); 
						$("#infoModal").modal();
				 }
		})
	
};