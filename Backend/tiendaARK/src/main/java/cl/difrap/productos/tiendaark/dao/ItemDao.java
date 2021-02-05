package cl.difrap.productos.tiendaark.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import cl.difrap.biblioteca.DAO;
import cl.difrap.productos.tiendaark.dto.Item;
@Repository
public class ItemDao extends DAO<Item>{

	public ItemDao(SqlSession sqlSession) {
		super(sqlSession);
	}
	
	public Item obtenerAleatorio()
	{
		return this.sqlSession.selectOne("getItemAleatorio");
	}
	
}
