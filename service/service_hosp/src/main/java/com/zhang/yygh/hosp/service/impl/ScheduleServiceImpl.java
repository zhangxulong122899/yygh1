/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.hosp.service.impl
 * @Author: 张栩垄
 * @CreateTime: 2023-09-19  18:04
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhang.yygh.hosp.repository.ScheduleRepository;
import com.zhang.yygh.hosp.service.HospitalService;
import com.zhang.yygh.hosp.service.ScheduleService;
import com.zhang.yygh.model.hosp.Hospital;
import com.zhang.yygh.model.hosp.Schedule;
import com.zhang.yygh.vo.hosp.BookingScheduleRuleVo;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private HospitalService hospitalService;

    @Override
    public void saveSchedule(Map<String, Object> stringObjectMap) {
        String s = JSONObject.toJSONString(stringObjectMap);
        Schedule schedule = JSONObject.parseObject(s, Schedule.class);
        String hoscode = schedule.getHoscode();
        String depcode = schedule.getDepcode();
        String hosScheduleId = schedule.getHosScheduleId();

        Schedule platformSchedule =  scheduleRepository.findByHoscodeAndDepcodeAndHosScheduleId(hoscode,depcode,hosScheduleId);
        if (platformSchedule == null) {
            schedule.setCreateTime(new Date());
            schedule.setUpdateTime(new Date());
            schedule.setIsDeleted(0);
            scheduleRepository.save(schedule);
        }else {
            schedule.setCreateTime(platformSchedule.getCreateTime());
            schedule.setUpdateTime(new Date());
            schedule.setIsDeleted(platformSchedule.getIsDeleted());

            schedule.setId(platformSchedule.getId());
            scheduleRepository.save(schedule);
        }
    }

    @Override
    public Page<Schedule> getSchedulePage(Map<String, Object> stringObjectMap) {
        Schedule schedule = new Schedule();
        String hoscode = (String) stringObjectMap.get("hoscode");
        schedule.setHoscode(hoscode);
        Example<Schedule> scheduleExample = Example.of(schedule);
        int page = Integer.parseInt(stringObjectMap.get("page").toString());
        int limit = Integer.parseInt(stringObjectMap.get("limit").toString());

        PageRequest pageRequest = PageRequest.of(page-1,limit, Sort.by("createTime").ascending());
        Page<Schedule> result = scheduleRepository.findAll(scheduleExample, pageRequest);
        return result;
    }

    @Override
    public void remove(Map<String, Object> stringObjectMap) {
        String hoscode = (String) stringObjectMap.get("hoscode");
        String hosScheduleId = (String) stringObjectMap.get("hosScheduleId");

        Schedule schedule = scheduleRepository.findByHoscodeAndHosScheduleId(hoscode,hosScheduleId);
        if (schedule != null){
            scheduleRepository.deleteById(schedule.getId());
        }
    }

    @Override
    public Map<String, Object> page(Integer pageNum, Integer pageSize, String hoscode, String depcode) {

        Criteria criteria = Criteria.where("hoscode").is(hoscode).and("depcode").is(depcode);
        //分组 聚合 最好使用mongoTemplate
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("workDate").first("workDate").as("workDate")
                        .sum("reservedNumber").as("reservedNumber")
                        .sum("availableNumber").as("availableNumber"),
                Aggregation.sort(Sort.Direction.ASC,"workDate"),
                Aggregation.skip((pageNum-1)*pageSize),
                Aggregation.limit(pageSize)
        );//聚合条件
        /*
                第一个参数Aggregation表示聚合条件
                第二个参数InputType 表示输入类型，可以根据当前指定的字节码找到mongo对应集合
                第三个参数OutputType 表示输出类型，封装聚合后的信息
         */
        AggregationResults<BookingScheduleRuleVo> aggregate = mongoTemplate.aggregate(aggregation, Schedule.class, BookingScheduleRuleVo.class);
        //当前页对应的排版列表数据
        List<BookingScheduleRuleVo> mappedResults = aggregate.getMappedResults();
        for (BookingScheduleRuleVo mappedResult : mappedResults) {
            Date workDate = mappedResult.getWorkDate();
            //工具类：每年旅游：周几
            String dayOfWeek = this.getDayOfWeek(new DateTime(workDate));
            mappedResult.setDayOfWeek(dayOfWeek);
        }


        //分组 聚合 最好使用mongoTemplate
        Aggregation aggregation2 = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("workDate")
        );//聚合条件
        /*
                第一个参数Aggregation表示聚合条件
                第二个参数InputType 表示输入类型，可以根据当前指定的字节码找到mongo对应集合
                第三个参数OutputType 表示输出类型，封装聚合后的信息
         */
        AggregationResults<BookingScheduleRuleVo> aggregate2 = mongoTemplate.aggregate(aggregation2, Schedule.class, BookingScheduleRuleVo.class);
        int size = aggregate2.getMappedResults().size();


        Map<String,Object> map = new HashMap<>();
        map.put("list",mappedResults);
        map.put("total",size);


        //获取医院名称
        Hospital hospital = hospitalService.getHospitalByHoscode(hoscode);
        //其他基础数据
        Map<String, String> baseMap = new HashMap<>();
        baseMap.put("hosname",hospital.getHosname());
        map.put("baseMap",baseMap);
        return map;
    }

    @Override
    public List<Schedule> detail(String hoscode, String depcode, Date workdate) {
        List<Schedule> scheduleList = scheduleRepository.findByHoscodeAndDepcodeAndWorkDate(hoscode,depcode,workdate);
        return scheduleList;
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
