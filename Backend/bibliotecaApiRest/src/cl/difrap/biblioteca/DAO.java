package cl.difrap.biblioteca;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public abstract class DAO<C extends Entidad>
{
	private static final Logger LOGGER = Logger.getLogger(DAO.class);
	protected final SqlSession sqlSession;

	public DAO(SqlSession sqlSession)
	{
		this.sqlSession = sqlSession;
	}

	public List<C> listar(C entidad)
	{
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Listando [" + this.obtieneClaseEntidad().getSimpleName() + "]");
		return this.sqlSession.selectList("select" + this.obtieneClaseEntidad().getSimpleName(), entidad);
	}

	public List<C> listar()
	{
		return listar(null);
	}

	public C obtener(C entidad)
	{
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Obteniendo [" + this.obtieneClaseEntidad().getSimpleName() + "]");
		return this.sqlSession.selectOne("get" + this.obtieneClaseEntidad().getSimpleName(), entidad);
	}

	public C agregar(C entidad)
	{
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Insertando [" + this.obtieneClaseEntidad().getSimpleName() + "]");
		this.sqlSession.insert("insert" + this.obtieneClaseEntidad().getSimpleName(), entidad);
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("idIncremental: " + entidad.getIdIncremental());
		return entidad;
	}

	public int modificar(C entidad)
	{
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Modificando [" + this.obtieneClaseEntidad().getSimpleName() + "]");
		return this.sqlSession.update("update" + this.obtieneClaseEntidad().getSimpleName(), entidad);
	}

	public int eliminar(C entidad)
	{
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Eliminando [" + this.obtieneClaseEntidad().getSimpleName() + "]");
		return this.sqlSession.delete("delete" + this.obtieneClaseEntidad().getSimpleName(), entidad);
	}

	@SuppressWarnings("unchecked")
	protected final Class<C> obtieneClaseEntidad()
	{
		return (Class<C>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
}