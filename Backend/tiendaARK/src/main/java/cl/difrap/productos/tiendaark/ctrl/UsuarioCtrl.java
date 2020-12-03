package cl.difrap.productos.tiendaark.ctrl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.difrap.biblioteca.Controlador;
import cl.difrap.productos.tiendaark.dao.UsuarioDao;
import cl.difrap.productos.tiendaark.dto.Usuario;
import cl.difrap.productos.tiendaark.util.Constantes;
import cl.difrap.productos.tiendaark.util.JwtUtil;
@RestController
@RequestMapping("/usuario")
public class UsuarioCtrl extends Controlador<UsuarioDao,Usuario>
{
	private static final Logger LOG = Logger.getLogger(UsuarioCtrl.class);
	
	@Autowired
	private JwtUtil tokenProvider;
	
	@Override
	@RequestMapping(value="/agregar", method=RequestMethod.POST)
	public ResponseEntity<Usuario> agregar(@RequestBody Usuario entidad) 
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
	
	@GetMapping(value = "/refrescar_token")
	public ResponseEntity<HashMap<String,Object>> refrescarToken(@RequestHeader(Constantes.HEADER_AUTORIZACION) String token, HttpServletRequest request)
	{
		HashMap<String,Object> retorno = new HashMap<>();
		if(token!=null)
			token=token.replace(Constantes.BEARER, "");
		else 
		{
			retorno.put("estado", false);
			retorno.put("codigo", Constantes.RETORNO_API.TOKEN_INVALIDO);
			retorno.put("descripcion", Constantes.RETORNO_API.TOKEN_INVALIDO.getDescripcion());
			return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.UNAUTHORIZED);
		}
		if(!tokenProvider.validarToken(token))
		{
			retorno.put("estado", false);
			retorno.put("codigo", Constantes.RETORNO_API.TOKEN_INVALIDO);
			retorno.put("descripcion", Constantes.RETORNO_API.TOKEN_INVALIDO.getDescripcion());
			return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.UNAUTHORIZED);
		}
		Usuario u = tokenProvider.getUserFromJWT(token);
		retorno.put("estado", false);
		retorno.put("codigo", Constantes.RETORNO_API.OK);
		retorno.put("descripcion", Constantes.RETORNO_API.OK.getDescripcion());
		String newToken = tokenProvider.makeToken(request, u);
		retorno.put("token", newToken);
		return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.OK);
		
	}
	
	private String encriptarPassword(String pass) throws NoSuchAlgorithmException 
	{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] digest = md.digest(pass.getBytes(StandardCharsets.UTF_8));
		String password = DatatypeConverter.printHexBinary(digest).toLowerCase();
		return password;
	}
}
