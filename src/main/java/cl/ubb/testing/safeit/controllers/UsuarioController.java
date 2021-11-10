package cl.ubb.testing.safeit.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.ubb.testing.safeit.models.Usuario;
import cl.ubb.testing.safeit.services.UsuarioService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping(value = "/agregar", produces ="application/json")
	public ResponseEntity<Usuario> addCliente(@RequestBody Usuario usuario) {
		try {
			usuarioService.saveUsuario(usuario);
			return new ResponseEntity<Usuario> (usuario, HttpStatus.CREATED);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("user")
	public ResponseEntity<Usuario> login(@RequestParam("correo") String correo, @RequestParam("password") String pwd) {
		String token = getJWTToken(correo);
		Usuario user = usuarioService.loadUsuarioByCorreo(correo);
		if (passwordEncoder.matches(pwd, user.getPassword())) {
			user.setToken(token);		
			return new ResponseEntity<Usuario>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<Usuario>(HttpStatus.UNAUTHORIZED);
		}
		
		
	}

	private String getJWTToken(String correo) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(usuarioService.loadUsuarioByCorreo(correo).getRol().toString());
		
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
	
	@GetMapping(value = "/listar", produces ="application/json")
	public ResponseEntity<List<Usuario>> listClientes() {
		
		try {
			List<Usuario> usuarios = usuarioService.getAll();
			return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<List<Usuario>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PutMapping(value = "/{id}")     
	public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario, @PathVariable("id") int id){
		Usuario usuarioAux;         
		try {
			Usuario updateUsuario = usuarioService.findById(id);
			usuario.setidUsuario(id);
			usuarioService.saveUsuario(usuario);
		} catch (Exception e) {             
			e.printStackTrace(); 
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);                       
	}
	

}
