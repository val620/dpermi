package com.dpermi.mapper;

import com.dpermi.domain.DataObject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by val620@126.com on 2018/7/2.
 */
@Mapper
public interface DataObjectMapper {
    Integer getDataType(@Param("className")String className, @Param("fieldName")String fieldName);

    List<DataObject> getDataTypes(String className);
}
