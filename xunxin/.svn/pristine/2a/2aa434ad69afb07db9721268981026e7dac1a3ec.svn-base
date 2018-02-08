package com.xunxin.controller.app.quartz;

import java.util.List;

import org.apache.log4j.Logger;
import org.bson.BsonArray;
import org.bson.BsonDouble;
import org.bson.Document;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.xunxin.config.LuceneEngineutil;
import com.xunxin.controller.app.message.MessTypeFactory;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.IMNewsService;
import com.xunxin.service.app.UserFriendsService;
import com.xunxin.service.app.square.UserBrushAgainstRecordService;
import com.xunxin.vo.im.UserFriends;
import com.xunxin.vo.square.UserBrushAgainstRecord;
import com.xunxin.vo.user.UserEntity;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2018年2月2日 -- 下午3:10:23
 * @Version 1.0
 * @Description     擦肩而过消息推送任务
 */
public class BrushAgainstJob implements Job{
    
    private static final Logger log = Logger.getLogger(BrushAgainstJob.class);

    @Autowired
    private AppUserService appUserService;
    @Autowired
    private UserFriendsService userFriendsService;
    @Autowired
    private IMNewsService iMNewsService;
    @Autowired
    private UserBrushAgainstRecordService userBrushAgainstRecordService;
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("infoMsg:--- 擦肩而过推送开始");
        Query query = new Query().with(new Sort(new Order(Direction.DESC,"createTime"))); 
        List<UserBrushAgainstRecord> againstList = userBrushAgainstRecordService.find(query);
        for(UserBrushAgainstRecord  against : againstList) {
            int userId = against.getUserId();
            UserEntity user = appUserService.findById(userId);
            if(null != user) {
                double longitude = Double.parseDouble(against.getCoordinate().getString("longitude"));
                double latitude = Double.parseDouble(against.getCoordinate().getString("latitude"));
                BsonArray array = new BsonArray();//坐标[longitude, latitude]
                array.add(new BsonDouble(longitude));//longitude
                array.add(new BsonDouble(latitude));// latitude
                
                Document near = new Document();
                near.put("$nearSphere", array);//表示near查询,单位是“度”
                near.put("$maxDistance",  100);//最大距离
                Document p = new Document();
                p.put("coordinate", near);//坐标在collection中的字段名,你这是loc
                p.put("isOpen", 1);
//                MongoClient mongoClient = LuceneEngineutil.getMongoClient();
                MongoCollection<Document> collection = LuceneEngineutil.getDatabase().getCollection("userBrushAgainstRecord");//获取数据库里的collection,你这是places
                Long start = System.currentTimeMillis();
                FindIterable<Document> dbCursor = collection.find(p).limit(20);//查找20个
                System.out.println("======查询附件的人耗时："+(System.currentTimeMillis()-start));
                
                for (Document  cursor : dbCursor) {
                    if(cursor != null) {
                        System.out.println(cursor.toString()); 
                        Integer anotherUserId = cursor.getInteger("userId");
                        UserEntity anotherUser = appUserService.findById(anotherUserId);
                        if(null != anotherUser) {
                            List<UserFriends> list = userFriendsService.findFriends(userId);
                            for(UserFriends friend : list) {
                                if(friend.getFriendId() == anotherUserId && anotherUserId != userId) {
                                    //推送给自己
                                    iMNewsService.userBehaviorPushMessage(MessTypeFactory.PAYY_BY_SELF,friend.getFriendId(),friend.getUserId(),null);
                                    System.out.println("推送给:"+appUserService.findById(friend.getFriendId()).getNickName()+",推送者id："+friend.getFriendId());
                                    //推送给对方
                                    iMNewsService.userBehaviorPushMessage(MessTypeFactory.PASS_BY_FRIEND,friend.getUserId(),friend.getFriendId(),null);
                                    System.out.println("推送给:"+appUserService.findById(friend.getUserId()).getNickName()+",推送者id："+friend.getUserId());
                                }
                            }    
                        }
                    }
                }
//                mongoClient.close();
            }
        }
        log.info("infoMsg:--- 擦肩而过推送结束");        
    }

}
