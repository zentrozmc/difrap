package cl.difrap.biblioteca;

public class Paginacion 
{
	private int desplazar;
	private int limite;
	
	public Paginacion() 
	{
		this.desplazar = 0;
		this.limite = Integer.MAX_VALUE;
	}
	public Paginacion(int desplazar,int limite) 
	{
		this.desplazar = desplazar;
		this.limite = limite;
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
}
