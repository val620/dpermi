package com.dpermi.dao;


import com.dpermi.core.PageModel;
import com.dpermi.domain.*;
import com.dpermi.domain.search.RoleSearch;
import com.dpermi.mapper.RoleMapper;
import com.dpermi.util.ConstantUtil;
import com.dpermi.util.SessionUtil;
import com.dpermi.util.TypeConvert;
import com.dpermi.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Created by val620@126.com on 2018/7/5.
 */
@Component
public class RoleDao {
    @Autowired
    private RoleMapper roleMapper;

    public List<Role> getRoles() {
        return roleMapper.getRoles();
    }

    public PageModel getRolesByName(RoleSearch search) {
        List<Role> roles = roleMapper.getRolesByName(search);
        int count = roleMapper.getRolesByNameCount(search);
        PageModel model = new PageModel(roles, count);
        return model;
    }

    public Role getRole(String id) {
        return roleMapper.getRole(id);
    }

    public String[] getObjIds(int dataType, String roleId) {
        return roleMapper.getObjIds(dataType, roleId);
    }

    public int addRole(ModifyRole role) {
        role.setRoleId(UUIDUtil.GetUUID());
        return roleMapper.addRole(role);
    }

    public int updateRole(ModifyRole role) {
        List<Role> roles = (List<Role>) SessionUtil.getSession(ConstantUtil.ROLES);
        if(roles!=null){
            Optional<Role> opt = roles.parallelStream().filter(x->x.getRoleId().equals(role.getRoleId())).findFirst();
            if(opt.isPresent()) {
                Role r=opt.get();
                if(r.getBeginTime()!=null && !r.getBeginTime().equals("") &&r.getEndTime()!=null && !r.getEndTime().equals("")) {
                    r.setBeginTime(TypeConvert.StringToDate(role.getBeginTime(),"yyyy-MM-dd HH:mm:ss"));
                    r.setEndTime(TypeConvert.StringToDate(role.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
                }
                else {
                    r.setBeginTime(null);
                    r.setEndTime(null);
                }
                SessionUtil.setSession(ConstantUtil.ROLES, roles);//即时更新缓存的角色时间
            }
        }
        return roleMapper.updateRole(role);
    }

    public int addRolePermission(RolePermission rolePermission) {
        return roleMapper.addRolePermission(rolePermission);
    }

    public int deleteRolePermission(SaveRolePermission srp) {
        return roleMapper.deleteRolePermission(srp);
    }

    public String[] getUserIds(String roleId) {
        return roleMapper.getUserIds(roleId);
    }

    public String[] getRoleIds(String userId) {
        return roleMapper.getRoleIds(userId);
    }

    public int addUserRole(UserRole userRole) {
        return roleMapper.addUserRole(userRole);
    }

    public int deleteUserRole(String roleId, String[] userIdArr) {
        return roleMapper.deleteUserRole(roleId, userIdArr);
    }
}
