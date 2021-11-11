package cl.ubb.testing.safeit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import cl.ubb.testing.safeit.exceptions.UsuarioNotFoundException;
import cl.ubb.testing.safeit.exceptions.WrongPasswordException;
import cl.ubb.testing.safeit.models.Rol;
import cl.ubb.testing.safeit.models.Usuario;
import cl.ubb.testing.safeit.repositories.UsuarioRepository;
import cl.ubb.testing.safeit.services.UsuarioService;
import cl.ubb.testing.safeit.services.UsuarioServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
	@Mock
	private UsuarioRepository repo;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private UsuarioServiceImplementation usuarioService;
	
	@Test
	void siIniciaSesionConDatosValidosRetornaUsuarioConToken() throws EmailNotFoundException, WrongPasswordException{
		// Arrange
		Usuario usuario = new Usuario();
		usuario.setidUsuario(1);
		usuario.setNombre("Ignacio");
		usuario.setApellidoPaterno("Orellana");
		usuario.setApellidoMaterno("Ramos");
		usuario.setCorreo("igorella@alumnos.ubiobio.cl");
		usuario.setPassword("1234567890");
		usuario.setRol(Rol.ADMINISTRADOR);
		
		when(repo.findByCorreo("igorella@alumnos.ubiobio.cl")).thenReturn(usuario);
		when(passwordEncoder.matches("1234567890", usuario.getPassword())).thenReturn(true);		
		// Act
		Usuario resultado = usuarioService.login(usuario.getCorreo(), usuario.getPassword());
		
		// Assert
		assertNotNull(resultado);
		assertTrue(usuario.getToken().matches("Bearer .*"));		
	}
	
	@Test
	void siIniciaSesionConEmailNoRegistradoLanzaEmailNotFoundException() {
		// Arrange
		when(repo.findByCorreo("igorella@alumnos.ubiobio.cl")).thenReturn(null);
		
		// Act + Assert
        assertThrows(EmailNotFoundException.class, () -> usuarioService.login("igorella@alumnos.ubiobio.cl", "1234"));
	}
	
	@Test
	void siIniciaSesionConContrasenaErroneaLanzaWrongPasswordException() {
		// Arrange
		Usuario usuario = new Usuario();
		usuario.setidUsuario(1);
		usuario.setNombre("Ignacio");
		usuario.setApellidoPaterno("Orellana");
		usuario.setApellidoMaterno("Ramos");
		usuario.setCorreo("igorella@alumnos.ubiobio.cl");
		usuario.setPassword("1234567890");
		usuario.setRol(Rol.ADMINISTRADOR);

		when(repo.findByCorreo("igorella@alumnos.ubiobio.cl")).thenReturn(usuario);
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
		
		when(repo.findById(1)).thenReturn(usuario);
		
		// Act
		Usuario resultado = usuarioService.validarUsuario(1); 
		
		// Assert
		assertTrue(resultado.isActivo());
		
	}
	
	@Test
	void siValidaUnUsuarioYElIdNoExisteLanzaUsuarioNotFoundException() {
		when(repo.findById(1)).thenReturn(null);
		
		// Act + Assert
        assertThrows(UsuarioNotFoundException.class, () -> usuarioService.validarUsuario(1));
	}
	
		
		
	
}
