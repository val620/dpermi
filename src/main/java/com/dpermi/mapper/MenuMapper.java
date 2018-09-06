package com.dpermi.mapper;

import com.dpermi.domain.Menu;
import com.dpermi.domain.search.MenuSearch;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by val620@126.com on 2018/7/17.
 */
@Mapper
public interface MenuMapper {
    List<Menu> getMenus();

    List<Menu> getMenusByName(MenuSearch search);

    int getMenusByNameCount(MenuSearch search);

    Menu getMenu(String id);

    int addMenu(Menu menu);

    int checkCode(String menuCode);

    int updateMenu(Menu menu);
}
