package com.dpermi.domain;

import lombok.Data;

import java.util.Date;

/**
 * Created by val620@126.com on 2018/7/16.
 */
@Data
public class ModifyUser {
    private String userId;
    private String name;
    private int gender;
    private String birthday;
    private Date newBirthday;
    private String idCard;
    private String icon;
    private String qq;
    private String email;
    private String address;
    private int userState;
    private String introduction;

}
