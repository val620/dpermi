package com.dpermi.mapper;

import com.dpermi.domain.*;
import com.dpermi.domain.search.UserSearch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by val620@126.com on 2017/9/14.
 */
@Mapper
public interface UserMapper {

    /**
     * 更新用户信息
     *
     * @param user
     */
    int updateUser(ModifyUser user);

    /**
     * 添加用户
     *
     * @param user
     */
    int addUser(User user);

    /**
     * 添加用户
     *
     * @param id
     */
    int deleteUser(String id);

    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    User findUser(String userId);

    List<User> getUsers(UserSearch search);

    int getUsersCount(UserSearch search);

    String checkCellphone(String cellphone);

    User login(@Param("cellphone") String cellphone, @Param("password") String password);

    /**
     * 修改密码
     *
     * @param userId
     * @param password
     * @return
     */
    int resetPassword(@Param("userId") String userId, @Param("password") String password);

    List<PermissionObject> getUserPermissionObjects(String userId);

    int deleteUserRole(@Param("userId") String userId, @Param("roleIds") String[] roleIds);


}

















