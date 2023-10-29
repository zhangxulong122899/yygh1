/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.client
 * @Author: 张栩垄
 * @CreateTime: 2023-09-24  20:38
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-cmn")//被调用方在注册中心的服务名称
public interface DictFeignClient {

    //根据医院所属的省市区编号获取医院所在的省市区
    @GetMapping("/admin/cmn/{value}")
    public String getNameByValue(@PathVariable(value = "value") Long value);

    //根据医院的登记编号获取医院等级信息
    @GetMapping("/admin/cmn/{dictCode}/{value}")
    public String getNameByDictCode(@PathVariable("dictCode")String dictCode ,@PathVariable(value = "value") Long value);

}
