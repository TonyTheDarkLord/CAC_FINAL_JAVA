package dao;

import java.sql.*;
import java.util.ArrayList;

import entities.ConexionDB;
import entities.Producto;

public class ProductoDAO {
	
	
	Connection cn = null;
	ConexionDB Con = new ConexionDB();
	
	public int agregarProducto(Producto producto){
		
		int filas=0;
		
		try{
			cn = Con.CrearConexion();
			Statement st = cn.createStatement();
			String query = "Insert into productos_cac(nombre_prod,descripcion_prod,precio_unitario_prod,stock_prod,imagen_prod,id_cat_prod,id_marca_prod,estado_prod) values ('"+producto.getNombre()+"','"+producto.getDescripcion()+"','"+producto.getPrecio()+"','"+producto.getStock()+"','"+producto.getURLImagen()+"','"+producto.getIdCat()+"','"+producto.getIdMarca()+"',1)";
			filas=st.executeUpdate(query);
			cn.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return filas;
	}
	
	public Producto obtenerUnProducto(int id){

		Producto producto = new Producto();

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from productos_cac where id_prod = ? AND estado_prod = 1");
			miSentencia.setInt(1, id); //Cargo el ID recibido
			ResultSet resultado = miSentencia.executeQuery();
			resultado.next();
			
			producto.setId(resultado.getInt(1));
			producto.setNombre(resultado.getString(2));
			producto.setDescripcion(resultado.getString(3));
			producto.setPrecio(Double.parseDouble(resultado.getString(4)));
			producto.setStock(Integer.parseInt(resultado.getString(5)));
			producto.setURLImagen(resultado.getString(6));
			producto.setIdCat(resultado.getInt(8));
			producto.setIdMarca(resultado.getInt(9));
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
			producto.setId(-1);
			return producto;
		}
		finally{
		}
		return producto;
	}
	
	public int modificarProducto(Producto producto){
		
		int filas=0;
		
		try{
			cn = Con.CrearConexion();
			Statement st = cn.createStatement();
			String query = "UPDATE productos_cac SET nombre_prod = '"+producto.getNombre()+"', descripcion_prod = '"+producto.getDescripcion()+"',precio_unitario_prod = '"+producto.getPrecio()+"', stock_prod = '"+producto.getStock()+"', imagen_prod='"+producto.getURLImagen()+"', estado_prod= '"+ producto.getEstado()+"', id_cat_prod = '"+producto.getIdCat()+"', id_marca_prod ='"+producto.getIdMarca()+"' WHERE (id_prod = "+producto.getId()+")";
			filas=st.executeUpdate(query);
			cn.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return filas;
	}
	
	public ArrayList<Producto> obtenerProductosRel(Producto prod){

		
		ArrayList<Producto> lista = new ArrayList<Producto>();

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from productos_cac where id_cat_prod = ? AND estado_prod = 1 AND id_prod != " + prod.getId() + " LIMIT 4");
			miSentencia.setInt(1, prod.getIdCat()); //Cargo el ID recibido
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				
				Producto producto = new Producto();
				producto.setId(resultado.getInt(1));
				producto.setNombre(resultado.getString(2));
				producto.setDescripcion(resultado.getString(3));
				producto.setPrecio(Double.parseDouble(resultado.getString(4)));
				producto.setURLImagen(resultado.getString(6));
				producto.setIdCat(resultado.getInt(8));
				producto.setIdMarca(resultado.getInt(9));
				
				lista.add(producto);
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
	
	public ArrayList<Producto> obtenerProductosXMarca(Producto prod){

		
		ArrayList<Producto> lista = new ArrayList<Producto>();

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from productos_cac where id_marca_prod = ? AND estado_prod = 1 AND id_prod != " + prod.getId());
			miSentencia.setInt(1, prod.getIdCat()); //Cargo el ID recibido
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				
				Producto producto = new Producto();
				producto.setId(resultado.getInt(1));
				producto.setNombre(resultado.getString(2));
				producto.setDescripcion(resultado.getString(3));
				producto.setPrecio(Double.parseDouble(resultado.getString(4)));
				producto.setURLImagen(resultado.getString(6));
				producto.setIdCat(resultado.getInt(8));
				producto.setIdMarca(resultado.getInt(9));
				
				lista.add(producto);
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
	
	public ArrayList<Producto> obtenerProductosInicio(){

		
		ArrayList<Producto> lista = new ArrayList<Producto>();

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from productos_cac WHERE estado_prod = 1 ORDER BY fecha_ingreso_prod DESC LIMIT 4");
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				
				Producto producto = new Producto();
				producto.setId(resultado.getInt(1));
				producto.setNombre(resultado.getString(2));
				producto.setPrecio(Double.parseDouble(resultado.getString(4)));
				producto.setURLImagen(resultado.getString(6));
				
				lista.add(producto);
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
	
	public ArrayList<Producto> obtenerCatalogo(String consulta){

		
		ArrayList<Producto> lista = new ArrayList<Producto>();

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from productos_cac"+consulta);
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				
				Producto producto = new Producto();
				producto.setId(resultado.getInt(1));
				producto.setNombre(resultado.getString(2));
				producto.setDescripcion(resultado.getString(3));
				producto.setPrecio(Double.parseDouble(resultado.getString(4)));
				producto.setURLImagen(resultado.getString(6));
				producto.setIdCat(resultado.getInt(8));
				producto.setIdMarca(resultado.getInt(9));
				
				lista.add(producto);
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
	
	public int obtenerStock(int id_prod){

		int stock = 0;

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select stock_prod from productos_cac where id_prod="+id_prod);
			ResultSet resultado = miSentencia.executeQuery();
			
			resultado.next();
				
			stock = resultado.getInt(1);
		    
		    cn.close();
		}
		catch(Exception e){
			System.out.println("Conexion fallida");
		}
		finally{
		}
		
		return stock;
	}
	
	public ArrayList<Producto> ObtenerTodasLosProductos(){

		
		ArrayList<Producto> lista = new ArrayList<Producto>();

		try{
			cn = Con.CrearConexion();
			PreparedStatement miSentencia = cn.prepareStatement("Select * from productos_cac");
			ResultSet resultado = miSentencia.executeQuery();
			
			while(resultado.next()){
				
				Producto producto = new Producto();
				producto.setId(resultado.getInt(1));
				producto.setNombre(resultado.getString(2));
				producto.setDescripcion(resultado.getString(3));
				producto.setPrecio(Double.parseDouble(resultado.getString(4)));
				producto.setStock(resultado.getInt(5));
				producto.setURLImagen(resultado.getString(6));
				producto.setEstado(resultado.getInt(7));
				producto.setIdCat(resultado.getInt(8));
				producto.setIdMarca(resultado.getInt(9));

				
				lista.add(producto);
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
