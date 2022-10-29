package com.hzj.shop;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component      //加入ioc容器中
public class MyMetaObjectHandler implements MetaObjectHandler {
//    插入时的填充策略
    @Override
    public void insertFill(MetaObject metaObject) {
//        this.setFieldValByName("ctime",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
