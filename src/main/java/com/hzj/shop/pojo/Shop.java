package com.hzj.shop.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("shop")
public class Shop {
    @TableId(value = "shopid",type = IdType.AUTO)
    private int shopid;

    private String photo;
    private String shopname;
    private String introduce;
    private String openinghours;
    private int status;
    private int bid;
    private String shoptype;
    private String address;
    private String recruit;
}
