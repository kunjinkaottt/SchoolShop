package com.hzj.shop.pojo.dto;

import lombok.Data;

@Data
public class MembersWithShopDto {
    private int shopid;
    private String photo;
    private String shopname;

    private String introduce;
    private String openinghours;

    private String phone;//business
    private String shoptype;
    private int status;

    private int bid;

    private String address;

    private String recruit;
    private String truename;//business

    private float avgGrade;
    private int fid;
    private int isfavorite;

//    新——卡片样式 查询收藏量
    private int fcount;
}
