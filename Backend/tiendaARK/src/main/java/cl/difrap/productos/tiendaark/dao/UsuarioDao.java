package cl.difrap.productos.tiendaark.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import cl.difrap.biblioteca.DAO;
import cl.difrap.productos.tiendaark.dto.Anuncio;
import cl.difrap.productos.tiendaark.dto.Usuario;

@Repository
public class UsuarioDao extends DAO<Usuario>{

	public UsuarioDao(SqlSession sqlSession) {
		super(sqlSession);
		// TODO Auto-generated constructor stub
	}
	
	public boolean agregarRel(Usuario usaurio)
	{
		List<Anuncio> lstAnuncios = this.sqlSession.selectList("selectAnuncioReal");
		for(Anuncio entidad :lstAnuncios) 
		{
			entidad.setIdUsuario(usaurio.getIdIncremental());
			this.sqlSession.insert("insertAnuncioRel", entidad);
		}
		return true;
	}

}
