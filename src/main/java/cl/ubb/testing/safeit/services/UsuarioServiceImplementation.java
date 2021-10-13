package cl.ubb.testing.safeit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ubb.testing.safeit.models.Usuario;
import cl.ubb.testing.safeit.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImplementation implements UsuarioService {
	@Autowired
	UsuarioRepository repo;

	@Override
	public void saveUsuario(Usuario usuario) {
		repo.save(usuario);
	}

	@Override
	public List<Usuario> getAll() {
		return repo.findAll();
	}


	@Override
	public Usuario getById(int id) {
		return repo.getById(id);
	}
	
	public List<Usuario> getApellido(String apellido) {
		return repo.findByApellidoPaterno(apellido);
		
	}
	
	
	

}
