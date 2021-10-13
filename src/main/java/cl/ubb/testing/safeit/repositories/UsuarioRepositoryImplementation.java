package cl.ubb.testing.safeit.repositories;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import cl.ubb.testing.safeit.models.Usuario;

@Repository
public class UsuarioRepositoryImplementation implements UsuarioRepository{
	
	@PersistenceContext
	private EntityManager em;
	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void save(Usuario usuario) {
		this.em.merge(usuario);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> findAll() {
		return this.em.createQuery("SELECT usuario FROM Usuario usuario").getResultList();

	}

	@Override
	public Usuario findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		
	}

}