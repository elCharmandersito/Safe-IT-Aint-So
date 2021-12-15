package cl.ubb.testing.safeit.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

	
	@PostMapping(value = "/reporte/agregar", produces ="application/json")
	public ResponseEntity<Reporte> createReporte(@RequestBody Reporte reporte) {
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
	
	
	@PutMapping(value = "/reporte/actualizar/{id}")     
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
	
	@DeleteMapping(value = "/reporte/eliminar/{id}")     
	public ResponseEntity<Reporte> deleteReporte(@PathVariable("id") int id){
		long cant = 0;         
		cant = reporteService.deleteById(id);
		if (cant == 1) {
			return new ResponseEntity<>(HttpStatus.OK);  
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}                    
	}
	
	@GetMapping(value = "/reportes/fecha/{fecha}", produces ="application/json")
	public ResponseEntity<List<Reporte>> findReportesByFecha(@PathVariable("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {
		List<Reporte> reportes = reporteService.findByFecha(fecha);
		if (!reportes.isEmpty()) {
			return new ResponseEntity<List<Reporte>>(reportes, HttpStatus.OK);
		}
		return new ResponseEntity<List<Reporte>>(HttpStatus.NOT_FOUND);

	}
	
	@GetMapping(value = "/reportes/nombre/{nombre}", produces ="application/json")
	public ResponseEntity<List<Reporte>> findReportesByNombre( @PathVariable("nombre") String nombre) {
		List<Reporte> reportes = reporteService.findByNombre(nombre);
		if (!reportes.isEmpty()) {
			return new ResponseEntity<List<Reporte>>(reportes, HttpStatus.OK);
		}
		return new ResponseEntity<List<Reporte>>(HttpStatus.NOT_FOUND);
	}
	
	
	
}
