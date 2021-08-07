function busquedaConFiltrosCat(id){
	document.getElementById('idCat').value = id;
	document.getElementById('idMarca').value = "";
	$('#filtrosBusquedaForm').submit()
}
function busquedaConFiltrosMarca(id){
	document.getElementById('idMarca').value = id;
	$('#filtrosBusquedaForm').submit()
}

function busqueda(){
	window.location.replace("catalogo.jsp?busqueda="+$("#busqueda").val());
}

$(".provincia").change(function() {

	$.post("./ServletAuxiliar", {id_prov : document.getElementById('provincia').value, tipoSolicitud : "listarLoc"}, function(responseText) {
					document.getElementById('localidad').innerHTML = responseText;
					document.getElementById('localidad').disabled = false;
	         }
	       );

});

$(".userReg").keyup(function() {

	$.post("./ServletAuxiliar", {user : document.getElementById('InputEmailRegistro').value, tipoSolicitud : "checkuser"}, function(responseText) {
				if(responseText!="success"){
						$('#InputEmailRegistro').attr("oninvalid","");
						document.getElementById('InputEmailRegistro').setCustomValidity('Este mail ya se encuentra registrado.');
					} else {
						$('#InputEmailRegistro').attr("oninvalid","Este campo no puede estar vacio.");
						document.getElementById('InputEmailRegistro').setCustomValidity('');
					}
	         }
	       );

});

$(".passConfirm").keyup(function() {
				if(document.getElementById('InputPasswordC').value != document.getElementById('InputPassword').value ){
						$('#InputPasswordC').attr("oninvalid","");
						document.getElementById('InputPasswordC').setCustomValidity('Las contraseñas no coinciden.');
					} else {
						$('#InputPasswordC').attr("oninvalid","Este campo no puede estar vacio.");
						document.getElementById('InputPasswordC').setCustomValidity('');
					}

});

function registrarUsuario(){
	
	$.ajax({url:'./ServletProductos',
		         type:'POST',
				 data:{accion : "registrar", usuario : document.getElementById("InputEmailRegistro").value, pass : document.getElementById("InputPassword").value, nombre : document.getElementById("InputNombre").value, apellido : document.getElementById("InputApellido").value, dni : document.getElementById("InputDNI").value, telefono : document.getElementById("InputTelefono").value, id_prov : document.getElementById("provincia").value, id_loc : document.getElementById("localidad").value},
				 success : function(){
				 }
		})
		
}


function cambiarPass(){
  (async () => {

  await Swal.fire({
    title: 'Cambiar contrase&ntilde;a',
	showCancelButton: true,
	cancelButtonText: `Cancelar`,
	confirmButtonText: `Cambiar`,
    html:
	  '<label>Contrase&ntilde;a Actual</label>' +
      '<input id="input1" name="input1" class="swal2-input" minlength="5" required>' +
	  '<label>Contrase&ntilde;a Nueva</label>' +
      '<input id="input2" name="input2" class="swal2-input" minlength="5" required>' +
	  '<label>Repetir Contrase&ntilde;a Nueva</label>' +
      '<input id="input3" name="input3" class="swal2-input" minlength="5" required>',
    focusConfirm: false,
    preConfirm: () => {
		if(document.getElementById('input2').value == "" || document.getElementById('input3').value == "" || document.getElementById('input1').value == ""){
			Swal.showValidationMessage(`No puede dejar campos vacios.`)
    	} else if (document.getElementById('input2').value != "" && document.getElementById('input3').value != "") {
			if (document.getElementById('input2').value != document.getElementById('input3').value){
      			Swal.showValidationMessage(`Las contrase&ntilde;as no coinciden.`)
			}
		}
		else if (document.getElementById('input2').value.length >= 5 && document.getElementById('input3').value.length >= 5) {
			Swal.showValidationMessage(`La contrase&ntilde;a debe tener almenos 5 digitos.`)
		}
		const oldPw = document.getElementById('input1').value;
		const password = document.getElementById('input2').value;
		return { oldPw: oldPw, password: password }
    }
  }).then((result) => {
	    	$.post("./ServletCambioPassword", { old_pass: result.value.oldPw, new_pass: result.value.password }, function(responseText) {
			if(responseText=="success"){
				Swal.fire('Exito', 'Se cambio correctamente la contrase&ntilde;a', 'success');
				} else {
					Swal.fire('Error', 'Hubo un error al cambiar la contrase&ntilde;a'+ responseText, 'error');
				}
	        });
}
  )})()
}

