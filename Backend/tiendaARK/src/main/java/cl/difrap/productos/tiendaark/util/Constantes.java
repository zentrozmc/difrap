package cl.difrap.productos.tiendaark.util;

public class Constantes 
{
	public static final String SUPER_SECRET_KEY="MtKqSyfWwjA9q5cV2PTJL8s8Vh3nzv4VNqhDDQuWuFr95d7GnsnZPCcDSYwmgd3pgmALxYLc6L7agqQKD3e2GhVNJkvBjuHE7CFqnDYFRNsyFZQgh3VsPZBYT6yCduHE4tftX2EhguxAkXNKjt5T8Yc3mHMz5QE6AVHaQ3hD4BuRG5WMpwfzds2qD95hGXwdzVAN4jpkp5SmwZgPPRfk9p3cBSXh6KzSnvhhFKK7Fh2YV4nXwyyqptnLJmSVVbRP";
	public static final String BEARER ="Bearer ";
	public static final String HEADER_AUTORIZACION ="Authorization";
	public static final String JSON_CONTENT_TYPE = "application/json";
	public static final String SECRET_KEY_AES = "cG9ydGFsRXZlcnRlY0FuZw==";
	public static final Long TOKEN_EXPIRATION_TIME=1800000L;
	
	//codigos retorno apis
	public static enum RETORNO_API
	{
		OK("Peticion Existosa"),
		NO_OK("Peticion Erronea"),
		JUGADOR_NO_CONECTADO("El jugador no se encuentra conectado"),
		SALDO_INSUFICIENTE("No existe saldo suficiente para comprar este item"),
		ANUNCIO_ACTIVADO("El anuncio ya fue activado hoy"),
		ANUNCIO_COBRADO("El anuncio ya fue cobrado hoy"),
		ANUNCIO_TIEMPO_ESPERA("Ha intentado cobrar el anuncio muy pronto, intente mas tarde"),
		TOKEN_INVALIDO("El token es invalido")
		;

		private String descripcion;
		private RETORNO_API(String string) {
			this.descripcion= string;
		}
		public String getDescripcion()
		{
			return this.descripcion;
		}
	}
	//estados anuncios
	public static final int ESTADO_ACTIVO = 1;
	public static final int ESTADO_COBRADO = 2;
	
	//Conexion Steam
	public static final String SERVIDOR_ARK = "190.164.68.47";
	public static final int PUERTO_ARK = 27022;
	public static final String CLAVE_ARK = "michis504A";
}
