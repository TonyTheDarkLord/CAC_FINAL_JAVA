package entities;

public class EstadoEnvio {

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
	public EstadoEnvio(int id, String detalle) {
		super();
		this.id = id;
		Detalle = detalle;
	}
	public EstadoEnvio() {
		super();
	}
	
}
