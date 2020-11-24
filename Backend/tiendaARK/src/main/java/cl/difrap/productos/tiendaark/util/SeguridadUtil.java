package cl.difrap.productos.tiendaark.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;


public class SeguridadUtil 
{
	private SeguridadUtil() 
	{
		
	}
	
	public static String getEncodedPassword(String plainText)
	{
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
	        byte[] digest = md.digest(plainText.getBytes(StandardCharsets.UTF_8));
	        return DatatypeConverter.printHexBinary(digest).toLowerCase();
		} catch (Exception e) {
			throw new ExcepcionApiTiendaArk("Error al cifrar contraseña", e);
		}
	}
}
