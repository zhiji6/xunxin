package com.xunxin.controller.app.rules;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.xunxin.service.ArecordService;
import com.xunxin.service.app.AppUserService;
import com.xunxin.vo.qa.ArecordVO;

public class QARuler implements Rules {

	@Override
	public BigDecimal getScore(Integer userId, Integer friendId,ArecordService arecordService,AppUserService appUserService) {
		Query query = new Query();
		query.addCriteria(Criteria.where("answerID").is(userId));
		query.addCriteria(Criteria.where("isDelete").is(false));
		List<ArecordVO> listUser = arecordService.find(query);
		Query queryFriend = new Query();
		queryFriend.addCriteria(Criteria.where("answerID").is(friendId));
		queryFriend.addCriteria(Criteria.where("isDelete").is(false));
		List<ArecordVO> listFriend =arecordService.find(query);
		
		int[][] sparseMatrix = new int[2][2];//建立用户稀疏矩阵，用于用户相似度计算【相似度矩阵】
		Map<String, Integer> userItemLength = new HashMap<>();//存储每一个用户对应的不同物品总数  eg: A 3
        Map<String, Set<String>> itemUserCollection = new HashMap<>();//建立物品到用户的倒排表 eg: a A B
        Set<String> items = new HashSet<>();//辅助存储物品集合
        Map<String, Integer> userID = new HashMap<>();//辅助存储每一个用户的用户ID映射
        Map<Integer, String> idUser = new HashMap<>();//辅助存储每一个ID对应的用户映射
        
        for (int i = 0; i < 2; i++) {//依次处理N个用户 输入数据  以空格间隔
        	if(i == 0){
        		 int length = listUser.size();
                 userItemLength.put(userId.toString(), length - 1);//eg: A 3
                 userID.put(userId.toString(), 0);//用户ID与稀疏矩阵建立对应关系
                 idUser.put(0, userId.toString());
                 //建立物品--用户倒排表
                 for (int j = 0; j < length; j++) {
                     if (items.contains(listUser.get(j).getAnswers())) {//如果已经包含对应的物品--用户映射，直接添加对应的用户
                         itemUserCollection.get(listUser.get(j).getAnswers()).add(userId.toString());
                     } else {//否则创建对应物品--用户集合映射
                    	 if(listUser.get(j).getAnswers() != null){
                    		 items.add(listUser.get(j).getAnswers());
                             itemUserCollection.put(listUser.get(j).getAnswers(), new HashSet<String>());//创建物品--用户倒排关系
                             itemUserCollection.get(listUser.get(j).getAnswers()).add(userId.toString());
                    	 }
                         
                     }
                 }
        	}else{
        		 int length = listFriend.size();
                 userItemLength.put(friendId.toString(), length - 1);//eg: A 3
                 userID.put(friendId.toString(), 1);//用户ID与稀疏矩阵建立对应关系
                 idUser.put(1, friendId.toString());
                 //建立物品--用户倒排表
                 for (int j = 1; j < length; j++) {
                     if (items.contains(listFriend.get(j).getAnswers())) {//如果已经包含对应的物品--用户映射，直接添加对应的用户
                         itemUserCollection.get(listFriend.get(j).getAnswers()).add(friendId.toString());
                     } else {//否则创建对应物品--用户集合映射
                    	 if(listUser.get(j).getAnswers() != null){
                    		 items.add(listUser.get(j).getAnswers());
                             itemUserCollection.put(listFriend.get(j).getAnswers(), new HashSet<String>());//创建物品--用户倒排关系
                             itemUserCollection.get(listFriend.get(j).getAnswers()).add(friendId.toString()); 
                    	 }
                         
                     }
                 }
        	}
           
        }
        
        System.out.println(itemUserCollection.toString());
        //计算相似度矩阵【稀疏】
        Set<Map.Entry<String, Set<String>>> entrySet = itemUserCollection.entrySet();
        Iterator<Map.Entry<String, Set<String>>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Set<String> commonUsers = iterator.next().getValue();
            for (String user_u : commonUsers) {
                for (String user_v : commonUsers) {
                    if (user_u.equals(user_v)) {
                        continue;
                    }
                    sparseMatrix[userID.get(user_u)][userID.get(user_v)] += 1;//计算用户u与用户v都有正反馈的物品总数
                }
            }
        }
        System.out.println(userItemLength.toString());
        System.out.println("Input the user for recommendation:<eg:A>");
        String recommendUser = userId.toString();
        System.out.println(userID.get(recommendUser));
        //计算用户之间的相似度【余弦相似性】
        int recommendUserId = userID.get(recommendUser);
        double sort = 0;
        for (int j = 0; j < sparseMatrix.length; j++) {
            if (j != recommendUserId) {
                System.out.println(idUser.get(recommendUserId) + "--" + idUser.get(j) + "相似度:" + sparseMatrix[recommendUserId][j] / Math.sqrt(userItemLength.get(idUser.get(recommendUserId)) * userItemLength.get(idUser.get(j))));
                 sort = sparseMatrix[recommendUserId][j] / Math.sqrt(userItemLength.get(idUser.get(recommendUserId)) * userItemLength.get(idUser.get(j)));
                 return BigDecimal.valueOf(sort);
            }
        }
        
		return BigDecimal.valueOf(0.0);
	}

}
