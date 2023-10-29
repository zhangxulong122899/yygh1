/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.hosp.controller
 * @Author: 张栩垄
 * @CreateTime: 2023-09-12  21:49
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.hosp.controller.api;

import com.zhang.yygh.common.exception.YyghException;
import com.zhang.yygh.common.util.MD5;
import com.zhang.yygh.hosp.bean.Result;
import com.zhang.yygh.hosp.service.impl.HospitalServiceImpl;
import com.zhang.yygh.hosp.util.HttpRequestHelper;
import com.zhang.yygh.model.hosp.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/hosp")
public class ApiHospitalController {

    @Autowired
    private HospitalServiceImpl hospitalService;

    @PostMapping("/hospital/show")
    public Result<Hospital> getHospitalInfo(HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(parameterMap);
        String hoscode = (String) stringObjectMap.get("hoscode");


        Hospital hospital = hospitalService.getHospitalByHoscode(hoscode);
        return Result.ok(hospital);
    }

    @PostMapping("/saveHospital")
    public Result saveHospital(HttpServletRequest request){


        //获取所有的参数
        Map<String, Object> resultMap = HttpRequestHelper.switchMap(request.getParameterMap());
        String requestSignKey = (String)resultMap.get("sign");
        String requestHoscode = (String) resultMap.get("hoscode");
        String platformSignKey =  hospitalService.getSignKeyWithHoscode(requestHoscode);
        String encrypt = MD5.encrypt(platformSignKey);

        //signkey验证
        if (!StringUtils.isEmpty(requestSignKey) && !StringUtils.isEmpty(encrypt) && encrypt.equals(requestSignKey)){
            String logoData = (String) resultMap.get("logoData");
            String s = logoData.replaceAll(" ", "+");
            resultMap.put("logoData",s);
            hospitalService.saveHospital(resultMap);
            return Result.ok();
        } else {
            throw   new YyghException(20001,"保存失败");
        }
    }
}
