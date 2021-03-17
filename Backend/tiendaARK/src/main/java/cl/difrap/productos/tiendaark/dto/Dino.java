package cl.difrap.productos.tiendaark.dto;

import cl.difrap.biblioteca.Entidad;

public class Dino extends Entidad{

	private String nombre;
	private Long nivel;
	private Long precio;
	private String comando;
	private String icono;
	public Dino() 
	{
		super(null);
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getPrecio() {
		return precio;
	}
	public void setPrecio(Long precio) {
		this.precio = precio;
	}
	public String getComando() {
		return comando;
	}
	public void setComando(String comando) {
		this.comando = comando;
	}
	public Long getNivel() {
		return nivel;
	}
	public void setNivel(Long nivel) {
		this.nivel = nivel;
	}
	public String getIcono() {
		return icono;
	}
	public void setIcono(String icono) {
		this.icono = icono;
	}
	

}
