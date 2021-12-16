package cl.ubb.testing.safeit.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.ubb.testing.safeit.models.Correccion;
import cl.ubb.testing.safeit.models.Reporte;

@Repository
public interface CorreccionRepository extends JpaRepository<Correccion, Integer>{
	Correccion findById(int id);
	
}