function addToCart(id){
 
	$.ajax({url:'./ServletCarrito',
	         type:'POST',
	         data:{id_prod : id,cant : document.getElementById('cantidad').value, action : "agregar"},
	         success : function(){
	             Swal.fire({
				  position: 'top-end',
				  icon: 'success',
				  title: 'Producto agregado al carrito',
				  showConfirmButton: false,
				  timer: 1000
				})
				
				if(document.getElementById('cantBadge')!=null){
				
					document.getElementById('cantBadge').innerHTML = parseInt(document.getElementById('cantBadge').innerHTML) + parseInt(document.getElementById('cantidad').value)
				
				} else {
				
					document.getElementById('iconosBarra').innerHTML="<a href=\"carrito.jsp\" style=\"position:relative;\"><span style=\"position:absolute;top: -24px;left: -5px;\" id=\"cantBadge\" class=\"badge\">" + document.getElementById('cantidad').value + "</span><i style=\"position:absolute;left: -28px;top: -13px;font-size: x-large;\" class=\"fas fa-shopping-cart\"></i></a></div>";
				
				}
				var remove = document.getElementById('cantidad').value
				var select = document.getElementById('cantidad')
				var length = select.options.length;
				var queda = length - remove
				if(queda>0){
					for (i = length-1; i >= queda; i--) {
					  select.options[i] = null;
					}
				} else {
					var h5 = document.getElementById('h5cant');
					h5.remove();
					select.remove();
					document.getElementById('btnCompra').disabled = true;
					document.getElementById('btnCompra').innerHTML = "SIN STOCK";
				}
	         }
	       });

}

function removerprodCarrito(id){

	$.ajax({url:'./ServletCarrito',
	         type:'POST',
	         data:{id_prod : id, action : "remover"},
	         success : function(){
				$('#cardcarritoprod'+id).fadeOut( "slow", function() {
					var cantidadAnterior = document.getElementById("cantidadEnCarro").innerHTML
					var nuevaCantidad = parseInt(cantidadAnterior) - parseInt(document.getElementById('cantidad'+id).innerHTML)
					document.getElementById("cantidadEnCarro").innerHTML = nuevaCantidad;
					document.getElementById('cardcarritoprod'+id).remove()
				 	var haymas = document.querySelector('[id^="cardcarritoprod"]');
					$.ajax({url:'./ServletAuxiliar',
				         type:'POST',
						 data:{tipoSolicitud : "carritoOnlyTotal"},
						 success : function(responseData){
								document.getElementById("totalCarrito").innerHTML = "Total $ " + responseData;
							 }
						 });
					if(haymas == null){
						document.getElementById("cantidadEnCarro").innerHTML = 0;
						document.getElementById("botonInicioCompra").remove();
						document.getElementById("carritodata").innerHTML = "<div style=\"text-align:center\"><h2>El carrito de compras est&aacute vacio</h2><br><h3><a href=\"Index.jsp\">Volver a la tienda <i class=\"fas fa-shopping-basket\"></i></a></h3></div>"
					}
				})
	         }
	       });
	

}


function consultarStock(id){
	return new Promise(function(resolve) {$.ajax({url:'./ServletAuxiliar',
			type:'POST',
	        data:{id_prod : id, tipoSolicitud : 'maxStock'},
			success : function(responseText){
				resolve(responseText)
			}});
			})
}

