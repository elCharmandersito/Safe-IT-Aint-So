package cl.ubb.testing.safeit.controllers;

import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.BDDMockito.given;
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
		String sDate1="09/12/2021";
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		
		given(reporteService.findByFecha(date)).willReturn(reportes);
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/reportes/fecha/2021-12-09")
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
		given(reporteService.findByFecha(date)).willReturn(new ArrayList<Reporte>());
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/reportes/fecha/2021-12-09")
		        .contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		
		//Then
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}
	
	/*
	@Test
	void siInvocoDeleteReporteYSeEliminaExitosamenteDebeRetornarStatusOk() {
		//Given
		Reporte reporte = ReporteFixture.obtenerReporte();
		//given(reporteService.deleteById(reporte.getIdReporte())).willReturn(reporte);
		
		//When
		
		
		//Then
			
		
		
	}
	
	@Test
	void siInvocoUpdateReporteYSeActualizaCorrectamenteDebeRetornarElReporteYStatusOk() throws IOException, Exception {
		//Given
		Reporte reporte = ReporteFixture.obtenerReporte();
		given(reporteService.update((Reporte) any(Reporte.class))).willReturn(reporte);
		
		//When
		MockHttpServletResponse response = mockMvc.perform(put("/reporte/actualizar/0")
		        .contentType(MediaType.APPLICATION_JSON).content(jsonReporte.write(reporte).getJson()))
				.andReturn()
				.getResponse();
		
		//Then
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		
		
		
	}*/
	
	
}
