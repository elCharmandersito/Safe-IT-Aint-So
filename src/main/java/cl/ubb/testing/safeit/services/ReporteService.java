package cl.ubb.testing.safeit.services;

import java.util.Date;
import java.util.List;
import cl.ubb.testing.safeit.exceptions.ReporteErrorException;
import cl.ubb.testing.safeit.models.Reporte;

public interface ReporteService {
	
	Reporte save(Reporte reporte) throws ReporteErrorException;

	public List<Reporte> getAll();

	Reporte findById(int id);

	Reporte update(Reporte reporte);
	
	List<Reporte> findByFecha(Date fecha);

	List<Reporte> findByNombre(String nombre);

	long deleteById(int id);

	List<Reporte> findByDescripcion(String descripcion);

}
