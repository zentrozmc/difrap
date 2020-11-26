package cl.difrap.bilbioteca.rcon;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import cl.difrap.bilbioteca.rcon.excepciones.PaqueteMalformadoExcepcion;

public class PaqueteRcon {
	
	public static final int SERVERDATA_EXECCOMMAND = 2;
	public static final int SERVERDATA_AUTH = 3;
	
	private int requestId;
	private int type;
	private byte[] payload;
	
	private PaqueteRcon(int requestId, int type, byte[] payload) {
		this.requestId = requestId;
		this.type = type;
		this.payload = payload;
	}
	
	public int getRequestId() {
		return requestId;
	}
	
	public int getType() {
		return type;
	}
	
	public byte[] getPayload() {
		return payload;
	}
	
	/**
	* Envíe un paquete Rcon y obtenga la respuesta
	*
	* @param rcon instancia de Rcon
	* @param type El tipo de paquete
	* @param payload La carga útil (contraseña, comando, etc.)
	* @return Un objeto PaqueteRcon que contiene la respuesta
	*
	* @throws IOException
	* @throws PaqueteMalformadoExcepcion
	*/
	protected static PaqueteRcon enviar(Rcon rcon, int type, byte[] payload) throws IOException {
		try {
			PaqueteRcon.write(rcon.getSocket().getOutputStream(), rcon.getRequestId(), type, payload);
		}
		catch(SocketException se) {
			//Cierra el socket si pasa algo
			rcon.getSocket().close();
			//Retorna la exception
			throw se;
		}
		return PaqueteRcon.leer(rcon.getSocket().getInputStream());
	}
	

	/**
	* Escriba un paquete rcon en un flujo de salida
	*
	* @param out El OutputStream para escribir
	* @param requestId La identificación de la solicitud
	* @param type El tipo de paquete
	* @param payload La carga útil
	*
	* @throws IOException
	*/
	private static void write(OutputStream out, int requestId, int type, byte[] payload) throws IOException {
		int bodyLength = PaqueteRcon.getBodyLength(payload.length);
		int packetLength = PaqueteRcon.getPacketLength(bodyLength);
		
		ByteBuffer buffer = ByteBuffer.allocate(packetLength);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putInt(bodyLength);
		buffer.putInt(requestId);
		buffer.putInt(type);
		buffer.put(payload);
		buffer.put((byte)0);
		buffer.put((byte)0);
		
		out.write(buffer.array());
		out.flush();
	}
	
	/**
	* Leer un paquete rcon entrante
	*
	* @param en The InputStream para seguir leyendo
	* @return El RconPacket leído
	*
	* @throws IOException
	* @throws PaqueteMalformadoExcepcion
	*/
	private static PaqueteRcon leer(InputStream in) throws IOException {
		// Cabecera es de 3 4-bytes ints
		byte[] header = new byte[4 * 3];
		
		// lee los 3 ints
		in.read(header);
		
		try {
			//Use un bytebuffer en little endian para leer las primeras 3 entradas
			ByteBuffer buffer = ByteBuffer.wrap(header);
			buffer.order(ByteOrder.LITTLE_ENDIAN);
			
			int length = buffer.getInt();
			int requestId = buffer.getInt();
			int type = buffer.getInt();
			
			// Payload el tamaño se puede calcular ahora que tenemos su longitud
			byte[] payload = new byte[length - 4 - 4 - 2];
			
			DataInputStream dis = new DataInputStream(in);
			
			// Lee el payload completo
			dis.readFully(payload);
			
			// Lee los bytes nulos
			dis.read(new byte[2]);
			
			return new PaqueteRcon(requestId, type, payload);
		}
		catch(BufferUnderflowException | EOFException e) {
			throw new PaqueteMalformadoExcepcion("No se puede leer todo el paquete");
		}
	}
	
	private static int getPacketLength(int bodyLength) {
		// 4 bytes for length + x bytes for body length
		return 4 + bodyLength;
	}
	
	private static int getBodyLength(int payloadLength) {
		// 4 bytes for requestId, 4 bytes for type, x bytes for payload, 2 bytes for two null bytes
		return 4 + 4 + payloadLength + 2;
	}

}
