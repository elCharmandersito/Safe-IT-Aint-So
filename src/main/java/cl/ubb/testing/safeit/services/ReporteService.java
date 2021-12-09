package cl.ubb.testing.safeit.services;

import java.util.List;
import cl.ubb.testing.safeit.exceptions.ReporteErrorException;
import cl.ubb.testing.safeit.models.Reporte;

public interface ReporteService {
	
	Reporte save(Reporte reporte) throws ReporteErrorException;

	public List<Reporte> getAll();

	Reporte findById(int id);

	Reporte update(Reporte reporte);

}
