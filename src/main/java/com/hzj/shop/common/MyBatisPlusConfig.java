package com.hzj.shop.common;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//mybatis plus 的分页插件
@Configuration  //配置注解
@MapperScan("com.hzj.shop.mapper")  //有了这个注解之后不用在每个Mapper接口写Mapper注解 2边同时有会报错
public class MyBatisPlusConfig {

/*  旧版分页查询插件  mybatisplus3.2
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();//前提 是mybabtis plus依赖中的包
    }*/

//  新版分页查询插件  mybatisplus3.5
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));//数据库类型
        return interceptor;
    }
}
