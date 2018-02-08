package com.xunxin.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.xunxin.service.app.qa.QuestionService;
import com.xunxin.util.PeriodsUtil;
import com.xunxin.util.SortAlgorithmUtils;
import com.xunxin.vo.qa.QuestionVO;
import com.xunxin.vo.sys.PageData;

/**
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 * 
 * @Author Noseparte
 * @Compile 2017年11月30日 -- 下午3:44:57
 * @Version 1.0
 * @Description 定时调度任务
 */
public class XQuarztService {
    
    private static final Logger log = Logger.getLogger(XQuarztService.class);

	@Autowired
	private QuestionService questionService;

	/**
	 * 每天上午8时，更新日排行榜
	 */
	public void day_leader() {
		try {
			// 话题参与量 我发布的+我参与的(答题+评论)
			// 各取前10
			// 发题前10的
			List<PageData> pdList = new ArrayList<>();
//			int scroll[] = new int[10];
//			int total = 0;
			Query query = new Query();
			query.addCriteria(Criteria.where("query").is(0)); // 筛选用户
			SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowDate = PeriodsUtil.getWholeTime(new Date());
			String preDate = PeriodsUtil.getWholeTime(PeriodsUtil.longToDate(PeriodsUtil.addDate(new Date()) - (60*60*24)));
			query.addCriteria(Criteria.where("createTime").gte(format.parse(preDate)).lt(format.parse(nowDate)));
			
			List<QuestionVO> qList = questionService.find(query);
			List<PageData> countList = new ArrayList<>();
			int[] bubble_sort = null;
			for (QuestionVO qa : qList) {
				Query countQuery = new Query();
				countQuery.addCriteria(Criteria.where("userId").is(qa.getUserID())); // 筛选用户
				Integer count = questionService.findCountByQuery(countQuery);
				PageData countPd = new PageData<>();
				countPd.put("count", count);
				countPd.put("userId", qa.getUserID());
				countList.add(countPd);
			}
			for (PageData p : countList) {
				int[] scroll = new int[countList.size()];
				bubble_sort = SortAlgorithmUtils.bubble_sort(scroll);
			}
			int[] range = Arrays.copyOfRange(bubble_sort, 0, 9);
				
				
				
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		


	}

	/**
	 * 每周上午8时，更新周排行榜
	 */
	public void week_leader() {
		// 话题参与量 我发布的+我参与的(答题+评论)
		// 各取前10
		// 发题前10的
		List<PageData> pdList = new ArrayList<>();
		int scroll[] = new int[10];
		int total = 0;
		Query query = new Query();
		query.addCriteria(Criteria.where("query").is(0)); // 筛选用户
		query.with(new Sort(new Order(Direction.DESC, "createTime")));
		List<QuestionVO> qList = questionService.find(query);
		for (QuestionVO qa : qList) {
			Query countQuery = new Query();
			countQuery.addCriteria(Criteria.where("userId").is(qa.getUserID())); // 筛选用户
			Integer count = questionService.findCountByQuery(countQuery);
			PageData pd = new PageData<>();
			pd.put("count", count);
			pd.put("userId", qa.getUserID());
			pdList.add(pd);
			for (PageData p : pdList) {
				p.get("count");
			}

		}
	}

	/**
	 * 每月上午8时，更新月排行榜
	 */
	public void month_leader() {
		// 话题参与量 我发布的+我参与的(答题+评论)
		// 各取前10
		// 发题前10的
		List<PageData> pdList = new ArrayList<>();
		int scroll[] = new int[10];
		int total = 0;
		Query query = new Query();
		query.addCriteria(Criteria.where("query").is(0)); // 筛选用户
		query.with(new Sort(new Order(Direction.DESC, "createTime")));
		List<QuestionVO> qList = questionService.find(query);
		for (QuestionVO qa : qList) {
			Query countQuery = new Query();
			countQuery.addCriteria(Criteria.where("userId").is(qa.getUserID())); // 筛选用户
			Integer count = questionService.findCountByQuery(countQuery);
			PageData pd = new PageData<>();
			pd.put("count", count);
			pd.put("userId", qa.getUserID());
			pdList.add(pd);
			for (PageData p : pdList) {
				p.get("count");
			}

		}
	}

	/**
	 * 定时更新Lucene索引任务
	 */
	public void updateLuceneIndex() {
	    log.info("infoMsg:--- 定时更新Lucene索引任务开始");
	    try {
	        LuceneEngineutil.creatIndex();
	        log.info("infoMsg:--- 定时更新Lucene索引任务结束");
        } catch (Exception e) {
            log.error("errorMsg:{--- 定时更新Lucene索引任务失败:" + e.getMessage() + "---}");
        }
	}
	
	/**
	 * 定时更新Lucene索引任务
	 */
	public void initialize() {
	   System.out.println("Hello World!");
	}
	
	
	
	
	
	
	
}
