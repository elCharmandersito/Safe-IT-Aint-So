package cl.ubb.testing.safeit.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.ubb.testing.safeit.models.Correccion;
import cl.ubb.testing.safeit.models.EmailBody;
import cl.ubb.testing.safeit.models.Reporte;
import cl.ubb.testing.safeit.models.Usuario;
import cl.ubb.testing.safeit.services.CorreccionService;
import cl.ubb.testing.safeit.services.ReporteService;

@RestController
public class CorreccionController {
	@Autowired
	private CorreccionService correccionService;
	
	@Autowired
	private ReporteService reporteService;
	
	@PostMapping(value = "/correcciones/agregar", produces ="application/json")
	public ResponseEntity<Correccion> createCorreccion(@RequestBody Correccion correccion, @RequestParam int id) {
		try {
			correccion.setReporte(reporteService.findById(id));
			List<Correccion> correcciones = reporteService.findById(id).getCorrecciones();
			correcciones.add(correccion);
			reporteService.findById(id).setCorrecciones(correcciones);
			correccionService.saveOrUpdate(correccion);
			return new ResponseEntity<Correccion> (correccion, HttpStatus.CREATED);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<Correccion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping(value = "/correcciones/listar", produces ="application/json")
	public ResponseEntity<List<Correccion>> listCorrecciones() {
		if (!correccionService.getCorrecciones().isEmpty()) {
			List<Correccion> correcciones = correccionService.getCorrecciones();
			return new ResponseEntity<List<Correccion>>(correcciones, HttpStatus.OK);
		}
		return new ResponseEntity<List<Correccion>>(HttpStatus.NOT_FOUND);
	}
}
