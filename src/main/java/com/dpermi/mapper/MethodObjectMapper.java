package com.dpermi.mapper;

import com.dpermi.domain.MethodObject;
import com.dpermi.domain.PermissionObject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by val620@126.com on 2018/6/27.
 */
@Mapper
public interface MethodObjectMapper {

    List<MethodObject> getMethodObjects();
    int insertMethodObject(MethodObject object);
    int updateMethodObject(MethodObject object);
    int deleteMethodObject(String id);
    List<MethodObject> getUserMethodObjects(String userId);
    List<PermissionObject> getUserRoleMethodObjects(String userId);
}
