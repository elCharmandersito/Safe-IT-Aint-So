package cl.ubb.testing.safeit.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cl.ubb.testing.safeit.models.Usuario;
import cl.ubb.testing.safeit.services.UsuarioService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping({"/","/index"})
	public String index(){return "index";}

	@PostMapping("/user_form")
	public String userForm(RedirectAttributes rAttributes){
		rAttributes.addFlashAttribute("mensaje", "Agregado correctamente");
		return "redirect:/success";
	}

	@GetMapping("/success")
	public String success(){return "success";
	}
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping(value = "/agregar", produces ="application/json")
	public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuario) {
		try {
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			usuarioService.save(usuario);
			return new ResponseEntity<Usuario> (usuario, HttpStatus.CREATED);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping("login")
	public ResponseEntity<Usuario> login(@RequestParam("correo") String correo, @RequestParam("password") String pwd) {	
		try {
			Usuario user = usuarioService.login(correo, pwd);
			return new ResponseEntity<Usuario>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Usuario>(HttpStatus.UNAUTHORIZED);

		}
	}

	
	@GetMapping(value = "/listar", produces ="application/json")
	public ResponseEntity<List<Usuario>> listClientes() {
		if (!usuarioService.getAll().isEmpty()) {
			List<Usuario> usuarios = usuarioService.getAll();
			return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
		}
		return new ResponseEntity<List<Usuario>>(HttpStatus.NOT_FOUND);
	}
	
	
	
	@PutMapping(value = "/actualizar/{id}")     
	public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario, @PathVariable("id") int id){ 
		try {
			usuario.setidUsuario(id);
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			if (usuarioService.merge(usuario) != null) {
				return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);  
			}else {
				return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
			}
			
			   
		} catch (Exception e) {             
			e.printStackTrace(); 
			return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
		}             
	}
	
	@PostMapping("/validarUsuario")
	public ResponseEntity<Usuario> validarUsuario(@RequestParam("id") int id) {	
		try {
			Usuario user = usuarioService.validarUsuario(id);
			return new ResponseEntity<Usuario>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);

		}
	}
	
	@DeleteMapping(value = "/eliminar/{id}")     
	public ResponseEntity<Usuario> deleteUsuario(@PathVariable("id") int id){
		Usuario usuario;         
		try {
			usuario = usuarioService.findById(id);
			usuario.setidUsuario(id);
			usuarioService.delete(usuario);
			return new ResponseEntity<>(HttpStatus.OK);  
		} catch (Exception e) {             
			e.printStackTrace(); 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		                     
	}

}