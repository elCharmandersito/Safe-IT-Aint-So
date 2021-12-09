package cl.ubb.testing.safeit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.ubb.testing.safeit.exceptions.ReporteErrorException;
import cl.ubb.testing.safeit.models.Reporte;
import cl.ubb.testing.safeit.repositories.ReporteRepository;

@Service
public class ReporteServiceImplementation implements ReporteService{

	@Autowired
	ReporteRepository repo;
	
	public Reporte save(Reporte reporte) throws ReporteErrorException {
		if(findById(reporte.getIdReporte()) == null) {
			return repo.save(reporte);
		}
		throw new ReporteErrorException();
	}

	public Reporte merge(Reporte reporte2) throws ReporteErrorException {
		if (findById(reporte2.getIdReporte()) != null) {
			if (repo.save(reporte2) != null) {
				return repo.save(reporte2);
			}else {
				throw new ReporteErrorException();
			}
		}
		throw new ReporteErrorException();
	}

	public Reporte findById(int i) {
		return repo.findById(i);
	}

	public List<Reporte> getAll() {
		return repo.findAll();
	}

	public void deleteById(int id) {
		repo.deleteById(id);
	}

	public Reporte update(Reporte reporte) {
		return repo.saveAndFlush(reporte);
	}


}
