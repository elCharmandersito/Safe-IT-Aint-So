package cl.ubb.testing.safeit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import cl.ubb.testing.safeit.exceptions.ReporteErrorException;
import cl.ubb.testing.safeit.fixtures.ReporteFixture;
import cl.ubb.testing.safeit.models.Reporte;
import cl.ubb.testing.safeit.repositories.ReporteRepository;
import cl.ubb.testing.safeit.services.ReporteServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class ReporteServiceTest {
	
	@Mock
	private ReporteRepository reporteRepository;
	
	@InjectMocks
	private ReporteServiceImplementation reporteService;

	@Test
	void siSolicitoSaveReporteConUnReporteValidoYSeGuardaCorrectamenteRetornaElReporte() throws ReporteErrorException{
		//Arrange
		Reporte reporte, resultado;
		reporte = ReporteFixture.obtenerReporte();
		when(reporteRepository.save(reporte)).thenReturn(reporte);
		
		//Act
		resultado = reporteService.save(reporte);
		
		//Assert
		assertNotNull(resultado);
		assertEquals(reporte.getIdReporte(),resultado.getIdReporte());
		assertEquals(reporte.getNombre(), resultado.getNombre());
		assertEquals(reporte.getDescripcion(), resultado.getDescripcion());
		assertEquals(reporte.getNivelGravedad(), resultado.getNivelGravedad());
		assertEquals(reporte.getUsuario(), resultado.getUsuario());
	}
	
	@Test
	void siSolicitoSaveReporteConUnReporteValidoYNoSeGuardaRetornaNull() throws ReporteErrorException {
		//Arrange
		Reporte reporte, resultado;
		reporte = ReporteFixture.obtenerReporte();
		when(reporteRepository.save(reporte)).thenReturn(null);
		
		//Act
		resultado = reporteService.save(reporte);
		
		//Assert
		assertNull(resultado);
	}
	
	@Test
	void siSolicitoSaveReporteConUnReporteQueYaExisteDebeLanzarReporteErrorExeption() throws ReporteErrorException {
		//Arrange
		Reporte reporte;
		reporte = ReporteFixture.obtenerReporte();
		when(reporteRepository.findById(reporte.getIdReporte())).thenReturn(reporte);
		
		//Act + Assert
		assertThrows(ReporteErrorException.class, () -> reporteService.save(reporte));
	}
	
	@Test
	void siSolicitoUpdateReporteConUnReporteValidoYSeActualizaDebeRetornarElReporte() throws ReporteErrorException {
		//Arrange
		Reporte reporte, reporte2, resultado;
		reporte = ReporteFixture.obtenerReporte();
		reporte2 = ReporteFixture.obtenerReporte();
		reporte2.setNombre("Rayados inapropiados");
		when(reporteRepository.findById(reporte.getIdReporte())).thenReturn(reporte);
		when(reporteRepository.save(reporte2)).thenReturn(reporte2);
		
		//Act
		resultado = reporteService.merge(reporte2);
		
		//Assert
		assertNotNull(resultado);
		assertEquals(reporte.getIdReporte(),resultado.getIdReporte());
		assertEquals(reporte.getDescripcion(), resultado.getDescripcion());
		assertEquals(reporte.getNivelGravedad(), resultado.getNivelGravedad());
		assertEquals(reporte.getUsuario(), resultado.getUsuario());
	}
	
	@Test
	void siSolicitoUpdateReporteConUnReporteQueNoExisteDebeLanzarReporteErrorException() throws ReporteErrorException{
		//Arrange
		Reporte reporte;
		reporte = ReporteFixture.obtenerReporte();		
		when(reporteRepository.findById(reporte.getIdReporte())).thenReturn(null);
				
		//Act + Assert	
		assertThrows(ReporteErrorException.class, () -> reporteService.merge(reporte));
	}
	
	@Test
	void siSolicitoUpdateReporteConUnReporteValidoYNoSeActualizaLanzaReporteErrorException() throws ReporteErrorException {
		//Arrange
		Reporte reporte;
		reporte = ReporteFixture.obtenerReporte();		
		when(reporteRepository.findById(reporte.getIdReporte())).thenReturn(reporte);
		
		//Act + Assert
		assertThrows(ReporteErrorException.class, () -> reporteService.merge(reporte));
	}
	
	@Test
	void siSolicitoReportePorSuIdYNoExisteUnReporteConEseIdDebeRetornarNull() {
		//Arrange
		Reporte resultado;
		when(reporteRepository.findById(0)).thenReturn(null);
		
		//Act
		resultado = reporteService.findById(0);
		
		//Assert
		assertNull(resultado);
	}
	
	@Test
	void siSolicitoReportePorSuIdYExisteUnReporteConEseIdDebeRetornalElReporte() {
		//Arrange
		Reporte reporte, resultado;
		reporte = ReporteFixture.obtenerReporte();
		when (reporteRepository.findById(reporte.getIdReporte())).thenReturn((reporte));
			
		//Act
		resultado = reporteService.findById(reporte.getIdReporte());
			
		//Assert
		assertNotNull(resultado);
		assertEquals(reporte.getIdReporte(),resultado.getIdReporte());
		assertEquals(reporte.getNombre(), resultado.getNombre());
		assertEquals(reporte.getDescripcion(), resultado.getDescripcion());
		assertEquals(reporte.getNivelGravedad(), resultado.getNivelGravedad());
		assertEquals(reporte.getUsuario(), resultado.getUsuario());
	}

	@Test
	void siInvocoGetAllYNoExistenReportesDebeRetornarUnaListaVacia() {
		//Arrange
		List<Reporte> resultado;
		when(reporteRepository.findAll()).thenReturn(new ArrayList<>());
		
		//Act
		resultado = reporteService.getAll();
		
		//Assert
		assertNotNull(resultado);
		assertEquals(0, resultado.size());
	}

	
	@Test
	void siInvocoGetAllYExistenReportesDebeRetornarUnaListaDeReportes() {
		//Arrange
		List<Reporte> resultado;
		when(reporteRepository.findAll()).thenReturn(ReporteFixture.obtenerReportesFixture());

		//Act
		resultado = reporteService.getAll();
		
		//Assert
		assertNotNull(resultado);
		assertEquals(0,resultado.get(0).getIdReporte());
		assertEquals("Rayados",resultado.get(0).getNombre());
		assertEquals(1,resultado.get(1).getIdReporte());
		assertEquals("Basura",resultado.get(1).getNombre());
	}
	
	@Test
	void siInvocoFindByFechaYExistenReportesConEsaFechaDebeRetornarUnaListaDeReportes() throws ParseException {
		// Arrange
		List<Reporte> resultado;
		when(reporteRepository.findByDate(new Date(1639017322L))).thenReturn(ReporteFixture.obtenerReportesFixture());
		String sDate1="09/12/2021";
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		
		// Act
		resultado = reporteService.findByFecha(new Date(1639017322L));
		
		// Assert
		assertNotNull(resultado);
		assertEquals(date, resultado.get(0).getFecha());
	}
	
	@Test
	void siInvocoFindByFechaYNoExistenReportesConEsaFechaDebeRetornarUnaListaVacia() throws ParseException {
		// Arrange
		List<Reporte> resultado;
		String sDate1="09/12/2021";
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		when(reporteRepository.findByDate(date)).thenReturn(new ArrayList<Reporte>());
		
		// Act
		resultado = reporteService.findByFecha(date);

		// Assert
		assertNotNull(resultado);
		assertEquals(0, resultado.size());
	}
}
