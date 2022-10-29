package com.hzj.shop.controller;

import com.hzj.shop.common.Result;
import com.hzj.shop.pojo.Comment;
import com.hzj.shop.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CommentService commentService;

    //商家-店铺管理-店铺评论
    @GetMapping("/MyShopCommentList/{currentPage}/{shopid}")
    public Result<?> MyShopCommentList(@PathVariable("currentPage") Integer currentPage,
                                       @PathVariable("shopid") Integer shopid) {
        return commentService.MyShopCommentList(currentPage,shopid);
    }

    //普通用户-店铺详情界面-店铺评论  默认置顶自己的评论
    @GetMapping("/MemberToShopDetailCommentList/{currentPage}/{mid}/{shopid}")
    public Result<?> MemberToShopDetailCommentList(@PathVariable("currentPage")Integer currentPage,
                                                   @PathVariable("mid")Integer mid,
                                                   @PathVariable("shopid") Integer shopid) {
        return commentService.MemberToShopDetailCommentList(currentPage,mid,shopid);
    }

    //商家、管理员-店铺详情界面-店铺评论    /   商家-店铺管理-店铺评论
    @GetMapping("/CommentList/{commentscurrentPage}/{shopid}")
    public Result<?> ToShopDetailCommentList(@PathVariable("commentscurrentPage")Integer currentPage,
                                             @PathVariable("shopid") Integer shopid) {
        return commentService.ToShopDetailCommentList(currentPage,shopid);
    }

    //普通用户-店铺详情界面 留言操作
    @PostMapping("/addComment")
    public Result<?> addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    //普通用户-店铺详情界面-删除（我的评论） 操作
    @DeleteMapping("/DeleteMyComment/{cid}")
    public Result<?> DeleteMyComment(@PathVariable("cid")Integer cid){
        return commentService.DeleteMyComment(cid);
    }

//     普通用户-评论管理-我的评论
    @GetMapping(value={"/MyCommentList/{currentPage}/{pageSize}/{mid}","/MyCommentList/{currentPage}/{pageSize}/{mid}/{search}"})
    public Result<?> MyCommentList(@PathVariable("currentPage") Integer currentPage,
                                   @PathVariable("pageSize")Integer pageSize,
                                   @PathVariable("mid")Integer mid,
                                   @PathVariable(value = "search",required = false) String search){
        return commentService.MyCommentList(currentPage,pageSize,mid,search);
    }

}
