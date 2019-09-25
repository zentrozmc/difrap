package cl.difrap.apirest.dto;

import cl.difrap.biblioteca.Entidad;

public class Album extends Entidad
{
	private String album;
	private String artista;
	private Long anho;
	private String link;
	private String youtube;

	public Album(Long idIncremental) {
		super(idIncremental);
	}

	public Album() 
	{
		super(null);
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public Long getAnho() {
		return anho;
	}

	public void setAnho(Long anho) {
		this.anho = anho;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getYoutube() {
		return youtube;
	}

	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}
	
	
}
