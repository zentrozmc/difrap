package cl.difrap.productos.tiendaark.ctrl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.difrap.biblioteca.Controlador;
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
	
	@PutMapping(value="/activar/{id}")
	public ResponseEntity<Anuncio> activar(@RequestHeader(Constantes.HEADER_AUTORIZACION)String token, @PathVariable Long id) 
	{
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
					return new ResponseEntity<Anuncio>(new Anuncio(),HttpStatus.CONFLICT);
			}
			else 
				activarAnuncio(a,dFechaActual);
			return new ResponseEntity<Anuncio>(a,HttpStatus.OK);
		} catch (ParseException e) 
		{
			LOG.error("Error al activar anuncio",e);
			return new ResponseEntity<Anuncio>(new Anuncio(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping(value="/cobrarPuntos/{id}")
	public ResponseEntity<Boolean> cobrarPuntos(@RequestHeader(Constantes.HEADER_AUTORIZACION)String token, @PathVariable Long id) 
	{
		try 
		{
			if(token!=null)
				token=token.replace(Constantes.BEARER, "");
			Usuario u = tokenProvider.getUserFromJWT(token);
			Anuncio a = new Anuncio();
			a.setUsuario(u.getUsuario());
			a.setIdIncremental(id);
			a = dao.obtener(a);
			
			if(a.getEstado()==Constantes.ESTADO_ACTIVO)
			{
				Date fechaTope = sumarRestarSegundosFecha(a.getFechaActivacion(),6);
				if(fechaTope.before(new Date()))
				{
					u = usuarioDao.obtener(u);
					Long puntos = u.getPuntos() + a.getValor();
					u.setPuntos(puntos);
					usuarioDao.modificar(u);
					return new ResponseEntity<Boolean>(true,HttpStatus.OK);
				}
				else
					return new ResponseEntity<Boolean>(false,HttpStatus.CONFLICT);
			}
			else
				return new ResponseEntity<Boolean>(false,HttpStatus.CONFLICT);
		} catch (Exception e) 
		{
			LOG.error("Error al activar anuncio",e);
			return new ResponseEntity<Boolean>(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
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
