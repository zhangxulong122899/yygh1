/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.cmn.listener
 * @Author: 张栩垄
 * @CreateTime: 2023-08-31  17:03
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.yygh.cmn.mapper.DictMapper;
import com.zhang.yygh.model.cmn.Dict;
import com.zhang.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;

public class DictListener extends AnalysisEventListener<DictEeVo> {

    private DictMapper dictMapper;

    public DictListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo,dict);
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Dict> id = queryWrapper.eq("id", dictEeVo.getId());
        Integer count = dictMapper.selectCount(id);
        if (count>0){
            dictMapper.updateById(dict);
        }else {
            this.dictMapper.insert(dict);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
