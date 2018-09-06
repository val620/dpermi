package com.dpermi.core;

import com.alibaba.fastjson.JSONObject;
import com.dpermi.common.UserService;
import com.dpermi.domain.User;
import com.dpermi.util.ConstantUtil;
import com.dpermi.util.FileConfig;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by val620@126.com on 2017/10/18.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //debug环境下不用验证接口权限
        if (FileConfig.DEBUG) return true;

        String uri = request.getRequestURI().toLowerCase();
        String notLoginUrl = FileConfig.getConfig("dpermi.notLoginUrl");
        if(notLoginUrl.contains(uri)) return true;

        User user = UserService.getCurrentUser();
        boolean login = (user != null);

        boolean hasPermi=false;
        String loginUrl= FileConfig.getConfig("dpermi.loginUrl");
        if(login) {
            hasPermi=loginUrl.contains(uri) || user.isAdmin()||UserService.hasPermission(uri);//是否有权限
        }

        if(!hasPermi) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            JsonResult jr = new JsonResult(403, "登录过期或没有权限!", null);
            String json = JSONObject.toJSONString(jr);
            out.print(json);
            return false;
        }
        return true;
    }

}
