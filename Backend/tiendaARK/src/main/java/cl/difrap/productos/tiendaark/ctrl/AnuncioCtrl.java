package cl.difrap.productos.tiendaark.ctrl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.difrap.biblioteca.Controlador;
import cl.difrap.biblioteca.RespuestaPaginada;
import cl.difrap.productos.tiendaark.dao.AnuncioDao;
import cl.difrap.productos.tiendaark.dao.UsuarioDao;
import cl.difrap.productos.tiendaark.dto.Anuncio;
import cl.difrap.productos.tiendaark.dto.Usuario;
import cl.difrap.productos.tiendaark.util.Constantes;
import cl.difrap.productos.tiendaark.util.JwtUtil;

@RestController
@RequestMapping("/anuncio")
public class AnuncioCtrl extends Controlador<AnuncioDao,Anuncio>
{
	private static final Logger LOG = Logger.getLogger(AnuncioCtrl.class);
	
	
	@Autowired
	private JwtUtil tokenProvider;
	
	@Autowired 
	private UsuarioDao usuarioDao;
	
	@Override
	@GetMapping(value = "oldListar")
	public ResponseEntity<RespuestaPaginada> listar(@RequestParam(required = false, defaultValue = "1") int pagina,@RequestParam(required = false, defaultValue = "25") int numreg,Anuncio entidad)
	{
		return super.listar(pagina, numreg, entidad);
	}
	@Override
	@GetMapping(value = "oldObtener/{id}")
	public ResponseEntity<Anuncio> obtener(@PathVariable Long id) 
	{
		return super.obtener(id);
	}
	
	@GetMapping(value = "")
	public ResponseEntity<RespuestaPaginada> listar(@RequestHeader(Constantes.HEADER_AUTORIZACION)String token,@RequestParam(required = false, defaultValue = "1") int pagina,@RequestParam(required = false, defaultValue = "25") int numreg,Anuncio entidad) 
	{
		if(token!=null)
			token=token.replace(Constantes.BEARER, "");
		Usuario u = tokenProvider.getUserFromJWT(token);
		entidad.setUsuario(u.getUsuario());
		return listar(pagina, numreg, entidad);
	}
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Anuncio> obtener(@RequestHeader(Constantes.HEADER_AUTORIZACION)String token,@PathVariable Long id) 
	{
		if(token!=null)
			token=token.replace(Constantes.BEARER, "");
		Usuario u = tokenProvider.getUserFromJWT(token);
		Anuncio e = new Anuncio();
		e.setUsuario(u.getUsuario());
		e.setIdIncremental(id);
		e = dao.obtener(e);
		if(e != null)
			return new ResponseEntity<>(e,HttpStatus.OK);
		else
			return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
	}
	
	@PutMapping(value="/activar/{id}")
	public ResponseEntity<HashMap<String,Object>> activar(@RequestHeader(Constantes.HEADER_AUTORIZACION)String token, @PathVariable Long id) 
	{
		HashMap<String,Object> retorno = new HashMap<>();
		try 
		{
			if(token!=null)
				token=token.replace(Constantes.BEARER, "");
			Usuario u = tokenProvider.getUserFromJWT(token);
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
			String fechaActual = sdf.format(new Date());
			Date dFechaActual;
			dFechaActual = sdf.parse(fechaActual);
			Anuncio a = new Anuncio();
			a.setUsuario(u.getUsuario());
			a.setIdIncremental(id);
			a = dao.obtener(a);
			a.setUsuario(u.getUsuario());
			if(a.getFechaUltimoUso()!=null)
			{
				if(a.getFechaUltimoUso().before(dFechaActual))
					activarAnuncio(a,dFechaActual);
				else 
				{
					retorno.put("estado", false);
					retorno.put("codigo", Constantes.RETORNO_API.ANUNCIO_ACTIVADO);
					retorno.put("descripcion", Constantes.RETORNO_API.ANUNCIO_ACTIVADO.getDescripcion());
					return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.CONFLICT);
				}
					
			}
			else 
				activarAnuncio(a,dFechaActual);
			retorno.put("estado", true);
			retorno.put("codigo", Constantes.RETORNO_API.OK);
			retorno.put("descripcion", Constantes.RETORNO_API.OK.getDescripcion());
			return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.OK);
				
		} catch (ParseException e) 
		{
			LOG.error("Error al activar anuncio",e);
			retorno.put("estado", false);
			retorno.put("codigo", Constantes.RETORNO_API.NO_OK);
			retorno.put("descripcion", Constantes.RETORNO_API.NO_OK.getDescripcion());
			return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping(value="/cobrarPuntos/{id}")
	public ResponseEntity<HashMap<String,Object>> cobrarPuntos(@RequestHeader(Constantes.HEADER_AUTORIZACION)String token, @PathVariable Long id) 
	{
		HashMap<String,Object> retorno = new HashMap<>();
		try 
		{
			if(token!=null)
				token=token.replace(Constantes.BEARER, "");
			Usuario u = tokenProvider.getUserFromJWT(token);
			Anuncio a = new Anuncio();
			a.setUsuario(u.getUsuario());
			a.setIdIncremental(id);
			a = dao.obtener(a);
			a.setUsuario(u.getUsuario());
			if(a.getEstado()==Constantes.ESTADO_ACTIVO)
			{
				Date fechaTope = sumarRestarSegundosFecha(a.getFechaActivacion(),6);
				if(fechaTope.before(new Date()))
				{
					u = usuarioDao.obtener(u);
					Long puntos = u.getPuntos() + a.getValor();
					u.setPuntos(puntos);
					usuarioDao.modificar(u);
					cobrarAnuncio(a);
					retorno.put("estado", true);
					retorno.put("codigo", Constantes.RETORNO_API.OK);
					retorno.put("descripcion", Constantes.RETORNO_API.OK.getDescripcion());
					return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.OK);
				}
				else
				{
					retorno.put("estado", false);
					retorno.put("codigo", Constantes.RETORNO_API.ANUNCIO_TIEMPO_ESPERA);
					retorno.put("descripcion", Constantes.RETORNO_API.ANUNCIO_TIEMPO_ESPERA.getDescripcion());
					return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.CONFLICT);
				}
					
			}
			else
			{
				retorno.put("estado", false);
				retorno.put("codigo", Constantes.RETORNO_API.ANUNCIO_COBRADO);
				retorno.put("descripcion", Constantes.RETORNO_API.ANUNCIO_COBRADO.getDescripcion());
				return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.CONFLICT);
			}
		} catch (Exception e) 
		{
			LOG.error("Error al cobrar anuncio",e);
			retorno.put("estado", false);
			retorno.put("codigo", Constantes.RETORNO_API.NO_OK);
			retorno.put("descripcion", Constantes.RETORNO_API.NO_OK.getDescripcion());
			return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	private void cobrarAnuncio(Anuncio a) 
	{
		a.setEstado(Constantes.ESTADO_COBRADO);
		dao.activarAnuncio(a);
	}
	
	private void activarAnuncio(Anuncio a,Date dFechaActual)
	{
		a.setFechaActivacion(new Date());
		a.setFechaUltimoUso(dFechaActual);
		a.setEstado(Constantes.ESTADO_ACTIVO);
		dao.activarAnuncio(a);
	}
	
	private Date sumarRestarSegundosFecha(Date fecha, int seg)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.SECOND, seg);  // numero de segundos a añadir, o restar en caso seg<0
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos segundos añadidos
    }
}
