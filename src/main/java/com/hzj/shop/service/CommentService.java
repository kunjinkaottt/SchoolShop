package com.hzj.shop.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzj.shop.common.Result;
import com.hzj.shop.mapper.CommentMapper;
import com.hzj.shop.pojo.Comment;
import com.hzj.shop.pojo.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;

    //商家-店铺管理-店铺评论
    public Result<?> MyShopCommentList(int currentPage,int shopid) {
        Page<CommentDto> commentDtoPage = commentMapper.CommentList(new Page<>(currentPage, 5), shopid);
        return Result.success(commentDtoPage);
    }

    //普通用户-店铺详情界面-店铺评论  默认置顶自己的评论
    public Result<?> MemberToShopDetailCommentList(int currentPage,int mid,int shopid) {
        Page<CommentDto> commentDtoPage = commentMapper.MemberToShopDetailCommentList(new Page<>(currentPage, 5), mid, shopid);
        return Result.success(commentDtoPage);
    }

    //商家、管理员-店铺详情界面-店铺评论    /   商家-店铺管理-店铺评论
    public Result<?> ToShopDetailCommentList(int currentPage,int shopid) {
        Page<CommentDto> commentDtoPage = commentMapper.CommentList(new Page<>(currentPage,5),shopid);
        return Result.success(commentDtoPage);
    }

    //普通用户-店铺详情界面 留言操作
    public Result<?> addComment(Comment comment){
        Comment result = commentMapper.selectOne(Wrappers.<Comment>lambdaQuery().eq(Comment::getMid, comment.getMid()).eq(Comment::getShopid, comment.getShopid()));
        if(result!=null){   //已有评论 》修改
            comment.setCid(result.getCid());
            commentMapper.updateById(comment);
        }else { //没有评论 》新增
            commentMapper.insert(comment);
        }
        return Result.success();
    }

    //普通用户-店铺详情界面-删除（我的评论） 操作
    public Result<?> DeleteMyComment(int cid){
        commentMapper.deleteById(cid);
        return Result.success();
    }

    //     普通用户-评论管理-我的评论
    public Result<?> MyCommentList(int currentPage,int pageSize,int mid,String search){
        Page<CommentDto> MyCommentList = commentMapper.MyCommentList(new Page<>(currentPage, pageSize), mid,search);
        return Result.success(MyCommentList);
    }

}
