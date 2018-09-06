package com.dpermi.domain.search;

import lombok.Data;

/**
 * Created by val620@126.com on 2018/7/16.
 */
@Data
public class UserSearch extends BaseSearch{
    private String name;
    private String cellphone;
    private String roleId;
    private String[] userIds;
    private Boolean newObj;
}
