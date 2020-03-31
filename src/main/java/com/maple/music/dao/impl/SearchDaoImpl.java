package com.maple.music.dao.impl;

import com.maple.music.dao.SearchDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/30
 */
@Repository("searchDao")
public class SearchDaoImpl implements SearchDao {
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public List<Map<String, Object>> searchSingleSongByKey(List<String> keywords) {
		Session session = sessionFactory.getCurrentSession();
		String baseSql = "SELECT \n" +
				"  s.name AS songName,\n" +
				"  s.song_id AS songId,\n" +
				"  s.alia,\n" +
				"  s.avator_id AS singerId,\n" +
				"  s.avator_names AS singerName,\n" +
				"  s.date_time AS dt,\n" +
				"  s.song_url AS songUrl,\n" +
				"  a.id AS albumId,\n" +
				"  a.name AS albumName \n" +
				"FROM\n" +
				"  m_Songs s \n" +
				"  LEFT JOIN m_album a \n" +
				"    ON s.album_id = a.id \n" +
				"WHERE s.name LIKE '%kk_kk%' ";
		StringBuilder sb = new StringBuilder(baseSql);
		if(keywords!=null&&keywords.size()>0){
			for (int i = 0; i < keywords.size(); i++) {
				if(i!=0){
					sb.append("union ").append(baseSql);
				}
				sb.replace(sb.indexOf("kk_kk"),sb.indexOf("kk_kk")+5,keywords.get(i));
			}
		}
		return session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public List<Map<String, Object>> searchAlbumByKey(List<String> keywords) {
		Session session = sessionFactory.getCurrentSession();
		String baseSql = "SELECT \n" +
						"  a.id,\n" +
						"  a.name,\n" +
						"  a.pic_url AS picUrl,\n" +
						"  s.id AS singerId,\n" +
						"  s.name AS singerName \n" +
						"FROM\n" +
						"  m_album a \n" +
						"  LEFT JOIN m_singer s \n" +
						"    ON a.singer_id = s.id \n" +
						"WHERE a.name LIKE '%kk_kk%' ";
		StringBuilder sb = new StringBuilder(baseSql);
		if(keywords!=null&&keywords.size()>0){
			for (int i = 0; i < keywords.size(); i++) {
				if(i!=0){
					sb.append("union ").append(baseSql);
				}
				sb.replace(sb.indexOf("kk_kk"),sb.indexOf("kk_kk")+5,keywords.get(i));
			}
		}
		return session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public List<Map<String, Object>> searchPlaylistByKey(List<String> keys) {
		Session session = sessionFactory.getCurrentSession();
		String baseSql = "SELECT \n" +
						"  p.id,\n" +
						"  p.name,\n" +
						"  u.nickname,\n" +
						"  p.create_time,\n" +
						"  p.play_count,\n" +
						"  p.cover_img_url \n" +
						"FROM\n" +
						"  m_playlists p \n" +
						"  LEFT JOIN m_user u \n" +
						"    ON u.user_id = p.user_id \n" +
						"WHERE p.name LIKE '%kk_kk%' ";
		StringBuilder sb = new StringBuilder(baseSql);
		if(keys!=null&&keys.size()>0){
			for (int i = 0; i < keys.size(); i++) {
				if(i!=0){
					sb.append("union ").append(baseSql);
				}
				sb.replace(sb.indexOf("kk_kk"),sb.indexOf("kk_kk")+5,keys.get(i));
			}
		}
		return session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public List<Map<String, Object>> searchSingerByKey(List<String> keys) {
		Session session = sessionFactory.getCurrentSession();
		String baseSql = "SELECT \n" +
						"  a.id,\n" +
						"  a.name,\n" +
						"  a.pic_url \n" +
						"FROM\n" +
						"  m_singer a \n" +
						"WHERE a.name LIKE '%kk_kk%' ";
		StringBuilder sb = new StringBuilder(baseSql);
		if(keys!=null&&keys.size()>0){
			for (int i = 0; i < keys.size(); i++) {
				if(i!=0){
					sb.append("union ").append(baseSql);
				}
				sb.replace(sb.indexOf("kk_kk"),sb.indexOf("kk_kk")+5,keys.get(i));
			}
		}
		return session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
}
