package cl.difrap.biblioteca;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class Controlador<C extends DAO<P>, P extends Entidad> 
{
	private static final Logger log = Logger.getLogger(Controlador.class);
	
	@Autowired
	protected C dao;

	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<P>> listar(@RequestParam(required = false, defaultValue = "1") int pagina,@RequestParam(required = false, defaultValue = "25") int numreg,P entidad) 
	{
		Paginacion paginacion = null;
		if(pagina>0)
		{
			int desplazar = (pagina-1)*numreg+1;
			int limite = pagina * numreg;
			log.info("Paginar desde el registro ["+ desplazar + "] hasta ["+limite+"]");
			paginacion = new Paginacion(desplazar,limite);
		}
		else
			paginacion = new Paginacion();
		entidad.setPaginacion(paginacion);
		List<P> listaE = (List<P>) dao.listar(entidad);
		if(listaE != null)
			return new ResponseEntity<>(listaE,HttpStatus.OK);
		else
			return new ResponseEntity<>(listaE,HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<P> obtener(@PathVariable Long id) 
	{
		P e = crearEntidad();
		e.setIdIncremental(id);
		e = dao.obtener(e);
		if(e != null)
			return new ResponseEntity<>(e,HttpStatus.OK);
		else
			return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/agregar", method=RequestMethod.POST)
	public ResponseEntity<P> agregar(@RequestBody P entidad) 
	{
		P e = dao.agregar(entidad);
		if(e.getIdIncremental()>-1)
			return new ResponseEntity<>(e,HttpStatus.OK);
		else 
			return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/eliminar/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<P> eliminar(@PathVariable Long id) 
	{
		P e = crearEntidad();
		e.setIdIncremental(id);
		int i = dao.eliminar(e);
		if(i>-1)
			return new ResponseEntity<>(e,HttpStatus.OK);
		else 
			return new ResponseEntity<>(e,HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value="/modificar/{id}", method=RequestMethod.PUT)
	public ResponseEntity<P> modificar(@PathVariable Long id, @RequestBody P entidad) 
	{
		if(id.compareTo(entidad.getIdIncremental())!=0)
			return new ResponseEntity<>(entidad,HttpStatus.CONFLICT);
		int i = dao.modificar(entidad);
		if(i>-1)
			return new ResponseEntity<>(entidad,HttpStatus.OK);
		else 
			return new ResponseEntity<>(entidad,HttpStatus.NOT_FOUND);
	}
	
	
	@SuppressWarnings("unchecked")
	protected final Class<P> obtieneClaseEntidad() 
	{
		return (Class<P>) ((ParameterizedType) Controlador.this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	protected P crearEntidad() 
	{
		Class<P> clase = obtieneClaseEntidad();
		try 
		{
			P e = clase.getConstructor().newInstance();
			return e;
		} catch (InstantiationException e) {
			throw new RuntimeException("No se puede instanciar la clase: ", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Constructor de la clase no es accesible:", e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Los argumentos del constructor no son correctos:", e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("No se logro invocar la clase:", e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("El metodo de la clase no existe:", e);
		} catch (SecurityException e) {
			throw new RuntimeException("Error de Seguridad:", e);
		}
	}
}
