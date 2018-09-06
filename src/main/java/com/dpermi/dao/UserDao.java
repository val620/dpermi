package com.dpermi.dao;

import com.dpermi.core.PageModel;
import com.dpermi.domain.*;
import com.dpermi.domain.search.UserSearch;
import com.dpermi.mapper.MenuMapper;
import com.dpermi.mapper.MethodObjectMapper;
import com.dpermi.mapper.UserMapper;
import com.dpermi.util.CryptographyUtil;
import com.dpermi.util.TypeConvert;
import com.dpermi.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by val620@126.com on 2018/7/12.
 */
@Component
public class UserDao {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MethodObjectMapper methodObjectMapper;
    @Autowired
    private MenuMapper menuMapper;


    public User login(String cellphone, String password) {
        password = encryptPassword(password);
        return userMapper.login(cellphone, password);
    }

    private String encryptPassword(String password) {
        String md5Str = CryptographyUtil.getMD5Encrypt(password);
        String aesStr = CryptographyUtil.getAESEncrypt(md5Str);
        return aesStr;
    }

    public String checkCellphone(String cellphone) throws Exception {
        return userMapper.checkCellphone(cellphone);
    }

    public List<PermissionObject> getUserPermissionObjects(String userId) {
        List<PermissionObject> permissionObjects = userMapper.getUserPermissionObjects(userId);
        List<PermissionObject> menuList = permissionObjects.parallelStream().filter(x -> x.getDataType() == 0).collect(Collectors.toList());
        if (menuList == null || menuList.size() == 0) return permissionObjects;

        List<MethodObject> methodObjects = methodObjectMapper.getMethodObjects();
        List<Menu> menus = menuMapper.getMenus();

        //找出所有MethodObject
        menuList.forEach(x -> {
            Optional<MethodObject> opt = methodObjects.parallelStream().filter(m -> m.getMethodObjectId().equals(x.getObjectId())).findFirst();
            if (opt.isPresent()) {//MethodObject
                MethodObject obj = opt.get();
                x.setUrl(obj.getUrl());

                //勾选子菜单，父菜单也要有权限
                Optional<Menu> optMenu = menus.parallelStream().filter(m -> m.getMenuCode() != null && m.getMenuCode().equals(obj.getParentId())).findFirst();
                if (optMenu.isPresent()) {
                    Menu menu = optMenu.get();
                    if (!permissionObjects.parallelStream().anyMatch(m -> m.getObjectId().equals(menu.getMenuId()))) {
                        PermissionObject pobj = menuToPermissionObject(menu);
                        permissionObjects.add(pobj);
                    }
                }
            } else if(menus.parallelStream().anyMatch(m->m.getMenuId().equals(x.getObjectId()))) {//Menu
                parents = new ArrayList<>();
                getParents(menus, x.getObjectId());
                if (parents.size() > 0) {
                    parents.forEach(m -> {
                        if (!permissionObjects.parallelStream().anyMatch(p -> p.getObjectId().equals(m.getMenuId()))) {
                            PermissionObject pobj = menuToPermissionObject(m);
                            permissionObjects.add(pobj);
                        }
                    });
                }
            }
        });

        return permissionObjects;
    }

    private List<Menu> parents;

    //获取菜单所有祖先菜单
    private void getParents(List<Menu> menus, String menuId) {
        Menu menu = menus.parallelStream().filter(x -> x.getMenuId().equals(menuId)).findFirst().get();
        String parentId = menu.getParentId();
        if (parentId != null && !parentId.equals("")) {
            Menu parent = menus.parallelStream().filter(x -> x.getMenuId().equals(parentId)).findFirst().get();
            parents.add(parent);
            getParents(menus, parentId);
        }

    }

    private PermissionObject menuToPermissionObject(Menu menu) {
        PermissionObject obj = new PermissionObject();
        obj.setUrl(menu.getMenuUrl());
        obj.setDataType(0);
        obj.setObjectId(menu.getMenuId());
        obj.setObjectName(menu.getMenuName());
        return obj;
    }

    /**
     * 更新用户信息
     *
     * @param user
     */
    public int updateUser(ModifyUser user) throws Exception {
        if (user.getBirthday() != null && user.getBirthday().length() > 0) {
            Date date = TypeConvert.StringToDate(user.getBirthday());
            user.setNewBirthday(date);
        }
        return userMapper.updateUser(user);
    }

    /**
     * 添加用户
     *
     * @param user
     */
    public int addUser(User user) {
//        User user = new User();
        user.setUserId(UUIDUtil.GetUUID());
        user.setCellphone(user.getCellphone());
//        password = encryptPassword(password);
        user.setPassword(encryptPassword(user.getPassword()));
        return userMapper.addUser(user);
    }

    /**
     * 添加用户
     *
     * @param id
     */
    public int deleteUser(String id) {
        return userMapper.deleteUser(id);
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    public User findUser(String userId) {
        return userMapper.findUser(userId);
    }

    public PageModel getUsers(UserSearch search) {
        List<User> users = userMapper.getUsers(search);
        int count = userMapper.getUsersCount(search);
        PageModel model = new PageModel(users, count);
        return model;
    }

    /**
     * 修改密码
     *
     * @param userId  password
     */
    public int resetPassword(String userId,String password) throws Exception {
        password=encryptPassword(password);
        return userMapper.resetPassword(userId,password);
    }

    public int deleteUserRole(String userId, String[] roleIds) {
        return userMapper.deleteUserRole(userId, roleIds);
    }
}
