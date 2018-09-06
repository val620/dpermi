package com.dpermi.mapper;

import com.dpermi.domain.*;
import com.dpermi.domain.search.RoleSearch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by val620@126.com on 2018/7/5.
 */
@Mapper
public interface RoleMapper {

    List<Role> getRoles();

    List<Role> getRolesByName(RoleSearch search);

    int getRolesByNameCount(RoleSearch search);

    Role getRole(String id);

    String[] getObjIds(@Param("dataType") int dataType, @Param("roleId") String roleId);

    int addRole(ModifyRole role);

    int updateRole(ModifyRole role);

    int addRolePermission(RolePermission rolePermission);

    int deleteRolePermission(SaveRolePermission srp);

    String[] getUserIds(String roleId);

    String[] getRoleIds(String userId);

    int addUserRole(UserRole userRole);

    int deleteUserRole( @Param("roleId") String roleId,@Param("userIdArr") String[] userIdArr);
}
