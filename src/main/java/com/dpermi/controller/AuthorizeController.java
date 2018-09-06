package com.dpermi.controller;


import com.dpermi.common.UserService;

import com.dpermi.core.JsonResult;
import com.dpermi.core.PageModel;
import com.dpermi.core.Permission;
import com.dpermi.dao.PermissionDao;
import com.dpermi.dao.RoleDao;
import com.dpermi.domain.PermissionObject;

import com.dpermi.domain.TreeNode;
import com.dpermi.domain.search.DataPermissionSearch;
import com.dpermi.service.IAuthorize;
import com.dpermi.util.BeanUtil;
import com.dpermi.util.JsonUtil;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by val620@126.com on 2018/7/18.
 */
@Permission(name = "权限",parentId = "")
@RequestMapping("/auth")
@RestController
public class AuthorizeController {
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RoleDao roleDao;

    @Permission(name = "菜单权限",parentId = "role")
    @ApiOperation(value = "获取菜单授权权限")
    @RequestMapping(value = "/MenuPermission", method = RequestMethod.GET)
    public String getMenuPermission(String roleId) throws Exception {
        List<TreeNode> nodes = permissionDao.getMenuPermission(roleId);
//        DataPermission data = new DataPermission();
//        List<TreeNode> permiNodes = data.getAuthorizedData(nodes, TreeNode.class);
//        JsonResult result = new JsonResult(200, "ok", permiNodes);
        String result = JsonUtil.toJsonString(nodes);

        return result;
    }

    @ApiOperation(value = "获取方法授权权限")
    @RequestMapping(value = "/MethodPermission", method = RequestMethod.POST)
    public JsonResult getMethodPermission(String url) throws Exception {
        boolean permi = UserService.hasPermission(url);
        JsonResult result = new JsonResult(200, "ok", permi);
        return result;
    }

    @Permission(name = "数据权限",parentId = "role")
    @ApiOperation(value = "获取数据授权权限")
    @RequestMapping(value = "/DataPermission", method = RequestMethod.POST)
    //要以可配置方式加载方法，每个dataType对应一个方法
    public JsonResult getDataPermission(DataPermissionSearch search) throws Exception {
//        ClassModel classModel = BeanUtil.getBean(String.valueOf(search.getDataType()), ClassModel.class);
//        Class<?> cAuth = Class.forName(classModel.getClassName());//反射得到实现类
//        IAuthorize authorize = (IAuthorize) cAuth.newInstance();//接口实例化
//        IAuthorize authorize = (IAuthorize) BeanUtil.getBean(String.valueOf(search.getDataType()));//接口实例化
        int dataType = search.getDataType();
        if (dataType < 0) return new JsonResult(200, "ok", null);

        String[] objIds = roleDao.getObjIds(dataType, search.getRoleId());
        search.setObjIds(objIds);
        PageModel model = null;
        if (!search.isNewObj() && objIds.length == 0) {
            model = new PageModel(new ArrayList<PermissionObject>(), 0);
        } else {
            IAuthorize authorize = BeanUtil.getBean(String.valueOf(dataType), IAuthorize.class);
            model = authorize.getDataObjects(search);//执行实例方法
        }
        JsonResult result = new JsonResult(200, "ok", model);
        return result;
    }


}
