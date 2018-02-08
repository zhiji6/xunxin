package com.xunxin.controller.app.user;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xunxin.util.MapRemoveNullUtil;
import com.xunxin.util.SortAlgorithmUtils;
import com.xunxin.vo.sys.PageData;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2018年1月23日 -- 下午1:51:48
 * @Version 1.0
 * @Description     QA话题分布单元测试
 */
public class TopicDistribution{
    
    private static final Map<Short,String> types = new HashMap<Short,String>();
    
    public static void fill() {
        types.put((short) 1, "科技");
        types.put((short) 3, "娱乐");
        types.put((short) 4, "体育");
        types.put((short) 5, "社会");
        types.put((short) 6, "财经");
        types.put((short) 7, "历史");
        types.put((short) 8, "家庭");
        types.put((short) 9, "生活");
        types.put((short) 10, "音乐");
        types.put((short) 11, "人文");
        types.put((short) 12, "自然");
        types.put((short) 13, "军事");
        types.put((short) 14, "影视");
    }

    public static void main(String[] args) {
        fill();
        Topic_distribution();
    }
    
	public static void	Topic_distribution(){
	    
	    Map<Short,Integer> map = new HashMap<>();
	    List<PageData> pdList = new ArrayList<>();
	    List<PageData> resultList = new ArrayList<>();
		
		//话题分布情况
        map.put((short)1,21);
        map.put((short)3,8);
        map.put((short)4,3);
        map.put((short)6,2);
        map.put((short)9,19);
        map.put((short)10,9);
        map.put((short)11,9);
        map.put((short)12,10);
        map.put((short)13,1);
        
        System.out.println(map.size());
        
        if(map.size() < 10) {
            Integer[] a = new Integer[map.size()];
            Integer[] array = map.values().toArray(a);
            Set<Integer> set = new HashSet<>();  
            for(int i=0;i<array.length;i++){  
                set.add(array[i]);  
            }  
            Integer[] arrayResult = (Integer[]) set.toArray(new Integer[set.size()]);  
            Integer[] bubble_sort = SortAlgorithmUtils.bubble_sort((Integer[])arrayResult);
            for(int key : bubble_sort) {
                List<Short> typeList = MapRemoveNullUtil.getKeyList(map,key);
                if(typeList.size() > 1) {
                    for(Short type : typeList) {
                        PageData tyPd = new PageData<>();
                        String sectionName = types.get(type);
                        // 创建一个数值格式化对象     
                        NumberFormat numberFormat = NumberFormat.getInstance();     
                        // 设置精确到小数点后2位     
                        numberFormat.setMaximumFractionDigits(0);     
                        String prent = numberFormat.format((float)key/(float)83*100); 
                        tyPd.put("sectionName", sectionName);
                        tyPd.put("prent", prent);
                        resultList.add(tyPd);
                    }
                }else {
                    PageData resultPd = new PageData<>();
                    Short type = MapRemoveNullUtil.getKey(map,key);
                    String sectionName = types.get(type);
                    // 创建一个数值格式化对象     
                    NumberFormat numberFormat = NumberFormat.getInstance();     
                    // 设置精确到小数点后2位     
                    numberFormat.setMaximumFractionDigits(0);     
                    String prent = numberFormat.format((float)key/(float)83*100); 
                    resultPd.put("sectionName", sectionName);
                    resultPd.put("prent", prent);
                    resultList.add(resultPd);
                }
            }
        }
//        for(PageData pd : pdList) {
//            resultList.add(pd);
//        }
	
        for(PageData pd : resultList) {
            System.out.println(pd.toString());
        }
        
	}
}