package cl.difrap.productos.tiendaark.filtros;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.difrap.productos.tiendaark.dto.Usuario;
import cl.difrap.productos.tiendaark.servicio.UsuarioSrv;
import cl.difrap.productos.tiendaark.util.Constantes;
import cl.difrap.productos.tiendaark.util.JwtUtil;

public class FiltroJwt extends OncePerRequestFilter
{
	@Autowired
	private UsuarioSrv userDetailsService;
	@Autowired
	private JwtUtil tokenProvider;
	
	private static final Logger log = Logger.getLogger(FiltroJwt.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		
		//Obtengo el token del header
		String token = request.getHeader(Constantes.HEADER_AUTORIZACION);	
		if (!requiresAuthentication(token)) {
			filterChain.doFilter(request, response);
			return;
		}		
		//Si existe, le remplazo el Prefijo beared
		if(token!=null)
			token=token.replace(Constantes.BEARER, "");
		//Valido el Token
		if(tokenProvider.validarToken(token)) 
		{
			//Si es valido, Obtengo el usuario
			Usuario user = tokenProvider.getUserFromJWT(token);
			//Busco la informacion del usuario
			UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsuario());
			//Autentico el Usuario
	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	        //Agrego al contexto de seguridad mi autentificacion
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        filterChain.doFilter(request, response);
		}
		else 
        {
        	Map<String, Object> body = new HashMap<String, Object>();
    		body.put("", "Token Invalido");
    		response.setContentType(Constantes.JSON_CONTENT_TYPE);
    		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
    		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
	}
	
	protected boolean requiresAuthentication(String header) 
	{
		return !(header == null || !header.startsWith(Constantes.BEARER));
	}

}
