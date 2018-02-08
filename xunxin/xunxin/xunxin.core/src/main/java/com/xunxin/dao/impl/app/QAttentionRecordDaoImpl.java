package com.xunxin.dao.impl.app;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.vo.qa.QAttentionRecord;

import com.xunxin.dao.qa.QAttentionRecordDao;

@Repository
public class QAttentionRecordDaoImpl extends GeneralDaoImpl<QAttentionRecord> implements QAttentionRecordDao{

    @Override
    protected Class<QAttentionRecord> getEntityClass() {
        return QAttentionRecord.class;
    }

}
