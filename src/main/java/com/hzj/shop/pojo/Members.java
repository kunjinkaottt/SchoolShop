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
@TableName("members")
public class Members {
    @TableId(value = "mid",type = IdType.AUTO)
    private int mid;
    private String truename;
    private int gender;
    private String username;
    private String phone; //11位varchar
    private String password;
}
