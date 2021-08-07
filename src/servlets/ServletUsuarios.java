package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClienteDAO;
import dao.UsuarioDAO;
import dao.Utilidades;
import entities.Cliente;
import entities.Provincia;
import entities.Usuario;

@WebServlet("/ServletUsuarios")
public class ServletUsuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ServletUsuarios() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		Cliente cliente = new Cliente();
		Usuario usuario = new Usuario();
		ArrayList<Provincia> provincias = new ArrayList<Provincia>();
		ArrayList<Provincia> localidades = new ArrayList<Provincia>();
		
		UsuarioDAO udao = new UsuarioDAO();
		ClienteDAO cdao = new ClienteDAO();
		Utilidades utildao = new Utilidades();
		
		String htmlTablaUsuarios = "";
		
		if(request.getParameter("accion")!=null) {
			
			if(request.getParameter("accion").matches("modificar")) {
				
				cliente.setId(Integer.parseInt(request.getParameter("id_cli").toString()));
				cliente.setApellido(request.getParameter("apellido").toString());
				cliente.setNombre(request.getParameter("nombre").toString());
				cliente.setDni(Integer.parseInt(request.getParameter("dni").toString()));
				cliente.setTelefono(request.getParameter("telefono").toString());
				cliente.setId_loc(Integer.parseInt(request.getParameter("localidad").toString()));
				cliente.setId_prov(Integer.parseInt(request.getParameter("provincia").toString()));
				
				usuario.setEstado(Integer.parseInt(request.getParameter("estado").toString()));
				usuario.setUsuario(request.getParameter("usuario").toString());
				usuario.setPass(request.getParameter("contrasena").toString());
				
				Cliente para_id = cdao.obtenerUnCliente(cliente.getId());
				
				usuario.setId(para_id.getId_usu());
				
				cdao.editarCliente(cliente);
				udao.editarUsuario(usuario);
				
				response.getWriter().append("Usuario Modificado Correctamente");
			}
			
		}  else {

			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// CARGO PAGINA NORMAL
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
			usuarios = udao.obtenerUsuarios();
			provincias = utildao.obtenerProvincias();
			
			
			for(Usuario u: usuarios) {
				
				cliente = cdao.obtenerUnClienteXusu(u.getId());
				

				htmlTablaUsuarios += "<tr><td style=\"display:none\" >"+cliente.getId()+"</td><td><input type=\"number\" id=\"dniCli" + cliente.getId() + "\" class=\"form-control\" placeholder=\"-\" value=\"" +cliente.getDni() + "\"></input></td><td><input type=\"text\" id=\"nombreCli" + cliente.getId() + "\" class=\"form-control\" placeholder=\"-\" value=\"" +cliente.getNombre() + "\"></input></td><td><input type=\"text\" id=\"apellidoCli" + cliente.getId() + "\" class=\"form-control\" placeholder=\"-\" value=\"" +cliente.getApellido() + "\"></input></td><td><input type=\"number\" id=\"telefonoCli" + cliente.getId() + "\" class=\"form-control\" placeholder=\"-\" value=\"" +cliente.getTelefono() + "\"></input></td>";
				
				// PROVINCIAS
				
				htmlTablaUsuarios += "<td><select class=\"form-control provinciaU\" id=\"provinciaSelect" + cliente.getId() + "\">";
				
				for(Provincia p: provincias) {

					if(p.getId() == cliente.getId_prov()) {
						htmlTablaUsuarios += "<option value=\""+p.getId()+"\" selected>"+p.getNombre()+"</option>";
						localidades = utildao.obtenerLocalidadesXProv(p.getId());
					} else {
						htmlTablaUsuarios += "<option value=\""+p.getId()+"\" >"+p.getNombre()+"</option>";
					}
					
				}
				
				htmlTablaUsuarios += "</select></td>";
				
				// LOCALIDADES
				
				htmlTablaUsuarios += "<td><select class=\"form-control\" id=\"localidadSelect" + cliente.getId() + "\">";
				for(Provincia l: localidades) {

					if(l.getId() == cliente.getId_loc()) {
						htmlTablaUsuarios += "<option value=\""+l.getId()+"\" selected>"+l.getNombre()+"</option>";
					} else {
						htmlTablaUsuarios += "<option value=\""+l.getId()+"\" >"+l.getNombre()+"</option>";
					}
					
				}
				
				htmlTablaUsuarios += "</select></td>";
				
				htmlTablaUsuarios += "<td><input type=\"text\" class=\"form-control\" id=\"usuarioCli" + cliente.getId() + "\" placeholder=\"-\" value=\"" +u.getUsuario() + "\"></input></td>";
				htmlTablaUsuarios += "<td><input type=\"text\" class=\"form-control\" id=\"passCli" + cliente.getId() + "\" placeholder=\"-\" value=\"" +u.getPass() + "\"></input></td>";
				
				htmlTablaUsuarios += "<td><select class=\"form-control\" id=\"estadoSelect" + cliente.getId() + "\">";
				
				if(u.getEstado()==1) {
					htmlTablaUsuarios += "<option value=\"0\">Deshabilitado</option>";
					htmlTablaUsuarios += "<option value=\"1\" selected>Habilitado</option>";
				} else {
					htmlTablaUsuarios += "<option value=\"0\" selected>Deshabilitado</option>";
					htmlTablaUsuarios += "<option value=\"1\">Habilitado</option>";
				}
				
				htmlTablaUsuarios += "</select></td>";
				
				htmlTablaUsuarios += "<td><button type=\"button\" class=\"btn-success form-control\" id=\"GuardaCambios\" onClick=\"guardarCambiosUsuario("+cliente.getId()+")\">Guardar</button></td></tr>";
			    
				}
			
			
			request.setAttribute("usuarios", htmlTablaUsuarios);
			RequestDispatcher rd = request.getRequestDispatcher("content/usuarios.jsp");
			rd.forward(request, response);
		}
		
		
		
	}

}
