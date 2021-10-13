package cl.ubb.testing.safeit.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.ubb.testing.safeit.models.Usuario;

public class CustomUsuarioRepositoryImplementation implements CustomUsuarioRepository {
	@Autowired 
	private EntityManager em;

	public List<Usuario> findByApellidoPaterno(String apellido) {
		return em.createQuery("SELECT Usuario FROM Usuario usuario").getResultList();
	}
	
	
}
