/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.hosp.controller.admin
 * @Author: 张栩垄
 * @CreateTime: 2023-10-09  10:15
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.hosp.controller.admin;

import com.zhang.yygh.common.result.R;
import com.zhang.yygh.hosp.service.DepartmentService;
import com.zhang.yygh.vo.hosp.DepartmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/hosp/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/{hoscode}")
    public R getDepartmentList(@PathVariable String hoscode){
        List<DepartmentVo> departments =  departmentService.getDepartmentList(hoscode);
        return R.ok().data("list",departments);
    }
}
