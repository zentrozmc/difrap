package cl.difrap.biblioteca;

public class Paginacion 
{
	private int desplazar;
	private int limite;
	private int numreg;
	private long cantidadRegistros;
	
	public Paginacion() 
	{
		this.desplazar = 0;
		this.limite = Integer.MAX_VALUE;
		this.numreg = Integer.MAX_VALUE;
		this.cantidadRegistros = 0;
	}
	public Paginacion(int desplazar,int limite,int numreg, long cantidadRegistros) 
	{
		this.desplazar = desplazar;
		this.limite = limite;
		this.numreg = numreg;
		this.cantidadRegistros = cantidadRegistros;
	}
	public int getDesplazar() {
		return desplazar;
	}
	public void setDesplazar(int desplazar) {
		this.desplazar = desplazar;
	}
	public int getLimite() {
		return limite;
	}
	public void setLimite(int limite) {
		this.limite = limite;
	}
	public int getNumreg() {
		return numreg;
	}
	public void setNumreg(int numreg) {
		this.numreg = numreg;
	}
	public long getCantidadRegistros() {
		return cantidadRegistros;
	}
	public void setCantidadRegistros(long cantidadRegistros) {
		this.cantidadRegistros = cantidadRegistros;
	}
	
	
	
}
