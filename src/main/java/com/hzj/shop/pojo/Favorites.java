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
@TableName("favorites")
public class Favorites {
    @TableId(value = "fid",type = IdType.AUTO)
    private int fid;
    private int mid;
    private int shopid;
    private int gid;
    private int isfavorite;
}
