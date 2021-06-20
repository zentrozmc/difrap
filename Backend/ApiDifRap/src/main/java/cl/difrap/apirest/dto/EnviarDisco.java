package cl.difrap.apirest.dto;

public class EnviarDisco 
{
	private Album album;
	private String correo;
	
	public EnviarDisco() 
	{
		
	}
	
	public Album getAlbum() {
		return album;
	}
	public void setAlbum(Album album) {
		this.album = album;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	@Override
	public String toString() {
		return "EnviarDisco [album=" + album + ", correo=" + correo + "]";
	}
	
}
