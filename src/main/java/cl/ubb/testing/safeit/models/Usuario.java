package cl.ubb.testing.safeit.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.bytebuddy.asm.Advice.This;

@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private int idUsuario;
	
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String direccion;
	private String correo;
	private String password;
	private String token;
	private int fono;
	private Rol rol;
	private boolean activo = false;
	
	@OneToMany(mappedBy = "usuario")
	@JsonIgnore
	List<Reporte> reportes;
	
	public Usuario(int idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String direccion,
			String correo, String password, String token, int fono, Rol rol, boolean activo) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.direccion = direccion;
		this.correo = correo;
		this.password = password;
		this.token = token;
		this.fono = fono;
		this.rol = rol;
		this.reportes = new ArrayList<Reporte>();
		this.setActivo(activo);
	}

	public Usuario() {
		
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setidUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public int getFono() {
		return fono;
	}

	public void setFono(int fono) {
		this.fono = fono;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	public List<Reporte> getReportes() {
		return reportes;
	}

	public void setReportes(List<Reporte> reportes) {
		this.reportes = reportes;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
	
	
	
	
}
