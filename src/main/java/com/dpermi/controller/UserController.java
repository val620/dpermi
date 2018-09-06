package com.dpermi.controller;

import com.dpermi.common.UserService;
import com.dpermi.core.JsonResult;
import com.dpermi.core.PageModel;
import com.dpermi.core.Permission;
import com.dpermi.dao.MethodObjectDao;
import com.dpermi.dao.RoleDao;
import com.dpermi.dao.UserDao;
import com.dpermi.domain.ModifyUser;
import com.dpermi.domain.PermissionObject;
import com.dpermi.domain.User;
import com.dpermi.domain.UserRole;
import com.dpermi.domain.search.UserSearch;
import com.dpermi.util.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by val620@126.com on 2018/7/12.
 */
@Permission(name = "用户",parentId = "")
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private MethodObjectDao methodObjectDao;
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private JedisCluster jedis;

    @Permission(name = "添加",parentId = "user")
    @ApiOperation(value = "添加用户")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public JsonResult addUser(User user) throws Exception {
       int val = userDao.addUser(user);
       JsonResult result=new JsonResult(200,"ok",val);
        return result;
    }

    @ApiOperation(value = "根据id获取用户")
    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    public JsonResult getUser(String id) throws Exception {
        User user = userDao.findUser(id);
        JsonResult result=new JsonResult(200,"ok",user);
        return result;
    }

    @ApiOperation(value = "获取当前用户")
    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.POST)
    public JsonResult getCurrentUser() throws Exception {
        User user = UserService.getCurrentUser();
        JsonResult result=new JsonResult(200,"ok",user);
        return result;
    }

    @Permission(name = "搜索",parentId = "user")
    @ApiOperation(value = "获取用户")
    @RequestMapping(value = "/getUsers", method = RequestMethod.POST)
    public JsonResult getUsers(UserSearch search) throws Exception {
        String roleId=search.getRoleId();
        String[] userIds=null;
        if(roleId!=null && roleId.length()>0) {
            userIds = roleDao.getUserIds(roleId);
            search.setUserIds(userIds);
        }
        PageModel model = null;
        //当没有已添加的角色用户时
        if (search.getNewObj()!=null && !search.getNewObj() && userIds!=null  && userIds.length == 0) {
            model = new PageModel(new ArrayList<User>(), 0);
        } else {
            model = userDao.getUsers(search);
        }
        JsonResult result=new JsonResult(200,"ok",model);
        return result;
    }

    @Permission(name = "编辑",parentId = "user")
    @ApiOperation(value = "更新用户")
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public JsonResult updateUser(ModifyUser user) throws Exception {
        int val = userDao.updateUser(user);
        JsonResult result=new JsonResult(200,"ok",val);
        return result;
    }

    @ApiOperation(value = "删除用户")
    @RequestMapping(value = "/deleteUser", method = RequestMethod.PUT)
    public JsonResult deleteUser(String id) throws Exception {
        int val = userDao.deleteUser(id);
        JsonResult result=new JsonResult(200,"ok",val);
        return result;
    }

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResult login(String cellphone, String password) throws Exception{

        JsonResult result;
        String userId = userDao.checkCellphone(cellphone);
        if (userId == null || userId.equals("")) {
            return new JsonResult(309, "手机号还没注册", null);
        }
        User user = userDao.login(cellphone,password);
        if (user == null) {
            result = new JsonResult(310, "密码输入有误或不允许登录", user);
        } else {
            SessionUtil.setSession(ConstantUtil.CURRENT_USER, user);
            CookieUtil.createCookie(ConstantUtil.CURRENT_USERNAME, cellphone, 30 * 24 * 60 * 60);

            jedis.setex((cellphone + ConstantUtil.CURRENT_USERNAME).getBytes(), 3 * 60 * 60, SerializeUtil.serialize
                    (user));
            //缓存当前用户权限
            List<PermissionObject> permissionObjects=userDao.getUserPermissionObjects(userId);
            permissionObjects= UserService.removeExpiredPermission(permissionObjects);
            SessionUtil.setSession(ConstantUtil.CURRENT_USER_PERMISSION, permissionObjects);
            jedis.setex((cellphone + ConstantUtil.CURRENT_USER_PERMISSION).getBytes(), 3 * 60 * 60, SerializeUtil
                    .serialize(permissionObjects));


            result = new JsonResult(200, "ok", null);
        }
        return result;
    }

    @ApiOperation(value = "注销")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public JsonResult logout() throws Exception{
        String currentUserId = UserService.getCurrentUserId();
        SessionUtil.delSession(currentUserId);
        SessionUtil.delSession(ConstantUtil.CURRENT_USER);
        String cellphone = CookieUtil.getCookie(ConstantUtil.CURRENT_USERNAME);
        jedis.del((cellphone + ConstantUtil.CURRENT_USERNAME).getBytes());

        //删除权限缓存
        SessionUtil.delSession(ConstantUtil.CURRENT_USER_PERMISSION);
        jedis.del((cellphone + ConstantUtil.CURRENT_USER_PERMISSION).getBytes());

        JsonResult result = new JsonResult(200, "ok", null);
        return result;
    }

    @ApiOperation(value = "修改密码")
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public JsonResult resetPassword(String password) throws Exception{
        String userId=UserService.getCurrentUserId();
        if(userId==null || userId.equals("")) return  new JsonResult(403, "请先登录，再修改密码", null);
        int count = userDao.resetPassword(userId, password);
        JsonResult result = new JsonResult(200, "ok", count);
        return result;
    }

    @Permission(name = "保存角色-新增",parentId = "user")
    @ApiOperation(value = "添加用户角色")
    @RequestMapping(value = "/addUserRole", method = RequestMethod.POST)
    public JsonResult addUserRole(String userId,String roleIds) throws Exception {
        String[] ids = roleIds.split(",");
        if (ids == null || ids.length == 0) {
            return new JsonResult(200, "ok", null);
        }
        UserRole ur;
        int val = 0;
        for (String id : ids) {
            ur = new UserRole();
            ur.setUserRoleId(UUIDUtil.GetUUID());
            ur.setRoleId(id);
            ur.setUserId(userId);
            val += roleDao.addUserRole(ur);
        }

        JsonResult result = new JsonResult(200, "ok", val);
        return result;
    }

    @Permission(name = "保存角色-删除",parentId = "user")
    @ApiOperation(value = "删除用户角色")
    @RequestMapping(value = "/deleteUserRole", method = RequestMethod.POST)
    public JsonResult deleteUserRole(String userId,String roleIds) throws Exception {
        String[] ids = roleIds.split(",");
        if (ids == null || ids.length == 0) {
            return new JsonResult(200, "ok", null);
        }

        int val = userDao.deleteUserRole(userId,ids);
        JsonResult result = new JsonResult(200, "ok", val);
        return result;
    }

}
