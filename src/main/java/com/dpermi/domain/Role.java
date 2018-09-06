package com.dpermi.domain;

import com.dpermi.util.TypeConvert;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by val620@126.com on 2018/7/5.
 */
@Data
public class Role implements Serializable {
    private String roleId;
    private String roleName;
    private String description;
    private Date beginTime;
    private Date endTime;
    private String beginTimeStr;
    private String endTimeStr;

    public String getBeginTimeStr(){
        if(beginTime==null) return "";
        return TypeConvert.DateToString(beginTime);
    }
    public String getEndTimeStr(){
        if(endTime==null) return "";
        return TypeConvert.DateToString(endTime);
    }
}
