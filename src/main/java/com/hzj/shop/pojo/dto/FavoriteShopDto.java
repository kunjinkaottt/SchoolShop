package com.hzj.shop.pojo.dto;

import lombok.Data;

@Data
public class FavoriteShopDto {
    private int fid;
    private int shopid;
    private String shopname;
    private String photo;
    private int bid;
    private String truename;
    private String phone;
    private String shoptype;
    private int status;
    private String address;

    private int isfavorite;
    private int mid;

}
