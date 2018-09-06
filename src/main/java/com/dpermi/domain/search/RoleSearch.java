package com.dpermi.domain.search;

import lombok.Data;

/**
 * Created by val620@126.com on 2018/7/17.
 */
@Data
public class RoleSearch extends BaseSearch {
    private String roleName;
    private String description;
    private String userId;
    private String[] roleIds;
    private Boolean newObj;
}
