package com.hzj.shop.pojo.dto;

import lombok.Data;

//用于普通用户-应聘管理 012 和 商家-员工管理-应聘申请
@Data
public class MemberApplyDto {

    private int applyid;
    private int shopid;
    private String shopname;
    private String businessTruename;
    private String bPhone;
    private String address;
    private String applytime;
    private int applystate;

    private int mid;
    private String memberTruename;
    private int gender;
    private String mPhone;
}