function cambiocantCarrito(id,tipo){
 	
	document.getElementById("btnaumentar"+id).disabled = true;
	document.getElementById("btndisminuir"+id).disabled = true;
	var maxStock = 0;
	
	consultarStock(id).then(function (val) {
			
	//alert(maxStock)
	
	$.ajax({url:'./ServletCarrito',
	         type:'POST',
	         data:{id_prod : id, action : tipo},
	         success : function(){
				if(tipo == "aumentar"){
					if(document.getElementById("cantidad"+id).innerHTML == val){
						Swal.fire({
						  position: 'center',
						  icon: 'info',
						  title: 'Maximo stock seleccionado',
						  showConfirmButton: false,
						  timer: 1500
						})
					} else {
						var cant = document.getElementById("cantidad"+id).innerHTML
						var cantidadAnterior = document.getElementById("cantidadEnCarro").innerHTML
						var nuevaCantidad = parseInt(cantidadAnterior) + 1
						document.getElementById("cantidadEnCarro").innerHTML = nuevaCantidad;
						cant++;
						document.getElementById("totalProd"+id).innerHTML = "$ " + (cant*document.getElementById("precioProd"+id).value).toFixed(2) + " &nbsp " + "<button type=\"button\" onClick=\"removerprodCarrito("+id+")\" class=\"btn btn-secondary\" style=\"width:40px\"><i class=\"fas fa-trash-alt\"></i></button>";
						document.getElementById("totalProdValue"+id).value = (cant*document.getElementById("precioProd"+id).value).toFixed(2)
						document.getElementById("cantidad"+id).innerHTML = cant;
						$.ajax({url:'./ServletAuxiliar',
				         type:'POST',
						 data:{tipoSolicitud : "carritoOnlyTotal"},
						 success : function(responseData){
								document.getElementById("totalCarrito").innerHTML = "Total $ " + responseData;
							 }
						 });
					}
				}
				if(tipo == "disminuir"){
					var cant = document.getElementById("cantidad"+id).innerHTML
					if(cant == 1){
					 var cantidadAnterior = document.getElementById("cantidadEnCarro").innerHTML
					 var nuevaCantidad = parseInt(cantidadAnterior) - 1
					 document.getElementById("cantidadEnCarro").innerHTML = nuevaCantidad;
					 $('#cardcarritoprod'+id).fadeOut( "slow", function() {
				     document.getElementById('cardcarritoprod'+id).remove()
					 var haymas = document.querySelector('[id^="cardcarritoprod"]');
					$.ajax({url:'./ServletAuxiliar',
				         type:'POST',
						 data:{tipoSolicitud : "carritoOnlyTotal"},
						 success : function(responseData){
								document.getElementById("totalCarrito").innerHTML = "Total $ " + responseData;
							 }
						 });
						if(haymas == null){
							document.getElementById("cantidadEnCarro").innerHTML = 0;
							document.getElementById("botonInicioCompra").remove();
							document.getElementById("carritodata").innerHTML = "<div style=\"text-align:center\"><h2>El carrito de compras est&aacute vacio</h2><br><h3><a href=\"Index.jsp\">Volver a la tienda <i class=\"fas fa-shopping-basket\"></i></a></h3></div>"
							
						}
					})
					 var haymas = document.querySelector('[id^="cardcarritoprod"]');
						if(haymas == null){
							document.getElementById("cantidadEnCarro").innerHTML = 0;
							document.getElementById("botonInicioCompra").remove();
							document.getElementById("carritodata").innerHTML = "<div style=\"text-align:center\"><h2>El carrito de compras est&aacute vacio</h2><br><h3><a href=\"Index.jsp\">Volver a la tienda <i class=\"fas fa-shopping-basket\"></i></a></h3></div>"
							
						}
					} else {
						cant--;
						var cantidadAnterior = document.getElementById("cantidadEnCarro").innerHTML
					 	var nuevaCantidad = parseInt(cantidadAnterior) - 1
					 	document.getElementById("cantidadEnCarro").innerHTML = nuevaCantidad;
						document.getElementById("totalProd"+id).innerHTML = "$ " + (cant*document.getElementById("precioProd"+id).value).toFixed(2) + " &nbsp " + "<button type=\"button\" onClick=\"removerprodCarrito("+id+")\" class=\"btn btn-secondary\" style=\"width:40px\"><i class=\"fas fa-trash-alt\"></i></button>";
						document.getElementById("totalProdValue"+id).value = (cant*document.getElementById("precioProd"+id).value).toFixed(2)
						//document.getElementById("totalCarrito").innerHTML = "Total $ "
						document.getElementById("cantidad"+id).innerHTML = cant;
						$.ajax({url:'./ServletAuxiliar',
				         type:'POST',
						 data:{tipoSolicitud : "carritoOnlyTotal"},
						 success : function(responseData){
								document.getElementById("totalCarrito").innerHTML = "Total $ " + responseData;
							 }
						 });
					}
					
				}					
	             
	         }
	       });

	document.getElementById("btnaumentar"+id).disabled = false;
	document.getElementById("btndisminuir"+id).disabled = false;
	
	})
}

