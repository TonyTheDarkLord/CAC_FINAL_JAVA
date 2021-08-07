package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import entities.Cliente;
import entities.ConexionDB;

public class ClienteDAO {
	
	Connection cn = null;
	ConexionDB Con = new ConexionDB();
	
	public int agregarCliente(Cliente cliente){
		
		int filas=0;
		
		try{
			cn = Con.CrearConexion();
			Statement st = cn.createStatement();
			String query = "INSERT INTO clientes_cac(id_usu_cli, dni_cli, nombre_cli, apellido_cli, telefono_cli, id_prov_loc_cli, id_prov_cli, guardar_datos_venta_cli) VALUES ('"+cliente.getId_usu()+"', '"+cliente.getDni()+"', '"+cliente.getNombre()+"', '"+cliente.getApellido()+"', '"+cliente.getTelefono()+"', '"+cliente.getId_prov()+"', '"+cliente.getId_loc()+"', '0')";
			filas=st.executeUpdate(query);
			cn.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return filas;
	}
	
	public Cliente obtenerUnCliente(int id_cli){

		Cliente cliente = new Cliente();
		
		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("SELECT * FROM clientes_cac where id_cli = "+ id_cli);
			ResultSet resultado = miSentencia.executeQuery();
			resultado.next();
			
			cliente.setId(resultado.getInt(1));
			cliente.setId_usu(resultado.getInt(2));
			cliente.setDni(resultado.getInt(3));
			cliente.setNombre(resultado.getString(4));
			cliente.setApellido(resultado.getString(5));
			cliente.setTelefono(resultado.getString(6));
			cliente.setId_prov(resultado.getInt(8));
			cliente.setId_loc(resultado.getInt(7));
			cliente.setGuardar(resultado.getBoolean(9));
			
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
			cliente.setId(-1);
			return cliente;
		}
		finally{
		}
		return cliente;
	}
	
	public Cliente obtenerUnClienteXusu(int id_usu){

		Cliente cliente = new Cliente();
		
		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("SELECT * FROM clientes_cac where id_usu_cli = "+ id_usu);
			ResultSet resultado = miSentencia.executeQuery();
			resultado.next();
			
			cliente.setId(resultado.getInt(1));
			cliente.setId_usu(resultado.getInt(2));
			cliente.setDni(resultado.getInt(3));
			cliente.setNombre(resultado.getString(4));
			cliente.setApellido(resultado.getString(5));
			cliente.setTelefono(resultado.getString(6));
			cliente.setId_prov(resultado.getInt(8));
			cliente.setId_loc(resultado.getInt(7));
			
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
			cliente.setId(-1);
			return cliente;
		}
		finally{
		}
		return cliente;
	}
	
	public int editarCliente(Cliente cli){
		
		int filas = 0 ;

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("UPDATE clientes_cac SET dni_cli = '"+cli.getDni()+"', nombre_cli = '"+cli.getNombre()+"', apellido_cli = '"+cli.getApellido()+"', telefono_cli = '"+cli.getTelefono()+"', id_prov_loc_cli = '"+cli.getId_loc()+"', id_prov_cli = '"+cli.getId_prov()+"' WHERE (id_cli = '"+cli.getId()+"')");
			filas = miSentencia.executeUpdate();

		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		return filas;
	}
}
