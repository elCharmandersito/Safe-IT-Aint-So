package cl.ubb.testing.safeit.models;

import java.util.Date;
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
public class Reporte {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_reporte")
	private int idReporte;
	private String nombre;
	private Date fecha;
	private String descripcion;
	private TipoReporte tipoReporte;

	private NivelGravedad nivelGravedad;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Usuario usuario;
	
	@OneToMany(mappedBy = "reporte")
	private List<Emisor> emisor;
	
	@OneToMany(mappedBy = "reporte")
	private List<Correccion> correcciones;
	
	
	public Reporte(int idReporte, String nombre, Date fecha, String descripcion, TipoReporte tipoReporte,
			NivelGravedad nivelGravedad, Usuario usuario, List<Emisor> emisor, List<Correccion> correcciones) {
		super();
		this.idReporte = idReporte;
		this.nombre = nombre;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.tipoReporte = tipoReporte;
		this.nivelGravedad = nivelGravedad;
		this.usuario = usuario;
		this.emisor = emisor;
		this.correcciones = correcciones;
	}

	public Reporte() {
		
	}

	public int getIdReporte() {
		return idReporte;
	}

	public void setIdReporte(int idReporte) {
		this.idReporte = idReporte;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public NivelGravedad getNivelGravedad() {
		return nivelGravedad;
	}

	public void setNivelGravedad(NivelGravedad nivelGravedad) {
		this.nivelGravedad = nivelGravedad;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Emisor> getEmisor() {
		return emisor;
	}

	public void setEmisor(List<Emisor> emisor) {
		this.emisor = emisor;
	}
	
	public List<Correccion> getCorrecciones() {
		return correcciones;
	}
	
	public void setCorrecciones(List<Correccion> correcciones) {
		this.correcciones = correcciones;
	}
	
	public TipoReporte getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(TipoReporte tipoReporte) {
		this.tipoReporte = tipoReporte;
	}

}
