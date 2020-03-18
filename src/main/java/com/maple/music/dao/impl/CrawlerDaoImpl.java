package com.maple.music.dao.impl;

import com.maple.music.dao.CrawlerDao;
import com.maple.music.entity.CategoriesConfig;
import com.maple.music.entity.Playlists;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LiangDong
 * @Date 2020/3/2
 */
@Repository("crawlerDao")
public class CrawlerDaoImpl implements CrawlerDao {
	private static final Logger logger= LoggerFactory.getLogger(CrawlerDaoImpl.class);
	@Resource(name="sessionFactory")
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
		query.setParameter("name",name);
		CategoriesConfig o = (CategoriesConfig) query.uniqueResult();
		if(o==null){
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
	public boolean isPlaylistExists(long id) {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "from Playlists p where p.id = :id";
		Query query = currentSession.createQuery(hql);
		query.setParameter("id",id);
		List list = query.list();
		if(list!=null&& list.size()>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean isUserExists(long id) {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "from User u where u.userId = :id";
		Query query = currentSession.createQuery(hql);
		query.setParameter("id",id);
		List list = query.list();
		if(list!=null&& list.size()>0){
			return true;
		}
		return false;
	}
}
