package cl.ubb.testing.safeit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.ubb.testing.safeit.fixtures.UsuarioFixture;
import cl.ubb.testing.safeit.models.Usuario;
import cl.ubb.testing.safeit.repositories.UsuarioRepository;
import cl.ubb.testing.safeit.services.UsuarioServiceImplementation;


@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
	
	@Mock
	private UsuarioRepository usuarioRepository;

	@InjectMocks
	private UsuarioServiceImplementation usuarioService;
	
	
	@Test
	void siSolicitoUsuarioPorSuIdYNoExisteUnUsuarioConEseIdDebeRetornalNull() {
		//Arrange
		Usuario resultado;
		when(usuarioRepository.getById(1)).thenReturn(null);
		
		//Act
		resultado = usuarioService.getById(1);
		
		//Assert
		assertNull(resultado);
	}
	
	@Test
	void siSolicitoUsuarioPorSuIdYExisteUnUsuarioConEseIdDebeRetornalElUsuario() {
		//Arrange
		Usuario resultado;
		when (usuarioRepository.getById(0)).thenReturn(UsuarioFixture.obtenerUsuario());
		
		//Act
		resultado = usuarioService.getById(0);
		
		//Assert
		assertNotNull(resultado);
		assertEquals("Daniel",resultado.getNombre());
	}
	
	
	@Test
	void siInvocoGetAllYNoExistenUsuariosDebeRetornarUnaListaVacia() {
		//Arrange
		List<Usuario> resultado;
		when(usuarioRepository.findAll()).thenReturn(new ArrayList<>());
		
		//Act
		resultado = usuarioService.getAll();
		
		//Assert
		assertNotNull(resultado);
		assertEquals(0, resultado.size());
	}

	
	@Test
	void siInvocoGetAllYExistenUsuariosDebeRetornarUnaListaDeUsuarios() {
		//Arrange
		List<Usuario> resultado;	
		when(usuarioRepository.findAll()).thenReturn(UsuarioFixture.obtenerUsuariosFixture());
		
		//Act
		resultado = usuarioService.getAll();
		
		//Assert
		assertNotNull(resultado);
		assertEquals("Orellana",resultado.get(0).getApellidoMaterno());
		assertEquals("Maria",resultado.get(0).getNombre());
	}
	
	
	@Test
	void siSolicitoGetApellidoDeUsuarioYNoExistenDebeRetornarUnaListaVacia() {
		//Arrange
		List<Usuario> resultado;
		when(usuarioRepository.findByApellidoPaterno("Pedro")).thenReturn(null);
		
		//Act
		resultado = usuarioService.getApellido("Pedro");
		
		//Assert
		assertNull(resultado);
	}
	
	@Test
	void siSolicitoGetApellidoDeUsuarioYExisteDebeRetornarUnaDeUsuario() {
		//Arrange
		List<Usuario> resultado;
		
		when(usuarioRepository.findByApellidoPaterno("Orellana")).thenReturn(UsuarioFixture.obtenerUsuariosFixture());
		
		//Act
		resultado = usuarioService.getApellido("Orellana");
		
		//Assert
		assertNotNull(resultado);
		assertEquals("Orellana",resultado.get(0).getApellidoMaterno());
	}

}
