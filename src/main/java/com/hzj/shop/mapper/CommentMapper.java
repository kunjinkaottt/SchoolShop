package com.hzj.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzj.shop.pojo.dto.CommentDto;
import com.hzj.shop.pojo.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //代表持久层
public interface CommentMapper extends BaseMapper<Comment> {

//    普通用户-评论管理-我的评论
    Page<CommentDto> MyCommentList(Page<CommentDto> page , @Param("mid")Integer mid,@Param("search") String search);
//  商家、管理员-首页-店铺详情 店铺评论 /  商家-店铺管理-店铺评论
    Page<CommentDto> CommentList(Page<CommentDto> page,@Param("shopid") Integer shopid);

//   普通用户-店铺详情界面-店铺评论  默认置顶自己的评论
    Page<CommentDto> MemberToShopDetailCommentList(Page<CommentDto> page, int mid, int shopid);

//  商家-店铺管理-店铺评论
//    Page<CommentDto> MyShopCommentList(Page<CommentDto> page, @Param("shopid") Integer shopid);
}
