package com.zhang.yygh.hosp.service;

import com.zhang.yygh.model.hosp.Department;
import com.zhang.yygh.vo.hosp.DepartmentVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    void saveDepartment(Map<String, Object> stringObjectMap);

    Page<Department> getDepartmentPage(Map<String, Object> stringObjectMap);

    void remove(Map<String, Object> stringObjectMap);

    List<DepartmentVo> getDepartmentList(String hoscode);

    String getDepName(String hoscode, String depcode);
}
