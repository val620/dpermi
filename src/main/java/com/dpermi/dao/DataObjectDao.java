package com.dpermi.dao;

import com.dpermi.domain.DataObject;
import com.dpermi.mapper.DataObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by val620@126.com on 2018/7/2.
 */
@Component
public class DataObjectDao {
    @Autowired
    private DataObjectMapper dataObjectMapper;

    public Integer getDataType(String className,String fieldName){
        return dataObjectMapper.getDataType(className,fieldName);
    }

    public List<DataObject> getDataTypes(String className){
        return dataObjectMapper.getDataTypes(className);
    }
}
