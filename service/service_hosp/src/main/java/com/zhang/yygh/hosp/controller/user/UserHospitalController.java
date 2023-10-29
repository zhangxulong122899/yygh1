/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.hosp.controller.user
 * @Author: 张栩垄
 * @CreateTime: 2023-10-21  21:38
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.hosp.controller.user;

import com.zhang.yygh.common.result.R;
import com.zhang.yygh.hosp.service.HospitalService;
import com.zhang.yygh.model.hosp.Hospital;
import com.zhang.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/hospital")
public class UserHospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/list")
    public R getHospitalList(HospitalQueryVo hospitalQueryVo){
        Page<Hospital> hospitalPage = hospitalService.getHospitalPage(1, 100000, hospitalQueryVo);
        return R.ok().data("list",hospitalPage.getContent());
    }

    @GetMapping("/{name}")
    public R findByName(@PathVariable String name){
        List<Hospital> hospitals =hospitalService.findByNameLike(name);
        return R.ok().data("list",hospitals);
    }
}
