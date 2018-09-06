package com.dpermi.domain;

import lombok.Data;

/**
 * Created by val620@126.com on 2018/8/16.
 */
@Data
public class RolePermission {
    private String rolePermissionId;
    private String roleId;
    private String objectId;
    private int dataType;
}
