package cl.ubb.testing.safeit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.ubb.testing.safeit.exceptions.ReporteErrorException;
import cl.ubb.testing.safeit.fixtures.ReporteFixture;
import cl.ubb.testing.safeit.models.Correccion;
import cl.ubb.testing.safeit.models.Reporte;
import cl.ubb.testing.safeit.repositories.CorreccionRepository;
import cl.ubb.testing.safeit.repositories.ReporteRepository;
import cl.ubb.testing.safeit.services.CorreccionServiceImplementation;
import cl.ubb.testing.safeit.services.ReporteServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class CorreccionServiceTest {
	@Mock
	private CorreccionRepository correccionRepository;
	
	@InjectMocks
	private CorreccionServiceImplementation correccionService;

	@Test
	void siSolicitoSaveOrUpdateCorreccionConUnaCorreccionValidaYSeGuardaCorrectamenteRetornaLaCorreccion() {
		//Arrange
		Correccion correccion, resultado;
		correccion = new Correccion();
		correccion.setDescripcion("Reporte falso");
		when(correccionRepository.saveAndFlush(correccion)).thenReturn(correccion);
		
		//Act
		resultado = correccionService.saveOrUpdate(correccion);
		
		//Assert
		assertNotNull(resultado);
		assertEquals("Reporte falso",resultado.getDescripcion());

	}
	
	@Test
	void siSolicitoSaveReporteConUnReporteValidoYNoSeGuardaRetornaNull() {
		//Arrange
		Correccion correccion, resultado;
		correccion = new Correccion();
		when(correccionRepository.saveAndFlush(correccion)).thenReturn(null);

		
		//Act
		resultado = correccionService.saveOrUpdate(correccion);
		
		//Assert
		assertNull(resultado);
	}
	
	@Test
	void siInvocoGetAllYNoExistenCorreccionesDebeRetornarUnaListaVacia() {
		//Arrange
		List<Correccion> resultado;
		when(correccionRepository.findAll()).thenReturn(new ArrayList<>());
		
		//Act
		resultado = correccionService.getCorrecciones();
		
		//Assert
		assertNotNull(resultado);
		assertEquals(0, resultado.size());
	}

	
	@Test
	void siInvocoGetAllYExistenReportesDebeRetornarUnaListaDeReportes() {
		//Arrange
		List<Correccion> correcciones, resultado;
		Correccion a = new Correccion();
		Correccion b = new Correccion();
		a.setDescripcion("Reporte falso");
		b.setDescripcion("Reporte erróneo");
		correcciones = new ArrayList<Correccion>();
		correcciones.add(a);
		correcciones.add(b);
		when(correccionRepository.findAll()).thenReturn(correcciones);

		//Act
		resultado = correccionService.getCorrecciones();
		
		//Assert
		assertNotNull(resultado);
		assertEquals("Reporte falso",resultado.get(0).getDescripcion());
		assertEquals("Reporte erróneo",resultado.get(1).getDescripcion());
	}
}
