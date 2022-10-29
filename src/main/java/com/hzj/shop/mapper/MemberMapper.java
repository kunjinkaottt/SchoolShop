package com.hzj.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzj.shop.pojo.dto.AllMembersDto;
import com.hzj.shop.pojo.Members;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository //代表持久层
public interface MemberMapper extends BaseMapper<Members> {
//    管理员-用户管理-普通用户
    IPage<AllMembersDto> allMembers(Page<Members> page, @Param("search") String search);



}
