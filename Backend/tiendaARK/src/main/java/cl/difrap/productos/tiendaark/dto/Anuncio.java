package cl.difrap.productos.tiendaark.dto;

import java.util.Date;

import cl.difrap.biblioteca.Entidad;

public class Anuncio extends Entidad 
{
	private String url;
	private Date fechaUltimoUso;
	private Date fechaActivacion;
	private Long valor;
	
	public Anuncio() 
	{
		super(null);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getFechaUltimoUso() {
		return fechaUltimoUso;
	}

	public void setFechaUltimoUso(Date fechaUltimoUso) {
		this.fechaUltimoUso = fechaUltimoUso;
	}

	public Date getFechaActivacion() {
		return fechaActivacion;
	}

	public void setFechaActivacion(Date fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	public Long getValor() {
		return valor;
	}

	public void setValor(Long valor) {
		this.valor = valor;
	}
	
	
}
