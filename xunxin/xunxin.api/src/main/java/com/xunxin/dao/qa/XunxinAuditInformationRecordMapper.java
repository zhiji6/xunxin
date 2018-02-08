package com.xunxin.dao.qa;

import com.xunxin.vo.qa.XunxinAuditInformationRecord;
import com.xunxin.vo.qa.XunxinAuditInformationRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface XunxinAuditInformationRecordMapper {
    int countByExample(XunxinAuditInformationRecordExample example);

    int deleteByExample(XunxinAuditInformationRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XunxinAuditInformationRecord record);

    int insertSelective(XunxinAuditInformationRecord record);

    List<XunxinAuditInformationRecord> selectByExample(XunxinAuditInformationRecordExample example);

    XunxinAuditInformationRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XunxinAuditInformationRecord record, @Param("example") XunxinAuditInformationRecordExample example);

    int updateByExample(@Param("record") XunxinAuditInformationRecord record, @Param("example") XunxinAuditInformationRecordExample example);

    int updateByPrimaryKeySelective(XunxinAuditInformationRecord record);

    int updateByPrimaryKey(XunxinAuditInformationRecord record);

	List<XunxinAuditInformationRecord> findListByQuestionId(String questionId);
}