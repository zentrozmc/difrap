package cl.difrap.productos.tiendaark.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import cl.difrap.biblioteca.DAO;
import cl.difrap.productos.tiendaark.dto.Dino;
@Repository
public class DinoDao extends DAO<Dino>
{

	public DinoDao(SqlSession sqlSession) {
		super(sqlSession);
	}

}
