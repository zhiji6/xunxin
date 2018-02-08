package com.xunxin.service.app.square;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunxin.dao.square.TurnplateAwardDao;
import com.xunxin.vo.square.TurnplateAwardVO;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2017年12月17日 -- 下午5:17:18
 * @Version 1.0
 * @Description		转盘游戏奖励	
 */
@Service("turnplateAwardService")
public class TurnplateAwardService {

	@Autowired
	private TurnplateAwardDao turnplateAwardDao;

	public TurnplateAwardVO findByState(int result) {
		return turnplateAwardDao.findByState(result);
	}

	public TurnplateAwardVO findByLevel(String awardLevel) {
		return turnplateAwardDao.findByLevel(awardLevel);
	}
	
}


