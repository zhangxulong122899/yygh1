/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.hosp.controller.admin
 * @Author: 张栩垄
 * @CreateTime: 2023-09-21  15:10
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.hosp.controller.admin;

import com.zhang.yygh.common.result.R;
import com.zhang.yygh.hosp.service.impl.HospitalServiceImpl;
import com.zhang.yygh.model.hosp.Hospital;
import com.zhang.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/hospital")
// @CrossOrigin
public class HospitalController {

    @Autowired
    private HospitalServiceImpl hospitalService;

    @GetMapping("/{pageNum}/{pageSize}")
    public R getHospitalPage(@PathVariable Integer pageNum,@PathVariable Integer pageSize, HospitalQueryVo hospitalQueryVo){
        Page<Hospital> hospitalPage =  hospitalService.getHospitalPage(pageNum,pageSize,hospitalQueryVo);
        return R.ok().data("total",hospitalPage.getTotalElements()).data("rows",hospitalPage.getContent());
    }

    //根据医院id修改医院状态
    @PutMapping("/{id}/{status}")
    public  R updateStatus(@PathVariable("id")String id,
                           @PathVariable("status") Integer status){
        hospitalService.updateStatus(id,status);
        return R.ok();
    }

    //根据医院id获取医院是的所有信息
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable("id")String id){
       Hospital hospital = hospitalService.detail(id);
       return R.ok().data("hospital",hospital);
    }
}
