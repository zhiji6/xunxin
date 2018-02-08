package com.xunxin.service.app;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunxin.dao.qa.SysDictMapper;
import com.xunxin.vo.qa.SysDict;
import com.xunxin.vo.qa.SysDictExample;
import com.xunxin.vo.qa.SysDictExample.Criteria;

@Service("sysDictService")
public class SysDictService {
	
	@Autowired 
	private SysDictMapper sysDictMapper;
	private static final Logger logger = Logger.getLogger(SysDictService.class);
	public List<SysDict> findDics(String type) {
		return sysDictMapper.findDictByType(type);
	}
	public String findDictByValue(Integer type) {
		SysDictExample example = new SysDictExample();
		Criteria create = example.createCriteria();
		create.andTypeEqualTo("pushMessage");
		create.andValueEqualTo(type.toString());
		create.andIsDeleteEqualTo("0");
		List<SysDict> list = sysDictMapper.selectByExample(example);
		if(list != null && list.size()>0){
			return list.get(0).getLabel();
		}else{
			return null;
		}
	}

}
