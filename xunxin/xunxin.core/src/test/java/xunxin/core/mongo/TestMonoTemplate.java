package xunxin.core.mongo;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2018年1月2日 -- 下午1:21:39
 * @Version 1.0
 * @Description
 */
public class TestMonoTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Test
    public void testCURD(){

        //测试增删改查操作(此处简化举例)
        Customer cu = new Customer();
        cu.setName("admin");
        cu.setPassword("noseparte");
        mongoTemplate.save(cu,"customer");   //添加

//        Query query = new Query();
//        query.addCriteria(Criteria.where("userId").is(13));
//        mongoTemplate.find(query,QACollectionRecord.class);  //查询操作

//         mongoTemplate.findOne();//单个结果查询操作
//         mongoTemplate.updateFirst();//进行第一条符合要求的数据更新
//        mongoTemplate.updateMulti();//进行更新多行数据
//        mongoTemplate.remove();//进行数据删除

    }
    
}
