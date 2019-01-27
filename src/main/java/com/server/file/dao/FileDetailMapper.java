package com.server.file.dao;

import com.server.file.model.FileDetail;
import com.server.file.model.FileDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileDetailMapper {
    long countByExample(FileDetailExample example);

    int deleteByExample(FileDetailExample example);

    int deleteByPrimaryKey(String id);

    int insert(FileDetail record);

    int insertSelective(FileDetail record);

    List<FileDetail> selectByExample(FileDetailExample example);

    FileDetail selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FileDetail record, @Param("example") FileDetailExample example);

    int updateByExample(@Param("record") FileDetail record, @Param("example") FileDetailExample example);

    int updateByPrimaryKeySelective(FileDetail record);

    int updateByPrimaryKey(FileDetail record);
}