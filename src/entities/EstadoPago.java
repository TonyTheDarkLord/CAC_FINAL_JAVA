package entities;

public class EstadoPago {

	private int id;
	private String Detalle;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDetalle() {
		return Detalle;
	}
	public void setDetalle(String detalle) {
		Detalle = detalle;
	}
	public EstadoPago(int id, String detalle) {
		super();
		this.id = id;
		Detalle = detalle;
	}
	public EstadoPago() {
		super();
	}
	
}
