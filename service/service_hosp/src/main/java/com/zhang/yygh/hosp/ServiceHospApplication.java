/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.hosp
 * @Author: 张栩垄
 * @CreateTime: 2023-08-06  12:55
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.hosp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@MapperScan(basePackages = "com.zhang.yygh.hosp.mapper")
@ComponentScan(basePackages = {"com.zhang"})
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zhang.yygh")
public class ServiceHospApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospApplication.class,args);
    }



}
