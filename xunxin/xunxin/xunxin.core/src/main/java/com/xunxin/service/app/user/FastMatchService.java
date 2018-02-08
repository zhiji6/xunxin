package com.xunxin.service.app.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Service("fastMatchService")
public class FastMatchService {

	public static void main(String[] args) {
		fastMatch();
	}
	public static void fastMatch() {
		System.out.println("===========开始快速匹配用户画像==========");
		MongoDatabase mdb=MongoDBUtil.getDatabase();
		MongoCollection<Document> document =mdb.getCollection("qaRecord");
		Long begin =System.currentTimeMillis();
		List<String> list_zhangsan =getRecordByName(1,document);
		List<String> list_lisi =getRecordByName(2,document);
		List<String> matchResult =receiveCollectionList(list_zhangsan,list_lisi);
		Long end =System.currentTimeMillis();
		System.out.println("****匹配到zhangsan与lisi共有相同的答题记录数据为："+matchResult.size());
		System.out.println("****匹配花费时间："+(end - begin));
		System.out.println("****部分相同数据如下：");
		for(int i=0;i<10;i++) {
			System.out.println(matchResult.get(i));
		}

		
	}

	public static List<String> getRecordByName(int userId,MongoCollection<Document> document){
		List<String> record =new ArrayList<String>();
		Document key =new Document();//指定只返回record字段信息  1表示需要显示，0表示不需要显示
		key.append("questionID", 1);
		key.append("answerID", 1);
		key.append("_id", 0);
		 for (Document  cursor : document.find(new Document().append("userID", userId)).projection(key)) {
			 record.add(cursor.getInteger("questionID")+""+cursor.getInteger("answerID"));
			}
		 System.out.println("****从mongodb中获取"+userId+"答题记录："+record.size());
		return record;
	}
	
	   /**
     * @方法描述：获取两个ArrayList的交集
     * @param firstArrayList 第一个ArrayList
     * @param secondArrayList 第二个ArrayList
     * @return resultList 交集ArrayList
     */
    public static List<String> receiveCollectionList(List<String> firstArrayList, List<String> secondArrayList) {
        List<String> resultList = new ArrayList<String>();
        LinkedList<String> result = new LinkedList<String>(firstArrayList);// 大集合用linkedlist  
        HashSet<String> othHash = new HashSet<String>(secondArrayList);// 小集合用hashset  
        Iterator<String> iter = result.iterator();// 采用Iterator迭代器进行数据的操作  
        while(iter.hasNext()) {
            if(!othHash.contains(iter.next())) {  
                iter.remove();            
            }     
        }
        resultList = new ArrayList<String>(result);
        return resultList;
    }
}
