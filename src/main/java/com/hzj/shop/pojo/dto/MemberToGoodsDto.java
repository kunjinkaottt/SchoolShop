package com.hzj.shop.pojo.dto;

import lombok.Data;

@Data
public class MemberToGoodsDto {
    private int gid;
    private int shopid;
    private String img;
    private String goodsname;
    private float price;
    private String goodstype;
    private int isdelete;

    private int fid;
    private int isfavorite;
}
