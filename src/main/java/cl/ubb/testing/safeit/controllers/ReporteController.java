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
import org.springframework.web.bind.annotation.RestController;
import cl.ubb.testing.safeit.models.Reporte;
import cl.ubb.testing.safeit.services.ReporteService;

@RestController
public class ReporteController {
	
	@Autowired
	private ReporteService reporteService;

	
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
	
	
}
