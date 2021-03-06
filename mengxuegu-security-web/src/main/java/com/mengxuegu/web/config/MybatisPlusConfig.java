package com.mengxuegu.web.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @auther xukehan
 */
@EnableTransactionManagement  //开始事务管理
@MapperScan("com.mengxuegu.web.mapper") //扫描Mapper接口
@Configuration
public class MybatisPlusConfig {


    /**
     * mybatis-plus分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }


}
