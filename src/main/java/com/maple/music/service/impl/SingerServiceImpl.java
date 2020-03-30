package com.maple.music.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.maple.music.dao.SingerDao;
import com.maple.music.dao.impl.SingerDaoImpl;
import com.maple.music.entity.Singer;
import com.maple.music.service.SingerService;
import com.maple.music.util.FileUtils;
import com.maple.music.util.HttpClientUtils;
import com.mysql.jdbc.log.LogFactory;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

/**
 * @author LiangDong
 * @Date 2020/3/23
 */
@Service("singerService")
@Transactional(rollbackFor={Exception.class, RuntimeException.class})
public class SingerServiceImpl implements SingerService {

	private static final Logger log = LoggerFactory.getLogger(SingerDaoImpl.class);

	@Resource
	private SingerDao singerDao;

	@Override
	public void insertSinger(Singer singer) {
		singerDao.insertSinger(singer);
	}

	@Override
	public boolean isSingerExists(BigInteger id) {
		return singerDao.isSingerExists(id);
	}

	@Override
	public List<String> getSingerIdsFromSong() {
		return singerDao.getSingerIdsFromSongs();
	}


}
