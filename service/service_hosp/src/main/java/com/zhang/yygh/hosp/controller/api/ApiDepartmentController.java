/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.hosp.controller
 * @Author: 张栩垄
 * @CreateTime: 2023-09-14  22:12
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.hosp.controller.api;


import com.zhang.yygh.hosp.bean.Result;
import com.zhang.yygh.hosp.service.impl.DepartmentServiceImpl;
import com.zhang.yygh.hosp.util.HttpRequestHelper;
import com.zhang.yygh.model.hosp.Department;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/hosp")
@Api(tags = "科室信息")
public class ApiDepartmentController {

    @Autowired
    private DepartmentServiceImpl departmentService;
    //医院的删除
    @PostMapping("/department/remove")
    public Result remove(HttpServletRequest httpServletRequest){
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(httpServletRequest.getParameterMap());
        departmentService.remove(stringObjectMap);
        return Result.ok();
    }

    //查询科室信息 /api/hosp
    @PostMapping("/department/list")
    public Result<Page> getDepartmentPage(HttpServletRequest httpServletRequest){
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(httpServletRequest.getParameterMap());
        Page<Department> page =  departmentService.getDepartmentPage(stringObjectMap);
        return Result.ok(page);
    }

    @PostMapping("/saveDepartment")
    public Result saveDepartment(HttpServletRequest request){
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(request.getParameterMap());
        departmentService.saveDepartment(stringObjectMap);
        return Result.ok();

    }
}
