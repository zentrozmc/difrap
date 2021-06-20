package cl.difrap.apirest.dto;

import cl.difrap.biblioteca.Entidad;

public class Track extends Entidad{

	private Long idAlbum;
	private String nombre;
	private String duracion;
	private String drive;
	public Track() {
		super(null);
	}
	public Long getIdAlbum() {
		return idAlbum;
	}
	public void setIdAlbum(Long idAlbum) {
		this.idAlbum = idAlbum;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDuracion() {
		return duracion;
	}
	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}
	public String getDrive() {
		return drive;
	}
	public void setDrive(String drive) {
		this.drive = drive;
	}
	@Override
	public String toString() {
		return "Track [idAlbum=" + idAlbum + ", nombre=" + nombre + ", duracion=" + duracion + ", drive=" + drive + "]";
	}
	
}
