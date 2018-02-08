package com.xunxin.dao.im;

import com.xunxin.vo.im.HeartConsonanc;
import com.xunxin.vo.im.HeartConsonancExample;
import com.xunxin.vo.im.UserMatch;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HeartConsonancMapper {
    int countByExample(HeartConsonancExample example);

    int deleteByExample(HeartConsonancExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HeartConsonanc record);

    int insertSelective(HeartConsonanc record);

    List<HeartConsonanc> selectByExample(HeartConsonancExample example);

    HeartConsonanc selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HeartConsonanc record, @Param("example") HeartConsonancExample example);

    int updateByExample(@Param("record") HeartConsonanc record, @Param("example") HeartConsonancExample example);

    int updateByPrimaryKeySelective(HeartConsonanc record);

    int updateByPrimaryKey(HeartConsonanc record);

	List<HeartConsonanc> findHertConsonanceByUserId(Integer userId);

}