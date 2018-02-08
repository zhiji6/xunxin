package com.xunxin.dao.pay;

import java.util.List;

import com.xunxin.vo.pay.ThirdPayBean;
import com.xunxin.vo.sys.PageData;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月4日 -- 上午1:45:51
 * @Version 1.0
 * @Description		第三方支付	
 */
public interface ThirdPayDao {

	List<ThirdPayBean> thirdPayList(PageData pd);
	
	ThirdPayBean findByPayId(Integer bank_id);
	
	
}
