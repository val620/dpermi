package com.dpermi.domain;

import lombok.Data;

/**
 * Created by val620@126.com on 2018/7/2.
 */
@Data
public class UserPermission {
    private String userPermissionId;
    private String userId;
    private String objectId;
    private int dataType;
}
