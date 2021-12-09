package cl.ubb.testing.safeit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.ubb.testing.safeit.models.Reporte;

public interface ReporteRepository extends JpaRepository<Reporte, Integer>{

	Reporte findById(int id);

}
