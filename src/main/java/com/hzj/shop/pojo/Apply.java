package com.hzj.shop.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("apply")
public class Apply {
    @TableId(value = "applyid",type = IdType.AUTO)
    private int applyid;
    private int mid;
    private int shopid;
    private String applytime;
    private int applystate;//0未处理 1通过 2不通过
    private int bid;

}
