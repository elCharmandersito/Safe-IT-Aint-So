package cl.ubb.testing.safeit.services;

import java.util.List;
import java.util.Optional;


import cl.ubb.testing.safeit.exceptions.EmailNotFoundException;
import cl.ubb.testing.safeit.exceptions.UsuarioErrorException;
import cl.ubb.testing.safeit.exceptions.UsuarioNotFoundException;
import cl.ubb.testing.safeit.exceptions.WrongPasswordException;

import org.springframework.stereotype.Service;

import cl.ubb.testing.safeit.models.Usuario;

public interface UsuarioService {

	Usuario save(Usuario usuario) throws UsuarioErrorException;
	
	//public void saveUsuario(Usuario usuario);

	public List<Usuario> getAll();

	public Usuario findById(int id);
	
	//public List<Usuario> getApellido(String apellido);
	
	public Usuario login(String correo, String password) throws EmailNotFoundException, WrongPasswordException, UsuarioErrorException;

	public String getJWTToken(String correo) throws UsuarioErrorException;

	public Usuario validarUsuario(int id) throws UsuarioNotFoundException;
	

	//public Usuario loadUsuarioByCorreo(String correo);

	public void delete(Usuario usuario);

	Usuario merge(Usuario usuario)throws UsuarioErrorException;


}
