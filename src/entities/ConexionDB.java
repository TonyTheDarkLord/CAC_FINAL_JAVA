package entities;
import java.sql.*;

public class ConexionDB {

	private String user = "root";
	private String pass = "admin";
	private String dbName = "cac_tp_final";
	private String host = "jdbc:mysql://localhost:3306/";
	public Connection cn = null;
	
	public ResultSet ObtenerConexion(String query) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			cn = DriverManager.getConnection(host+dbName, user,pass);
			Statement stm=cn.createStatement();
	        ResultSet rs=stm.executeQuery(query);
	        return rs;
		} catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Error");
		}
		return null;
	}
	
	public void CerrarConexion() {
		
		try {
			cn.close();
		} catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Error");
		}
	}
	
	public Connection CrearConexion() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cn = DriverManager.getConnection(host+dbName, user,pass);
	        return cn;
		} catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Error");
		}
		return null;
	}
}
