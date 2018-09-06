package com.dpermi.domain;

import lombok.Data;

/**
 * Created by val620@126.com on 2018/6/27.
 */
@Data
public class MethodObject {
    private String methodObjectId;
    private String methodId;
    private String methodName;
    private String url;
    private String parentId;
    private int orderNo;
}
