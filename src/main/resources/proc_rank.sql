-- 创建每天更新排行榜存过
DELIMITER $$
CREATE PROCEDURE PROC_UPDATE_RANK_DAY()
BEGIN
    -- 模拟歌曲每天增加的播放量
    UPDATE m_Songs SET play_count = play_count+FLOOR(200 * RAND()*1000);
    -- 模拟歌单每天增加的播放量
    UPDATE m_playlists SET play_count = play_count+FLOOR(200 * RAND()*1000);

    -- 清除老的飙升榜和新歌榜数据
    DELETE FROM m_rank_new WHERE rank_type IN('soar','new');

    -- 飙升榜数据
    -- 规则：当前播放量-昨天播放量 desc，每天定时插入到rank_new表中。
    INSERT INTO m_rank_new(song_id,play_count,update_time,rank_type)
    SELECT
        s.song_id,(s.play_count - s.old_play_count),NOW(),'soar'
    FROM
        m_Songs s
    ORDER BY (s.play_count - s.old_play_count) DESC
    LIMIT 50;

    -- 新歌榜数据
    -- 规则：歌曲创建时间在1周之内的播放量desc，每天定时插入到rank_new表中。
    INSERT INTO m_rank_new(song_id,play_count,update_time,rank_type)
    SELECT
        s.song_id,s.play_count,NOW(),'new'
    FROM
        m_Songs s
    WHERE s.create_date>DATE_SUB(NOW(),INTERVAL 177 DAY) -- 实验用，因为不新增歌曲了，范围调大，方便到答辩时间还有数据展示.
    ORDER BY s.play_count DESC
    LIMIT 50;

    -- 同步播放量到昨天播放量字段。
    UPDATE m_Songs SET old_play_count=play_count;
    -- 同步歌单播放量到昨天播放量字段
    UPDATE m_playlists SET old_play_count = play_count;
END$$
DELIMITER ;

-- 创建每周更新排行榜存过
DELIMITER $$
CREATE PROCEDURE PROC_UPDATE_RANK_WEEK()
BEGIN
	-- 清除老的热歌榜和歌手榜数据
	DELETE FROM m_rank_new WHERE rank_type IN('hot','singer');

	-- 热歌榜数据
	-- 规则：当前播放量-一周前播放量 desc
	INSERT INTO m_rank_new(song_id,play_count,update_time,rank_type)
	SELECT
	s.song_id,(s.play_count-s.last_week_play_count),NOW(),'hot'
	FROM
	  m_Songs s
	ORDER BY (s.play_count-s.last_week_play_count) DESC
	LIMIT 50;

	-- 歌手榜数据
	-- 规则:统计歌手所有歌曲的播放量之和desc
	INSERT INTO m_rank_new(song_id,play_count,update_time,rank_type)
	SELECT a.id,SUM(s.play_count),NOW(),'singer' FROM m_singer a
	LEFT JOIN m_Songs s ON a.id LIKE s.avator_id
	GROUP BY a.id,a.name
	ORDER BY SUM(s.play_count) DESC
	LIMIT 50;

	-- 同步当前播放量到一周前播放量
	UPDATE m_Songs SET last_week_play_count = play_count;
END $$

-- 创建每天更新排行榜定时任务
CREATE EVENT RANK_DAY_JOB
ON SCHEDULE EVERY 1 DAY
STARTS DATE_ADD(NOW(),INTERVAL 3 HOUR)
DO CALL PROC_UPDATE_RANK_DAY();

-- 创建每周更新排行榜定时任务
CREATE EVENT RANK_WEEK_JOB
ON SCHEDULE EVERY 7 DAY
STARTS DATE_ADD(NOW(),INTERVAL 3 HOUR)
DO
CALL PROC_UPDATE_RANK_WEEK();


-- 查看ssh_music库的存过
SHOW PROCEDURE STATUS WHERE db ='ssh_music';
-- 查看事件调度器是否启用
SHOW VARIABLES LIKE 'event_scheduler';
-- 启用事件调度器
SET GLOBAL event_scheduler = 1;
-- 查看所有事件
SHOW EVENTS;