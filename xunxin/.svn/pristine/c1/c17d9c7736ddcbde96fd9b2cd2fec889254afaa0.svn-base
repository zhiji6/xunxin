package com.xunxin.util.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionRandomUtil {

  
    /**从数组中随机抽取元素 
     * @return   
     * @Title: createRandomArray  
     * @Description: TODO 
     * @param arr 
     * @param i  
     * @return void   
     * @throws  
     */   
    public static int[] createRandomArray(int[] arr, int n) {  
        // TODO Auto-generated method stub  
        Map map = new HashMap();  
        int[] arrNew = new int[n];  
        if(arr.length<=n){  
            return arr;  
        }else{  
            int count = 0;//新数组下标计数  
            while(map.size()<n){  
                int random = (int) (Math.random() * arr.length);  
                if (!map.containsKey(random)) {  
                    map.put(random, "");  
                    System.out.println(random+"==========="+arr[random]);  
                    arrNew[count++] = arr[random];  
                }  
            }  
            return arrNew;  
        }  
    }  
  
    /**从list中随机抽取元素 
     * @return   
     * @Title: createRandomList  
     * @Description: TODO 
     * @param list 
     * @param i  
     * @return void   
     * @throws  
     */   
    public static List createRandomList(List list, int n) {  
        // TODO Auto-generated method stub  
        Map map = new HashMap();  
        List listNew = new ArrayList();  
        if(list.size()<=n){  
            return list;  
        }else{  
            while(map.size()<n){  
                int random = (int) (Math.random() * list.size());  
                if (!map.containsKey(random)) {  
                    map.put(random, "");  
//                    System.out.println(random+"==========="+list.get(random));  
                    listNew.add(list.get(random));  
                }  
            }  
            return listNew;  
        }  
    }  
    
    
 // 把list转换为string，用,分割
    public static String listToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }
    
}
