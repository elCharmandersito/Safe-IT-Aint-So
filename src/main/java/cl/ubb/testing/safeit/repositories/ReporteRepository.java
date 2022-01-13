package cl.ubb.testing.safeit.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.ubb.testing.safeit.models.Reporte;

public interface ReporteRepository extends JpaRepository<Reporte, Integer>{

	Reporte findById(int id);
	
	List<Reporte> findAllByFechaBetween(Date date, Date date2);

	List<Reporte> findByNombreContaining(String nombre);
	
	void deleteById(int id);

	List<Reporte> findByDescripcionContaining(String descripcion);

	List<Reporte> findAllByOrderByFechaAsc();

	List<Reporte> findAllByOrderByFechaDesc();
	
	@Query(value="SELECT * FROM reporte WHERE reporte.nivel_gravedad=:nivel", nativeQuery=true)
	List<Reporte> obtenerReportesNivelDeGravedad(int nivel);

	@Query(value="SELECT * FROM reporte WHERE reporte.tipo_reporte=:i", nativeQuery=true)
	List<Reporte> findByReportesPorTipo(int i);
	
	@Query(value="Select * from reporte ORDER BY reporte.nivel_gravedad DESC", nativeQuery=true)
    List<Reporte> findByReporteOrdenDeGravedad();

}
