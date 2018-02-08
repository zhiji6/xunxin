package com.xunxin.dao.impl.app;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.app.user.UserOnlineRecordDao;
import com.xunxin.vo.user.UserOnlineRecord;

@Repository
public class UserOnlineRecordDaoImpl extends GeneralDaoImpl<UserOnlineRecord> implements UserOnlineRecordDao{

    @Override
    protected Class<UserOnlineRecord> getEntityClass() {
        return UserOnlineRecord.class;
    }

}
