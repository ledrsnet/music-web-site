package com.maple.music.dao.impl;

import com.maple.music.dao.RankDao;
import com.maple.music.entity.GuessRank;
import com.maple.music.entity.RankNew;
import com.maple.music.entity.Songs;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
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

	@Override
	public List<Map<String, Object>> getGuessRank() {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT \n" +
					"  g.*,\n" +
					"  u.nickname, \n" +
					"  u.avatar_url \n" +
					"FROM\n" +
					"  m_guess_rank g \n" +
					"  LEFT JOIN m_user u \n" +
					"    ON g.user_id = u.user_id ";
		return session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public List<Map<String, Object>> getGuessSongsInfo() {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT NAME,song_url,date_time FROM m_Songs WHERE song_id IN \n" +
				"(SELECT song_id FROM m_playlist_song WHERE playlist_id =4875099155)";
		return session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public int addGuessRankInfo(GuessRank guessRank) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.save(guessRank);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean isInsertGuessRank(BigInteger userId, int obNum) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "select g.count from GuessRank g where g.userId = :id";
		Integer count = (Integer) session.createQuery(sql).setParameter("id", userId).uniqueResult();
		if(count==null){
			return true;
		}else if(obNum>count){
			return true;
		}
		return false;
	}

}
