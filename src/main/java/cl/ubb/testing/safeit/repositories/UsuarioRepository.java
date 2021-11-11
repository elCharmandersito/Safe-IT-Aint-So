package cl.ubb.testing.safeit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.ubb.testing.safeit.exceptions.UsuarioErrorException;
import cl.ubb.testing.safeit.models.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, CustomUsuarioRepository<Usuario>{

	
	Usuario findById(int id);
	
	Usuario findByCorreo(String correo) throws UsuarioErrorException ;

}
