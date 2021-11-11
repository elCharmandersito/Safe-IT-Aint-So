package cl.ubb.testing.safeit.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.ubb.testing.safeit.exceptions.EmailNotFoundException;
import cl.ubb.testing.safeit.exceptions.UsuarioNotFoundException;
import cl.ubb.testing.safeit.exceptions.WrongPasswordException;
import cl.ubb.testing.safeit.models.Rol;
import cl.ubb.testing.safeit.models.Usuario;
import cl.ubb.testing.safeit.repositories.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UsuarioServiceImplementation implements UsuarioService {
	@Autowired
	UsuarioRepository repo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void saveUsuario(Usuario usuario) {
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		repo.save(usuario);
	}

	@Override
	public List<Usuario> getAll() {
		return repo.findAll();
	}


	@Override
	public Usuario findById(int id) {
		return repo.findById(id);
	}
	
	public List<Usuario> getApellido(String apellido) {
		return repo.findByApellidoPaterno(apellido);
		
	}
	
	public Usuario login(String correo, String password) throws EmailNotFoundException, WrongPasswordException {
		Usuario user = repo.findByCorreo(correo);
		if (user == null) {
			throw new EmailNotFoundException();
		}
		
		if (passwordEncoder.matches(password, user.getPassword())) {
			user.setToken(getJWTToken(correo));
			repo.save(user);
			return user;
		} else {
			throw new WrongPasswordException();
		}
		
		
	}
	
	public String getJWTToken(String correo) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(repo.findByCorreo(correo).getRol().toString());
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(correo)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public Usuario validarUsuario(int id) throws UsuarioNotFoundException{
		Usuario usuario = repo.findById(id);
		if (usuario != null) {
			usuario.setActivo(true);
			repo.save(usuario);
			return usuario;
		} else {
			throw new UsuarioNotFoundException();
		}
	}
	

}

