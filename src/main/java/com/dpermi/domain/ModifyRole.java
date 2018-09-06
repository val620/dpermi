package com.dpermi.domain;

import lombok.Data;

/**
 * Created by val620@126.com on 2018/7/17.
 */
@Data
public class ModifyRole {
    private String roleId;
    private String roleName;
    private String description;
    private String beginTime;
    private String endTime;
}
