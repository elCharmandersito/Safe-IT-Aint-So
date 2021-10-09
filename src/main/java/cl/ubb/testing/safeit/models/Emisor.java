package cl.ubb.testing.safeit.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Emisor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_emisor")
	private int idEmisor;
	private String servicioEmergencia;
	
	
	@ManyToOne
	private Reporte reporte;
	
	@OneToMany(mappedBy = "emisor")
	private List<Notificacion> notificaciones;

	public Emisor(int idEmisor, String servicioEmergencia, Reporte reporte, List<Notificacion> notificaciones) {
		super();
		this.idEmisor = idEmisor;
		this.servicioEmergencia = servicioEmergencia;
		this.reporte = reporte;
		this.notificaciones = notificaciones;
	}
	
	public Emisor() {
		
	}

	public int getIdEmisor() {
		return idEmisor;
	}

	public void setIdEmisor(int idEmisor) {
		this.idEmisor = idEmisor;
	}

	public String getServicioEmergencia() {
		return servicioEmergencia;
	}

	public void setServicioEmergencia(String servicioEmergencia) {
		this.servicioEmergencia = servicioEmergencia;
	}

	public Reporte getReporte() {
		return reporte;
	}

	public void setReporte(Reporte reporte) {
		this.reporte = reporte;
	}

	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}
	
	
	
	
	
}
