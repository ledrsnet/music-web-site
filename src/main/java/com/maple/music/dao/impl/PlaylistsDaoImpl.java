package com.maple.music.dao.impl;

import com.maple.music.dao.CrawlerDao;
import com.maple.music.dao.PlaylistsDao;
import com.maple.music.entity.CategoriesBigConfig;
import com.maple.music.entity.CategoriesConfig;
import com.maple.music.entity.Playlists;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/14
 */
@Repository("playlistsDao")
public class PlaylistsDaoImpl implements PlaylistsDao {

	@Resource
	private CrawlerDao crawlerDao;

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	public List<CategoriesBigConfig> getBigCategories(){
		Session currentSession = sessionFactory.getCurrentSession();
		String hql ="from CategoriesBigConfig";
		Query query = currentSession.createQuery(hql);
		List<CategoriesBigConfig> list = query.list();
		return list;
	}

	@Override
	public List<CategoriesConfig> getCategoriesConfig() {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql ="from CategoriesConfig";
		Query query = currentSession.createQuery(hql);
		List<CategoriesConfig> list = query.list();
		return list;
	}

	@Override
	public List<Map<String,Object>> getPlaylists(String cat, String order) {
		Session currentSession = sessionFactory.getCurrentSession();
		Integer categoryId = crawlerDao.getCategoryId(cat);
		String sql ="SELECT p.id,\n" +
				"  p.name,\n" +
				"  p.user_id AS userId,\n" +
				"  p.create_time AS createaTime,\n" +
				"  p.update_time AS updateTime,\n" +
				"  p.subscribed_count AS subscribedCount,\n" +
				"  p.track_count AS trackCount,\n" +
				"  p.cover_img_url AS coverImgUrl,\n" +
				"  p.description,\n" +
				"  p.play_count AS playCount,\n" +
				"  p.tags,\n" +
				"  u.user_id,\n" +
				"  u.nickname FROM m_playlists p,m_user u WHERE CONCAT(',',p.tags,',') LIKE ?  " ;
		if(order.equals("hot")){
			sql+="AND p.user_id=u.user_id ORDER BY p.play_count DESC LIMIT 50";
		}else{
			sql+="AND p.user_id=u.user_id ORDER BY p.create_time DESC LIMIT 50";
		}
		//使用此方法返回List<Map<String,Object>>结果集
		Query query = currentSession.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter(0,"%,"+categoryId+",%");


		List<Map<String,Object>> list = query.list();
		return list;
	}

	@Override
	public List<Map<String,Object>> getPlaylists() {
		Session currentSession = sessionFactory.getCurrentSession();
		String sql ="SELECT p.id,\n" +
				"  p.name,\n" +
				"  p.user_id AS userId,\n" +
				"  p.create_time AS createaTime,\n" +
				"  p.update_time AS updateTime,\n" +
				"  p.subscribed_count AS subscribedCount,\n" +
				"  p.track_count AS trackCount,\n" +
				"  p.cover_img_url AS coverImgUrl,\n" +
				"  p.description,\n" +
				"  p.play_count AS playCount,\n" +
				"  p.tags,\n" +
				"  u.user_id,\n" +
				"  u.nickname FROM m_playlists p,m_user u" +
				" WHERE p.user_id=u.user_id ORDER BY p.update_time DESC LIMIT 50";

		return currentSession.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
}
