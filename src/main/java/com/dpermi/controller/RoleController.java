package com.dpermi.controller;

import com.dpermi.core.JsonResult;
import com.dpermi.core.PageModel;
import com.dpermi.core.Permission;
import com.dpermi.dao.RoleDao;
import com.dpermi.domain.*;
import com.dpermi.domain.search.RoleSearch;
import com.dpermi.util.TypeConvert;
import com.dpermi.util.UUIDUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by val620@126.com on 2018/7/17.
 */
@Permission(name = "角色",parentId = "")
@RequestMapping("/role")
@RestController
public class RoleController {
    @Autowired
    private RoleDao roleDao;

    @ApiOperation(value = "根据id获取角色")
    @RequestMapping(value = "/getRole", method = RequestMethod.POST)
    public JsonResult getRole(String id) throws Exception {
        Role role = roleDao.getRole(id);
        JsonResult result=new JsonResult(200,"ok",role);
        return result;
    }

    @Permission(name = "搜索",parentId = "role")
    @ApiOperation(value = "获取角色")
    @RequestMapping(value = "/getRoles", method = RequestMethod.POST)
    public JsonResult getRoles(RoleSearch search) throws Exception {
        String userId=search.getUserId();
        String[] roleIds=null;
        if(userId!=null && userId.length()>0) {
            roleIds = roleDao.getRoleIds(userId);
            search.setRoleIds(roleIds);
        }
        PageModel model = null;
        //当没有已添加的用户角色时
        if (search.getNewObj()!=null && !search.getNewObj() && roleIds!=null  && roleIds.length == 0) {
            model = new PageModel(new ArrayList<User>(), 0);
        } else {
            model = roleDao.getRolesByName(search);
        }

        JsonResult result=new JsonResult(200,"ok",model);
        return result;
    }

    @Permission(name = "添加",parentId = "role")
    @ApiOperation(value = "添加角色")
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public JsonResult addRole(ModifyRole role) throws Exception {
        int val = roleDao.addRole(role);
        JsonResult result=new JsonResult(200,"ok",val);
        return result;
    }

    @Permission(name = "修改",parentId = "role")
    @ApiOperation(value = "更新角色")
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    public JsonResult updateRole(ModifyRole role) throws Exception {
        int val = roleDao.updateRole(role);
        JsonResult result=new JsonResult(200,"ok",val);
        return result;
    }

    @Permission(name = "保存数据权限-添加",parentId = "role")
    @ApiOperation(value = "添加角色权限")
    @RequestMapping(value = "/addRolePermission", method = RequestMethod.POST)
    public JsonResult addRolePermission(SaveRolePermission srp) throws Exception {
        String[] objIds = srp.getObjIds().split(",");
        if (objIds == null || objIds.length == 0) {
            return new JsonResult(200, "ok", null);
        }
        int val =addRolePermission(srp.getRoleId(),srp.getDataType(),objIds);
        JsonResult result = new JsonResult(200, "ok", val);

        return result;
    }

    @Permission(name = "保存数据权限-删除",parentId = "role")
    @ApiOperation(value = "删除角色权限")
    @RequestMapping(value = "/deleteRolePermission", method = RequestMethod.POST)
    public JsonResult deleteRolePermission(SaveRolePermission srp) throws Exception {
        String[] objIds = srp.getObjIds().split(",");
        if (objIds == null || objIds.length == 0) {
            return new JsonResult(200, "ok", null);
        }
        srp.setObjIdArr(objIds);
        int val = roleDao.deleteRolePermission(srp);
        JsonResult result = new JsonResult(200, "ok", val);
        return result;
    }

    @Permission(name = "保存用户-新增",parentId = "role")
    @ApiOperation(value = "添加用户")
    @RequestMapping(value = "/addUserRole", method = RequestMethod.POST)
    public JsonResult addUserRole(String roleId,String userIds) throws Exception {
        String[] ids = userIds.split(",");
        if (ids == null || ids.length == 0) {
            return new JsonResult(200, "ok", null);
        }
        UserRole ur;
        int val = 0;
        for (String id : ids) {
            ur = new UserRole();
            ur.setUserRoleId(UUIDUtil.GetUUID());
            ur.setRoleId(roleId);
            ur.setUserId(id);
            val += roleDao.addUserRole(ur);
        }

        JsonResult result = new JsonResult(200, "ok", val);
        return result;
    }

    @Permission(name = "保存用户-删除",parentId = "role")
    @ApiOperation(value = "删除用户")
    @RequestMapping(value = "/deleteUserRole", method = RequestMethod.POST)
    public JsonResult deleteUserRole(String roleId,String userIds) throws Exception {
        String[] ids = userIds.split(",");
        if (ids == null || ids.length == 0) {
            return new JsonResult(200, "ok", null);
        }

        int val = roleDao.deleteUserRole(roleId,ids);
        JsonResult result = new JsonResult(200, "ok", val);
        return result;
    }

    @Permission(name = "保存菜单权限",parentId = "role")
    @ApiOperation(value = "保存角色权限")
    @RequestMapping(value = "/saveRolePermission", method = RequestMethod.POST)
    public JsonResult saveRolePermission(SaveRolePermission srp) throws Exception {
        String[] objIds = srp.getObjIds().split(",");
        if (objIds == null || objIds.length == 0) {
            return new JsonResult(200, "ok", null);
        }
        int val = roleDao.deleteRolePermission(srp);
        val+=addRolePermission(srp.getRoleId(),0,objIds);
        JsonResult result = new JsonResult(200, "ok", val);
        return result;
    }

    private int addRolePermission(String roleId,int dataType,String[] objIds){
        int val=0;
        RolePermission rp;
        for (String objId : objIds) {
            rp = new RolePermission();
            rp.setRolePermissionId(UUIDUtil.GetUUID());
            rp.setRoleId(roleId);
            rp.setDataType(dataType);
            rp.setObjectId(objId);
            val += roleDao.addRolePermission(rp);
        }
        return val;
    }

}
