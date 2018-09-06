package com.dpermi.domain;

import lombok.Data;

/**
 * Created by val620@126.com on 2018/7/17.
 */
@Data
public class Menu {
    private String menuId;
    private String parentId;
    private String menuName;
    private String menuCode;
    private String menuUrl;
    private int menuOrder;
    private String parent;

}
