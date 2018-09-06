package com.dpermi.core;

import java.util.List;

/**
 * Created by val620@126.com on 2018/7/13.
 */
public class PageModel {
    public PageModel(){}

    public PageModel(List<?> rows,int count){
        this.rows=rows;
        this.count=count;
    }

    public List<?> rows;
    public int count;

}
