package cl.difrap.apirest.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import cl.difrap.apirest.dto.Album;
import cl.difrap.biblioteca.DAO;

@Repository
public class AlbumDao extends DAO<Album>
{

	public AlbumDao(SqlSession sqlSession) {
		super(sqlSession);
	}

}
