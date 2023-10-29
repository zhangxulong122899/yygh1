package com.zhang.yygh.hosp.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.yygh.common.exception.YyghException;
import com.zhang.yygh.common.result.R;
import com.zhang.yygh.common.util.MD5;
import com.zhang.yygh.hosp.service.HospitalSetService;
import com.zhang.yygh.model.hosp.HospitalSet;
import com.zhang.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * <p>
 * 医院设置表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-08-07
 */
@RestController
@Api(tags = "医院设置信息")
@RequestMapping("/admin/hosp/hospital-set")
@Slf4j
// @CrossOrigin //解决跨域问题
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    //锁定与解锁
    @PutMapping("/status/{id}/{status}")
    public R updateStatus(@PathVariable Long id,@PathVariable Integer status){
        log.info("current thread is" + Thread.currentThread().getId() + ",params : id= " +id);
        // HospitalSet hospitalSet = hospitalSetService.getById(id);
        HospitalSet hospitalSet = new HospitalSet();
        hospitalSet.setId(id);
        hospitalSet.setStatus(status);
        hospitalSetService.updateById(hospitalSet);
        log.info("current thread is" + Thread.currentThread().getId() + hospitalSet.toString());
        return R.ok();
    }

    //批量删除
    @DeleteMapping("/delete")
    public R batchDelete(@RequestBody List<Integer> ids){
        boolean b = hospitalSetService.removeByIds(ids);
        return R.ok();
    }

    //修改之回显数据
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable Integer id){
        HospitalSet byId = hospitalSetService.getById(id);
        return R.ok().data("item",byId);
    }

    //修改数据
    @PutMapping("/update")
    public R update(@RequestBody HospitalSet hospitalSet){
        boolean b = hospitalSetService.updateById(hospitalSet);
        return R.ok();
    }

    @ApiOperation(value = "新增接口")
    @PostMapping("/save")
    public R save(@RequestBody HospitalSet hospitalSet){
        //设置状态 1 使用 0 不能使用
        hospitalSet.setStatus(1);
        //当前时间戳+随机数+MD5加密
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));
        boolean save = hospitalSetService.save(hospitalSet);
        return R.ok();
    }


    @ApiOperation(value = "查询医院分页信息")
    @PostMapping("/page/{pageNum}/{size}")
    public R pageList(@ApiParam(name = "pageNum",value = "当前页") @PathVariable(value = "pageNum")Integer pageNum,
                      @ApiParam(name = "size",value = "每页显示的条数") @PathVariable(value = "size")Integer size,
                       @RequestBody HospitalSetQueryVo hospitalSetQueryVo
    ){
        Page page = new Page(pageNum,size);
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(hospitalSetQueryVo.getHosname())){
            queryWrapper.like("hosname",hospitalSetQueryVo.getHosname());
        }
        if (!StringUtils.isEmpty(hospitalSetQueryVo.getHoscode())){
            queryWrapper.eq("hoscode",hospitalSetQueryVo.getHoscode());
        }
        Page page1 = hospitalSetService.page(page,queryWrapper);
        return R.ok().data("total",page1.getTotal()).data("rows",page1.getRecords());
    }

    
    //swagger是接口文档省的|接口测试工具
    @ApiOperation(value = "查询所有的医院信息")
    @GetMapping("/findAll")
    public R findAll(){
        List<HospitalSet> list = null;
        try {
            list = hospitalSetService.list();
        } catch (Exception e) {
            throw new YyghException(200013,"xxx异常");
        }
        // int i = 10/0;
        return R.ok().data("items",list);
    }

    //根据医院id删除某个医院信息
    @ApiOperation(value = "根据医院的id删除医院信息")
    @DeleteMapping("/deleteById/{id}")
    public R removeById(@ApiParam(name = "sid",value = "医院id",required = true) @PathVariable(value = "id") Integer id){
        boolean b = hospitalSetService.removeById(id);
        if (b){
            return R.ok();
        }else {
            return R.error();
        }
    }


}

