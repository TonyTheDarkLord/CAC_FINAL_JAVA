package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import entities.Compra;
import entities.ConexionDB;

public class CompraDAO {
	
	Connection cn = null;
	ConexionDB Con = new ConexionDB();
	
	public ArrayList<Compra> obtenerComprasXCliente(int id_cli){

		
		ArrayList<Compra> compras = new ArrayList<Compra>();

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from ventas_cac where id_cli_venta = " + id_cli + " ORDER BY fecha_venta DESC");
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				
				Compra compra = new Compra();
				
				compra.setId(resultado.getInt(1));
				compra.setId_cli(resultado.getInt(2));
				compra.setDireccion(resultado.getString(3));
				compra.setCp(resultado.getInt(4));
				compra.setId_loc(resultado.getInt(5));
				compra.setNro_tarjeta(resultado.getString(6));
				compra.setNombre_tarjeta(resultado.getString(7));
				compra.setDni_titular(resultado.getInt(9));
				//compra.setFecha(resultado.getDate(10));
				compra.setTotal(resultado.getInt(11));
				
				compras.add(compra);
			}
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		
		finally{
		}
		
		return compras;
	}

}
