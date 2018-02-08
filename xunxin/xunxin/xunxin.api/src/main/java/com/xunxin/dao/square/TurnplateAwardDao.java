package com.xunxin.dao.square;

import com.xunxin.vo.square.TurnplateAwardVO;

public interface TurnplateAwardDao {

	TurnplateAwardVO findByState(Integer awardState);

	TurnplateAwardVO findByLevel(String awardLevel);

	
	
	
}
