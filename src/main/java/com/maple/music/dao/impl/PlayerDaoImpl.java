package com.maple.music.dao.impl;

import com.maple.music.dao.PlayerDao;
import com.maple.music.entity.PlayerVo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/27
 */
@Repository("playerDao")
public class PlayerDaoImpl implements PlayerDao {

	private static final Logger logger = LoggerFactory.getLogger(PlayerDaoImpl.class);
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public List<Map<String,Object>> getSongsByPlaylistId(BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT \n" +
				"  s.*,a.pic_url \n" +
				"FROM\n" +
				"  m_Songs s \n" +
				"LEFT JOIN m_album a ON a.id = s.album_id\n" +
				"WHERE s.song_id IN \n" +
				"  (SELECT \n" +
				"    ps.song_id \n" +
				"  FROM\n" +
				"    m_playlist_song ps \n" +
				"  WHERE ps.playlist_id = :id) \n" +
				"ORDER BY s.song_id DESC ";
		return session.createSQLQuery(sql).setParameter("id",id).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public List<Map<String, Object>> getSongsBySongId(BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		String sql  ="SELECT \n" +
				"  s.*,\n" +
				"  a.pic_url \n" +
				"FROM\n" +
				"  m_Songs s \n" +
				"  LEFT JOIN m_album a \n" +
				"    ON a.id = s.album_id \n" +
				"WHERE s.song_id = :id ";
		return session.createSQLQuery(sql).setParameter("id",id).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public String getLrcBySongId(BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "select lrc from Songs where songId = :id";
		return (String) session.createQuery(hql).setParameter("id",id).uniqueResult();
	}

	@Override
	public List<Map<String, Object>> getSongsBySingerId(BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT \n" +
				"  s.*,\n" +
				"  a.pic_url \n" +
				"FROM\n" +
				"  m_Songs s \n" +
				"  LEFT JOIN m_album a \n" +
				"    ON s.album_id = a.id \n" +
				"WHERE CONCAT(',', s.avator_id, ',') LIKE ? \n" +
				"ORDER BY s.play_count DESC \n" +
				"LIMIT 15 ";
		return session.createSQLQuery(sql).setParameter(0,"%,"+id+",%").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public List<Map<String, Object>> getSongsByRank(String cat) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT \n" +
				"  s.*,\n" +
				"  a.pic_url \n" +
				"FROM\n" +
				"  m_rank_new r \n" +
				"  LEFT JOIN m_Songs s \n" +
				"    ON r.song_id = s.song_id \n" +
				"  LEFT JOIN m_album a \n" +
				"    ON s.album_id = a.id \n" +
				"WHERE r.rank_type = :cat " +
				"ORDER BY r.play_count DESC";
		return session.createSQLQuery(sql).setParameter("cat",cat).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public List<Map<String, Object>> getSongsByAlbumId(BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT \n" +
					"  *\n" +
					"FROM\n" +
					"  m_Songs a\n" +
					"WHERE \n" +
					"  a.album_id = :id ";
		return session.createSQLQuery(sql).setParameter("id",id).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
}
