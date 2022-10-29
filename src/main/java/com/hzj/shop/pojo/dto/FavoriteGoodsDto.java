package com.hzj.shop.pojo.dto;

import lombok.Data;

@Data
public class FavoriteGoodsDto {
    private  int fid;
    private int shopid;
    private int gid;
    private String  shopname;
    private String img;
    private String goodsname;
    private float price;
    private String goodstype;
    private String address;

    private int isfavorite;
    private int mid;

}
