package com.hzj.shop.pojo.dto;

import lombok.Data;

@Data
public class CommentDto {
    private int cid;
    private int shopid;
    private String shopname;
    private String comment;
    private int grade;
    private String ctime;

    //用于管理员、商家、普通用户-店铺详情 的 评论列表
    private int mid;
    private String username;
}
