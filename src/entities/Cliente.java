package entities;

public class Cliente {
	
	private int id;
	private int id_usu;
	private int dni;
	private String nombre;
	private String apellido;
	private String telefono;
	private int id_loc;
	private int id_prov;
	private boolean guardar;
	
	public boolean isGuardar() {
		return guardar;
	}
	public void setGuardar(boolean guardar) {
		this.guardar = guardar;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public int getId_loc() {
		return id_loc;
	}
	public void setId_loc(int id_loc) {
		this.id_loc = id_loc;
	}
	public int getId_prov() {
		return id_prov;
	}
	public void setId_prov(int id_prov) {
		this.id_prov = id_prov;
	}
	public int getId_usu() {
		return id_usu;
	}
	public void setId_usu(int id_usu) {
		this.id_usu = id_usu;
	}
	
	public Cliente(int id, int dni, String nombre, String apellido, String telefono, int id_loc, int id_prov,int id_usu,boolean guardar) {
		super();
		this.id = id;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.id_loc = id_loc;
		this.id_prov = id_prov;
		this.id_usu = id_usu;
		this.guardar = guardar;
	}
	
	public Cliente() {
		super();

	}
	
	
}
