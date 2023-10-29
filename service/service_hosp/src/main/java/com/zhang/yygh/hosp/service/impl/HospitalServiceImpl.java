/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.hosp.service.impl
 * @Author: 张栩垄
 * @CreateTime: 2023-09-13  20:34
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.yygh.client.DictFeignClient;
import com.zhang.yygh.common.exception.YyghException;
import com.zhang.yygh.enums.DictEnum;
import com.zhang.yygh.hosp.mapper.HospitalSetMapper;
import com.zhang.yygh.hosp.repository.HospitalRepository;
import com.zhang.yygh.hosp.service.HospitalService;
import com.zhang.yygh.model.hosp.Hospital;
import com.zhang.yygh.model.hosp.HospitalSet;
import com.zhang.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private HospitalSetMapper hospitalSetMapper;

    @Autowired
    private DictFeignClient dictFeignClient;

    @Override
    public void saveHospital(Map<String, Object> resultMap) {
        String s = JSONObject.toJSONString(resultMap);
        Hospital hospital = JSONObject.parseObject(s, Hospital.class);

        String hoscode = hospital.getHoscode();
        Hospital  collection = hospitalRepository.findByHoscode(hoscode);
        if (collection == null){
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }else {//平台上有该医院信息做修改

            hospital.setStatus(collection.getStatus());
            hospital.setCreateTime(collection.getCreateTime());
            hospital.setIsDeleted(collection.getIsDeleted());
            hospital.setUpdateTime(new Date());

            hospital.setId(collection.getId());
            hospitalRepository.save(hospital);
        }

    }

    @Override
    public String getSignKeyWithHoscode(String requestHoscode) {
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("hoscode", requestHoscode);
        HospitalSet hospitalSet = hospitalSetMapper.selectOne(queryWrapper);
        if (hospitalSet==null) {
            throw new  YyghException(20001,"该医院不存在");
        }
        return hospitalSet.getSignKey();
    }

    @Override
    public Hospital getHospitalByHoscode(String hoscode) {
        Hospital byHoscode = hospitalRepository.findByHoscode(hoscode);
        return byHoscode;
    }

    @Override
    public Page<Hospital> getHospitalPage(Integer pageNum, Integer pageSize, HospitalQueryVo hospitalQueryVo) {
        Hospital hospital = new Hospital();
        if (!StringUtils.isEmpty(hospitalQueryVo.getHosname())){
            hospital.setHosname(hospitalQueryVo.getHosname());
        }
        if (!StringUtils.isEmpty(hospitalQueryVo.getProvinceCode())){
            hospital.setProvinceCode(hospitalQueryVo.getProvinceCode());
        }
        if (!StringUtils.isEmpty(hospitalQueryVo.getCityCode())){
            hospital.setCityCode(hospitalQueryVo.getCityCode());
        }

        ExampleMatcher ma = ExampleMatcher.matching() //构建对象
                // .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withMatcher("hosname",ExampleMatcher.GenericPropertyMatchers.startsWith()) //指定某个字段支持模糊查询
                .withIgnoreCase(true);//改变默认大小写忽略方式 ： 忽略大小写
        Example<Hospital> of = Example.of(hospital);

        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, Sort.by("createTime").ascending());
        Page<Hospital> hospitalPage = hospitalRepository.findAll(of, pageRequest);
        List<Hospital> content = hospitalPage.getContent();
        content.stream().forEach(hospital1 -> {
            packageHospital(hospital1);
        });
        return hospitalPage;
    }

    @Override
    public void updateStatus(String id, Integer status) {
        if (status == 0 || status == 1){
            Optional<Hospital> byId = hospitalRepository.findById(id);
            Hospital hospital = byId.get();
            hospital.setStatus(status);
            hospital.setUpdateTime(new Date());
            hospitalRepository.save(hospital);
        }
    }

    @Override
    public Hospital detail(String id) {
        Hospital hospital = hospitalRepository.findById(id).get();
        this.packageHospital(hospital);
        return hospital;
    }

    @Override
    public List<Hospital> findByNameLike(String name) {
        List<Hospital> hospitals = hospitalRepository.findByHosnameLike(name);
        return hospitals;
    }

    private void packageHospital(Hospital hospital1){
        String hostype = hospital1.getHostype();
        String provinceCode = hospital1.getProvinceCode();
        String cityCode = hospital1.getCityCode();
        String districtCode = hospital1.getDistrictCode();

        String provinceAddress = dictFeignClient.getNameByValue(Long.valueOf(provinceCode));
        String cityAddress = dictFeignClient.getNameByValue(Long.valueOf(cityCode));
        String districtAddress = dictFeignClient.getNameByValue(Long.valueOf(districtCode));

        String level = dictFeignClient.getNameByDictCode(DictEnum.HOSTYPE.getDictCode(), Long.valueOf(hostype));


        hospital1.getParam().put("hostypeString",level);
        hospital1.getParam().put("fullAddress",provinceAddress+cityAddress+districtAddress+hospital1.getAddress());

    }

}
