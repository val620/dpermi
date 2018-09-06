package com.dpermi.domain;

import com.dpermi.util.ConstantUtil;
import com.dpermi.util.TypeConvert;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by val620@126.com on 2017/9/14.
 * 用户基本信息
 */
@Data
public class User implements Serializable {
    private String userId;            //用户id
    private String name;         //姓名
    private String email;         //邮箱
    private String cellphone;         //手机号
    private String password;         //密码
    private int gender;         //性别
    private Date birthday;         //出生日期
    private String birthdayDisplay;         //出生日期
    private int userState;         //用户状态
    private String qq;         //qq
    private String idCard;         //身份证号
    private String icon;         //头像
    private String introduction;         //个人介绍
    private String address;         //家庭地址

    private Boolean isDelete;         //是否已删除
    private boolean admin;//是否是管理员

    /**
     * 获取日期的显示
     *
     * @return
     */
    public String getBirthdayDisplay() {
        String date = "";
        try {
            date = birthday != null ? TypeConvert.DateToString(birthday, ConstantUtil.DATE_FORMAT) : "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 用于性别显示
     *
     * @return
     */
    public String getGenderDisplay() {
        if (gender == ConstantUtil.USER_MAN) {
            return "男";
        } else if (gender == ConstantUtil.USER_WOMAN) {
            return "女";
        } else {
            return "保密";
        }
    }

    public String getUserStateDisplay() {
        if (userState == ConstantUtil.USER_STATE_UNVALIDATE) {
            return "未验证";
        } else if (userState == ConstantUtil.USER_STATE_VALIDATE) {
            return "已验证";
        } else if (userState == ConstantUtil.USER_STATE_REJECT) {
            return "验证未通过";
        } else if (userState == ConstantUtil.USER_STATE_AUDIT) {
            return "审核中";
        }else {
            return "用户黑名单";
        }
    }

    public String getIcon(){
        return "/image/"+icon;
    }

}
