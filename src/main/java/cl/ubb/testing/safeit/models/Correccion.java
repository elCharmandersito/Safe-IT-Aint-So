package cl.ubb.testing.safeit.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Correccion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCorreccion;
	
	private String descripcion;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Reporte reporte;

	public Correccion(int idCorreccion, String descripcion, Reporte reporte) {
		super();
		this.idCorreccion = idCorreccion;
		this.descripcion = descripcion;
		this.reporte = reporte;
	}
	
	public Correccion() {
		
	}

	public int getIdCorreccion() {
		return idCorreccion;
	}

	public void setIdCorreccion(int idCorreccion) {
		this.idCorreccion = idCorreccion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Reporte getReporte() {
		return reporte;
	}

	public void setReporte(Reporte reporte) {
		this.reporte = reporte;
	}
	
	
}
