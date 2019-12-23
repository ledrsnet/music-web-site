package com.maple.music.dao.impl;

import com.maple.music.dao.UserDao;
import com.maple.music.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author LiangDong
 * @Date 2019/11/20
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {

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
		System.out.println("======="+user.getUsername());

		//使用getCurrentSession后，hibernate 自己维护session的关闭，写了反而会报错

	}
}