package cl.ubb.testing.safeit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import org.springframework.security.crypto.password.PasswordEncoder;

import cl.ubb.testing.safeit.exceptions.EmailNotFoundException;
import cl.ubb.testing.safeit.exceptions.UsuarioErrorException;
import cl.ubb.testing.safeit.exceptions.UsuarioNotFoundException;
import cl.ubb.testing.safeit.exceptions.WrongPasswordException;
import cl.ubb.testing.safeit.models.Rol;
import cl.ubb.testing.safeit.models.Usuario;
import cl.ubb.testing.safeit.repositories.UsuarioRepository;
import cl.ubb.testing.safeit.services.UsuarioService;
import cl.ubb.testing.safeit.fixtures.UsuarioFixture;
import cl.ubb.testing.safeit.models.Usuario;
import cl.ubb.testing.safeit.repositories.UsuarioRepository;
import cl.ubb.testing.safeit.services.UsuarioServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private UsuarioServiceImplementation usuarioService;
	
	@Test
	void siIniciaSesionConDatosValidosRetornaUsuarioConToken() throws EmailNotFoundException, WrongPasswordException, UsuarioErrorException{
		// Arrange
		Usuario usuario = new Usuario();
		usuario.setidUsuario(1);
		usuario.setNombre("Ignacio");
		usuario.setApellidoPaterno("Orellana");
		usuario.setApellidoMaterno("Ramos");
		usuario.setCorreo("igorella@alumnos.ubiobio.cl");
		usuario.setPassword("1234567890");
		usuario.setRol(Rol.ADMINISTRADOR);
		
		when(usuarioRepository.findByCorreo("igorella@alumnos.ubiobio.cl")).thenReturn(usuario);
		when(passwordEncoder.matches("1234567890", usuario.getPassword())).thenReturn(true);		
		// Act
		Usuario resultado = usuarioService.login(usuario.getCorreo(), usuario.getPassword());
		
		// Assert
		assertNotNull(resultado);
		assertTrue(usuario.getToken().matches("Bearer .*"));		
	}
	
	@Test
	void siIniciaSesionConEmailNoRegistradoLanzaEmailNotFoundException() throws UsuarioErrorException {
		// Arrange
		when(usuarioRepository.findByCorreo("igorella@alumnos.ubiobio.cl")).thenReturn(null);
		
		// Act + Assert
        assertThrows(EmailNotFoundException.class, () -> usuarioService.login("igorella@alumnos.ubiobio.cl", "1234"));
	}
	
	@Test
	void siIniciaSesionConContrasenaErroneaLanzaWrongPasswordException() throws UsuarioErrorException {
		// Arrange
		Usuario usuario = new Usuario();
		usuario.setidUsuario(1);
		usuario.setNombre("Ignacio");
		usuario.setApellidoPaterno("Orellana");
		usuario.setApellidoMaterno("Ramos");
		usuario.setCorreo("igorella@alumnos.ubiobio.cl");
		usuario.setPassword("1234567890");
		usuario.setRol(Rol.ADMINISTRADOR);

		when(usuarioRepository.findByCorreo("igorella@alumnos.ubiobio.cl")).thenReturn(usuario);
		when(passwordEncoder.matches("abcdefgh", usuario.getPassword())).thenReturn(false);
		
		// Act + Assert
        assertThrows(WrongPasswordException.class, () -> usuarioService.login("igorella@alumnos.ubiobio.cl", "abcdefgh"));
	}
	
	@Test
	void siValidaUsuarioYElIdUsuarioExisteRetornaElUsuarioActivo() throws UsuarioNotFoundException {
		// Arrange
		Usuario usuario = new Usuario();
		usuario.setidUsuario(1);
		usuario.setNombre("Ignacio");
		usuario.setApellidoPaterno("Orellana");
		usuario.setApellidoMaterno("Ramos");
		usuario.setCorreo("igorella@alumnos.ubiobio.cl");
		usuario.setPassword("1234567890");
		usuario.setRol(Rol.ADMINISTRADOR);
		
		when(usuarioRepository.findById(1)).thenReturn(usuario);
		
		// Act
		Usuario resultado = usuarioService.validarUsuario(1); 
		
		// Assert
		assertTrue(resultado.isActivo());
		
	}
	
	@Test
	void siValidaUnUsuarioYElIdNoExisteLanzaUsuarioNotFoundException() {
		when(usuarioRepository.findById(1)).thenReturn(null);
		
		// Act + Assert
        assertThrows(UsuarioNotFoundException.class, () -> usuarioService.validarUsuario(1));
	}
	
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
		when(usuarioRepository.findById(usuario.getIdUsuario())).thenReturn(usuario);
		
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
		when(usuarioRepository.findById(usuario.getIdUsuario())).thenReturn(usuario);
		when(usuarioRepository.save(usuario2)).thenReturn(usuario2);
		
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
		when(usuarioRepository.findById(usuario.getIdUsuario())).thenReturn(null);
				
		//Act + Assert	
		assertThrows(UsuarioErrorException.class, () -> usuarioService.merge(usuario));
	}
	
	@Test
	void siSolicitoUpdateUsuarioConUnUsuarioValidoYNoSeActualizaLanzaUsuarioErrorException() throws UsuarioErrorException {
		//Arrange
		Usuario usuario;
		usuario = UsuarioFixture.obtenerUsuario();		
		when(usuarioRepository.findById(usuario.getIdUsuario())).thenReturn(usuario);
		
		//Act + Assert
		assertThrows(UsuarioErrorException.class, () -> usuarioService.merge(usuario));
	}
	
	@Test
	void siSolicitoUsuarioPorSuIdYNoExisteUnUsuarioConEseIdDebeRetornarNull() {
		//Arrange
		Usuario resultado;
		when(usuarioRepository.findById(1)).thenReturn(null);
		
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
		when (usuarioRepository.findById(usuario.getIdUsuario())).thenReturn((usuario));
			
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
