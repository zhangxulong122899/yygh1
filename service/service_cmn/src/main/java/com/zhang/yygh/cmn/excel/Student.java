/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.cmn.excel
 * @Author: 张栩垄
 * @CreateTime: 2023-08-29  17:02
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.cmn.excel;

import com.alibaba.excel.annotation.ExcelProperty;

public class Student {

    public Student() {
    }

    public Student(Integer sid, String name, Integer age, boolean gender) {
        this.sid = sid;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
    @ExcelProperty("学生id")
    private Integer sid;
    @ExcelProperty("学生名字")
    private  String name;
    @ExcelProperty("学生年龄")
    private Integer age;
    @ExcelProperty("学生性别")
    private boolean gender;


    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sid=" + sid +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                '}';
    }
}
