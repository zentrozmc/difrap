package cl.difrap.productos.tiendaark.util;

public class ExcepcionRcon extends Exception
{
	private static final long serialVersionUID = 1L;

	public ExcepcionRcon()
	{
		super();
	}
	
	public ExcepcionRcon(String mensaje)
	{
		super(mensaje);
	}
	
	public ExcepcionRcon(Throwable excepcion)
	{
		super(excepcion);
	}	
	
	public ExcepcionRcon(String mensaje, Throwable excepcion)
	{
		super(mensaje, excepcion);
	}
}
