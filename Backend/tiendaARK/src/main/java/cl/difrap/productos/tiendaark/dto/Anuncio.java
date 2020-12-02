package cl.difrap.productos.tiendaark.dto;

import java.util.Date;

import cl.difrap.biblioteca.Entidad;

public class Anuncio extends Entidad 
{
	private String usuario;
	private String url;
	private Date fechaUltimoUso;
	private Date fechaActivacion;
	private Long valor;
	private Integer estado;
	
	public Anuncio() 
	{
		super(null);
	}

	
	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
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

	

	public Integer getEstado() {
		return estado;
	}


	public void setEstado(Integer estado) {
		this.estado = estado;
	}


	@Override
	public String toString() {
		return "Anuncio [usuario=" + usuario + ", url=" + url + ", fechaUltimoUso=" + fechaUltimoUso
				+ ", fechaActivacion=" + fechaActivacion + ", valor=" + valor + ", estado=" + estado + "]";
	}


	
	
}
