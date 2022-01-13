package cl.ubb.testing.safeit.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cl.ubb.testing.safeit.exceptions.UsuarioErrorException;
import cl.ubb.testing.safeit.models.EmailBody;
import cl.ubb.testing.safeit.models.Reporte;
import cl.ubb.testing.safeit.models.Usuario;
import cl.ubb.testing.safeit.services.EmailService;
import cl.ubb.testing.safeit.services.ReporteService;
import cl.ubb.testing.safeit.services.UsuarioService;

@RestController
public class ReporteController {
	
	@Autowired
	private ReporteService reporteService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UsuarioService usuarioService;

	
	@GetMapping(value = "/reportes/{id}", produces ="application/json")
	public ResponseEntity<Reporte> getReporte( @PathVariable("id") int id) {
		if (reporteService.findById(id) != null) {
			Reporte reporte = reporteService.findById(id);
			return new ResponseEntity<Reporte>(reporte, HttpStatus.OK);
		}
		return new ResponseEntity<Reporte>(HttpStatus.NOT_FOUND);
	}

	

	@PostMapping(value = "/reportes/agregar/{id}", produces ="application/json")
	public ResponseEntity<Reporte> createReporte(@RequestBody Reporte reporte, @PathVariable("id") int id) {
		List<Usuario> users = new ArrayList<>();
		EmailBody mail = null;
		if (reporteService.findById(reporte.getIdReporte()) == null) {
			String mailContent = reporte.getNombre() + "\n"
					+ reporte.getFecha() + "\n" + reporte.getDescripcion() +"\n" + reporte.getNivelGravedad();
			users = usuarioService.getAll();
			for(Usuario user: users) {
				mail = new EmailBody(user.getCorreo(), mailContent, "Nuevo reporte añadido");
				emailService.sendEmail(mail);
			}
		}
		Usuario usuario = usuarioService.findById(id);
		reporte.setUsuario(usuario);
		
		List<Reporte> reportes = new ArrayList<>();
		reportes.add(reporte);
		usuario.setReportes(reportes);
		
		try {
			reporteService.save(reporte);	
			return new ResponseEntity<Reporte> (reporte, HttpStatus.CREATED);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<Reporte>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping(value = "/reportes/listar", produces ="application/json")
	public ResponseEntity<List<Reporte>> listReportes() {
		if (!reporteService.getAll().isEmpty()) {
			List<Reporte> reportes = reporteService.getAll();
			return new ResponseEntity<List<Reporte>>(reportes, HttpStatus.OK);
		}
		return new ResponseEntity<List<Reporte>>(HttpStatus.NOT_FOUND);
	}
	
	
	@PutMapping(value = "/reportes/actualizar/{id}")     
	public ResponseEntity<Reporte> updateReporte(@RequestBody Reporte reporte, @PathVariable("id") int id){ 
		try {
			reporte.setIdReporte(id);
			if (reporteService.update(reporte) != null) {
				return new ResponseEntity<Reporte>(reporte, HttpStatus.OK);  
			}else {
				return new ResponseEntity<Reporte>(HttpStatus.NOT_FOUND);
			}
			
			   
		} catch (Exception e) {             
			e.printStackTrace(); 
			return new ResponseEntity<Reporte>(HttpStatus.BAD_REQUEST);
		}             
	}
	
	
	
	@DeleteMapping(value = "/reportes/eliminar/{id}")     
	public ResponseEntity<Reporte> deleteReporte(@PathVariable("id") int id){        
		try {
			Reporte reporte = reporteService.findById(id);
			reporte.setIdReporte(id);
			reporteService.delete(reporte);
			return new ResponseEntity<>(HttpStatus.OK);    
		} catch (Exception e) {             
			e.printStackTrace(); 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}     
	}
	
	@GetMapping(value = "/reportes/fecha/{fecha}/{fecha2}", produces ="application/json")
	public ResponseEntity<List<Reporte>> findReportesByFecha(@PathVariable("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha, @PathVariable("fecha2") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha2) {
		List<Reporte> reportes = reporteService.findAllByFechaBetween(fecha, fecha2);
		if (!reportes.isEmpty()) {
			return new ResponseEntity<List<Reporte>>(reportes, HttpStatus.OK);
		}
		return new ResponseEntity<List<Reporte>>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/reportes/nombre/{nombre}", produces ="application/json")
	public ResponseEntity<List<Reporte>> findReportesByNombre( @PathVariable("nombre") String nombre) {
		List<Reporte> reportes = reporteService.findByNombre(nombre);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getName());
		if (!reportes.isEmpty()) {
			return new ResponseEntity<List<Reporte>>(reportes, HttpStatus.OK);
		}
		return new ResponseEntity<List<Reporte>>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/reportes/descripcion/{descripcion}", produces ="application/json")
	public ResponseEntity<List<Reporte>> findReportesByDescripcion( @PathVariable("descripcion") String descripcion) {
		List<Reporte> reportes = reporteService.findByDescripcion(descripcion);
		if (!reportes.isEmpty()) {
			return new ResponseEntity<List<Reporte>>(reportes, HttpStatus.OK);
		}
		return new ResponseEntity<List<Reporte>>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/reportes/listar/ascendente", produces ="application/json")
	public ResponseEntity<List<Reporte>> findAllByOrderByFechaAsc() {
		List<Reporte> reportes = reporteService.findAllByOrderByFechaAsc();
		if (!reportes.isEmpty()) {
			return new ResponseEntity<List<Reporte>>(reportes, HttpStatus.OK);
		}
		return new ResponseEntity<List<Reporte>>(HttpStatus.NOT_FOUND);
	}


	@GetMapping(value = "/reportes/listar/descendente", produces ="application/json")
	public ResponseEntity<List<Reporte>> findAllByOrderByFechaDesc() {
		List<Reporte> reportes = reporteService.findAllByOrderByFechaDesc();
		if (!reportes.isEmpty()) {
			return new ResponseEntity<List<Reporte>>(reportes, HttpStatus.OK);
		}
		return new ResponseEntity<List<Reporte>>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/reportes/nivelDeGravedad/{nivel}", produces ="application/json")
	public ResponseEntity<List<Reporte>> findReportesNivelGravedad( @PathVariable("nivel") String nivel) {
		List<Reporte> reportes = reporteService.obtenerReportesPorNivelDeGravedad(nivel.toUpperCase());
		if (!reportes.isEmpty()) {
			return new ResponseEntity<List<Reporte>>(reportes, HttpStatus.OK);
		}
		return new ResponseEntity<List<Reporte>>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/reportes/tipoReporte/{tipo}", produces ="application/json")
	public ResponseEntity<List<Reporte>> findReportesTipo( @PathVariable("tipo") String tipo) {
		List<Reporte> reportes = reporteService.findByTipo(tipo.toUpperCase());
		if (!reportes.isEmpty()) {
			return new ResponseEntity<List<Reporte>>(reportes, HttpStatus.OK);
		}
		return new ResponseEntity<List<Reporte>>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/reportes/me", produces ="application/json")
	public ResponseEntity<List<Reporte>> findReportesByNombre() throws UsuarioErrorException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = usuarioService.findByCorreo(auth.getName());
		System.out.println(user.getReportes().size());
		if (user != null) {
			return new ResponseEntity<List<Reporte>>(user.getReportes(), HttpStatus.OK);
		}
		return new ResponseEntity<List<Reporte>>(HttpStatus.NOT_FOUND);
	}

}

