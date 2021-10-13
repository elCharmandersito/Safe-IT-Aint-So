package cl.ubb.testing.safeit.services;

import java.util.List;
import java.util.Optional;

import cl.ubb.testing.safeit.models.Usuario;

public interface UsuarioService {	
	public void saveUsuario(Usuario usuario);

	public List<Usuario> getAll();

	public Usuario getById(int id);
	
	public List<Usuario> getApellido(String apellido);
}
