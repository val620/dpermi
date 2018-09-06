package com.dpermi.domain.search;

import lombok.Data;

/**
 * Created by val620@126.com on 2018/7/17.
 */
@Data
public class BaseSearch {
    private int pageIndex=1;
    private int pageSize=10;

    public int getStartIndex(){ return (pageIndex-1)*pageSize;}
}
