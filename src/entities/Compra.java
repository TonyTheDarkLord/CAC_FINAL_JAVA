package entities;

import java.sql.Date;

public class Compra {
	
	private int id;
	private int id_cli;
	private String direccion;
	private int cp;
	private int id_loc;
	private String nro_tarjeta;
	private String nombre_tarjeta;
	private int dni_titular;
	private Date fecha;
	private double total;
	
	public Compra() {
		super();
	}
	
	public Compra(int id_cli, String direccion, int cp, int id_loc, String nro_tarjeta, String nombre_tarjeta,
			int dni_titular, double total) {
		super();
		this.id_cli = id_cli;
		this.direccion = direccion;
		this.cp = cp;
		this.id_loc = id_loc;
		this.nro_tarjeta = nro_tarjeta;
		this.nombre_tarjeta = nombre_tarjeta;
		this.dni_titular = dni_titular;
		this.total = total;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_cli() {
		return id_cli;
	}
	public void setId_cli(int id_cli) {
		this.id_cli = id_cli;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getCp() {
		return cp;
	}
	public void setCp(int cp) {
		this.cp = cp;
	}
	public int getId_loc() {
		return id_loc;
	}
	public void setId_loc(int id_loc) {
		this.id_loc = id_loc;
	}
	public String getNro_tarjeta() {
		return nro_tarjeta;
	}
	public void setNro_tarjeta(String nro_tarjeta) {
		this.nro_tarjeta = nro_tarjeta;
	}
	public String getNombre_tarjeta() {
		return nombre_tarjeta;
	}
	public void setNombre_tarjeta(String nombre_tarjeta) {
		this.nombre_tarjeta = nombre_tarjeta;
	}
	public int getDni_titular() {
		return dni_titular;
	}
	public void setDni_titular(int dni_titular) {
		this.dni_titular = dni_titular;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	
}
