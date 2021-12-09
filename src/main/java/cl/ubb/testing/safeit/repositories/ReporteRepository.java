package cl.ubb.testing.safeit.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.ubb.testing.safeit.models.Reporte;

public interface ReporteRepository extends JpaRepository<Reporte, Integer>{

	Reporte findById(int id);
	
	List<Reporte> findByDate(Date date);

}
