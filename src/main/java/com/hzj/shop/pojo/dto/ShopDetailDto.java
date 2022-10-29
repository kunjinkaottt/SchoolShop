package com.hzj.shop.pojo.dto;

import lombok.Data;

@Data
public class ShopDetailDto {
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
}
