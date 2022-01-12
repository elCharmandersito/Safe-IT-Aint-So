package bdd.stepDefs;

import cl.ubb.testing.safeit.models.NivelGravedad;
import cl.ubb.testing.safeit.models.Reporte;
import cl.ubb.testing.safeit.repositories.ReporteRepository;
import cl.ubb.testing.safeit.services.ReporteService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class ReporteStepDefs {
    @LocalServerPort
    private int port;
    private Reporte reporte;
    private ResponseEntity<Reporte> reporteResponseEntity;
    private ResponseEntity<List<Reporte>> reportesResponseEntity;

    @Autowired
    private ReporteRepository reporteRepository;
    @Autowired
    private ReporteService reporteService;
    @Autowired
    private TestRestTemplate testRestTemplate;

    private String getEndPoint(){ return "http://localhost:"+port+"/safeit/reporte/";}

    @Given("reporte con id {int},nombre {string}, fecha{string}, descripcion{string}, nivelGravedad{int}")
    public void reporte_con_id_nombre_fecha_descripcion_nivelgravedad(
            Integer id, String nombre, Date fecha, String descripcion, NivelGravedad nivelGravedad
            ) {
        Reporte reporte = new Reporte();
        reporte.setIdReporte(id);
        reporte.setNombre(nombre);
        reporte.setFecha(fecha);
        reporte.setDescripcion(descripcion);
        reporte.setNivelGravedad(nivelGravedad);
    }

    @When("solicito un reporte con nombre{string}")
    public void solicito_reporte_con_nombre(String nombre){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE,APPLICATION_JSON_VALUE);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);

        testRestTemplate= new TestRestTemplate();
        reporteResponseEntity = testRestTemplate.exchange(getEndPoint()+nombre, HttpMethod.GET, requestEntity, Reporte.class);
    }

    @Then("obtengo status{string} y un reporte con nombre {string} ")
    public void obtengo_status_y_reporte_con_nombre(String estado, String nombre){
        assertEquals(estado.toUpperCase(), reporteResponseEntity.getStatusCode().name());

        Reporte reporte= reporteResponseEntity.getBody();
        assertNotNull(reporte);
        assertEquals(nombre, reporte.getNombre());
    }

    @When("solicito un reporte con descripcion {string}")
    public void solicito_reporte_con_descripcion(String descripcion){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE,APPLICATION_JSON_VALUE);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);

        testRestTemplate= new TestRestTemplate();
        reporteResponseEntity = testRestTemplate.exchange(getEndPoint()+descripcion, HttpMethod.GET, requestEntity, Reporte.class);
    }
    @Then("obtengo status{string} y un reporte con descripcion{string}")
    public void obtengo_status_y_reporte_con_descripcion(String estado, String descripcion){
        assertEquals(estado.toUpperCase(), reporteResponseEntity.getStatusCode().name());

        Reporte reporte= reporteResponseEntity.getBody();
        assertNotNull(reporte);
        assertEquals(descripcion, reporte.getDescripcion());
    }
}
