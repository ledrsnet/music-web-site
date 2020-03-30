package com.maple.music.dao.impl;

import com.maple.music.dao.CategorieDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author LiangDong
 * @Date 2020/3/22
 */
@Repository("categorieDao")
public class CategorieDaoImpl implements CategorieDao {

	private static final Logger logger = LoggerFactory.getLogger(CategorieDaoImpl.class);

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;


	@Override
	public String getNameById(Integer id) {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "select name from CategoriesConfig where id = :id";
		return (String) currentSession.createQuery(hql).setParameter("id", id).uniqueResult();
	}
}
