package cl.difrap.biblioteca;

public class Paginacion 
{
	private int desplazar;
	private int limite;
	private int numreg;
	
	public Paginacion() 
	{
		this.desplazar = 0;
		this.limite = Integer.MAX_VALUE;
		this.numreg = Integer.MAX_VALUE;
	}
	public Paginacion(int desplazar,int limite,int numreg) 
	{
		this.desplazar = desplazar;
		this.limite = limite;
		this.numreg = numreg;
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
	
}
