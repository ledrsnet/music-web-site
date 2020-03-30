package com.maple.music.dao.impl;

import com.maple.music.dao.SingerDao;
import com.maple.music.entity.Singer;
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
@Repository("singerDao")
public class SingerDaoImpl implements SingerDao {

	private static final Logger logger = LoggerFactory.getLogger(SingerDaoImpl.class);
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void insertSinger(Singer singer) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.save(singer);
		} catch (Exception e) {
			logger.error("插入歌手数据到数据库失败！！！");
			e.printStackTrace();
		}
	}

	@Override
	public boolean isSingerExists(BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Singer where id =:id";
		List list = session.createQuery(hql).setParameter("id", id).list();
		return list!=null&&list.size()>0;
	}

	@Override
	public List<Singer> getSingerByIds(String avatorId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Singer where id in (" + avatorId +")";
		return session.createQuery(hql).list();
	}

	@Override
	public List<String> getSingerIdsFromSongs() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT DISTINCT avatorId FROM Songs";
		return session.createQuery(hql).list();
	}


}
