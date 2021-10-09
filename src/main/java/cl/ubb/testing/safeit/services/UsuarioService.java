package cl.ubb.testing.safeit.services;

import java.util.List;

import cl.ubb.testing.safeit.models.Usuario;

public interface UsuarioService {	
	public void createUsuario(Usuario usuario);

	public List<Usuario> getAll();
}
