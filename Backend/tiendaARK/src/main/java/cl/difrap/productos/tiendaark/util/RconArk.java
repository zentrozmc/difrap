package cl.difrap.productos.tiendaark.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cl.difrap.bilbioteca.rcon.Rcon;
import cl.difrap.bilbioteca.rcon.excepciones.AutenticacionExcepcion;

public class RconArk 
{
	private static final Logger LOG = Logger.getLogger(RconArk.class);
	private Rcon rcon;	
	public RconArk() throws IOException, AutenticacionExcepcion 
	{
		rcon = new Rcon(Constantes.SERVIDOR_ARK,Constantes.PUERTO_ARK,Constantes.CLAVE_ARK.getBytes());
	}
	
	public Map<String,String> listarJugadores() throws IOException, ExcepcionRcon
	{
		HashMap<String,String> jugadores = new HashMap<String,String>();
		String result = rcon.comando("listplayers");
		if(result.contains("No Players Connected"))
			throw new ExcepcionRcon("No hay jugadores conectados");
		BufferedReader br = new BufferedReader(new StringReader(result));
		String linea;
		while((linea=br.readLine())!=null)
		{
			if(!"".equals(linea.trim()))
			{
				String nombre = linea.split(",")[0].split("\\.")[1];
				String steamId = linea.split(",")[1];
				jugadores.put(steamId.trim(),nombre.trim());
			}
		}
		return jugadores;
	}
	
	public boolean ejecutarComando(String comando) throws IOException 
	{
		LOG.info("Ejecutando Comando: "+comando);
		String result = rcon.comando(comando);
		LOG.info("Resultado Comando: "+result);
		if(result.contains("Server received, But no response!!"))
			return true;
		else
			return false;
	}
}
