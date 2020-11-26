package cl.difrap.bilbioteca.rcon;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Random;

import cl.difrap.bilbioteca.rcon.excepciones.AutenticacionExcepcion;

public class Rcon {
	
	private final Object sync = new Object();
	private final Random rand = new Random();
	
	private int requestId;
	private Socket socket;
	
	private Charset charset;

	/**
	* Crear, conectar y autenticar un nuevo objeto Rcon
	*
	* @param servidor dirección del servidor Rcon
	* @param puerto Puerto del servidor Rcon
	* @param password contraseña del servidor Rcon
	*
	* @throws IOException
	* @throws AutenticacionExcepcion
	*/
	public Rcon(String servidor, int puerto, byte[] password) throws IOException, AutenticacionExcepcion {
		// Default charset is utf8
		this.charset = Charset.forName("UTF-8");
		
		// Conectarse al servidor
		this.conectar(servidor, puerto, password);
	}
	
	 /**
	 * Conectarse a un servidor rcon
	 *
	 * @param host dirección del servidor Rcon
	 * @param port Puerto del servidor Rcon
	 * @param contraseña contraseña del servidor Rcon
	 *
	 * @throws IOException
	 * @throws AutenticacionExcepcion
	 */
	public void conectar(String servidor, int puerto, byte[] password) throws IOException, AutenticacionExcepcion {
		if(servidor == null || servidor.trim().isEmpty()) {
			throw new IllegalArgumentException("El servidor no puede ser nulo o vacío");
		}
		
		if(puerto < 1 || puerto > 65535) {
			throw new IllegalArgumentException("El puerto está fuera de rango");
		}
		
		//Conectando al servidor rcon
		synchronized(sync) {
			//Generando requestId aleatorio
			this.requestId = rand.nextInt();
			//No podemos reutilizar un socket, por lo que necesitamos uno nuevo.
			this.socket = new Socket(servidor, puerto);
		}
		
		//Enviar paquete de autenticacion
		PaqueteRcon res = this.enviar(PaqueteRcon.SERVERDATA_AUTH, password);
		//Autenticacion fallida
		if(res.getRequestId() == -1) {
			throw new AutenticacionExcepcion("Contraseña rechazada por el servidor");
		}
	}
	
	/**
	 * Desconectarse del servidor actual
	 * 
	 * @throws IOException
	 */
	public void desconectar() throws IOException {
		synchronized(sync) {
			this.socket.close();
		}
	}
	
	
	/**
	 * Envía un comando al servidor
	 * @param payload El comando para enviar
	 * @return El payload de la respuesta
	 * 
	 * @throws IOException
	 */
	public String comando(String payload) throws IOException {
		if(payload == null || payload.trim().isEmpty()) {
			throw new IllegalArgumentException("payload no puede ser nulo o vacío");
		}
		
		PaqueteRcon response = this.enviar(PaqueteRcon.SERVERDATA_EXECCOMMAND, payload.getBytes());
		
		return new String(response.getPayload(), this.getCharset());
	}
	
	private PaqueteRcon enviar(int type, byte[] payload) throws IOException {
		synchronized(sync) {
			return PaqueteRcon.enviar(this, type, payload);
		}
	}

	public int getRequestId() {
		return requestId;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public Charset getCharset() {
		return charset;
	}
	
	public void setCharset(Charset charset) {
		this.charset = charset;
	}

}
