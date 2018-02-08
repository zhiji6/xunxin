package com.xunxin.service.app;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.im.NearLocationDao;
import com.xunxin.dao.impl.NearLocationDaoImpl;
import com.xunxin.util.app.GeoHash.GeoHash;
import com.xunxin.vo.im.NearLocation;

@Repository
public class NearLocationService extends NearLocationDaoImpl{
	@Autowired
	private NearLocationDao nearLocationDao;

	public void saveLocation(Integer userId, Double longitude, Double latitude, String cityCode) {
		
		if(longitude == null || longitude <1 || latitude== null || latitude<1){
			return;
		}
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("userId").is(userId));
		
		GeoHash g = new GeoHash(latitude, longitude);
		
		String geoHashCode = g.getGeoHashBase32();
		List<NearLocation> userLocationList = find(query);
		if(userLocationList != null && userLocationList.size()>0){
			Update update = Update.update("geoHashCode", geoHashCode);
			update.set("longitude", longitude);
			update.set("latitude", latitude);
			update.set("latitude", latitude);
			update.set("isOnLine", 0);
			updateAllByQuery(query, update);
		}else{
			NearLocation location = new NearLocation();
			location.setCityCode(cityCode);
			location.setIsOnLine(0);
			location.setUserId(userId);
			location.setLatitude(latitude);
			location.setLongitude(longitude);
			location.setGeoHashCode(geoHashCode);
			location.setIsDelete(false);
			location.setCreateTime(new Date());
			location.setUpdateTime(new Date());
			insert(location);
		}
		
	}
	//在线更新用户状态
	public void updateOnLine(Integer userId,Integer isOnLine) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("userId").is(userId));
		
		List<NearLocation> userLocationList = find(query);
		if(userLocationList != null && userLocationList.size()>0){
			Update update = Update.update("isOnLine", isOnLine);
			updateFirst(query, update);
		}
		
	}
	//查询附近的人
	public List<NearLocation> queryNearLocation(Double longitude, Double latitude,Integer userId,Integer num) {
		
		GeoHash g = new GeoHash(latitude, longitude);
		List<String> geoHashBase32For9 = g.getGeoHashBase32For9();
		List<NearLocation> userLocationList = null;
		
		userLocationList =findNearLocationPeople19(geoHashBase32For9,userId,num);
		if(userLocationList == null ||userLocationList.size() < num+1){
			userLocationList =findNearLocationPeople80(geoHashBase32For9,userId,num);
			if(userLocationList == null ||userLocationList.size() < num+1){
				userLocationList =findNearLocationPeople600(geoHashBase32For9,userId,num);
				if(userLocationList == null ||userLocationList.size() < num+1){
					userLocationList =findNearLocationPeople2000(geoHashBase32For9,userId,num);
					if(userLocationList == null ||userLocationList.size() < num+1){
						userLocationList =findNearLocationPeople20Km(geoHashBase32For9,userId,num);
						if(userLocationList == null ||userLocationList.size() < num+1){
							userLocationList =findNearLocationPeople100Km(geoHashBase32For9,userId,num);
						}
					}
				}
			}
		}
		
		for (NearLocation nearLocation : userLocationList) {
			double distance=0;
			if(longitude!= null && latitude!= null && nearLocation.getLongitude()!= null && nearLocation.getLatitude()!= null){
				
			distance = GeoHash.countDistance(longitude,latitude,nearLocation.getLongitude(),nearLocation.getLatitude());
			
			}
			nearLocation.setDistance(distance);
		}
		
		return userLocationList;
	}
	//查询附近100千米的人
	private List<NearLocation> findNearLocationPeople100Km(List<String> geoHashBase32For9, Integer userId,Integer num) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("isOnLine").is(0));
		query.addCriteria(Criteria.where("userId").ne(userId));
		query.limit(num);
		Criteria criteria = new Criteria();
		query.addCriteria(criteria.orOperator(
				Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(0).substring(0, 3)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(1).substring(0, 3)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(2).substring(0, 3)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(3).substring(0, 3)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(4).substring(0, 3)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(5).substring(0, 3)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(6).substring(0, 3)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(7).substring(0, 3)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(8).substring(0, 3)+ ".*"))
			));
        return find(query);
	}
	//查询附近20千米的人
	private List<NearLocation> findNearLocationPeople20Km(List<String> geoHashBase32For9, Integer userId,Integer num) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("isOnLine").is(0));
		query.addCriteria(Criteria.where("userId").ne(userId));
		query.limit(num);
		Criteria criteria = new Criteria();
		query.addCriteria(criteria.orOperator(
				Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(0).substring(0, 4)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(1).substring(0,4)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(2).substring(0, 4)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(3).substring(0, 4)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(4).substring(0, 4)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(5).substring(0, 4)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(6).substring(0, 4)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(7).substring(0, 4)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(8).substring(0, 4)+ ".*"))
			));
        return find(query);
	}
	//查询附近2千米的人
	private List<NearLocation> findNearLocationPeople2000(List<String> geoHashBase32For9, Integer userId,Integer num) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("isOnLine").is(0));
		query.addCriteria(Criteria.where("userId").ne(userId));
		query.limit(num);
		Criteria criteria = new Criteria();
		query.addCriteria(criteria.orOperator(
				Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(0).substring(0, 5)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(1).substring(0, 5)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(2).substring(0, 5)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(3).substring(0, 5)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(4).substring(0, 5)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(5).substring(0, 5)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(6).substring(0, 5)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(7).substring(0, 5)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(8).substring(0, 5)+ ".*"))
			));
        return find(query);
	}
	//查询附近600米得人
	private List<NearLocation> findNearLocationPeople600(List<String> geoHashBase32For9, Integer userId,Integer num) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("isOnLine").is(0));
		query.addCriteria(Criteria.where("userId").ne(userId));
		query.limit(num);
		Criteria criteria = new Criteria();
		query.addCriteria(criteria.orOperator(
				Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(0).substring(0, 6)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(1).substring(0, 6)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(2).substring(0, 6)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(3).substring(0, 6)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(4).substring(0, 6)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(5).substring(0, 6)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(6).substring(0, 6)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(7).substring(0, 6)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(8).substring(0, 6)+ ".*"))
			));
        return find(query);
	}
	//查询附近80米的人
	private List<NearLocation> findNearLocationPeople80(List<String> geoHashBase32For9, Integer userId,Integer num) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("isOnLine").is(0));
		query.addCriteria(Criteria.where("userId").ne(userId));
		query.limit(num);
		Criteria criteria = new Criteria();
		query.addCriteria(criteria.orOperator(
				Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(0).substring(0, 7)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(1).substring(0, 7)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(2).substring(0, 7)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(3).substring(0, 7)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(4).substring(0, 7)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(5).substring(0, 7)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(6).substring(0, 7)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(7).substring(0, 7)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(8).substring(0, 7)+ ".*"))
			));
        return find(query);
	}
	//查询附近19米的人
	private List<NearLocation> findNearLocationPeople19(List<String> geoHashBase32For9, Integer userId,Integer num) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("isOnLine").is(0));
		query.addCriteria(Criteria.where("userId").ne(userId));
		query.limit(num);
		Criteria criteria = new Criteria();
		query.addCriteria(criteria.orOperator(
				Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(0).substring(0, 8)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(1).substring(0, 8)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(2).substring(0, 8)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(3).substring(0, 8)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(4).substring(0, 8)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(5).substring(0, 8)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(6).substring(0, 8)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(7).substring(0, 8)+ ".*"))
			    ,Criteria.where("geoHashCode").regex(StringEscapeUtils.unescapeJava(".*?\\" +geoHashBase32For9.get(8).substring(0, 8)+ ".*"))
			));
        return find(query);
	}
	public BigDecimal queryDistance(Integer userId, Integer consonanceId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("userId").is(userId));
		List<NearLocation> userdis = find(query);
		
		Query queryMatch = new Query();
		queryMatch.addCriteria(Criteria.where("isDelete").is(false));
		queryMatch.addCriteria(Criteria.where("userId").is(consonanceId));
		List<NearLocation> matchDis = find(queryMatch);
		double countDistance=0;
		if(userdis != null && userdis.size()>0 && userdis.get(0).getLatitude() != null && userdis.get(0).getLongitude() != null
				&& matchDis != null && matchDis.size()>0 && matchDis.get(0).getLatitude() != null && matchDis.get(0).getLongitude() != null){
			 countDistance = GeoHash.countDistance(userdis.get(0).getLongitude(), userdis.get(0).getLatitude(), matchDis.get(0).getLongitude(), matchDis.get(0).getLatitude());
		}
		return BigDecimal.valueOf(countDistance);
	}
	public NearLocation findOneByQuery(Integer userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("userId").is(userId));
		query.with(new Sort(new Order(Direction.DESC, "createTime")));
		return findOneByQuery(query);
	}
	//根据距离找附近的人
	public List<NearLocation> queryNearLocationRange(Double longitude, Double latitude, Integer userId,
			Integer range) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("isOnLine").is(0));
		query.addCriteria(Criteria.where("userId").ne(userId));
		Map map = GeoHash.getAround(latitude.toString(), longitude.toString(), range.toString());
//        map.put("minLat", minLat+"");
//        map.put("maxLat", maxLat+"");
//        map.put("minLng", minLng+"");
//        map.put("maxLng", maxLng+"");
		query.addCriteria(Criteria.where("latitude").gte(Double.valueOf((String) map.get("minLng"))).lte(Double.valueOf((String) map.get("maxLng"))));
		query.addCriteria(Criteria.where("longitude").gte(Double.valueOf((String) map.get("minLat"))).lte(Double.valueOf((String) map.get("maxLat"))));
		
		List<NearLocation> list = find(query);
		for (NearLocation nearLocation : list) {
			double distance=0;
			if(longitude!= null && latitude!= null && nearLocation.getLongitude()!= null && nearLocation.getLatitude()!= null){
				
			distance = GeoHash.countDistance(longitude,latitude,nearLocation.getLongitude(),nearLocation.getLatitude());
			
			}
			nearLocation.setDistance(distance);
		}
		return list;
	}

}
