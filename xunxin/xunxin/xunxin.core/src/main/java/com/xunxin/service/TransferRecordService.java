package com.xunxin.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xunxin.dao.app.user.AppUserDao;
import com.xunxin.dao.pay.ThirdPayDao;
import com.xunxin.dao.pay.TransferRecordDao;
import com.xunxin.util.PeriodsUtil;
import com.xunxin.vo.account.TransferRecord;
import com.xunxin.vo.pay.ThirdPayBean;
import com.xunxin.vo.user.UserEntity;

@Service("transferRecordService")
public class TransferRecordService {

	private static final Logger log = Logger.getLogger(TransferRecordService.class);
	
	@Autowired
	private AppUserDao appUserDao;
	@Autowired
	private TransferRecordDao transferRecordDao;
	@Autowired
	private ThirdPayDao thirdPayDao;


	/**
	 * 支付宝转账
	 * 
	 * @param body
	 * @param passbackParams
	 * @param amount
	 * @return
	 */
	public boolean saveTransRecord(String body, String passbackParams, String amount) {
		log.info("支付宝转账开始");
		try {
			int userId = Integer.parseInt(passbackParams.split("|")[0]);
			UserEntity entity = appUserDao.findById(userId);
			Double tansferBefore = entity.getAmount();
			Double tansferAmount = Double.parseDouble(amount);
			Double tansferEnd = tansferBefore - tansferAmount;
			int bank_id = Integer.parseInt(passbackParams.split("|")[1]);
			ThirdPayBean bean = thirdPayDao.findByPayId(bank_id);
			JSONObject body_object = JSON.parseObject(body);
			String alipay_transfer_response = body_object.getString("alipay_fund_trans_toaccount_transfer_response");
			JSONObject transfer_object = JSON.parseObject(alipay_transfer_response);
			String orderNo = transfer_object.getString("out_biz_no");
			String pay_date = transfer_object.getString("pay_date");
			Date tansferTime = PeriodsUtil.getStringToDate(pay_date);
			String remark = "用户:" + entity.getPhone() + "--- 使用" + bean.getThird_name() + "提现" + tansferAmount + "元";
			//生成转账记录
			TransferRecord record = new TransferRecord(userId, orderNo, bank_id, tansferBefore, tansferAmount, tansferEnd, tansferTime, 0, remark);
			transferRecordDao.saveTransRecord(record);
			//账变
			entity.setAmount(tansferEnd);
			appUserDao.updateAccount(entity);
			log.info("支付宝转账结束");
			return true;
		} catch (Exception e) {
			log.error("支付宝转账失败");
		}
		return false;
	}
	
	
	
	
}
