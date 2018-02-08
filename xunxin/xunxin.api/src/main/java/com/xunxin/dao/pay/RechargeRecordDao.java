package com.xunxin.dao.pay;

import java.util.List;


import com.xunxin.vo.account.RechargeRecord;
import com.xunxin.vo.sys.PageData;

public interface RechargeRecordDao {

	/**
	 * 新增订单
	 */
	void saveBill(RechargeRecord record);
	
	/**
	 * 通过订单号查找订单
	 */
	RechargeRecord findByOrderNo(String orderNo);

	/**
	 * 更新订单
	 */
	void updateBill(RechargeRecord record);

	//充值记录列表
    List<RechargeRecord> findRechargelist(PageData pd);

    RechargeRecord findByOrderId(Integer id);

    void delete(PageData pd);

	void updateBillState(String order_no);

	RechargeRecord findRechargeRecordByOrderNo(String orderNo);

	void insertBill(RechargeRecord record);


	

}
