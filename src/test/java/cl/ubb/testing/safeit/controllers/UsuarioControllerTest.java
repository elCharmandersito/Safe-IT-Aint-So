package cl.ubb.testing.safeit.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.ubb.testing.safeit.fixtures.UsuarioFixture;
import cl.ubb.testing.safeit.models.Usuario;
import cl.ubb.testing.safeit.services.UsuarioService;
import cl.ubb.testing.safeit.services.UsuarioServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {
	
	@Mock
	private UsuarioServiceImplementation usuarioService;
	
	@InjectMocks
	private UsuarioController usuarioController;

	
	@SuppressWarnings("unchecked")
	
	@Test 
	public void siInvocoListClientesYNoExistenUsuariosDebeRetornarNull() {
		//Arrange
		when(usuarioService.getAll()).thenReturn(null);
		
		//Act
		List <Usuario> resultado = usuarioController.listClientes().getBody();

		//Assert
		assertNull(resultado);
	}
	
	@Test 
	public void siInvocoListClientesDebeListarTodosLosUsuarios() {
		//Arrange
		List<Usuario> usuarios = UsuarioFixture.obtenerUsuariosFixture();
		when(usuarioService.getAll()).thenReturn(usuarios);
		
		//Act
		List <Usuario> resultado = usuarioController.listClientes().getBody();

		//Assert
		assertNotNull(resultado);
		assertEquals(usuarios.get(0).getidUsuario(),resultado.get(0).getidUsuario());
		assertEquals(usuarios.get(0).getApellidoMaterno(),resultado.get(0).getApellidoMaterno());
		assertEquals(usuarios.get(0).getApellidoPaterno(),resultado.get(0).getApellidoPaterno());
		assertEquals(usuarios.get(1).getidUsuario(),resultado.get(1).getidUsuario());
		assertEquals(usuarios.get(1).getApellidoMaterno(),resultado.get(1).getApellidoMaterno());
		assertEquals(usuarios.get(1).getApellidoPaterno(),resultado.get(1).getApellidoPaterno());
	}
	
	
	
	
}
