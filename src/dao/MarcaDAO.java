package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import entities.ConexionDB;
import entities.Marca;

public class MarcaDAO {
	
	Connection cn = null;
	ConexionDB Con = new ConexionDB();
	
	public String obtenerNombreMarca(int id){
		
		String marca = null;

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select descripcion_marca from marcas_cac where id_marca = ?");
			miSentencia.setInt(1, id); //Cargo el ID recibido
			ResultSet resultado = miSentencia.executeQuery();
			resultado.next();
			
			marca = resultado.getString(1);
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
		return marca;
	}
	
	public void cargarMarca(String nombre){


		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("INSERT INTO marcas_cac (descripcion_marca) VALUES ('"+nombre+"')");
			miSentencia.executeUpdate();
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
	}
	
	public ArrayList<Marca> obtenerMarcas(){

		
		ArrayList<Marca> lista = new ArrayList<Marca>();

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from marcas_cac");
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				
				Marca marca = new Marca();
				marca.setId(resultado.getInt(1));
				marca.setDescripcion(resultado.getString(2));
				
				lista.add(marca);
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

	public ArrayList<Marca> obtenerMarcasXCategoria(String id, String busqueda){

		
		ArrayList<Marca> lista = new ArrayList<Marca>();

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia;
			if(busqueda.equals("")) {
				miSentencia = cn.prepareStatement("SELECT id_marca_prod FROM cac_tp_final.productos_cac WHERE id_cat_prod ="+id+" group by id_marca_prod");
			}else {
				miSentencia = cn.prepareStatement("SELECT id_marca_prod FROM cac_tp_final.productos_cac WHERE nombre_prod LIKE '%"+busqueda+"%' AND id_cat_prod ="+id+" group by id_marca_prod");
			}
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				
				Marca marca = new Marca();
				marca.setId(resultado.getInt(1));
				marca.setDescripcion(obtenerNombreMarca(resultado.getInt(1)));
				
				lista.add(marca);
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