function mostrarInfoPago(){
	if($('#tarjeta').is(':checked')) {
		document.getElementById("rowTarjeta").style.display="flex";
		document.getElementById("rowTarjetaDatos").style.display="flex";
		document.getElementById("rowTransferencia").style.display="none";
		$('#cc-cvv').attr("required", "true")
		$('#cc-expiration').attr("required", "true")
		$('#cc-number').attr("required", "true")
		$('#cc-name').attr("required", "true")
	}
	if($('#efectivo').is(':checked')) {
		document.getElementById("rowTarjeta").style.display="none";
		document.getElementById("rowTarjetaDatos").style.display="none";
		document.getElementById("rowTransferencia").style.display="flex";
		$('#cc-cvv').removeAttr('required');
		$('#cc-expiration').removeAttr('required');
		$('#cc-number').removeAttr('required');
		$('#cc-name').removeAttr('required');
	}
}

function realizarCompra(){
	var procederPago = 0;
	
	if(document.getElementById("provincia").value!="-1" && document.getElementById("localidad").value!="-1" && document.getElementById("zip").value!="" && document.getElementById("address").value!="" && document.getElementById("firstName").value!="" && document.getElementById("lastName").value!=""){
			
		if($('#tarjeta').is(':checked')) {
			
			if(document.getElementById("cc-name").value!="" && document.getElementById("cc-number").value!="" && document.getElementById("cc-number").value.length==16 && document.getElementById("cc-expiration").value!="" && document.getElementById("cc-cvv").value.length==3){
			
				procederPago = 1;
			
			}
			
		} else {
			if($('#efectivo').is(':checked')) {
				procederPago = 1;
			}
		}
	}
	
	if(procederPago==1){
		
		document.getElementById("blankFields").style.display = "none"
		var tipoPago = "";
		
		if($('#efectivo').is(':checked')){
			tipoPago = "1"
		} 
		if( $('#tarjeta').is(':checked')) {
			tipoPago = "2"
		}
		
		$.post("./ServletFinalizarCompra", { accion: "add", nombre: document.getElementById("firstName").value, apellido: document.getElementById("lastName").value, direccion: document.getElementById("address").value, provincia: document.getElementById("localidad").value, localidad: document.getElementById("localidad").value, cp: document.getElementById("zip").value, tipo: tipoPago, nombreT: document.getElementById("cc-name").value, numeroT: document.getElementById("cc-number").value}, function(responseText) {
			if(responseText.toString().split(",")[0]=="success"){
					Swal.fire({title:'Compra Realizada', confirmButtonColor:'#3085d6', icon: 'success', confirmButtonText: 'ir a compras'}).then((result) => {
  							 if (result.isConfirmed) {
								location.href = responseText.toString().split(",")[1];
				}});
				} else {
					Swal.fire('Error', 'En este momento no se pudo realizar la compra, intente nuevamente', 'error');
				}
	        });
		
	} else {
		
		if($('#efectivo').is(':checked') || $('#tarjeta').is(':checked')){
			document.getElementById("blankPago").style.display = "none"
		} else {
			document.getElementById("blankPago").style.display = "block"
		}
		if(document.getElementById("cc-name").value==""){
			$("#cc-name").addClass("red");
		} else {
			$("#cc-name").removeClass("red");
		}
		if(document.getElementById("cc-number").value=="" || document.getElementById("cc-number").value.length!=16){
			$("#cc-number").addClass("red");
		} else {
			$("#cc-number").removeClass("red");
		}
		if(document.getElementById("cc-expiration").value==""){
			$("#cc-expiration").addClass("red");
		} else {
			$("#cc-expiration").removeClass("red");
		}
		if(document.getElementById("cc-cvv").value=="" || document.getElementById("cc-cvv").value.length!=3){
			$("#cc-cvv").addClass("red");
		} else {
			$("#cc-cvv").removeClass("red");
		}
		if(document.getElementById("provincia").value=="-1"){
			$("#provincia").addClass("red");
		} else {
			$("#provincia").removeClass("red");
		}
		if(document.getElementById("localidad").value=="-1"){
			$("#localidad").addClass("red");
		} else {
			$("#localidad").removeClass("red");
		}
		if(document.getElementById("address").value==""){
			$("#address").addClass("red");
		} else {
			$("#address").removeClass("red");
		}
		if(document.getElementById("firstName").value==""){
			$("#firstName").addClass("red");
		} else {
			$("#firstName").removeClass("red");
		}
		if(document.getElementById("lastName").value==""){
			$("#lastName").addClass("red");
		} else {
			$("#lastName").removeClass("red");
		}
		if(document.getElementById("zip").value==""){
			$("#zip").addClass("red");
		} else {
			$("#zip").removeClass("red");
		}
		
		document.getElementById("blankFields").style.display = "block"
		
	}
}