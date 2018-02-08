package com.xunxin.dao.impl.app;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.square.UserBrushAgainstRecordDao;
import com.xunxin.vo.square.UserBrushAgainstRecord;

@Repository
public class UserBrushAgainstRecordDaoImpl extends GeneralDaoImpl<UserBrushAgainstRecord> implements UserBrushAgainstRecordDao{

    @Override
    protected Class<UserBrushAgainstRecord> getEntityClass() {
        return UserBrushAgainstRecord.class;
    }

}
