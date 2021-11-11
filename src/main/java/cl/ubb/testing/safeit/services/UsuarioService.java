package cl.ubb.testing.safeit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.ubb.testing.safeit.exception.UsuarioErrorException;
import cl.ubb.testing.safeit.models.Usuario;

public interface UsuarioService {

	Usuario save(Usuario usuario) throws UsuarioErrorException;

	Usuario merge(Usuario usuario) throws UsuarioErrorException;
	
	//public void saveUsuario(Usuario usuario);

	public List<Usuario> getAll();

	public Usuario findById(int id);
	
	//public List<Usuario> getApellido(String apellido);
	
	//public Usuario loadUsuarioByCorreo(String correo);

	//public void delete(Usuario usuario);
}
