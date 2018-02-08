package com.xunxin.config;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.BsonArray;
import org.bson.BsonDouble;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class LBS {
	 private static MongoClient mongoClient = null;
	   private static MongoDatabase database = null;
	   private static ServerAddress serverAddress = null;
	   private static MongoCredential  credentials  = null;
	   private static List<ServerAddress> addressLists =new ArrayList<ServerAddress>();
	   private static List<MongoCredential> credentialsLists = new ArrayList<MongoCredential>();

	   private static final String IP="localhost";
	   private static final int PORT = 27017;

	   public static void init(){
		   try{
		   serverAddress = new ServerAddress(IP,PORT);
		   addressLists.add(serverAddress);
		   mongoClient = new MongoClient(addressLists);

		   }catch (MongoException e)
	       {
	           System.out.println(e.toString());
	       }
		   if(null != mongoClient){
			   database = mongoClient.getDatabase("test");
		   }
		 
	   }
	   public static void main(String[] args) {
		init();
//		creatDatas();
		findNear();
	}
	   
	   public static void findNear() {
//		   db.lbs.find({'coordinate':{$nearSphere: [45.167038, 57.05973], $maxDistance:100}}).limit(10)
		   
		   BsonArray array = new BsonArray();//坐标[longitude, latitude]
		   array.add(new BsonDouble(45.167038d));//longitude
		   array.add(new BsonDouble(57.05973d));// latitude

		   Document near = new Document();
		   near.put("$nearSphere", array);//表示near查询,单位是“度”
		   near.put("$maxDistance",  100);//最大距离
		   Document p = new Document();
		   p.put("coordinate", near);//坐标在collection中的字段名,你这是loc
		   MongoCollection<Document> collection =database.getCollection("lbs");//获取数据库里的collection,你这是places
		   Long start =System.currentTimeMillis();
		   FindIterable<Document> dbCursor = collection.find(p).limit(20);//查找20个
		   System.out.println("======查询附件的人耗时："+(System.currentTimeMillis()-start));

		   for (Document  cursor : dbCursor) {
			   System.out.println(cursor);
		   }
	   }
	   /**
	    * 1. 随机生成100万个坐标
	    * 2. 手动创建索引：
	    * db.lbs.ensureIndex({'coordinate':'2dsphere'})
	    * 3. shell 查询语句:
	    * db.lbs.find({'coordinate':{$nearSphere: [45.167038, 57.05973], $maxDistance:2}}).limit(10)
	    * ps:参考文献（http://blog.csdn.net/huangrunqing/article/details/9112227）
	    */
	   public static void creatDatas() {
		   List<Document> list = new ArrayList<Document>();
		   MongoCollection<Document> collection =database.getCollection("lbs");

			for(int i=0;i<1000000;i++) {
				Double[] loc =randomLonLat(30d, 90d, 30d, 90d);
				Document newDocument =new Document().append("userID", "user_"+i).append("updateTime", new Date())
						.append("coordinate",new Document().append("longitude",loc[0])
						.append("latitude", loc[1]));
//				Document newDocument =new Document().append("userID", "user_"+i).append("updateTime", new Date())
//						.append("coordinate",loc);
		        list.add(newDocument);
		        if(list.size()>10000) {
		        	collection.insertMany(list);
		        	list = new ArrayList<Document>();
		        }
			}
			if(list.size()>0) {
	        	collection.insertMany(list);
	        	list = new ArrayList<Document>();
	        }
			
	   }
	   
	   /**
	    * @Title: randomLonLat
	    * @Description: 在矩形内随机生成经纬度
	    * @param MinLon：最新经度  MaxLon： 最大经度   MinLat：最新纬度   MaxLat：最大纬度 
	    * @return
	    * @throws
	    */
	  public static Double[] randomLonLat(double MinLon, double MaxLon, double MinLat, double MaxLat) {
	    BigDecimal db = new BigDecimal(Math.random() * (MaxLon - MinLon) + MinLon);
	    String lon = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();// 小数后6位
	    db = new BigDecimal(Math.random() * (MaxLat - MinLat) + MinLat);
	    String lat = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
	    Double[] loc = new Double[2];
	    loc[0]=Double.valueOf(lon);
	    loc[1]=Double.valueOf(lat);
	    return loc;
	  }

}
