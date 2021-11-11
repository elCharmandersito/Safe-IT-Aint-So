package cl.ubb.testing.safeit.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import ch.qos.logback.core.status.Status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.ubb.testing.safeit.exceptions.UsuarioErrorException;
import cl.ubb.testing.safeit.fixtures.UsuarioFixture;
import cl.ubb.testing.safeit.models.Usuario;
import cl.ubb.testing.safeit.services.UsuarioService;
import cl.ubb.testing.safeit.services.UsuarioServiceImplementation;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private UsuarioServiceImplementation usuarioService;
	
	@InjectMocks
	private UsuarioController usuarioController;

	private JacksonTester<Usuario> jsonUsuario;
	private JacksonTester<List<Usuario>> jsonListUsuario;
	
	@BeforeEach
	void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
		mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
	}
	
	@Test
	public void siInvocoAddUsuarioConUnUsuarioValidoDebeRetornarElUsuarioYCreated() throws Exception {

		// Given
		Usuario usuario = new Usuario();
		//given(usuarioService.save(usuario)).willReturn(usuario);
				
		// When
		MockHttpServletResponse response = mockMvc.perform(post("/agregar")
		        .contentType(MediaType.APPLICATION_JSON).content(jsonUsuario.write(usuario).getJson()))
				.andReturn()
				.getResponse();
				
		// Then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.getContentAsString()).isEqualTo(
		              jsonUsuario.write(usuario).getJson());
	}
	
	@Test
	public void siSeInvocaListUsuarioYExistenClientesDebeRetornarListaDeUsuarioYOk() throws Exception {
		//Given
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(new Usuario());
		usuarios.add(new Usuario());
		given(usuarioService.getAll()).willReturn(usuarios);
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/listar")
                .accept(MediaType.APPLICATION_JSON))
        		.andReturn()
        		.getResponse();
		
		
		// Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonListUsuario.write(usuarios).getJson()
        );
	}
	
	@Test
	public void siSeInvocaListUsuarioYNoExistenClientesDebeRetornarNotFound() throws Exception {
		//Given
		given(usuarioService.getAll()).willReturn(new ArrayList<Usuario>());
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/listar")
                .accept(MediaType.APPLICATION_JSON))
        		.andReturn()
        		.getResponse();
		
		
		// Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}	
}
