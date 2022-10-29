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
@TableName("business")
public class Business {
    @TableId(value = "bid",type = IdType.AUTO)
    private int bid;
    private String truename;
    private int gender; //1 男 2女
    private String phone;  //11位varchar
    private String password;    //最多16位
    private int shopid;
    private int state;  //0未审核 1通过 2不通过

//    private String token;//登录token

}
