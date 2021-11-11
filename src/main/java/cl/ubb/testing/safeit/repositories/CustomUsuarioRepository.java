package cl.ubb.testing.safeit.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.ubb.testing.safeit.models.Usuario;

@Repository
public interface CustomUsuarioRepository<Usuario> {
	List<Usuario> findByApellidoPaterno(String apellido);
	
}
