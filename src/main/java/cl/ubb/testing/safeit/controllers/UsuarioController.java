package cl.ubb.testing.safeit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ubb.testing.safeit.models.Usuario;
import cl.ubb.testing.safeit.services.UsuarioService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping({"/","/index"})
	public String index(){return "index";}

	@PostMapping("/user_form")
	public String userForm(RedirectAttributes rAttributes){
		rAttributes
				.addFlashAttribute("mensaje", "Agregado correctamente");
		return "redirect:/success";
	}

	@GetMapping("/success")
	public String success(){return "success";
	}
	
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
			Usuario updateUsuario = usuarioService.getById(id);
			usuario.setidUsuario(id);
			usuarioService.saveUsuario(usuario);
		} catch (Exception e) {             
			e.printStackTrace(); 
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);                       
	}
}