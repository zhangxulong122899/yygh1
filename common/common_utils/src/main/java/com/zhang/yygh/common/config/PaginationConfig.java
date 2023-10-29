/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.common.config
 * @Author: 张栩垄
 * @CreateTime: 2023-08-07  20:18
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaginationConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
