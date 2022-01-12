package cl.ubb.testing.safeit.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.ubb.testing.safeit.exceptions.ReporteErrorException;
import cl.ubb.testing.safeit.fixtures.ReporteFixture;
import cl.ubb.testing.safeit.fixtures.UsuarioFixture;
import cl.ubb.testing.safeit.models.Reporte;
import cl.ubb.testing.safeit.models.Usuario;
import cl.ubb.testing.safeit.services.ReporteServiceImplementation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ReporteControllerTest {

private MockMvc mockMvc;
	
	@Mock
	private ReporteServiceImplementation reporteService;
	
	@InjectMocks
	private ReporteController reporteController;
	
	private JacksonTester<Reporte> jsonReporte;
	private JacksonTester<List<Reporte>> jsonListReportes;
	
	@BeforeEach
	void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
		mockMvc = MockMvcBuilders.standaloneSetup(reporteController).build();
	}
	
	@Test
	void siInvocoGetReportesConUnIdYExisteUnReporteConEseIdDebeDevolverReporteYStatusOk() throws Exception {
		//Given
		Reporte reporte = ReporteFixture.obtenerReporte();
		given(reporteService.findById(reporte.getIdReporte())).willReturn(reporte);
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/reportes/0")
		        .contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		
		//Then
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonReporte.write(reporte).getJson(), response.getContentAsString());
	}
	
	@Test
	void siInvocoGetReporteConUnIdYNoExisteUnEmpleadoConEseIdDebeDevolverStatusNotFound() throws Exception {
		//Given
		Reporte reporte = ReporteFixture.obtenerReporte();
		given(reporteService.findById(reporte.getIdReporte())).willReturn(null);
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/reportes/0")
		        .accept(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		
		//Then
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}
	
	/*
	@Test
	void siInvocoCreateReporteSeDebeAlmacenarYDevolverElReporteConStatusCreated() throws IOException, Exception {
		//Given
		Reporte reporte = ReporteFixture.obtenerReporte();
		given(reporteService.save(reporte)).willReturn(reporte);
		
		//When
		MockHttpServletResponse response = mockMvc.perform(post("/reporte/agregar")
		        .contentType(MediaType.APPLICATION_JSON).content(jsonReporte.write(reporte).getJson()))
				.andReturn()
				.getResponse();
				
		// Then
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals(jsonReporte.write(reporte).getJson(), response.getContentAsString());
	}*/
	
	@Test
	void siInvocoListReportesYExistenReportesDebeDevolverTodosLosReportesYStatusOk() throws Exception {
		//Given
		List <Reporte> reportes = ReporteFixture.obtenerReportesFixture();
		given(reporteService.getAll()).willReturn(reportes);
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/reportes/listar")
		        .contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		
		//Then
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonListReportes.write(reportes).getJson(), response.getContentAsString());
	}
	
	@Test
	void siInvocoListReportesYNoExistenReportesDebeDevolverStatusNotFound() throws Exception {
		//Given
		given(reporteService.getAll()).willReturn(new ArrayList<>());
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/reportes/listar")
		        .contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		
		//Then
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}
	
	@Test
	void siInvocoFindByFechaYExistenReportesDebeDevolverTodosLosReportesConEsaFechaYStatusOk() throws Exception {
		//Given
		List <Reporte> reportes = ReporteFixture.obtenerReportesFixture();
		String sDate1="08/12/2021";
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		String sDate2="20/12/2021";
		Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);
		
		given(reporteService.findAllByFechaBetween(date, date2)).willReturn(reportes);
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/reportes/fecha/2021-12-08/2021-12-20")
		        .contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		
		//Then
		System.out.println(jsonListReportes.write(reportes));
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonListReportes.write(reportes).getJson(), response.getContentAsString());
	}
	
	@Test
	void siInvocoFindByFechaYNoExistenReportesConEsaFechaDebeDevolverStatusNotFound() throws Exception {
		//Given
		String sDate1="09/12/2021";
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		String sDate2="20/12/2021";
		Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);
		given(reporteService.findAllByFechaBetween(date, date2)).willReturn(new ArrayList<Reporte>());
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/reportes/fecha/2021-12-09/2021-12-20")
		        .contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		
		//Then
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}
	
	@Test
	void siInvocoFindByNombreYExistenReportesConEseNombreDebeDevolverReportesConEseNombreYStatusOk() throws Exception {
		//Given
		List <Reporte> reportes = ReporteFixture.obtenerReportesPorNombreFixture();
		given(reporteService.findByNombre("Rayados")).willReturn(reportes);
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/reportes/nombre/Rayados")
		        .contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		
		//Then
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonListReportes.write(reportes).getJson(), response.getContentAsString());
	}
	
	@Test
	void siInvocoFindByNombreYNoExistenReportesConEseNombreDebeRetornarStatusNotFound() throws Exception {
		//Given
		given(reporteService.findByNombre("Rayados")).willReturn(new ArrayList<>());
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/reportes/nombre/Rayados")
		        .contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		
		//Then
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}
	

	/*
	
	@Test
	void siInvocoFindByDescripcionYExistenReportesConEsaDescripcionDebeDevolverReportesConEsaDescripcionYStatusOk() throws Exception {
		//Given
		List <Reporte> reportes = ReporteFixture.obtenerReportesPorNombreFixture();
		given(reporteService.findByDescripcion("Rayados")).willReturn(reportes);
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/reportes/descripcion/Rayados")
		        .contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		
		//Then
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonListReportes.write(reportes).getJson(), response.getContentAsString());
	}
	*/
	
	/*
	@Test
	void siInvocoFindByDescripcionYNoExistenReportesConEsaDescripcionDebeRetornarStatusNotFound() throws Exception {
		//Given
		given(reporteService.findByDescripcion("Rayados")).willReturn(new ArrayList<>());
		
		//When
<<<<<<< HEAD
		MockHttpServletResponse response = mockMvc.perform(delete("/reportes/eliminar/0")
=======
		MockHttpServletResponse response = mockMvc.perform(get("/reportes/descripcion/Rayados")
>>>>>>> 5b62c78180e15bb194b26de411e26442765ef5cb
		        .contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		
		//Then
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}
<<<<<<< HEAD
	*/
	

	
	@Test
	void siInvocoFindByDescripcionYExistenReportesConEsaDescripcionDebeDevolverReportesConEsaDescripcionYStatusOk() throws Exception {
		//Given
		List <Reporte> reportes = ReporteFixture.obtenerReportesPorNombreFixture();
		given(reporteService.findByDescripcion("Rayados")).willReturn(reportes);
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/reportes/descripcion/Rayados")
		        .contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		
		//Then
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonListReportes.write(reportes).getJson(), response.getContentAsString());
	}
	
	@Test
	void siInvocoFindByDescripcionYNoExistenReportesConEsaDescripcionDebeRetornarStatusNotFound() throws Exception {
		//Given
		given(reporteService.findByDescripcion("Rayados")).willReturn(new ArrayList<>());
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/reportes/descripcion/Rayados")
		        .contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		
		//Then
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}


	/*
	@Test
	void siInvocoUpdateReporteYSeActualizaCorrectamenteDebeRetornarElReporteYStatusOk() throws IOException, Exception {
		//Given
		Reporte reporte = ReporteFixture.obtenerReporte();
		given(reporteService.update((Reporte) any(Reporte.class))).willReturn(reporte);
		//When
		MockHttpServletResponse response = mockMvc.perform(put("/reportes/actualizar/0")
		        .contentType(MediaType.APPLICATION_JSON).content(jsonReporte.write(reporte).getJson()))
				.andReturn()
				.getResponse();
		
		//Then
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	*/

	@Test
	@DisplayName(value = "FindAllByOrderByFechaAsc")
	void siInvocoFindAllByOrderByFechaAscYExistenReportesDebeRetornarUnaListaOrdenadaAscYStatusOK() throws Exception {
		//Given
		String fechaMenor="2021/10/11";
		String fechaMayor="2021/11/15";

		Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(fechaMenor);
		Date date2 = new SimpleDateFormat("yyyy/MM/dd").parse(fechaMayor);

		Reporte reporte1 = new Reporte();
		Reporte reporte2 = new Reporte();

		reporte1.setFecha(date1);
		reporte2.setFecha(date2);

		ArrayList<Reporte> reportes = new ArrayList<>();
		reportes.add(reporte1);
		reportes.add(reporte2);

		given(reporteService.findAllByOrderByFechaAsc()).willReturn(reportes);

		//When
		MockHttpServletResponse response = mockMvc.perform(get("/reportes/listar/ascendente")
						.contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();

		//Then
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertTrue(reporte1.getFecha().before(reporte2.getFecha()));
	}

	@Test
	@DisplayName(value = "FindAllByOrderByFechaDesc")
	void siInvocoFindAllByOrderByFechaDescYExistenReportesDebeRetornarUnaListaOrdenadaAscYStatusOK() throws Exception {
		//Given
		String fechaMenor="2021/10/11";
		String fechaMayor="2021/11/15";

		Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(fechaMenor);
		Date date2 = new SimpleDateFormat("yyyy/MM/dd").parse(fechaMayor);

		Reporte reporte1 = new Reporte();
		Reporte reporte2 = new Reporte();

		reporte1.setFecha(date1);
		reporte2.setFecha(date2);

		ArrayList<Reporte> reportes = new ArrayList<>();
		reportes.add(reporte1);
		reportes.add(reporte2);

		given(reporteService.findAllByOrderByFechaDesc()).willReturn(reportes);

		//When
		MockHttpServletResponse response = mockMvc.perform(get("/reportes/listar/descendente")

						.contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();

		//Then
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertTrue(reporte2.getFecha().after(reporte1.getFecha()));
	}

	@Test
	@DisplayName(value = "FindAllByOrderByFechaAsc NOT_FOUD")
	void siInvocoFindAllByOrderByFechaAscYNoExistenReportesDebeRetornarUnaListaVaciaYStatusNotFound() throws Exception {
		//Given
		String fechaMenor="2021/10/11";
		String fechaMayor="2021/11/15";

		Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(fechaMenor);
		Date date2 = new SimpleDateFormat("yyyy/MM/dd").parse(fechaMayor);

		Reporte reporte1 = new Reporte();
		Reporte reporte2 = new Reporte();

		reporte1.setFecha(date1);
		reporte2.setFecha(date2);

		given(reporteService.findAllByOrderByFechaAsc()).willReturn(new ArrayList<Reporte>());

		//When
		MockHttpServletResponse response = mockMvc.perform(get("/reportes/listar/ascendente")
						.contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();

		//Then
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}

	@Test
	@DisplayName(value = "FindAllByOrderByFechaDesc NOT_FOUD")
	void siInvocoFindAllByOrderByFechaDescYNoExistenReportesDebeRetornarUnaListaVaciaYStatusNotFound() throws Exception {
		//Given
		String fechaMenor="2021/10/11";
		String fechaMayor="2021/11/15";

		Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(fechaMenor);
		Date date2 = new SimpleDateFormat("yyyy/MM/dd").parse(fechaMayor);

		Reporte reporte1 = new Reporte();
		Reporte reporte2 = new Reporte();

		reporte1.setFecha(date1);
		reporte2.setFecha(date2);

		given(reporteService.findAllByOrderByFechaDesc()).willReturn(new ArrayList<Reporte>());

		//When
		MockHttpServletResponse response = mockMvc.perform(get("/reportes/listar/descendente")
						.contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();

		//Then
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}
	
	@Test
	void siBuscoReportesPorNivelDeGravedadYExistenReportesConEseNivelDebeRetornarUnaListaDeReportesYStatusOk() throws Exception {
		//Given
		List<Reporte> reportes= ReporteFixture.obtenerReportesNivelDeGravedadFixture();
		given(reporteService.obtenerReportesPorNivelDeGravedad("MEDIA")).willReturn(reportes);
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/reportes/nivelDeGravedad/MEDIA")
		        .contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		
		//Then
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonListReportes.write(reportes).getJson(), response.getContentAsString());
		
	}
	
	@Test
	void siBuscoReportesPorNivelDeGravedadYNoExistenReportesConEseNivelDebeRetornarUnaListaVaciaYStatusNotFound() throws Exception {
		
		//Given
		given(reporteService.obtenerReportesPorNivelDeGravedad("MEDIA")).willReturn(new ArrayList<>());
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/reportes/nivelDeGravedad/MEDIA")
		        .contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		
		//Then
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());	
		
		
	}

}
