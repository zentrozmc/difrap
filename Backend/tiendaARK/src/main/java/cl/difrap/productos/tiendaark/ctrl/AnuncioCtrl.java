package cl.difrap.productos.tiendaark.ctrl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.difrap.biblioteca.Controlador;
import cl.difrap.productos.tiendaark.dao.AnuncioDao;
import cl.difrap.productos.tiendaark.dto.Anuncio;

@RestController
@RequestMapping("/anuncio")
public class AnuncioCtrl extends Controlador<AnuncioDao,Anuncio>
{
	private static final Logger LOG = Logger.getLogger(AnuncioCtrl.class);
	
	@PutMapping(value="/activar/{id}")
	public ResponseEntity<Anuncio> activar(@PathVariable Long id) 
	{
		try 
		{
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
			String fechaActual = sdf.format(new Date());
			Date dFechaActual;
			dFechaActual = sdf.parse(fechaActual);
			Anuncio a = new Anuncio();
			a.setIdIncremental(id);
			a = dao.obtener(a);
			if(a.getFechaUltimoUso()!=null)
			{
				if(a.getFechaUltimoUso().before(dFechaActual))
				{
					a.setFechaActivacion(new Date());
					a.setFechaUltimoUso(dFechaActual);
					dao.modificar(a);
				}
			}
			return new ResponseEntity<Anuncio>(a,HttpStatus.OK);
		} catch (ParseException e) 
		{
			LOG.error("Error al activar anuncio",e);
			return new ResponseEntity<Anuncio>(new Anuncio(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
