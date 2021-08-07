package entities;

import java.sql.Date;

public class DatosVenta {
	
	private int id;
	private int id_cli;
	private String direccion;
	private int cp;
	private int id_loc;
	private String nro_tarjeta;
	private String nombre_tarjeta;
	private String apellido_tarjeta;
	private int dni;
	private Date fecha;
	private double total;
	private int estado_envio;
	private int estado_pago;
	private int tipo_venta;
	
	public DatosVenta(int id, int id_cli, String direccion, int cp, int id_loc, String nro_tarjeta,
			String nombre_tarjeta, String apellido_tarjeta, int dni, Date fecha, double total, int estado_envio,
			int estado_pago, int tipo_venta) {
		super();
		this.id = id;
		this.id_cli = id_cli;
		this.direccion = direccion;
		this.cp = cp;
		this.id_loc = id_loc;
		this.nro_tarjeta = nro_tarjeta;
		this.nombre_tarjeta = nombre_tarjeta;
		this.apellido_tarjeta = apellido_tarjeta;
		this.dni = dni;
		this.fecha = fecha;
		this.total = total;
		this.estado_envio = estado_envio;
		this.estado_pago = estado_pago;
		this.tipo_venta = tipo_venta;
	}
	
	public DatosVenta() {
		super();
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
	public String getApellido_tarjeta() {
		return apellido_tarjeta;
	}
	public void setApellido_tarjeta(String apellido_tarjeta) {
		this.apellido_tarjeta = apellido_tarjeta;
	}
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
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
	public int getEstado_envio() {
		return estado_envio;
	}
	public void setEstado_envio(int estado_envio) {
		this.estado_envio = estado_envio;
	}
	public int getEstado_pago() {
		return estado_pago;
	}
	public void setEstado_pago(int estado_pago) {
		this.estado_pago = estado_pago;
	}
	public int getTipo_venta() {
		return tipo_venta;
	}
	public void setTipo_venta(int tipo_venta) {
		this.tipo_venta = tipo_venta;
	}

}
