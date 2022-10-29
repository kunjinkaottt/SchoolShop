package com.hzj.shop.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("staff")
public class Staff {
    @TableId(value = "staffid",type = IdType.AUTO)
    private int staffid;
    private int mid;
    private int shopid;
    private int bid;
    private String createTime;
}
