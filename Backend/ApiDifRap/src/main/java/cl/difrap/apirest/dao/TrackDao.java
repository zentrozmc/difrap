package cl.difrap.apirest.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import cl.difrap.apirest.dto.Track;
import cl.difrap.biblioteca.DAO;
@Repository
public class TrackDao extends DAO<Track>{

	public TrackDao(SqlSession sqlSession) {
		super(sqlSession);
		// TODO Auto-generated constructor stub
	}
	
	

}
