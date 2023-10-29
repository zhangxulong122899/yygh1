/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.cmn.excel
 * @Author: 张栩垄
 * @CreateTime: 2023-08-29  20:43
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.cmn.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;

public class EasyExcelReadDemo {
    public static void main(String[] args) {
        //简单读取
        // EasyExcel.read("C:\\\\Users\\\\Administrator\\\\Desktop\\\\student.xlsx",Student.class,new StudentListener()).sheet(0).doRead();
        //读取多个sheet
        ExcelReader excelReader = EasyExcel.read("C:\\Users\\Administrator\\Desktop\\student.xlsx").build();
        ReadSheet readSheet = EasyExcel.readSheet(0).head(Student.class).registerReadListener(new StudentListener()).build();
        ReadSheet readSheet1 = EasyExcel.readSheet(1).head(Student.class).registerReadListener(new StudentListener()).build();
        ExcelReader read = excelReader.read(readSheet, readSheet1);
        excelReader.finish();
    }
}
