package com.zhang.yygh.cmn.controller;


import com.zhang.yygh.cmn.service.DictService;
import com.zhang.yygh.common.result.R;
import com.zhang.yygh.model.cmn.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 组织架构表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-08-27
 */
@RestController
@RequestMapping("/admin/cmn")
// @CrossOrigin //解决跨域问题
public class DictController {

    @Autowired
    private DictService dictService;

    @GetMapping("/childList/{pid}")
    public R getChildListByPid(@PathVariable Long pid){
        List<Dict> list = dictService.getChildListByPid(pid);
        return R.ok().data("items",list);
    }

    @GetMapping("/download")
    public void downLoad(HttpServletResponse response) throws IOException {
        dictService.download(response);
    }

    @PostMapping("/upload")
    public R upload(MultipartFile file) throws IOException {
        dictService.upload(file);

        return R.ok();
    }

    //根据医院所属的省市区编号获取医院所在的省市区
    @GetMapping("/{value}")
    public String getNameByValue(@PathVariable(value = "value") Long value){
        String nameByValue = dictService.getNameByValue(value);
        return nameByValue;
    }

    //根据医院的登记编号获取医院等级信息
    @GetMapping("/{dictCode}/{value}")
    public String getNameByDictCode(@PathVariable("dictCode")String dictCode ,@PathVariable(value = "value") Long value){

        return dictService.getNameByDictCode(dictCode,value);
    }
}


