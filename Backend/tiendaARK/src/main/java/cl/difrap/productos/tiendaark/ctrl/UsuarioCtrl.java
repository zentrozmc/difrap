package cl.difrap.productos.tiendaark.ctrl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cl.difrap.biblioteca.Controlador;
import cl.difrap.productos.tiendaark.dao.UsuarioDao;
import cl.difrap.productos.tiendaark.dto.Usuario;

public class UsuarioCtrl extends Controlador<UsuarioDao,Usuario>
{
	private static final Logger LOG = Logger.getLogger(UsuarioCtrl.class);
	
	@Override
	public ResponseEntity<Usuario> agregar(Usuario entidad) 
	{
		try 
		{
			entidad.setPassword(encriptarPassword(entidad.getPassword()));
			return super.agregar(entidad);
		} catch (NoSuchAlgorithmException e) 
		{
			LOG.error("Error al agregar usuario",e);
			return new ResponseEntity<Usuario>(new Usuario(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private String encriptarPassword(String pass) throws NoSuchAlgorithmException 
	{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] digest = md.digest(pass.getBytes(StandardCharsets.UTF_8));
		String password = DatatypeConverter.printHexBinary(digest).toLowerCase();
		return password;
	}
}
