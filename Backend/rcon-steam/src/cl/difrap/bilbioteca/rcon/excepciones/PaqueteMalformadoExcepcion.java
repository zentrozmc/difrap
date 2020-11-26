package cl.difrap.bilbioteca.rcon.excepciones;

import java.io.IOException;

public class PaqueteMalformadoExcepcion extends IOException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PaqueteMalformadoExcepcion(String message) {
		super(message);
	}
	
}
