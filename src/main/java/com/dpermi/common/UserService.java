package com.dpermi.common;

import com.dpermi.dao.RoleDao;
import com.dpermi.domain.MethodObject;
import com.dpermi.domain.PermissionObject;
import com.dpermi.domain.Role;
import com.dpermi.domain.User;
import com.dpermi.util.ConstantUtil;
import com.dpermi.util.CookieUtil;
import com.dpermi.util.SerializeUtil;
import com.dpermi.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by val620@126.com on 2017/12/7.
 */
@Component
public class UserService {
    @Autowired
    private JedisCluster jedis;
    private static JedisCluster staticJedis;

    @Autowired
    private RoleDao roleDao;
    private static RoleDao staticRoleDao;

    @PostConstruct
    public void init() {
        staticJedis = this.jedis;
        staticRoleDao = this.roleDao;
    }

    public static User getCurrentUser() {
        User user = (User) SessionUtil.getSession(ConstantUtil.CURRENT_USER);
        if (user == null) {
            String cellphone = CookieUtil.getCookie(ConstantUtil.CURRENT_USERNAME);
            byte[] userBytes = staticJedis.get((cellphone + ConstantUtil.CURRENT_USERNAME).getBytes());
            if (userBytes != null && userBytes.length > 0) {
                user = (User) SerializeUtil.deserialize(userBytes);
                SessionUtil.setSession(ConstantUtil.CURRENT_USER, user);
            }
        }
        return user;
    }

    public static String getCurrentUserId() {
        String userId = null;
        User user = getCurrentUser();
        if (user != null) userId = user.getUserId();
        return userId;
    }

    public static List<PermissionObject> getPermission() {
        List<PermissionObject> permissionObjects = removeExpiredPermission((List<PermissionObject>) SessionUtil.getSession(ConstantUtil.CURRENT_USER_PERMISSION));
        if (permissionObjects == null) {
            String cellphone = CookieUtil.getCookie(ConstantUtil.CURRENT_USERNAME);
            byte[] bytes = staticJedis.get((cellphone + ConstantUtil.CURRENT_USER_PERMISSION).getBytes());
            if (bytes != null && bytes.length > 0) {
                permissionObjects = removeExpiredPermission((List<PermissionObject>) SerializeUtil.deserialize(bytes));
                SessionUtil.setSession(ConstantUtil.CURRENT_USER_PERMISSION, permissionObjects);
            }
        }
        return permissionObjects;
    }

    public static List<PermissionObject> removeExpiredPermission(List<PermissionObject> permissionObjects) {
        if (permissionObjects == null || permissionObjects.size() == 0) return null;

        long seconds = new Date().getTime();
//        List<PermissionObject> validPermission=permissionObjects.parallelStream().filter(x->x.getEndTime()==null).collect(Collectors.toList());
//        List<PermissionObject> activePermission=permissionObjects.parallelStream().filter(x->x.getBeginTime()!=null && x.getEndTime()!=null
//                && x.getBeginTime().getTime() <=seconds && seconds<=x.getEndTime().getTime()).collect(Collectors.toList());
//        validPermission.addAll(activePermission);

        List<Role> roles = getRoles();
        List<String> roleIds = roles.parallelStream().filter(x -> x.getBeginTime() != null && x.getEndTime() != null
                && (seconds < x.getBeginTime().getTime() || seconds > x.getEndTime().getTime())).map(x -> x
                .getRoleId()).collect(Collectors.toList());//找出已过期角色
        List<PermissionObject> validPermission=permissionObjects;
        if(roleIds!=null && roleIds.size()>0) {
            validPermission = permissionObjects.parallelStream().filter(x -> !roleIds.contains(x
                    .getRoleId())).collect(Collectors.toList());
        }

        return validPermission;
    }


    public static boolean hasPermission(String url) {
        if(getCurrentUser().isAdmin()) return true;

        List<PermissionObject> permissionObjects = getPermission();

        if(permissionObjects==null || permissionObjects.size()==0) return false;

        return permissionObjects.stream().anyMatch(x -> x.getUrl()!=null && x.getUrl().equals(url.toLowerCase()));
    }

    //获取所有角色
    public static List<Role> getRoles() {
        List<Role> roles = (List<Role>) SessionUtil.getSession(ConstantUtil.ROLES);
        if (roles == null) {
            roles = staticRoleDao.getRoles();
            SessionUtil.setSession(ConstantUtil.ROLES, roles);
        }
        return roles;
    }

}
