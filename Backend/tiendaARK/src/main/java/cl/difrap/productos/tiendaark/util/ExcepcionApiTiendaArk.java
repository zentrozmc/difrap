package cl.difrap.productos.tiendaark.util;

class ExcepcionApiTiendaArk extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public ExcepcionApiTiendaArk()
	{
		super();
	}
	
	public ExcepcionApiTiendaArk(String mensaje)
	{
		super(mensaje);
	}
	
	public ExcepcionApiTiendaArk(Throwable excepcion)
	{
		super(excepcion);
	}	
	
	public ExcepcionApiTiendaArk(String mensaje, Throwable excepcion)
	{
		super(mensaje, excepcion);
	}
}
