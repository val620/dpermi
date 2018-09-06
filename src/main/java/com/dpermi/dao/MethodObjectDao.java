package com.dpermi.dao;

import com.dpermi.domain.MethodObject;
import com.dpermi.domain.PermissionObject;
import com.dpermi.mapper.MethodObjectMapper;
import com.dpermi.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by val620@126.com on 2018/6/27.
 */
@Component
public class MethodObjectDao {
    @Autowired
    private MethodObjectMapper methodObjectMapper;

    public List<MethodObject> getMethodObjects(){
        return methodObjectMapper.getMethodObjects();
    }

    public int insertMethodObject(MethodObject object){
        object.setMethodObjectId(UUIDUtil.GetUUID());
        return methodObjectMapper.insertMethodObject(object);
    }

    public int updateMethodObject(MethodObject object){
        return methodObjectMapper.updateMethodObject(object);
    }

    public int deleteMethodObject(String id){
        return methodObjectMapper.deleteMethodObject(id);
    }

    public List<MethodObject> getUserMethodObjects(String userId){
        return methodObjectMapper.getUserMethodObjects(userId);
    }

    public List<PermissionObject> getUserRoleMethodObjects(String userId){
        return methodObjectMapper.getUserRoleMethodObjects(userId);
    }
}
