package cl.difrap.productos.tiendaark.dto;

import cl.difrap.biblioteca.Entidad;

public class Item extends Entidad
{
	private String nombre;
	private Long cantidad;
	private String comando;
	private Long precio;
	
	public Item() 
	{
		super(null);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public String getComando() {
		return comando;
	}

	public void setComando(String comando) {
		this.comando = comando;
	}

	public Long getPrecio() {
		return precio;
	}

	public void setPrecio(Long precio) {
		this.precio = precio;
	}
	
	
}
