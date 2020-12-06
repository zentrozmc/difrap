package cl.difrap.productos.tiendaark.ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.difrap.bilbioteca.rcon.excepciones.AutenticacionExcepcion;
import cl.difrap.productos.tiendaark.util.Constantes;
import cl.difrap.productos.tiendaark.util.ExcepcionRcon;
import cl.difrap.productos.tiendaark.util.RconArk;

@RestController
@RequestMapping("/player")
public class PlayerCtrl {
	
	@GetMapping(value = "")
	public ResponseEntity<HashMap<String,Object>> listarPlayers()
	{
		HashMap<String,Object> retorno = new HashMap<>();
		try 
		{
			RconArk rcon = new RconArk();
			Map<String,String> jugadoresConectados = rcon.listarJugadores();
			retorno.put("estado", true);
			retorno.put("codigo", Constantes.RETORNO_API.OK);
			retorno.put("descripcion", Constantes.RETORNO_API.OK.getDescripcion());
			List<HashMap<String,String>> players = new ArrayList<>();
			for(String idSteam : jugadoresConectados.keySet()) 
			{
				HashMap<String,String> player = new HashMap<>();
				player.put("idSteam",idSteam);
				player.put("nombre",jugadoresConectados.get(idSteam));
				players.add(player);
			}
			retorno.put("resultado",players);
			return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.OK);
		} catch (IOException | AutenticacionExcepcion e) {
			retorno.put("estado", false);
			retorno.put("codigo", Constantes.RETORNO_API.NO_OK);
			retorno.put("descripcion", Constantes.RETORNO_API.NO_OK.getDescripcion());
			return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (ExcepcionRcon e2) 
		{
			retorno.put("estado", false);
			retorno.put("codigo", Constantes.RETORNO_API.NO_OK);
			retorno.put("descripcion", e2.getMessage());
			return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
