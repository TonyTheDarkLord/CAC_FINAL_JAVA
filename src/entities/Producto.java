package entities;

public class Producto {
	
	private int id;
	private String nombre;
	private String descripcion;
	private double precio;
	private int stock;
	private String URLImagen;
	private int idCat;
	private int idMarca;
	private int estado;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getURLImagen() {
		return URLImagen;
	}
	public void setURLImagen(String URLImagen) {
		this.URLImagen = URLImagen;
	}
	public int getIdCat() {
		return idCat;
	}
	public void setIdCat(int idCat) {
		this.idCat = idCat;
	}
	public int getIdMarca() {
		return idMarca;
	}
	public void setIdMarca(int idMarca) {
		this.idMarca = idMarca;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public Producto(int id, String nombre, String descripcion, double precio, int stock, String URLImagen, int idCat, int idMarca, int estado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.URLImagen = URLImagen;
		this.idCat = idCat;
		this.idMarca = idMarca;
		this.estado = estado;
	}
	
	public Producto() {}
}
