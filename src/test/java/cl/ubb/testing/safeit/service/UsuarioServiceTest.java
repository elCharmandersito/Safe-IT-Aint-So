package cl.ubb.testing.safeit.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import cl.ubb.testing.safeit.exception.UsuarioErrorException;
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
	void siSolicitoSaveUsuarioConUnUsuarioValidoYSeGuardaCorrectamenteRetornaElUsuario() throws UsuarioErrorException{
		//Arrange
		Usuario usuario, resultado;
		usuario = UsuarioFixture.obtenerUsuario();
		when(usuarioRepository.save(usuario)).thenReturn(usuario);
		
		//Act
		resultado = usuarioService.save(usuario);
		
		//Assert
		assertNotNull(resultado);
		assertEquals(usuario.getIdUsuario(),resultado.getIdUsuario());
		assertEquals(usuario.getApellidoMaterno(),resultado.getApellidoMaterno());
		assertEquals(usuario.getApellidoPaterno(),resultado.getApellidoPaterno());
		assertEquals(usuario.getCorreo(),resultado.getCorreo());
		assertEquals(usuario.getFono(),resultado.getFono());
		assertEquals(usuario.getDireccion(),resultado.getDireccion());
	}
	
	@Test
	void siSolicitoSaveUsuarioConUnUsuarioValidoYNoSeGuardaRetornaNull() throws UsuarioErrorException {
		//Arrange
		Usuario usuario, resultado;
		usuario = UsuarioFixture.obtenerUsuario();
		when(usuarioRepository.save(usuario)).thenReturn(null);
		
		//Act
		resultado = usuarioService.save(usuario);
		
		//Assert
		assertNull(resultado);
	}
	
	@Test
	void siSolicitoSaveUsuarioConUnUsuarioQueYaExisteDebeLanzarUsuarioErrorExeption() throws UsuarioErrorException {
		//Arrange
		Usuario usuario;
		usuario = UsuarioFixture.obtenerUsuario();
		when(usuarioRepository.findById(usuario.getIdUsuario())).thenReturn(Optional.of(usuario));
		
		//Act + Assert
		assertThrows(UsuarioErrorException.class, () -> usuarioService.save(usuario));
	}
	
	@Test
	void siSolicitoUpdateUsuarioConUnUsuarioValidoYSeActualizaConExisteDebeRetornarElUsuario() throws UsuarioErrorException {
		//Arrange
		Usuario usuario, usuario2, resultado;
		usuario = UsuarioFixture.obtenerUsuario();
		usuario2 = UsuarioFixture.obtenerUsuario();
		usuario2.setCorreo("MiCorreo@gmail.com");
		when(usuarioRepository.findById(usuario.getIdUsuario())).thenReturn(Optional.of(usuario));
		when(usuarioRepository.merge(usuario2)).thenReturn(usuario2);
		
		//Act
		resultado = usuarioService.merge(usuario2);
		
		//Assert
		assertNotNull(resultado);
		assertNotEquals(usuario.getCorreo(),resultado.getCorreo());
		assertEquals(usuario.getIdUsuario(),resultado.getIdUsuario());
		assertEquals(usuario.getApellidoMaterno(),resultado.getApellidoMaterno());
		assertEquals(usuario.getApellidoPaterno(),resultado.getApellidoPaterno());
		assertEquals(usuario.getFono(),resultado.getFono());
		assertEquals(usuario.getDireccion(),resultado.getDireccion());
	}
	
	@Test
	void siSolicitoUpdateUsuarioConUnUsuarioQueNoExisteDebeLanzarUsuarioErrorException() throws UsuarioErrorException{
		//Arrange
		Usuario usuario;
		usuario = UsuarioFixture.obtenerUsuario();		
		when(usuarioRepository.findById(usuario.getIdUsuario())).thenReturn(Optional.empty());
				
		//Act + Assert	
		assertThrows(UsuarioErrorException.class, () -> usuarioService.merge(usuario));
	}
	
	@Test
	void siSolicitoUpdateUsuarioConUnUsuarioValidoYNoSeActualizaLanzaUsuarioErrorException() throws UsuarioErrorException {
		//Arrange
		Usuario usuario;
		usuario = UsuarioFixture.obtenerUsuario();		
		when(usuarioRepository.findById(usuario.getIdUsuario())).thenReturn(Optional.of(usuario));
		
		//Act + Assert
		assertThrows(UsuarioErrorException.class, () -> usuarioService.merge(usuario));
	}
	
	@Test
	void siSolicitoUsuarioPorSuIdYNoExisteUnUsuarioConEseIdDebeRetornarNull() {
		//Arrange
		Usuario resultado;
		when(usuarioRepository.findById(1)).thenReturn(Optional.empty());
		
		//Act
		resultado = usuarioService.findById(1);
		
		//Assert
		assertNull(resultado);
	}
	
	@Test
	void siSolicitoUsuarioPorSuIdYExisteUnUsuarioConEseIdDebeRetornalElUsuario() {
		//Arrange
		Usuario usuario, resultado;
		usuario = UsuarioFixture.obtenerUsuario();
		when (usuarioRepository.findById(usuario.getIdUsuario())).thenReturn(Optional.of(usuario));
			
		//Act
		resultado = usuarioService.findById(usuario.getIdUsuario());
			
		//Assert
		assertNotNull(resultado);
		assertEquals(usuario.getCorreo(),resultado.getCorreo());
		assertEquals(usuario.getIdUsuario(),resultado.getIdUsuario());
		assertEquals(usuario.getApellidoMaterno(),resultado.getApellidoMaterno());
		assertEquals(usuario.getApellidoPaterno(),resultado.getApellidoPaterno());
		assertEquals(usuario.getFono(),resultado.getFono());
		assertEquals(usuario.getDireccion(),resultado.getDireccion());
	}
	/*
	
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
	}*/

}
