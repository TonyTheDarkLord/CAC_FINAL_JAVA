package entities;

public class ProductoCarrito {
	
	private int id;
	private int cantidad;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public ProductoCarrito(int id, int cantidad) {
		super();
		this.id = id;
		this.cantidad = cantidad;
	}
	
	public ProductoCarrito() {
		super();
	}
	
}
