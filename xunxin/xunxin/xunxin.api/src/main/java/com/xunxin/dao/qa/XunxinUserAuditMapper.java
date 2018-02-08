package com.xunxin.dao.qa;

import com.xunxin.vo.qa.XunxinUserAudit;
import com.xunxin.vo.qa.XunxinUserAuditExample;
import com.xunxin.vo.user.UserEntity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface XunxinUserAuditMapper {
    int countByExample(XunxinUserAuditExample example);

    int deleteByExample(XunxinUserAuditExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XunxinUserAudit record);

    int insertSelective(XunxinUserAudit record);

    List<XunxinUserAudit> selectByExample(XunxinUserAuditExample example);

    XunxinUserAudit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XunxinUserAudit record, @Param("example") XunxinUserAuditExample example);

    int updateByExample(@Param("record") XunxinUserAudit record, @Param("example") XunxinUserAuditExample example);

    int updateByPrimaryKeySelective(XunxinUserAudit record);

    int updateByPrimaryKey(XunxinUserAudit record);

	List<XunxinUserAudit> findListUserId(Integer userId);

	List<XunxinUserAudit> findAuditUserLogin(@Param("type")Integer type,@Param("auditNum") Integer auditNum);

	List<XunxinUserAudit> findAuditTimerTaskUserLogin(Map map);
}