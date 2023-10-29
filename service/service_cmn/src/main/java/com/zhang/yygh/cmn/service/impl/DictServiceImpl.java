package com.zhang.yygh.cmn.service.impl;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhang.yygh.cmn.listener.DictListener;
import com.zhang.yygh.cmn.mapper.DictMapper;
import com.zhang.yygh.cmn.service.DictService;
import com.zhang.yygh.model.cmn.Dict;
import com.zhang.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 组织架构表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-08-27
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    // @Autowired
    // private RedisTemplate redisTemplate;

    @Override
    @Cacheable(value = "abd",key = "'selectIndexList--'+#pid")//用在查询方法上，表示查询数据的时候，先去缓存中查，缓存中没有再去数据库查
    public List<Dict> getChildListByPid(Long pid) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",pid);
        List<Dict> dicts = baseMapper.selectList(queryWrapper);
        for (Dict dict : dicts) {
            boolean flag = idHasChildren(dict.getId());
            dict.setHasChildren(flag);
        }
        return dicts;
    }

    @Override
    public void download(HttpServletResponse response) throws IOException {

        List<Dict> list = baseMapper.selectList(null);
        ArrayList<DictEeVo> dictEeVos = new ArrayList<>(list.size());
        for (Dict dict : list) {
            DictEeVo dictEeVo = new DictEeVo();
            BeanUtils.copyProperties(dict,dictEeVo);
            dictEeVos.add(dictEeVo);
        }
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("字典文件", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet(0).doWrite(dictEeVos);
    }

    @Override
    @CacheEvict(value = "abc",allEntries = true)
    public void upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), DictEeVo.class,new DictListener(baseMapper)).sheet(0).doRead();
    }

    @Override
    public String getNameByValue(Long value) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("value",value);
        Dict dict = baseMapper.selectOne(queryWrapper);
        if (dict != null){
            return dict.getName();
        }
        return null;
    }

    @Override
    public String getNameByDictCode(String dictCode, Long value) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_code",dictCode);
        Dict dict = baseMapper.selectOne(queryWrapper);

        Long id = dict.getId();
        QueryWrapper<Dict> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("parent_id", id);
        queryWrapper1.eq("value",value);

        Dict dict1 = baseMapper.selectOne(queryWrapper1);

        return dict1.getName();
    }

    private boolean idHasChildren(Long pid) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<Dict>();
        queryWrapper.eq("parent_id",pid);
        Integer count = baseMapper.selectCount(queryWrapper);

        return count>0;
    }
}
