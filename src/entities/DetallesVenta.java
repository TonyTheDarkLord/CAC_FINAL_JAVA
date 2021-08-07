package entities;

public class DetallesVenta {
	
	private int id_venta;
	private int id_prod;
	private double precio;
	private int cantidad;
	
	public DetallesVenta() {
		super();

	}
	public DetallesVenta(int id_venta, int id_prod, double precio, int cantidad) {
		super();
		this.id_venta = id_venta;
		this.id_prod = id_prod;
		this.precio = precio;
		this.cantidad = cantidad;
	}
	public int getId_venta() {
		return id_venta;
	}
	public void setId_venta(int id_venta) {
		this.id_venta = id_venta;
	}
	public int getId_prod() {
		return id_prod;
	}
	public void setId_prod(int id_prod) {
		this.id_prod = id_prod;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
