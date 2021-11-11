package cl.ubb.testing.safeit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.ubb.testing.safeit.exceptions.UsuarioErrorException;
import cl.ubb.testing.safeit.models.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, CustomUsuarioRepository<Usuario>{
	
	Usuario findById(int id);
	Usuario findByCorreo(String correo) throws UsuarioErrorException ;

}
