package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import entities.ConexionDB;
import entities.DatosVenta;
import entities.DetallesVenta;
import entities.EmpresaEnvio;
import entities.EstadoEnvio;
import entities.EstadoPago;
import entities.Provincia;
import entities.TipoVenta;

public class Utilidades {
	
	Connection cn = null;
	ConexionDB Con = new ConexionDB();
	
	public ArrayList<DetallesVenta> obtenerProductosVenta(int id_venta){

		
		ArrayList<DetallesVenta> lista = new ArrayList<DetallesVenta>();

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from detalle_ventas_cac where id_venta_detalle_venta = ?");
			miSentencia.setInt(1, id_venta); //Cargo el ID recibido
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				
				DetallesVenta detalle = new DetallesVenta();
				detalle.setId_prod(resultado.getInt(3));
				detalle.setPrecio(Double.parseDouble(resultado.getString(4)));
				detalle.setCantidad(resultado.getInt(5));
				
				lista.add(detalle);
			}
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
		
		return lista;
	}
	
	public String obtenerEstadoPago(int id_venta){

		
		String estado = "No se pudo obtener la informacion solicitada.";

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select detalle_estado_pago from ventas_cac inner join estados_pago_cac on estado_pago_venta = id_estado_pago where id_venta = ?");
			miSentencia.setInt(1, id_venta); //Cargo el ID recibido
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				estado = resultado.getString(1);
			}
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
		
		return estado;
	}
	
	public String obtenerEstadoEnvio(int id_venta){

		
		String estado = "No se pudo obtener la informacion solicitada.";

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select detalle_estado_envio from ventas_cac inner join estados_envio_cac on estado_envio_venta = id_estado_envio where id_venta = ?");
			miSentencia.setInt(1, id_venta); //Cargo el ID recibido
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				estado = resultado.getString(1);
			}
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
		
		return estado;
	}
	
	public String obtenerDireccionEnvio(int id_venta){

		
		String estado = "No se pudo obtener la informacion solicitada.";

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select direccion_venta, nombre_loc, nombre_prov from ventas_cac inner join localidades_cac on id_loc=id_loc_venta inner join provincias_cac on id_prov=id_prov_loc where id_venta = ?");
			miSentencia.setInt(1, id_venta); //Cargo el ID recibido
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				estado = resultado.getString(1) + ", ";
				estado += resultado.getString(2) + ", ";
				estado += resultado.getString(3);
			}
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
		
		return estado;
	}
	
	public DatosVenta obtenerDatosUltimaVenta(int id_cli){

		
		DatosVenta datosVenta = new DatosVenta();

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from ventas_cac where id_cli_venta = ? ORDER BY fecha_venta DESC LIMIT 1");
			miSentencia.setInt(1, id_cli); //Cargo el ID recibido
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){

				datosVenta.setId(resultado.getInt(1));
				datosVenta.setId_cli(resultado.getInt(2));
				datosVenta.setDireccion(resultado.getString(3));
				datosVenta.setCp(resultado.getInt(4));
				datosVenta.setId_loc(resultado.getInt(5));
				datosVenta.setNro_tarjeta(resultado.getString(6));
				datosVenta.setNombre_tarjeta(resultado.getString(7));
				datosVenta.setApellido_tarjeta(resultado.getString(8));
				datosVenta.setDni(resultado.getInt(9));
				datosVenta.setFecha(resultado.getDate(10));
				datosVenta.setTotal(resultado.getInt(11));
				datosVenta.setEstado_envio(resultado.getInt(12));
				datosVenta.setEstado_pago(resultado.getInt(13));
				datosVenta.setTipo_venta(resultado.getInt(14));

			}
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
		
		return datosVenta;
	}
	
	public ArrayList<Provincia> obtenerProvincias(){
		
		ArrayList<Provincia> lista = new ArrayList<Provincia>();

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from provincias_cac");
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				
				Provincia provincia = new Provincia();
				provincia.setId(resultado.getInt(1));
				provincia.setNombre(resultado.getString(2));
				
				lista.add(provincia);
			}
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
		
		return lista;
	}
	
	public ArrayList<Provincia> obtenerLocalidadesXProv(int id_prov){
		
		ArrayList<Provincia> lista = new ArrayList<Provincia>();

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from localidades_cac where id_prov_loc = " + id_prov);
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				
				Provincia provincia = new Provincia();
				provincia.setId(resultado.getInt(1));
				provincia.setNombre(resultado.getString(3));
				
				lista.add(provincia);
			}
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
		
		return lista;
	}
	
	public int obtenerIdProvSelect(int id_loc){

		
		int prov = 0;

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select id_prov_loc from localidades_cac WHERE id_loc = ?");
			miSentencia.setInt(1, id_loc); //Cargo el ID recibido
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				prov = resultado.getInt(1);
			}
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
		
		return prov;
	}
	
	public ArrayList<EstadoPago> obtenerEstadosPago(){

		
		ArrayList<EstadoPago> lista = new ArrayList<EstadoPago>();

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from estados_pago_cac");
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				
				EstadoPago estado = new EstadoPago();
				estado.setId(resultado.getInt(1));
				estado.setDetalle(resultado.getString(2));
				
				lista.add(estado);
			}
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
		
		return lista;
	}

	public ArrayList<TipoVenta> obtenerTiposVenta(){

	
	ArrayList<TipoVenta> lista = new ArrayList<TipoVenta>();

	try{
		cn = Con.CrearConexion();
		PreparedStatement miSentencia = cn.prepareStatement("Select * from tipos_venta_cac");
		ResultSet resultado = miSentencia.executeQuery();
		
		while(resultado.next()){
			
			TipoVenta tipo = new TipoVenta();
			tipo.setId(resultado.getInt(1));
			tipo.setDetalle(resultado.getString(2));
			
			lista.add(tipo);
		}
	    
	    cn.close();
	}
	catch(Exception e){
		System.out.println("Conexion fallida");
	}
	finally{
	}
	
	return lista;
	}
	
	public ArrayList<EstadoEnvio> obtenerEstadosEnvio(){

		
		ArrayList<EstadoEnvio> lista = new ArrayList<EstadoEnvio>();

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from estados_envio_cac");
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				
				EstadoEnvio estado = new EstadoEnvio();
				estado.setId(resultado.getInt(1));
				estado.setDetalle(resultado.getString(2));
				
				lista.add(estado);
			}
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
		
		return lista;
	}
	
	public ArrayList<EmpresaEnvio> obtenerEmpresasEnvios(){

		
		ArrayList<EmpresaEnvio> lista = new ArrayList<EmpresaEnvio>();

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from empresas_envios_cac");
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				
				EmpresaEnvio empresa = new EmpresaEnvio();
				empresa.setId(resultado.getInt(1));
				empresa.setNombre(resultado.getString(2));
				empresa.setPrecio(resultado.getDouble(3));
				
				lista.add(empresa);
			}
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
		
		return lista;
	}
}
