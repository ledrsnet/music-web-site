package com.maple.music.dao.impl;

import com.maple.music.dao.RankDao;
import com.maple.music.entity.RankNew;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/29
 */
@Repository("rankDao")
public class RankDaoImpl implements RankDao {
	private static final Logger log =  LoggerFactory.getLogger(RankDaoImpl.class);

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public List<RankNew> getRankInfo(String rankType) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from RankNew where rankType = :type order by playCount desc";
		return session.createQuery(hql).setParameter("type",rankType).list();
	}

	@Override
	public List<Map<String, Object>> getSingerRankInfo(String ids) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT \n" +
				"  s.id,\n" +
				"  r.play_count,\n" +
				"  r.update_time,\n" +
				"  s.name,\n" +
				"  t.`singer_text_c` \n" +
				"FROM\n" +
				"  m_rank_new r \n" +
				"  LEFT JOIN m_singer s \n" +
				"    ON r.song_id = s.id \n" +
				"  LEFT JOIN m_singer_type_config t \n" +
				"    ON t.singer_code = s.singer_type \n" +
				"WHERE r.song_id IN ("+ids+")" +
				"ORDER BY r.play_count DESC";
		return session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public List<Map<String, Object>> getSongsByType(String rank_type) {
		Session currentSession = sessionFactory.getCurrentSession();
		String sql = "SELECT \n" +
				"  s.name AS songName,\n" +
				"  s.song_id AS songId,\n" +
				"  s.alia,\n" +
				"  s.avator_id AS singerId,\n" +
				"  s.avator_names AS singerName,\n" +
				"  s.date_time AS dt,\n" +
				"  s.song_url AS songUrl,\n" +
				"  s.lrc,\n" +
				"  a.id AS albumId,\n" +
				"  a.name AS albumName \n" +
				"FROM" +
				" m_rank_new r\n" +
				"LEFT JOIN  m_Songs s ON r.song_id = s.song_id \n" +
				"  LEFT JOIN m_album a ON s.album_id = a.id \n" +
				"WHERE r.rank_type = :type order by r.play_count desc ";
		return currentSession.createSQLQuery(sql).setParameter("type",rank_type).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public List<Map<String, Object>> getSingerRankInfoByType(String type) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT \n" +
				"  s.id,\n" +
				"  r.play_count,\n" +
				"  r.update_time,\n" +
				"  s.name,\n" +
				"  t.`singer_text_c` \n" +
				"FROM\n" +
				"  m_rank_new r \n" +
				"  LEFT JOIN m_singer s \n" +
				"    ON r.song_id = s.id \n" +
				"  LEFT JOIN m_singer_type_config t \n" +
				"    ON t.singer_code = s.singer_type \n" +
				"WHERE r.rank_type = :type " +
				"ORDER BY r.play_count DESC";
		return session.createSQLQuery(sql).setParameter("type",type).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

}
