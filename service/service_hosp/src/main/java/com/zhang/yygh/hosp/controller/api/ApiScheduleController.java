/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.hosp.controller
 * @Author: 张栩垄
 * @CreateTime: 2023-09-19  18:00
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.hosp.controller.api;

import com.zhang.yygh.hosp.bean.Result;
import com.zhang.yygh.hosp.service.impl.ScheduleServiceImpl;
import com.zhang.yygh.hosp.util.HttpRequestHelper;
import com.zhang.yygh.model.hosp.Schedule;
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
@Api(tags = "排班信息")
public class ApiScheduleController {


    @Autowired
    private ScheduleServiceImpl scheduleService;

    @PostMapping("/saveSchedule")
    public Result saveSchedule(HttpServletRequest request){
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(request.getParameterMap());
        scheduleService.saveSchedule(stringObjectMap);
        return Result.ok();
    }

    //查询排班分页
    @PostMapping("/schedule/list")
    public Result<Page> getSchedulePage(HttpServletRequest request){
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(request.getParameterMap());
        Page<Schedule> schedulePage = scheduleService.getSchedulePage(stringObjectMap);
        return Result.ok(schedulePage);
    }

    @PostMapping("/schedule/remove")
    public Result remove(HttpServletRequest request){
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(request.getParameterMap());
        scheduleService.remove(stringObjectMap);
        return Result.ok();
    }
}
