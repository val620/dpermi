package com.dpermi.domain.search;

import lombok.Data;

/**
 * Created by val620@126.com on 2018/7/24.
 */
@Data
public class DataPermissionSearch extends BaseSearch {
     private int dataType;
     private String objectName;
     private String roleId;
     private boolean newObj;
     private String[] objIds;
}
