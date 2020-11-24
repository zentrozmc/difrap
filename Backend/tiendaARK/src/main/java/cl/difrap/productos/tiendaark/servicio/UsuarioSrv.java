package cl.difrap.productos.tiendaark.servicio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cl.difrap.productos.tiendaark.dao.UsuarioDao;
import cl.difrap.productos.tiendaark.dto.Usuario;

@Service
public class UsuarioSrv implements UserDetailsService
{
	@Autowired
	private UsuarioDao usuarioDao;
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario entidad = new Usuario();
		entidad.setUsuario(username);
		usuarioDao.obtener(entidad);
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		roles.add(new SimpleGrantedAuthority("NONE"));
		return new User(entidad.getUsuario(),entidad.getPassword(),true,true,true,true,roles);
	}
	
	public int bloqueaUsuario(Usuario entidad) 
	{
		return usuarioDao.modificar(entidad);
	}
	
}
