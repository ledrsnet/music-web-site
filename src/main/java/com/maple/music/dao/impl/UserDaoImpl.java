package com.maple.music.dao.impl;

import com.maple.music.dao.UserDao;
import com.maple.music.entity.User;
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
 * @Date 2019/11/20
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {
	private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;


	public User getUser(Long uid) {
		Session session = sessionFactory.getCurrentSession();
		//当getCurrentSession所在的方法，或者调用该方法的方法绑定了事务之后，session就与当前线程绑定了，也就能通过currentSession来获取，否则就不能。
		User user = session.get(User.class, uid);
//        session.close();

		return user;
	}

	public void saveUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.save(user);

		//使用getCurrentSession后，hibernate 自己维护session的关闭，写了反而会报错

	}

	@Override
	public User getUserByName(String username) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM User U where U.username = :name";
		Query query = session.createQuery(hql);
		query.setParameter("name",username);
		Object o = query.uniqueResult();
		return (User) o;
	}

	@Override
	public Integer keepAlive() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "select 1 from User where id =1";
		Query query = session.createQuery(hql);
		Object o = query.uniqueResult();
		return (Integer) o;
	}

	@Override
	public boolean checkNickName(String nickname) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM User U where U.nickname = :name";
		Query query = session.createQuery(hql);
		query.setParameter("name",nickname);
		Object o = query.uniqueResult();
		if(o!=null){
			return false;
		}
		return true;
	}

	@Override
	public String getNicknameByUserId(Long id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "select nickname FROM User U where U.userId = :uid";
		return (String) session.createQuery(hql).setParameter("uid",id).uniqueResult();
	}

	@Override
	public List<Map<String, Object>> getUserFavorite(long userId) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT \n" +
				"  p.id,p.name,p.cover_img_url,\n" +
				"  u.nickname \n" +
				"FROM\n" +
				"  m_playlists p \n" +
				"  LEFT JOIN m_user u \n" +
				"    ON u.user_id = p.user_id \n" +
				"WHERE p.id IN \n" +
				"  (SELECT \n" +
				"    playlist_id \n" +
				"  FROM\n" +
				"    m_user_favorite \n" +
				"  WHERE user_id = :id)";
		return session.createSQLQuery(sql).setParameter("id",userId).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public int deSubscrib(BigInteger userId,BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "delete from UserFavorite where userId = :uid and playlistId =:id";
		try {
			log.info("============userId:"+userId);
			log.info("============playlistId:"+id);
			int i = session.createQuery(sql).setParameter("uid", userId).setParameter("id", id).executeUpdate();
			if(i>0){
				log.info("删除收藏歌单成功");
				return 200;
			}else{
				log.error("删除收藏歌单失败！");
				return 500;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 200;
	}

	@Override
	public long getMaxUserId() {
		Session session = sessionFactory.getCurrentSession();
		String sql = "select max(userId) from User";
		return (long) session.createQuery(sql).uniqueResult();
	}

	@Override
	public void updateUser(User sessionUser) {
		Session session = sessionFactory.getCurrentSession();
		session.update(sessionUser);
	}
}