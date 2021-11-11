package cl.ubb.testing.safeit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.ubb.testing.safeit.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, CustomUsuarioRepository<Usuario>{

	Usuario findByCorreo(String correo);
	
	Usuario findById(int id);

}
