package entities;

public class Usuario {
	
	private int id;
	private String usuario;
	private String pass;
	private int tipo;
	private int estado;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	public Usuario(int id, String usuario, String pass, int tipo, int estado) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.pass = pass;
		this.tipo = tipo;
		this.estado = estado;
	}
	
	public Usuario() {}
}
