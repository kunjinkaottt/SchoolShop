package com.hzj.shop.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("goods")
public class Goods {
    @TableId(value = "gid",type = IdType.AUTO)
    private int gid;
    private int shopid;
    private String img;
    private String goodsname;
    private float price;
    private String goodstype;
    private int isdelete;

}
