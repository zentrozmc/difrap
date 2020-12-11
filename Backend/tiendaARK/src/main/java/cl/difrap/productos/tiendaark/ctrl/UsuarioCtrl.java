package cl.difrap.productos.tiendaark.ctrl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789#$.-_*abcdefghijklmnopqrstuvwxyz";
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private JwtUtil tokenProvider;
	@Override
	@RequestMapping(value="/oldAgregar", method=RequestMethod.POST)
	public ResponseEntity<Usuario> agregar(@RequestBody Usuario entidad)
	{
		return super.agregar(entidad);
	}
	@RequestMapping(value="/agregar", method=RequestMethod.POST)
	public ResponseEntity<HashMap<String,Object>> agregar2(@RequestBody Usuario entidad) 
	{
		HashMap<String,Object> retorno = new HashMap<>();
		try 
		{
			
			entidad.setPassword(encriptarPassword(entidad.getPassword()));
			entidad.setPuntos(0L);
			List<Usuario> listaU = dao.listar(entidad);
			for(Usuario u:listaU) 
			{
				if(u.getSteamId().equals(entidad.getSteamId()))
				{
					retorno.put("estado", false);
					retorno.put("codigo", Constantes.RETORNO_API.STEAM_ID_DUPLICADO);
					retorno.put("descripcion", Constantes.RETORNO_API.STEAM_ID_DUPLICADO.getDescripcion());
					return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			Usuario u = dao.obtener(entidad);
			if(u==null || u.getIdIncremental()==null)
			{
				u =dao.agregar(entidad);
				dao.agregarRel(u);
				retorno.put("estado", false);
				retorno.put("codigo", Constantes.RETORNO_API.OK);
				retorno.put("descripcion", Constantes.RETORNO_API.OK.getDescripcion());
				retorno.put("resultado", u);
				return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.OK);
			}
			retorno.put("estado", false);
			retorno.put("codigo", Constantes.RETORNO_API.NOMBRE_USUARIO_DUPLICADO);
			retorno.put("descripcion", Constantes.RETORNO_API.NOMBRE_USUARIO_DUPLICADO.getDescripcion());
			return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoSuchAlgorithmException e) 
		{
			LOG.error("Error al agregar usuario",e);
			retorno.put("estado", false);
			retorno.put("codigo", Constantes.RETORNO_API.NO_OK);
			retorno.put("descripcion", Constantes.RETORNO_API.NO_OK.getDescripcion());
			return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="oldObtener/{id}", method=RequestMethod.GET)
	public ResponseEntity<Usuario> obtener(@PathVariable Long id) 
	{
		return super.obtener(id);
	}
	
	@RequestMapping(value="/{usuario}", method=RequestMethod.GET)
	public ResponseEntity<Usuario> obtener(@PathVariable String usuario) 
	{
		Usuario e = crearEntidad();
		e.setUsuario(usuario);
		e = dao.obtener(e);
		if(e != null)
			return new ResponseEntity<>(e,HttpStatus.OK);
		else
			return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
	}
	
	@PutMapping(value = "/actualizarCuenta")
	public ResponseEntity<HashMap<String,Object>> actualizarCuenta(@RequestBody Usuario entidad)
	{
		HashMap<String,Object> retorno = new HashMap<>();
		
		Usuario u = dao.obtener(entidad);
		entidad.setPassword(u.getPassword());
		entidad.setPuntos(u.getPuntos());
		int i = dao.modificar(entidad);
		if(i>-1)
		{
			retorno.put("estado", true);
			retorno.put("codigo", Constantes.RETORNO_API.OK);
			retorno.put("descripcion", Constantes.RETORNO_API.OK.getDescripcion());
			return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.OK);
		}
		else 
		{
			retorno.put("estado", false);
			retorno.put("codigo", Constantes.RETORNO_API.NO_OK);
			retorno.put("descripcion", Constantes.RETORNO_API.NO_OK.getDescripcion());
			return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping(value = "/recuperarPassword")
	public ResponseEntity<HashMap<String,Object>> recuperarPassword(@RequestBody Usuario entidad)
	{
		HashMap<String,Object> retorno = new HashMap<>();
		try 
		{
			Usuario u = dao.obtener(entidad);
			String newPass = randomAlphaNumeric(10);
			String hashPass = encriptarPassword(newPass);
			u.setPassword(hashPass);
			int i = dao.updateUsuarioPass(u);
			if(i>-1)
			{
				sendHTMLMail(u,newPass);
				retorno.put("estado", true);
				retorno.put("codigo", Constantes.RETORNO_API.OK);
				retorno.put("descripcion", Constantes.RETORNO_API.OK.getDescripcion());
				return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.OK);
			}
			else 
			{
				retorno.put("estado", false);
				retorno.put("codigo", Constantes.RETORNO_API.NO_OK);
				retorno.put("descripcion", Constantes.RETORNO_API.NO_OK.getDescripcion());
				return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (NoSuchAlgorithmException | MessagingException e) {
			retorno.put("estado", false);
			retorno.put("codigo", Constantes.RETORNO_API.NO_OK);
			retorno.put("descripcion", Constantes.RETORNO_API.NO_OK.getDescripcion());
			return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.INTERNAL_SERVER_ERROR);		}

		
		
	}
	
	@PutMapping(value = "/actualizarPassword")
	public ResponseEntity<HashMap<String,Object>> actualizarPassword(@RequestBody Usuario entidad)
	{
		HashMap<String,Object> retorno = new HashMap<>();
		try 
		{
			String hashPass = encriptarPassword(entidad.getPassword());
			Usuario u = dao.obtener(entidad);
			u.setPassword(hashPass);
			int i = dao.updateUsuarioPass(u);
			if(i>-1)
			{
				retorno.put("estado", true);
				retorno.put("codigo", Constantes.RETORNO_API.OK);
				retorno.put("descripcion", Constantes.RETORNO_API.OK.getDescripcion());
				return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.OK);
			}
			else 
			{
				retorno.put("estado", false);
				retorno.put("codigo", Constantes.RETORNO_API.NO_OK);
				retorno.put("descripcion", Constantes.RETORNO_API.NO_OK.getDescripcion());
				return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (NoSuchAlgorithmException e) {
			retorno.put("estado", false);
			retorno.put("codigo", Constantes.RETORNO_API.NO_OK);
			retorno.put("descripcion", Constantes.RETORNO_API.NO_OK.getDescripcion());
			return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.INTERNAL_SERVER_ERROR);
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
		newToken = Constantes.BEARER+newToken;
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
	
	private String randomAlphaNumeric(int count)
	{
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = new Random().nextInt(ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	private void sendHTMLMail(Usuario u,String pass) throws MessagingException 
    {
		String contenido = "<html>";
		contenido+="<body>";
		contenido+="<p><b>Su contraseña es:<b></p>";
		contenido+="<p>"+pass+"</p>";
		contenido+="</body>";
		contenido+="</html>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
        helper.setTo(u.getCorreo());
        message.setFrom("no-reply@michisaurios.cl");
        helper.setSubject("Recuperacion de contraseña");   
        message.setContent(contenido, "text/html");
        mailSender.send(message);
    }
}
