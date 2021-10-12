package cl.ubb.testing.safeit.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import cl.ubb.testing.safeit.models.Usuario;

public interface UsuarioRepository{

	public void save(Usuario usuario);
	
	public List<Usuario> findAll();
	
	public Usuario findById(int id);
	
	public void deleteById(int id);
	
	
	
}
