 /**  
 *@Description: GeoHash实现经纬度的转化
 */ 
package com.xunxin.util.app.GeoHash;  

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunxin.util.JacksonUtil;


  
public class GeoHash {
	private LocationBean location;
	/**
	 * 1 2500km;2 630km;3 78km;4 30km
	 * 5 2.4km; 6 610m; 7 76m; 8 19m
	 */
	private int hashLength = 8; //经纬度转化为geohash长度
	private int latLength = 20; //纬度转化为二进制长度
	private int lngLength = 20; //经度转化为二进制长度
	
	private double minLat;//每格纬度的单位大小
	private double minLng;//每个经度的倒下
	private static final char[] CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', 
				'8', '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 
				'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	
	public GeoHash(double lat, double lng) {
		location = new LocationBean(lat, lng);
		setMinLatLng();
	}
	
	public int gethashLength() {
		return hashLength;
	}
	
	/**
	 * @Author:lulei  
	 * @Description: 设置经纬度的最小单位
	 */
	private void setMinLatLng() {
		minLat = LocationBean.MAXLAT - LocationBean.MINLAT;
		for (int i = 0; i < latLength; i++) {
			minLat /= 2.0;
		}
		minLng = LocationBean.MAXLNG - LocationBean.MINLNG;
		for (int i = 0; i < lngLength; i++) {
			minLng /= 2.0;
		}
	}
	
	/**
	 * @return
	 * @Author:lulei  
	 * @Description: 求所在坐标点及周围点组成的九个
	 */
	public List<String> getGeoHashBase32For9() {
		double leftLat = location.getLat() - minLat;
		double rightLat = location.getLat() + minLat;
		double upLng = location.getLng() - minLng;
		double downLng = location.getLng() + minLng;
		List<String> base32For9 = new ArrayList<String>();
		//左侧从上到下 3个
		String leftUp = getGeoHashBase32(leftLat, upLng);
		if (!(leftUp == null || "".equals(leftUp))) {
			base32For9.add(leftUp);
		}
		String leftMid = getGeoHashBase32(leftLat, location.getLng());
		if (!(leftMid == null || "".equals(leftMid))) {
			base32For9.add(leftMid);
		}
		String leftDown = getGeoHashBase32(leftLat, downLng);
		if (!(leftDown == null || "".equals(leftDown))) {
			base32For9.add(leftDown);
		}
		//中间从上到下 3个
		String midUp = getGeoHashBase32(location.getLat(), upLng);
		if (!(midUp == null || "".equals(midUp))) {
			base32For9.add(midUp);
		}
		String midMid = getGeoHashBase32(location.getLat(), location.getLng());
		if (!(midMid == null || "".equals(midMid))) {
			base32For9.add(midMid);
		}
		String midDown = getGeoHashBase32(location.getLat(), downLng);
		if (!(midDown == null || "".equals(midDown))) {
			base32For9.add(midDown);
		}
		//右侧从上到下 3个
		String rightUp = getGeoHashBase32(rightLat, upLng);
		if (!(rightUp == null || "".equals(rightUp))) {
			base32For9.add(rightUp);
		}
		String rightMid = getGeoHashBase32(rightLat, location.getLng());
		if (!(rightMid == null || "".equals(rightMid))) {
			base32For9.add(rightMid);
		}
		String rightDown = getGeoHashBase32(rightLat, downLng);
		if (!(rightDown == null || "".equals(rightDown))) {
			base32For9.add(rightDown);
		}
		return base32For9;
	}

	/**
	 * @param length
	 * @return
	 * @Author:lulei  
	 * @Description: 设置经纬度转化为geohash长度
	 */
	public boolean sethashLength(int length) {
		if (length < 1) {
			return false;
		}
		hashLength = length;
		latLength = (length * 5) / 2;
		if (length % 2 == 0) {
			lngLength = latLength;
		} else {
			lngLength = latLength + 1;
		}
		setMinLatLng();
		return true;
	}
	
	/**
	 * @return
	 * @Author:lulei  
	 * @Description: 获取经纬度的base32字符串
	 */
	public String getGeoHashBase32() {
		return getGeoHashBase32(location.getLat(), location.getLng());
	}
	
	/**
	 * @param lat
	 * @param lng
	 * @return
	 * @Author:lulei  
	 * @Description: 获取经纬度的base32字符串
	 */
	private String getGeoHashBase32(double lat, double lng) {
		boolean[] bools = getGeoBinary(lat, lng);
		if (bools == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bools.length; i = i + 5) {
			boolean[] base32 = new boolean[5];
			for (int j = 0; j < 5; j++) {
				base32[j] = bools[i + j];
			}
			char cha = getBase32Char(base32);
			if (' ' == cha) {
				return null;
			}
			sb.append(cha);
		}
		return sb.toString();
	}
	
	/**
	 * @param base32
	 * @return
	 * @Author:lulei  
	 * @Description: 将五位二进制转化为base32
	 */
	private char getBase32Char(boolean[] base32) {
		if (base32 == null || base32.length != 5) {
			return ' ';
		}
		int num = 0;
		for (boolean bool : base32) {
			num <<= 1;
			if (bool) {
				num += 1;
			}
		}
		return CHARS[num % CHARS.length];
	}
	
