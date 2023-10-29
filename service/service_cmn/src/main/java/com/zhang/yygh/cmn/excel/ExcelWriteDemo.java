/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.cmn.excel
 * @Author: 张栩垄
 * @CreateTime: 2023-08-29  16:59
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.cmn.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.util.ArrayList;

public class ExcelWriteDemo {
    public static void main(String[] args) {
        //方式一 往单个sheet中写数据
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student(1,"张三",18,true));
        students.add(new Student(1,"李四",20,true));
        students.add(new Student(1,"小红",18,false));
        // EasyExcel.write("C:\\Users\\Administrator\\Desktop\\student.xlsx",Student.class).sheet("学生列表1").doWrite(students);

        //方式二 往多个sheet中写数据
        ExcelWriter excelWriter = EasyExcel.write("C:\\\\Users\\\\Administrator\\\\Desktop\\\\student.xlsx", Student.class).build();
        WriteSheet sheet = EasyExcel.writerSheet(0, "学生列表1").build();
        WriteSheet sheet1 = EasyExcel.writerSheet(1, "学生列表2").build();
        excelWriter.write(students,sheet);
        excelWriter.write(students,sheet1);
        excelWriter.finish();
    }
}
