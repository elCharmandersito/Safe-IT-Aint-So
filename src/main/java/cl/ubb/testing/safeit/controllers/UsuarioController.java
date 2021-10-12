package cl.ubb.testing.safeit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ubb.testing.safeit.models.Usuario;
import cl.ubb.testing.safeit.services.UsuarioService;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping(value = "/agregar", produces ="application/json")
	public ResponseEntity<Usuario> addCliente(@RequestBody Usuario usuario) {
		try {
			usuarioService.createUsuario(usuario);
			return new ResponseEntity<Usuario> (usuario, HttpStatus.CREATED);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
		}
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
	

}
