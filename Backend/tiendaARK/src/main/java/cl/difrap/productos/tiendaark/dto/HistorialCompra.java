package cl.difrap.productos.tiendaark.dto;

import cl.difrap.biblioteca.Entidad;

public class HistorialCompra extends Entidad
{

	private Long idUsuario;
	private Long idItem;
	private String steamId;
	private String arkId;
	private Long cantidad;
	private Long precio;
	
	public HistorialCompra() {
		super(null);
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public String getSteamId() {
		return steamId;
	}

	public void setSteamId(String steamId) {
		this.steamId = steamId;
	}

	public String getArkId() {
		return arkId;
	}

	public void setArkId(String arkId) {
		this.arkId = arkId;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Long getPrecio() {
		return precio;
	}

	public void setPrecio(Long precio) {
		this.precio = precio;
	}

	
}
