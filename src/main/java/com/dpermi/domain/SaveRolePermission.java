package com.dpermi.domain;

import lombok.Data;

/**
 * Created by val620@126.com on 2018/8/16.
 */
@Data
public class SaveRolePermission {
    private String roleId;
    private int dataType;
    private String objIds;
    private String[] objIdArr;
}
