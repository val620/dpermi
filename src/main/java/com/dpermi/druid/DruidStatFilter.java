package com.dpermi.druid;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "exclusions", value = "*.gif,*.jpg,*.jpeg,*.png,*.bmp,*.swf,*.ico,*.woff,*.woff2,*.ttf,*.eot,*.js,*.css,*.html,*htm,/druid/*")//忽略资源
        })
public class DruidStatFilter extends WebStatFilter {

}
