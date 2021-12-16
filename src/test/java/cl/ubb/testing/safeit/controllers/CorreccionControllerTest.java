package cl.ubb.testing.safeit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

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

import cl.ubb.testing.safeit.fixtures.ReporteFixture;
import cl.ubb.testing.safeit.models.Correccion;
import cl.ubb.testing.safeit.models.Reporte;
import cl.ubb.testing.safeit.services.CorreccionServiceImplementation;
import cl.ubb.testing.safeit.services.ReporteServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class CorreccionControllerTest {
	private MockMvc mockMvc;
	
	@Mock
	private CorreccionServiceImplementation correccionService;
	
	@Mock
	private ReporteServiceImplementation reporteService;
	
	@InjectMocks
	private CorreccionController correccionController;
	
	private JacksonTester<Correccion> jsonCorreccion;
	private JacksonTester<List<Correccion>> jsonListCorrecciones;
	
	@BeforeEach
	void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
		mockMvc = MockMvcBuilders.standaloneSetup(correccionController).build();
	}
	
	@Test
	void siInvocoListReportesYExistenReportesDebeDevolverTodosLosReportesYStatusOk() throws Exception {
		//Given
		List <Correccion> correcciones = new ArrayList<Correccion>();
		Correccion a = new Correccion();
		Correccion b = new Correccion();
		a.setDescripcion("Reporte falso");
		b.setDescripcion("Reporte erroneo");
		correcciones.add(a);
		correcciones.add(b);

		given(correccionService.getCorrecciones()).willReturn(correcciones);
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/correcciones/listar")
		        .contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		
		//Then
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonListCorrecciones.write(correcciones).getJson(), response.getContentAsString());
	}
	
	@Test
	void siInvocoListCorreccionesYNoExistenCorreccionesDebeDevolverStatusNotFound() throws Exception {
		//Given
		given(correccionService.getCorrecciones()).willReturn(new ArrayList<>());
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/correcciones/listar")
		        .contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		
		//Then
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}
	
	/*
	@Test
	void siInvocoCreateCorreccionSeDebeAlmacenarYDevolverLaCorreccionConStatusCreated() throws Exception {
		//Given
		Correccion correccion = new Correccion();
		correccion.setDescripcion("Reporte falso");
		Reporte reporte = new Reporte();
		given(correccionService.saveOrUpdate(correccion)).willReturn(correccion);
		given(reporteService.findById(1)).willReturn(reporte);
		given(reporteService.findById(1).getCorrecciones()).willReturn(new ArrayList<Correccion>());
		
		//When
		MockHttpServletResponse response = mockMvc.perform(post("/correcciones/agregar?id=1")
		        .contentType(MediaType.APPLICATION_JSON).content(jsonCorreccion.write(correccion).getJson()))
				.andReturn()
				.getResponse();
				
		// Then
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals(jsonCorreccion.write(correccion).getJson(), response.getContentAsString());
	}
	*/
	
}
