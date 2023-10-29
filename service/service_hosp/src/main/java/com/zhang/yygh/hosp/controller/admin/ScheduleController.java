/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.hosp.controller.admin
 * @Author: 张栩垄
 * @CreateTime: 2023-10-10  10:15
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.hosp.controller.admin;

import com.zhang.yygh.common.result.R;
import com.zhang.yygh.hosp.service.DepartmentService;
import com.zhang.yygh.hosp.service.HospitalService;
import com.zhang.yygh.hosp.service.ScheduleService;
import com.zhang.yygh.model.hosp.Schedule;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/hosp/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/{hoscode}/{depcode}/{workdate}")
    public  R detail( @PathVariable String hoscode,
                      @PathVariable String depcode,
                      @PathVariable String workdate){
        Date date = new DateTime(workdate).toDate();
        List<Schedule>  scheduleList = scheduleService.detail(hoscode,depcode,date);
        //把得到list集合遍历，向设置其他值：医院名称、科室名称、日期对应星期
        scheduleList.stream().forEach(item->{
            this.packageSchedule(item);
        });
        return R.ok().data("list",scheduleList);
    }

    private void packageSchedule(Schedule schedule) {
        //设置医院名称
        schedule.getParam().put("hosname",hospitalService.getHospitalByHoscode(schedule.getHoscode()).getHosname());
        //设置科室名称
        schedule.getParam().put("depname",
                departmentService.getDepName(schedule.getHoscode(),schedule.getDepcode()));
        //设置日期对应星期
        schedule.getParam().put("dayOfWeek",this.getDayOfWeek(new DateTime(schedule.getWorkDate())));
    }



    @GetMapping("/{pageNum}/{pageSize}/{hoscode}/{depcode}")
    public R page(@PathVariable Integer pageNum,
                  @PathVariable Integer pageSize,
                  @PathVariable String hoscode,
                  @PathVariable String depcode){
        Map<String,Object> map = scheduleService.page(pageNum,pageSize,hoscode,depcode);
        return R.ok().data(map);
    }

    private String getDayOfWeek(DateTime dateTime) {
        String dayOfWeek = "";
        switch (dateTime.getDayOfWeek()) {
            case DateTimeConstants.SUNDAY:
                dayOfWeek = "周日";
                break;
            case DateTimeConstants.MONDAY:
                dayOfWeek = "周一";
                break;
            case DateTimeConstants.TUESDAY:
                dayOfWeek = "周二";
                break;
            case DateTimeConstants.WEDNESDAY:
                dayOfWeek = "周三";
                break;
            case DateTimeConstants.THURSDAY:
                dayOfWeek = "周四";
                break;
            case DateTimeConstants.FRIDAY:
                dayOfWeek = "周五";
                break;
            case DateTimeConstants.SATURDAY:
                dayOfWeek = "周六";
            default:
                break;
        }
        return dayOfWeek;
    }

}
