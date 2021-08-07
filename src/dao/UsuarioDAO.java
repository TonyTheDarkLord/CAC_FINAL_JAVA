package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import entities.ConexionDB;
import entities.Usuario;

public class UsuarioDAO {
	
	Connection cn = null;
	ConexionDB Con = new ConexionDB();
	
	public int agregarUsuario(Usuario usuario){
		
		int id=0;
		
		try{
			cn = Con.CrearConexion();
			Statement st = cn.createStatement();
			String query = "Insert into usuarios_cac(usuario_usu, pass_usu, tipo_usu, estado_usu) VALUES ('"+usuario.getUsuario()+"', '"+usuario.getPass()+"', '"+usuario.getTipo()+"', '"+usuario.getEstado()+"')";
			st.executeUpdate(query);
			
			PreparedStatement miSentencia = cn.prepareStatement("Select * from usuarios_cac WHERE usuario_usu = '"+usuario.getUsuario()+"'");
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				id=resultado.getInt(1);
			}
			
			cn.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}
	
	
	public Usuario validarUsuario(String user, String pass){

		Usuario usuario = new Usuario();
		
		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from usuarios_cac where usuario_usu = '"+ user +"' AND pass_usu = '"+ pass +"'");
			ResultSet resultado = miSentencia.executeQuery();
			resultado.next();
			
			usuario.setId(resultado.getInt(1));
			usuario.setUsuario(resultado.getString(2));
			usuario.setPass(resultado.getString(3));
			usuario.setTipo(resultado.getInt(4));
			usuario.setEstado(resultado.getInt(5));
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
			usuario.setId(-1);
			return usuario;
		}
		finally{
		}
		return usuario;
	}
	
	public ArrayList<Usuario> obtenerUsuarios(){

		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from usuarios_cac WHERE tipo_usu = 1");
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				
				Usuario usuario = new Usuario();
				usuario.setId(resultado.getInt(1));
				usuario.setUsuario(resultado.getString(2));
				usuario.setPass(resultado.getString(3));
				usuario.setTipo(resultado.getInt(4));
				usuario.setEstado(resultado.getInt(5));
				
				usuarios.add(usuario);
			}
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
		
		return usuarios;
	}
	
	public int checkUser(String user) {
		int filas = 0;
		
		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("SELECT COUNT(*) FROM usuarios_cac WHERE usuario_usu = '"+user+"'");
			ResultSet resultado = miSentencia.executeQuery();
			resultado.next();
			
			filas = resultado.getInt(1);
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		
		return filas;
	}
	
	public Usuario validarUsuarioXID(String user, String pass){

		Usuario usuario = new Usuario();
		
		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from usuarios_cac where id_usu = '"+ user +"' AND pass_usu = '"+ pass +"'");
			ResultSet resultado = miSentencia.executeQuery();
			resultado.next();
			
			usuario.setId(resultado.getInt(1));
			usuario.setUsuario(resultado.getString(2));
			usuario.setPass(resultado.getString(3));
			usuario.setTipo(resultado.getInt(4));
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
			usuario.setId(-1);
			return usuario;
		}
		finally{
		}
		return usuario;
	}
	
	public String cambiarContrasenaUsuario(String user, String pass){

		String result="error";
		
		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("UPDATE usuarios_cac SET pass_usu = '"+ pass +"'" + " WHERE id_usu = "+ user);
			int resultado = miSentencia.executeUpdate();
		    cn.close();
		    if(resultado == 1) {
		    	result = "success";
		    }
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
			return result;
		}
		finally{
		}
		return result;
	}
	
	public int editarUsuario(Usuario usu){
		
		int filas = 0 ;

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("UPDATE usuarios_cac SET usuario_usu = '"+usu.getUsuario()+"', pass_usu = '"+usu.getPass()+"', estado_usu = "+usu.getEstado()+" WHERE (id_usu = "+usu.getId()+")");
			filas = miSentencia.executeUpdate();

		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		return filas;
	}

}
