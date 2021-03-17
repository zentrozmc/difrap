package cl.difrap.productos.tiendaark.ctrl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.difrap.biblioteca.Controlador;
import cl.difrap.productos.tiendaark.dao.DinoDao;
import cl.difrap.productos.tiendaark.dao.UsuarioDao;
import cl.difrap.productos.tiendaark.dto.Dino;
import cl.difrap.productos.tiendaark.dto.Usuario;
import cl.difrap.productos.tiendaark.util.Constantes;
import cl.difrap.productos.tiendaark.util.JwtUtil;
import cl.difrap.productos.tiendaark.util.RconArk;
import cl.difrap.productos.tiendaark.util.Constantes.RETORNO_API;
@RestController
@RequestMapping("/dino")
public class DinoCtrl extends Controlador<DinoDao,Dino>
{
	private static final Logger LOG = Logger.getLogger(DinoCtrl.class);
	
	@Autowired
	private JwtUtil tokenProvider;
	@Autowired 
	private UsuarioDao usuarioDao;
	@PutMapping(value="/{id}/comprar")
	public ResponseEntity<HashMap<String,Object>> comprar(@RequestHeader(Constantes.HEADER_AUTORIZACION) String token, @PathVariable Long id,@RequestBody Dino dino) 
	{
		HashMap<String,Object> retorno = new HashMap<>();
		if(token!=null)
			token=token.replace(Constantes.BEARER, "");
		Usuario u = tokenProvider.getUserFromJWT(token);
		u = usuarioDao.obtener(u);
		Dino i = new Dino();
		i.setIdIncremental(id);
		i = dao.obtener(i);
		Long precio = i.getPrecio()*dino.getNivel();
		i.setPrecio(precio);
		i.setNivel(dino.getNivel());
		if(u.getPuntos()>= i.getPrecio()) 
		{
			Long resta = u.getPuntos() - i.getPrecio();
			u.setPuntos(resta);
			RETORNO_API resultadoCmd = ejecutarComando(i,u);
			retorno.put("codigo", resultadoCmd);
			retorno.put("descripcion", resultadoCmd.getDescripcion());
			if(resultadoCmd==Constantes.RETORNO_API.OK)
			{
				retorno.put("estado", true);
				usuarioDao.modificar(u);
				return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.OK);
			}
			else
			{
				retorno.put("estado", false);
				return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.CONFLICT);
			}
		}
		else 
		{
			retorno.put("estado", false);
			retorno.put("codigo", Constantes.RETORNO_API.SALDO_INSUFICIENTE);
			retorno.put("descripcion", Constantes.RETORNO_API.SALDO_INSUFICIENTE.getDescripcion());
			return new ResponseEntity<HashMap<String,Object>>(retorno,HttpStatus.CONFLICT);
		}
		
	}
	
	private RETORNO_API ejecutarComando(Dino i, Usuario u) 
	{
		try 
		{
			String comando = i.getComando().replace("[[cantidad]]",String.valueOf(i.getNivel()));
			comando = comando.replace("[[nivel]]",String.valueOf(i.getNivel()));
			comando = comando.replace("[[steamId]]",u.getSteamId());
			RconArk rcon = new RconArk();
			Map<String,String> jugadoresConectados = rcon.listarJugadores();
			if(jugadoresConectados.containsKey(u.getSteamId()))
			{
				if(rcon.ejecutarComando(comando))
					return Constantes.RETORNO_API.OK;
				else
					return Constantes.RETORNO_API.NO_OK;
			}
			else
				return Constantes.RETORNO_API.JUGADOR_NO_CONECTADO;
			
		} catch (Exception e) 
		{
			LOG.error("Ha Ocurrido un error al ejecutar comando",e);
			return Constantes.RETORNO_API.NO_OK;
		}
	}
}
