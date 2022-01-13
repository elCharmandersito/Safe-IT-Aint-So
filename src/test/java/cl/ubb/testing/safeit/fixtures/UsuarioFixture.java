package cl.ubb.testing.safeit.fixtures;

import java.util.ArrayList;
import java.util.List;

import cl.ubb.testing.safeit.models.Usuario;

public class UsuarioFixture {

	public static List<Usuario> obtenerUsuariosFixture(){	

		Usuario usuarioUno = new Usuario();
		Usuario usuarioDos = new Usuario();
		ArrayList<Usuario> usuarios = new ArrayList<>(); 
		
		usuarioUno.setidUsuario(0);
		usuarioUno.setApellidoMaterno("Orellana");
		usuarioUno.setApellidoPaterno("Lopez");
		usuarioUno.setNombre("Maria");
		
		usuarioDos.setidUsuario(1);
		usuarioDos.setApellidoMaterno("Cea");
		usuarioDos.setApellidoPaterno("Parra");
		usuarioDos.setNombre("Daniel");

		usuarios.add(usuarioUno);
		usuarios.add(usuarioDos);
		return usuarios;
		
	}
	
	public static Usuario obtenerUsuario() {
		Usuario usuario = new Usuario();
		usuario.setidUsuario(1);
		usuario.setCorreo("correo@gmail.com");
		usuario.setApellidoMaterno("Cea");
		usuario.setApellidoPaterno("Parra");
		usuario.setNombre("Daniel");
		usuario.setReportes(ReporteFixture.obtenerReportesFixture());
		return usuario;
	}
	
}
