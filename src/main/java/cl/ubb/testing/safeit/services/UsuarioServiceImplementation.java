package cl.ubb.testing.safeit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.ubb.testing.safeit.exception.UsuarioErrorException;
import cl.ubb.testing.safeit.models.Usuario;
import cl.ubb.testing.safeit.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImplementation implements UsuarioService {
	
	@Autowired
	UsuarioRepository repo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Usuario save(Usuario usuario) throws UsuarioErrorException{
		if (findById(usuario.getIdUsuario()) == null) {
			return repo.save(usuario);
		}
		throw new UsuarioErrorException();
	}

	public Usuario merge(Usuario usuario) throws UsuarioErrorException {
		if (findById(usuario.getIdUsuario()) != null) {
			if (repo.merge(usuario) != null) {
				return repo.merge(usuario);
			}else {
				throw new UsuarioErrorException();
			}
		}
		throw new UsuarioErrorException();
		
	}

	public Usuario findById(int idUsuario) {
		if (repo.findById(idUsuario).isPresent()) {
			return repo.findById(idUsuario).get();
		}else {
			return null;
		}
		
	}

	/*
	@Override
	public void saveUsuario(Usuario usuario) {
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		repo.save(usuario);
	}*/

	@Override
	public List<Usuario> getAll() {
		return repo.findAll();
	}

/*
	@Override
	public Usuario findById(int id) {
		return repo.findById(id).get();
	}
	
	public List<Usuario> getApellido(String apellido) {
		return repo.findByApellidoPaterno(apellido);
	}

	public Usuario loadUsuarioByCorreo(String correo) {
		return repo.findByCorreo(correo);
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*
	@Override
	public void delete(Usuario usuario) {
		repo.delete(usuario);
		
	}*/
	

}

