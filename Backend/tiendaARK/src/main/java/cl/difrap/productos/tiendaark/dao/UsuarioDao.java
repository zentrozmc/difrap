package cl.difrap.productos.tiendaark.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import cl.difrap.biblioteca.DAO;
import cl.difrap.productos.tiendaark.dto.Usuario;

@Repository
public class UsuarioDao extends DAO<Usuario>{

	public UsuarioDao(SqlSession sqlSession) {
		super(sqlSession);
		// TODO Auto-generated constructor stub
	}

}
