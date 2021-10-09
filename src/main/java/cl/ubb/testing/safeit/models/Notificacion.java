package cl.ubb.testing.safeit.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import cl.ubb.testing.safeit.models.Emisor;

@Entity
public class Notificacion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_notificacion")
	private int idNotificacion;
	private String nombre;
	private String tipo;
	private String descripcion;
	
	@ManyToOne
	private Emisor emisor;

	public Notificacion(int idNotificacion, String nombre, String tipo, String descripcion, Emisor emisor) {
		super();
		this.idNotificacion = idNotificacion;
		this.nombre = nombre;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.emisor = emisor;
	}
	
	public Notificacion() {
		
	}

	public int getIdNotificacion() {
		return idNotificacion;
	}

	public void setIdNotificacion(int idNotificacion) {
		this.idNotificacion = idNotificacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Emisor getEmisor() {
		return emisor;
	}

	public void setEmisor(Emisor emisor) {
		this.emisor = emisor;
	}
	
	
	
}
