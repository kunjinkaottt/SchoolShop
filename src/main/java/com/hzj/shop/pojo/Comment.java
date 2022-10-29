package com.hzj.shop.pojo;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("comment")
public class Comment {
    @TableId(value = "cid",type = IdType.AUTO)
    private int cid;
    private int mid;
    private String comment;
    private int grade;
    private String ctime;
    private int shopid;
}
