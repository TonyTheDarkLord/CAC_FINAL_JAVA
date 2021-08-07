package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import entities.Categoria;
import entities.ConexionDB;

public class CategoriaDAO {
	
	Connection cn = null;
	ConexionDB Con = new ConexionDB();
	
	public String obtenerNombreCat(int id){
		
		String categoria = null;

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select descripcion_cat from categorias_cac where id_cat = ?");
			miSentencia.setInt(1, id); //Cargo el ID recibido
			ResultSet resultado = miSentencia.executeQuery();
			resultado.next();
			
			categoria = resultado.getString(1);
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
		return categoria;
	}
	
	public ArrayList<Categoria> obtenerCategorias(){

		
		ArrayList<Categoria> lista = new ArrayList<Categoria>();

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from categorias_cac");
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				
				Categoria categoria = new Categoria();
				categoria.setId(resultado.getInt(1));
				categoria.setDescripcion(resultado.getString(2));
				
				lista.add(categoria);
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
	
	public void cargarCategoria(String nombre){


		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("INSERT INTO categorias_cac (descripcion_cat) VALUES ('"+nombre+"')");
			miSentencia.executeUpdate();
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
	}

}
