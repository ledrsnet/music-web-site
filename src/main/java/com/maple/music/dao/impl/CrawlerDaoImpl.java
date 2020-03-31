package com.maple.music.dao.impl;

import com.maple.music.dao.CrawlerDao;
import com.maple.music.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
 * @Date 2020/3/2
 */
@Repository("crawlerDao")
public class CrawlerDaoImpl implements CrawlerDao {
	private static final Logger logger = LoggerFactory.getLogger(CrawlerDaoImpl.class);
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void insertCategory(CategoriesConfig categoriesConfig) {
		Session currentSession = sessionFactory.getCurrentSession();
		try {
			currentSession.save(categoriesConfig);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=======插入分类失败！=======");
		}
	}

	@Override
	public Integer getCategoryId(String name) {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "from CategoriesConfig F where F.name = :name";
		Query query = currentSession.createQuery(hql);
		query.setParameter("name", name);
		CategoriesConfig o = (CategoriesConfig) query.uniqueResult();
		if (o == null) {
			return null;
		}
		return o.getId();
	}

	@Override
	public void insertPlaylists(Playlists lists) {
		Session currentSession = sessionFactory.getCurrentSession();
		try {
			currentSession.save(lists);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=======插入歌单失败！=======");
		}
	}

	@Override
	public boolean isPlaylistExists(BigInteger id) {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "from Playlists p where p.id = :id";
		Query query = currentSession.createQuery(hql);
		query.setParameter("id", id);
		List list = query.list();
		return list != null && list.size() > 0;
	}

	@Override
	public boolean isUserExists(long id) {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "from User u where u.userId = :id";
		Query query = currentSession.createQuery(hql);
		query.setParameter("id", id);
		List list = query.list();
		return list != null && list.size() > 0;
	}

	@Override
	public List<BigInteger> getAllPlaylistId() {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "select id from Playlists";
		List<BigInteger> list = currentSession.createQuery(hql).list();
		return list;
	}

	@Override
	public void insertSongs(Songs songs) {
		Session currentSession = sessionFactory.getCurrentSession();
		try {
			currentSession.save(songs);
		} catch (Exception e) {
			logger.error("插入歌曲失败。");
			e.printStackTrace();
		}
	}

	@Override
	public boolean isSongsExists(BigInteger id) {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "select 1 from Songs S where S.songId = :id";
		List id1 = currentSession.createQuery(hql).setParameter("id", id).list();
		return id1 != null && id1.size() > 0;
	}

	@Override
	public List<Integer> getAllSingerCatIds() {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "select singerCode from SingerTypeConfig";
		return currentSession.createQuery(hql).list();
	}



	@Override
	public void insertPlaylistSong(PlaylistSong playlistSong) {
		Session currentSession = sessionFactory.getCurrentSession();
		try {
			currentSession.save(playlistSong);
		} catch (Exception e) {
			logger.error("插入歌单与歌映射失败!");
			e.printStackTrace();
		}
	}

	@Override
	public boolean isPlaylistSongExists(BigInteger playlistId, BigInteger songId) {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "from PlaylistSong where playlistId =:playlistId and songId = :songId";
		Query query = currentSession.createQuery(hql);
		query.setParameter("playlistId",playlistId);
		query.setParameter("songId",songId);
		List list = query.list();
		return list!=null && list.size()>0;
	}

	@Override
	public List<Map<String, Object>> getSingerIdFromSong() {
		Session currentSession = sessionFactory.getCurrentSession();
		String sql ="SELECT id,avator_id FROM m_Songs where avator_names is null";

		return currentSession.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public List<String> getNameBySingerId(String avator_id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "select name from Singer where id in ("+avator_id+")";
		return session.createQuery(hql).list();
	}

	@Override
	public void updateNameById(BigInteger id, String name) {
		Session session = sessionFactory.getCurrentSession();
		Songs songs = session.get(Songs.class, id);
		songs.setAvatorNames(name);
		logger.info(id+name);
		session.update(songs);
//		String hql = "update m_Songs set avator_names = ? where id = ?";
//		int i = session.createSQLQuery(hql).setParameter(0, name).setParameter(1, id).executeUpdate();
		session.flush();
		session.clear();
	}

	@Override
	public List<Map<String, Object>> getTagsIdfromPlaylist() {
		Session currentSession = sessionFactory.getCurrentSession();
		String sql ="SELECT id,tags FROM m_playlists where tags_text is null and tags is not null and tags !=''";

		return currentSession.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public String getNameByCategoryId(String tags) {
		Session currentSession = sessionFactory.getCurrentSession();
		String sql ="SELECT GROUP_CONCAT(NAME) FROM m_categories_config WHERE id IN (" +tags +")" ;
		return (String) currentSession.createSQLQuery(sql).uniqueResult();
	}

	@Override
	public void updateNameByIdFromPlaylist(BigInteger id, String names) {
		Session session = sessionFactory.getCurrentSession();
		Playlists playlists = session.get(Playlists.class, id);
		playlists.setTagsText(names);
		logger.info(id+names);
		session.update(playlists);
		session.flush();
		session.clear();
	}

	@Override
	public void insertPlaylistComments(CommentsForPlaylist commentsForPlaylist) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.save(commentsForPlaylist);
		} catch (Exception e) {
			logger.error("插入评论失败！");
			e.printStackTrace();
		}
	}
}