	/**
	 * @param lat
	 * @param lng
	 * @return
	 * @Author:lulei  
	 * @Description: 获取坐标的geo二进制字符串
	 */
	private boolean[] getGeoBinary(double lat, double lng) {
		boolean[] latArray = getHashArray(lat, LocationBean.MINLAT, LocationBean.MAXLAT, latLength);
		boolean[] lngArray = getHashArray(lng, LocationBean.MINLNG, LocationBean.MAXLNG, lngLength);
		return merge(latArray, lngArray);
	}
	
	/**
	 * @param latArray
	 * @param lngArray
	 * @return
	 * @Author:lulei  
	 * @Description: 合并经纬度二进制
	 */
	private boolean[] merge(boolean[] latArray, boolean[] lngArray) {
		if (latArray == null || lngArray == null) {
			return null;
		}
		boolean[] result = new boolean[lngArray.length + latArray.length];
		Arrays.fill(result, false);
		for (int i = 0; i < lngArray.length; i++) {
			result[2 * i] = lngArray[i];
		}
		for (int i = 0; i < latArray.length; i++) {
			result[2 * i + 1] = latArray[i];
		}
		return result;
	}
	
	/**
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 * @Author:lulei  
	 * @Description: 将数字转化为geohash二进制字符串
	 */
	private boolean[] getHashArray(double value, double min, double max, int length) {
		if (value < min || value > max) {
			return null;
		}
		if (length < 1) {
			return null;
		}
		boolean[] result = new boolean[length];
		for (int i = 0; i < length; i++) {
			double mid = (min + max) / 2.0;
			if (value > mid) {
				result[i] = true;
				min = mid;
			} else {
				result[i] = false;
				max = mid;
			}
		}
		return result;
	}
	
	/**
	 * 计算两经纬度点之间的距离（单位：米）
	 * 
	 * @param longitude1
	 *            坐标1经度
	 * @param latitude1
	 *            坐标1纬度
	 * @param longitude2
	 *            坐标2经度
	 * @param latitude2
	 *            坐标1纬度
	 * @return
	 */
	public static double countDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
		double radLat1 = Math.toRadians(latitude1);
		double radLat2 = Math.toRadians(latitude2);
		double a = radLat1 - radLat2;
		double b = Math.toRadians(longitude1) - Math.toRadians(longitude2);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378137.0;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	
	
private static double EARTH_RADIUS = 6378.137; 
    
    private static double rad(double d) { 
        return d * Math.PI / 180.0; 
    }
      
    /**
     * 根据两个位置的经纬度，来计算两地的距离（单位为KM）
     * 参数为String类型
     * @param lat1 用户经度
     * @param lng1 用户纬度
     * @param lat2 商家经度
     * @param lng2 商家纬度
     * @return
     */
    public static String getDistance(String lat1Str, String lng1Str, String lat2Str, String lng2Str) {
        Double lat1 = Double.parseDouble(lat1Str);
        Double lng1 = Double.parseDouble(lng1Str);
        Double lat2 = Double.parseDouble(lat2Str);
        Double lng2 = Double.parseDouble(lng2Str);
          
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double difference = radLat1 - radLat2;
        double mdifference = rad(lng1) - rad(lng2);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(mdifference / 2), 2)));
        distance = distance * EARTH_RADIUS;
        distance = Math.round(distance * 10000) / 10000;
        String distanceStr = distance+"";
        distanceStr = distanceStr.
            substring(0, distanceStr.indexOf("."));
          
        return distanceStr;
    }
      
    /**
     * 获取当前用户一定距离以内的经纬度值
     * 单位米 return minLat
     * 最小经度 minLng
     * 最小纬度 maxLat
     * 最大经度 maxLng
     * 最大纬度 minLat
     */
    public static Map getAround(String latStr, String lngStr, String raidus) {
        Map map = new HashMap();
          
        Double latitude = Double.parseDouble(latStr);// 传值给经度
        Double longitude = Double.parseDouble(lngStr);// 传值给纬度
  
        Double degree = (24901 * 1609) / 360.0; // 获取每度
        double raidusMile = Double.parseDouble(raidus);
          
        Double mpdLng = Double.parseDouble((degree * Math.cos(latitude * (Math.PI / 180))+"").replace("-", ""));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        //获取最小经度
        Double minLat = longitude - radiusLng;
        // 获取最大经度
        Double maxLat = longitude + radiusLng;
          
        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        // 获取最小纬度
        Double minLng = latitude - radiusLat;
        // 获取最大纬度
        Double maxLng = latitude + radiusLat;
          
        map.put("minLat", minLat+"");
        map.put("maxLat", maxLat+"");
        map.put("minLng", minLng+"");
        map.put("maxLng", maxLng+"");
          
        return map;
    }
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub 
//		GeoHash g = new GeoHash(40.222012, 116.248283);
//		System.out.println(g.getGeoHashBase32());
//		System.out.println(JacksonUtil.Builder().bean2Json(g.getGeoHashBase32For9()));
//		System.out.println(g.getGeoHashBase32For9());
		System.out.println(countDistance(120,30,120,30.0001));
	}

}
