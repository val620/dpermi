package com.dpermi.controller;

import com.dpermi.core.JsonResult;
import com.dpermi.core.PageModel;
import com.dpermi.core.Permission;
import com.dpermi.dao.MenuDao;
import com.dpermi.domain.Menu;
import com.dpermi.domain.search.MenuSearch;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by val620@126.com on 2018/7/17.
 */
@Permission(name = "菜单",parentId = "")
@RequestMapping("/menu")
@RestController
public class MenuController {
    @Autowired
    private MenuDao menuDao;

    @ApiOperation(value = "根据id获取菜单")
    @RequestMapping(value = "/getMenu", method = RequestMethod.POST)
    public JsonResult getMenu(String id) throws Exception {
        Menu menu = menuDao.getMenu(id);
        JsonResult result=new JsonResult(200,"ok",menu);
        return result;
    }

    @Permission(name = "搜索",parentId = "menu")
    @ApiOperation(value = "根据条件获取菜单")
    @RequestMapping(value = "/getMenus", method = RequestMethod.POST)
    public JsonResult getMenus(MenuSearch search) throws Exception {
        PageModel model = menuDao.getMenusByName(search);
        JsonResult result=new JsonResult(200,"ok",model);
        return result;
    }

    @Permission(name = "加载菜单",parentId = "menu")
    @ApiOperation(value = "获取菜单树")
    @RequestMapping(value = "/getMenuTree", method = RequestMethod.POST)
    public JsonResult getMenuTree() throws Exception {
        String menuTree = menuDao.getMenuTree();
        JsonResult result=new JsonResult(200,"ok",menuTree);
        return result;
    }

    @Permission(name = "所有菜单",parentId = "menu")
    @ApiOperation(value = "获取所有菜单")
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public JsonResult getAll() throws Exception {
        List<Menu> menus = menuDao.getMenus();
        JsonResult result=new JsonResult(200,"ok",menus);
        return result;
    }

    @Permission(name = "添加",parentId = "menu")
    @ApiOperation(value = "添加菜单")
    @RequestMapping(value = "/addMenu", method = RequestMethod.POST)
    public JsonResult addMenu(Menu menu) throws Exception {
        int val = menuDao.addMenu(menu);
        JsonResult result=new JsonResult(200,"ok",val);
        return result;
    }

    @Permission(name = "更新",parentId = "menu")
    @ApiOperation(value = "更新菜单")
    @RequestMapping(value = "/updateMenu", method = RequestMethod.POST)
    public JsonResult updateMenu(Menu menu) throws Exception {
        int val = menuDao.updateMenu(menu);
        JsonResult result=new JsonResult(200,"ok",val);
        return result;
    }
}
