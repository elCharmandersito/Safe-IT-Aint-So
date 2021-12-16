package cl.ubb.testing.safeit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ubb.testing.safeit.models.Correccion;
import cl.ubb.testing.safeit.repositories.CorreccionRepository;

@Service
public class CorreccionServiceImplementation implements CorreccionService {
	@Autowired
	private CorreccionRepository correccionRepository;
	
	public Correccion saveOrUpdate(Correccion correccion) {
		return correccionRepository.saveAndFlush(correccion);
	}

	public List<Correccion> getCorrecciones() {
		return correccionRepository.findAll();
	}
	
	public Correccion findById(int id) {
		return correccionRepository.findById(id);
	}
}
