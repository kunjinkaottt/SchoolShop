package com.hzj.shop.controller;

import com.hzj.shop.common.Result;
import com.hzj.shop.mapper.ApplyMapper;
import com.hzj.shop.pojo.Apply;
import com.hzj.shop.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApplyController {

    @Autowired
    ApplyMapper applyMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ApplyService applyService;

    //商家-员工管理-应聘申请 获取shopid该店铺的所有应聘申请
    @GetMapping("/AllApplyList/{currentPage}/{pageSize}/{shopid}")
    public Result<?> AllApplyList(@PathVariable("currentPage")Integer currentPage,
                                  @PathVariable("pageSize")Integer pageSize,
                                  @PathVariable("shopid")Integer shopid){
        return applyService.AllApplyList(currentPage,pageSize,shopid);
    }

    //商家-员工管理-应聘申请 通过该mid的应聘申请
    @PutMapping("/Passapply/{mid}/{shopid}/{bid}")
    public Result<?> Passapply(@PathVariable("mid")Integer mid,
                               @PathVariable("shopid")Integer shopid,
                               @PathVariable("bid")Integer bid){
        return applyService.Passapply(mid,shopid,bid);
    }

    //商家-员工管理-应聘申请 不通过该mid的应聘申请
    @PutMapping("/Failapply/{mid}/{shopid}")
    public Result<?> Failapply(@PathVariable("mid")Integer mid,
                               @PathVariable("shopid")Integer shopid){
        return applyService.Failapply(mid,shopid);
    }

    //普通用户-店铺详情页面-申请应聘
    @PostMapping("/Apply")
    public Result<?> Apply(@RequestBody Apply apply){
        return applyService.Apply(apply);
    }

    //普通用户-应聘管理 ——传的参包括申请状态 applystate 只有0 1 2 处理中 通过 不通过
    @GetMapping(value = {"/MemberApplyList/{currentPage}/{pageSize}/{applystate}/{mid}","/MemberApplyList/{currentPage}/{pageSize}/{applystate}/{mid}/{search}"})
    public Result<?> MemberApplyList(@PathVariable("currentPage") Integer currentPage,    //默认值 第一页   （pageNum第几页）
                                @PathVariable("pageSize") Integer pageSize,
                                @PathVariable("applystate") Integer applystate,
                                @PathVariable("mid") Integer mid,
                                @PathVariable(value = "search",required = false) String search ){//默认值 每页10条记录（ pageSize每页多少条记录）
        return applyService.MemberApplyList(currentPage,pageSize,applystate,mid,search);
    }

//  普通用户-应聘管理-不通过的申请 点击 删除 按钮
    @DeleteMapping("/DeleteFailApply/{applyid}")
    public Result<?> DeleteFailApply(@PathVariable("applyid")Integer applyid){
        return applyService.DeleteFailApply(applyid);
    }

//  普通用户-应聘管理-处理中的申请 点击 取消申请 按钮
    @DeleteMapping("/CancelApply/{applyid}")
    public Result<?> cancelApply(@PathVariable("applyid")Integer applyid){
        return applyService.cancelApply(applyid);    //removeById是mybatisplus提供的方法，前提是业务类必须继承basemapper  返回类型是Boolean值
    }


}
