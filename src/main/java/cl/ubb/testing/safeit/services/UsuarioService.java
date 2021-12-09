package cl.ubb.testing.safeit.services;

import java.util.List;
import cl.ubb.testing.safeit.exceptions.EmailNotFoundException;
import cl.ubb.testing.safeit.exceptions.UsuarioErrorException;
import cl.ubb.testing.safeit.exceptions.UsuarioNotFoundException;
import cl.ubb.testing.safeit.exceptions.WrongPasswordException;
import cl.ubb.testing.safeit.models.Usuario;

public interface UsuarioService {

	Usuario save(Usuario usuario) throws UsuarioErrorException;

	public List<Usuario> getAll();

	public Usuario findById(int id);
	
	public Usuario login(String correo, String password) throws EmailNotFoundException, WrongPasswordException, UsuarioErrorException;

	public String getJWTToken(String correo) throws UsuarioErrorException;

	public Usuario validarUsuario(int id) throws UsuarioNotFoundException;
	
	public void delete(Usuario usuario);

	Usuario merge(Usuario usuario)throws UsuarioErrorException;


}
