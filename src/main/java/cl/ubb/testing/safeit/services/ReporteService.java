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
	
	List<Reporte> findAllByFechaBetween(Date fecha, Date fecha2);

	List<Reporte> findByNombre(String nombre);

	void deleteById(int id);
	
	boolean existsById(int id);

	List<Reporte> findByDescripcion(String descripcion);

	List<Reporte> findAllByOrderByFechaAsc();

	List<Reporte> findAllByOrderByFechaDesc();
	
	List<Reporte> obtenerReportesPorNivelDeGravedad(String nivel);
	
	List<Reporte> findByTipo(String tipo);

}
