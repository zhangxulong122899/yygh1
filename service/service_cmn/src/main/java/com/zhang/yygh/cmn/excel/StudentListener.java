/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.cmn.excel
 * @Author: 张栩垄
 * @CreateTime: 2023-08-29  20:45
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.cmn.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class StudentListener extends AnalysisEventListener<Student> {

    //每解析excel文件中的一行数据都会调用一次invoke方法
    @Override
    public void invoke(Student student, AnalysisContext analysisContext) {
        System.out.println(student);
    }

    //当解析excel文件某个sheet的标题的时候
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("标题为："+headMap);
    }

    //当excel文件被解析完毕之后走这个方法：收尾工作，关闭连接
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }


}
