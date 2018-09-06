package com.dpermi.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用于用户权限和授权
 * Created by val620@126.com on 2018/7/16
 */
@Data
public class PermissionObject implements Serializable {
    private String objectId;
    private int dataType;
    private String objectName;
    private String url;
    private String parentId;
//    private Date beginTime;
//    private Date endTime;
    private String roleId;
    private int order;
}
