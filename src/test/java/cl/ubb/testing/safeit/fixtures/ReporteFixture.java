package cl.ubb.testing.safeit.fixtures;

import java.util.ArrayList;
import java.util.List;

import cl.ubb.testing.safeit.models.Reporte;

public class ReporteFixture {
	
	public static Reporte obtenerReporte() {
		Reporte reporte = new Reporte();
		reporte.setIdReporte(0);
		reporte.setNombre("Rayados");
		reporte.setDescripcion("Se encontraron rayones en las bancas de la plaza");
		return reporte;
	}

	public static List<Reporte> obtenerReportesFixture() {
		List <Reporte> reportes = new ArrayList<>();
		
		Reporte reporte = new Reporte();
		reporte.setIdReporte(0);
		reporte.setNombre("Rayados");
		reporte.setDescripcion("Se encontraron rayones en las bancas de la plaza");
		
		Reporte reporte2 = new Reporte();
		reporte2.setIdReporte(1);
		reporte2.setNombre("Basura");
		reporte2.setDescripcion("Se encontraron Basureros rotos y toda la basura desparramada");
		
		reportes.add(reporte);
		reportes.add(reporte2);
		
		return reportes;
		
	}
}
