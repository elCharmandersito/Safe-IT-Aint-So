package cl.ubb.testing.safeit.services;

import java.util.Date;
import java.util.List;

import cl.ubb.testing.safeit.exceptions.ReporteErrorException;
import cl.ubb.testing.safeit.models.Correccion;
import cl.ubb.testing.safeit.models.Reporte;

public interface CorreccionService {
	Correccion saveOrUpdate(Correccion correccion);

	Correccion findById(int id);
	
	List<Correccion> getCorrecciones();


}
