package cl.ubb.testing.safeit.services;

import java.util.List;
import java.util.Optional;

import cl.ubb.testing.safeit.exceptions.EmailNotFoundException;
import cl.ubb.testing.safeit.exceptions.UsuarioNotFoundException;
import cl.ubb.testing.safeit.exceptions.WrongPasswordException;
import cl.ubb.testing.safeit.models.Usuario;

public interface UsuarioService {	
	public void saveUsuario(Usuario usuario);

	public List<Usuario> getAll();

	public Usuario findById(int id);
	
	public List<Usuario> getApellido(String apellido);
	
	public Usuario login(String correo, String password) throws EmailNotFoundException, WrongPasswordException;

	public String getJWTToken(String correo);

	public Usuario validarUsuario(int id) throws UsuarioNotFoundException;
	
}
