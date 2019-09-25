package cl.difrap.biblioteca;

import java.util.List;

public class RespuestaPaginada 
{
	private List<? extends Entidad> entidad;
	private Paginacion paginacion;
	
	public RespuestaPaginada() 
	{
		
	}

	public List<? extends Entidad> getEntidad() {
		return entidad;
	}

	public void setEntidad(List<? extends Entidad> entidad) {
		this.entidad = entidad;
	}

	public Paginacion getPaginacion() {
		return paginacion;
	}

	public void setPaginacion(Paginacion paginacion) {
		this.paginacion = paginacion;
	}

	
	
}
