package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import entities.ConexionDB;
import entities.Venta;

public class VentasDAO {

	Connection cn = null;
	ConexionDB Con = new ConexionDB();
	
	public ArrayList<Venta> ObtenerTodasLasVentas(){

		
		ArrayList<Venta> lista = new ArrayList<Venta>();

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from ventas_cac inner join clientes_cac on id_cli_venta = id_cli where estado_venta = 1");
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				
				Venta Venta = new Venta();
				
				Venta.setId(resultado.getInt(1));
				Venta.setId_cli(resultado.getInt(2));
				Venta.setDireccion(resultado.getString(3));
				Venta.setCp(resultado.getInt(4));
				Venta.setId_loc(resultado.getInt(5));
				Venta.setNro_tarjeta(resultado.getString(6));
				Venta.setNombre_tarjeta(resultado.getString(7));
				Venta.setApellido_tarjeta(resultado.getString(8));
				Venta.setDni(resultado.getInt(20));
				Venta.setFecha(resultado.getDate(10));
				Venta.setTotal(resultado.getInt(11));
				Venta.setEstado_envio(resultado.getInt(12));
				Venta.setEstado_pago(resultado.getInt(13));
				Venta.setTipo_venta(resultado.getInt(14));
				Venta.setNombre_cli(resultado.getString(21));
				Venta.setApellido_cli(resultado.getString(22));
				Venta.setCodigo_envio(resultado.getString(16));
				Venta.setEmpresa_envio(resultado.getInt(17));
				
				lista.add(Venta);
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
	
	public int eliminarVenta(int id){
		
		int resultado =0;
		
		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("UPDATE ventas_cac SET estado_venta = '0' WHERE id_venta = '"+id+"'");
			resultado = miSentencia.executeUpdate();

		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
		
		return resultado;
	}
	
	public int modificarVenta(int id, String direccion, int estado_envio, int estado_pago){
		
		int resultado =0;
		
		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("UPDATE ventas_cac SET direccion_venta = '"+ direccion +"' , estado_envio_venta = "+estado_envio+" , estado_pago_venta = "+estado_pago+"  WHERE id_venta = '"+id+"'");
			resultado = miSentencia.executeUpdate();

		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
		
		return resultado;
	}
	
	public int cargarVenta(Venta venta){
		
		int resultado =0;
		
		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("INSERT INTO ventas_cac (id_cli_venta, direccion_venta, cp_venta, id_loc_venta, total_venta, estado_envio_venta, estado_pago_venta, tipo_venta, estado_venta) VALUES (?, '"+venta.getDireccion()+"', ?, ?, ?, ?, ?, ?, ?);");
			miSentencia.setInt(1, venta.getId_cli());
			//miSentencia.setString(2, venta.getDireccion());
			miSentencia.setInt(2, venta.getCp());
			miSentencia.setInt(3, venta.getId_loc());
			miSentencia.setDouble(4, venta.getTotal());
			miSentencia.setInt(5, venta.getEstado_envio());
			miSentencia.setInt(6, venta.getEstado_pago());
			miSentencia.setInt(7, venta.getTipo_venta());
			miSentencia.setInt(8, 1);
			
			resultado = miSentencia.executeUpdate();

		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
		
		return resultado;
	}
	
	public int obtenerUltimaVenta(int id_cli){
		
		int id_venta = 0;
		
		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from ventas_cac WHERE id_cli_venta = ? order by fecha_venta desc limit 1");
			miSentencia.setInt(1, id_cli);
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				id_venta=resultado.getInt(1);
			}

		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
		
		return id_venta;
	}
	
	public int agregarDetalle(int id_detalle,int id_venta, int id_prod, double precio, int cant){
		
		int resultado =0;
		
		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("INSERT INTO detalle_ventas_cac (id_detalle_venta, id_venta_detalle_venta, id_prod_detalle_venta, precio_prod_detalle_venta, cantidad_prod_detalle_venta) VALUES (?,?,?,?,?);");
			miSentencia.setInt(1, id_detalle);
			miSentencia.setInt(2, id_venta);
			miSentencia.setInt(3, id_prod);
			miSentencia.setDouble(4, precio);
			miSentencia.setInt(5, cant);
			resultado = miSentencia.executeUpdate();

		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
		
		return resultado;
	}
	
}
