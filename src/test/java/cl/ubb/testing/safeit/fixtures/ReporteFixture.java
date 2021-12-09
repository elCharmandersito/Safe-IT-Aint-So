package cl.ubb.testing.safeit.fixtures;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cl.ubb.testing.safeit.models.NivelGravedad;
import cl.ubb.testing.safeit.models.Reporte;

public class ReporteFixture {
	
	public static Reporte obtenerReporte() {
		Reporte reporte = new Reporte();
		reporte.setIdReporte(0);
		reporte.setNombre("Rayados");
		Date date = new Date();
		try {
			String sDate1="09/12/2021";
			date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
			reporte.setFecha(date);
		} catch (Exception e) {
			
		}
		reporte.setFecha(date);
		reporte.setDescripcion("Se encontraron rayones en las bancas de la plaza");
		return reporte;
	}

	public static List<Reporte> obtenerReportesFixture() {
		List <Reporte> reportes = new ArrayList<>();
		
		Reporte reporte = new Reporte();
		reporte.setIdReporte(0);
		reporte.setNombre("Rayados");
		reporte.setDescripcion("Se encontraron rayones en las bancas de la plaza");
		Date date = new Date();
		try {
			String sDate1="09/12/2021";
			date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
			reporte.setFecha(date);
		} catch (Exception e) {
			
		}
		 
		
		Reporte reporte2 = new Reporte();
		reporte2.setIdReporte(1);
		reporte2.setNombre("Basura");
		reporte2.setDescripcion("Se encontraron Basureros rotos y toda la basura desparramada");
		reporte2.setFecha(date);
		
		reportes.add(reporte);
		reportes.add(reporte2);
		
		return reportes;
		
	}

	public static List<Reporte> obtenerReportesPorNombreFixture() {
		List <Reporte> reportes = new ArrayList<>();
		
		Reporte reporte = new Reporte();
		reporte.setIdReporte(0);
		reporte.setNombre("Rayados en pared");
		reporte.setDescripcion("Se encontraron rayones en mi casa");
		Date date = new Date();
		try {
			String sDate1="09/12/2021";
			date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
			reporte.setFecha(date);
		} catch (Exception e) {
			
		}
		 
		
		Reporte reporte2 = new Reporte();
		reporte2.setIdReporte(1);
		reporte2.setNombre("Rayados en las bancas");
		reporte2.setDescripcion("Se encontraron rayones en las bancas de la plaza");
		reporte2.setFecha(date);
		
		reportes.add(reporte);
		reportes.add(reporte2);
		
		return reportes;
	}
}
