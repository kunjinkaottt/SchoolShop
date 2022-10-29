package com.hzj.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzj.shop.pojo.dto.MemberApplyDto;
import com.hzj.shop.pojo.Apply;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository //代表持久层
public interface ApplyMapper extends BaseMapper<Apply> {
//    普通用户-应聘管理
    Page<MemberApplyDto> MemberApplyList(Page<MemberApplyDto> page,
                                         @Param("applystate") Integer applystate,
                                         @Param("mid") Integer mid,
                                         @Param("search")String search);

//  商家-员工管理-应聘申请 获取shopid该店铺的所有应聘申请
    Page<MemberApplyDto> AllApplyList(Page<MemberApplyDto> page,@Param("shopid")Integer shopid);

    //商家-员工管理-应聘申请 不通过该mid的应聘申请
    @Update("update apply set applystate=2 where mid=#{mid} and shopid=#{shopid}")
    void Failapply(int mid, int shopid);

}
