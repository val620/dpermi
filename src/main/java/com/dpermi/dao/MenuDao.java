package com.dpermi.dao;

import com.dpermi.common.DataPermission;
import com.dpermi.core.PageModel;
import com.dpermi.domain.Menu;
import com.dpermi.domain.search.MenuSearch;
import com.dpermi.mapper.MenuMapper;
import com.dpermi.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by val620@126.com on 2018/7/17.
 */
@Component
public class MenuDao {
    @Autowired
    private MenuMapper menuMapper;

    private List<Menu> menuTree;
    private StringBuilder sb;

    public String getMenuTree() throws Exception{
        List<Menu> menus = getMenus();

        DataPermission data = new DataPermission();
        List<Menu> permiMenus = data.getAuthorizedData(menus, Menu.class);

        List<Menu> root = permiMenus.parallelStream().filter(x -> x.getParentId() == null || x.getParentId().equals("")).collect(Collectors.toList());

        if (root != null && root.size() > 0) {
            sb = new StringBuilder("<ul class=\"nav nav-list\">");
            menuTree = new ArrayList<>();
            root.forEach(x -> {
                appendMenu(permiMenus, x);
            });
            sb.append("</ul>");
        }

        return sb.toString();
    }

    private void buildMenuTree(List<Menu> menus, String menuId) {
        List<Menu> children = menus.parallelStream().filter(x -> x.getParentId() != null && x.getParentId().equals(menuId)).collect(Collectors.toList());

        if (children == null || children.size() == 0) {
            sb.append(" </a>");
            return;
        }
        sb.append(" <b class=\"arrow icon-angle-down\" onclick=\"openOrClose(this);\" ></b></a>");

        sb.append("<ul class=\"submenu\" >");
        children.forEach(x -> {
            appendMenu(menus, x);
        });
        sb.append("</ul>");
    }

    private void appendMenu(List<Menu> menus, Menu menu) {
        menuTree.add(menu);
        String url = menu.getMenuUrl(), name = menu.getMenuName();
        String openTab = String.format("openTab('%s','%s','%s');", menu.getMenuCode(), url, name);

        sb.append("<li>");
        if (url != null && url.length() > 0) {
            sb.append(String.format("<a href=\"#\" onclick=\"%s\" class=\"dropdown-toggle\">", openTab));
        } else {
            sb.append("<a href=\"#\" >");
        }
        sb.append(String.format("<span class=\"menu-text\"> %s </span>", name));
        buildMenuTree(menus, menu.getMenuId());
        sb.append("</li>");
    }

    public List<Menu> getMenus() {
        return menuMapper.getMenus();
    }

    public PageModel getMenusByName(MenuSearch search) {
        List<Menu> menus = menuMapper.getMenusByName(search);

        if (menus != null && menus.size() > 0) {
            List<Menu> all = menuMapper.getMenus();
            menus.forEach(x -> {
                String parentId = x.getParentId();
                if (parentId != null && !parentId.equals("")) {
                    Menu menu = all.parallelStream().filter(m -> m.getMenuId().equals(parentId)).findFirst().get();
                    if (menu != null) {
                        x.setParent(menu.getMenuName());
                    }
                }
            });
        }
        int count = menuMapper.getMenusByNameCount(search);
        PageModel model = new PageModel(menus, count);
        return model;
    }

    public Menu getMenu(String id) {
        return menuMapper.getMenu(id);
    }

    public int addMenu(Menu menu) throws Exception {
        boolean exist = checkCode(menu.getMenuCode());
        if (exist) throw new Exception("308;MenuCode已存在，请重新输入。");

        menu.setMenuId(UUIDUtil.GetUUID());
        return menuMapper.addMenu(menu);
    }

    public boolean checkCode(String menuCode) {
        int count = menuMapper.checkCode(menuCode);
        return count > 0;
    }

    public int updateMenu(Menu menu) {
        return menuMapper.updateMenu(menu);
    }
}
