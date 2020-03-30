package com.maple.music.dao.impl;

import com.maple.music.dao.AlbumDao;
import com.maple.music.entity.Album;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * @author LiangDong
 * @Date 2020/3/23
 */
@Repository("albumDao")
public class AlbumDaoImpl implements AlbumDao {
	private static final Logger logger = LoggerFactory.getLogger(AlbumDaoImpl.class);

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public List<BigInteger> getAlbumIdsFromSongs() {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "select distinct albumId from Songs WHERE albumId NOT IN (SELECT id FROM Album)";
		return currentSession.createQuery(hql).list();
	}

	@Override
	public boolean isAlbumExist(BigInteger id) {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "select id from Album where id = :id";
		List list = currentSession.createQuery(hql).setParameter("id", id).list();
		return list!=null&&list.size()>0;
	}

	@Override
	public void insertAlbum(Album album) {
		Session currentSession = sessionFactory.getCurrentSession();
		try {
			currentSession.save(album);
		} catch (Exception e) {
			logger.error("插入专辑数据到数据库失败！！");
			e.printStackTrace();
		}
	}

	@Override
	public Album getAlbumById(BigInteger albumId) {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql ="from Album where id = :id";
		return (Album) currentSession.createQuery(hql).setParameter("id",albumId).uniqueResult();
	}
}
