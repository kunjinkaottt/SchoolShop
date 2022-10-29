package com.hzj.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzj.shop.pojo.dto.BusinessDto;
import com.hzj.shop.pojo.Business;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository //代表持久层
public interface BusinessMapper extends BaseMapper<Business> {
//管理员-审核管理-未审核的商家申请
    Page<BusinessDto> ExamineBusinessList(Page<BusinessDto> page, @Param("search") String search);
//管理员-审核管理-不通过的商家申请
    Page<BusinessDto> FailBusinessList(Page<BusinessDto> page,@Param("search") String search);
//管理员-用户管理-商家账号
    Page<BusinessDto> AllBusinessList(Page<BusinessDto> page,@Param("search")String search);

//    管理员-审核管理-未审核的商家申请-点击 不通过 按钮
    @Update("update business set state=2 where bid=#{bid} and shopid=#{shopid}")
    int FailBusiness(int bid,int shopid);

//    管理员-审核管理-不通过的商家申请-点击 删除 按钮
    @Delete("delete from business where bid=#{bid} and state=2")
    int DeleteFailBusiness(int bid);

}
