package com.pactera.demo.po;

import com.dap.dao.BasePo;

/**
 * @Author: zhoulonghui
 * @Date: 2019/2/13 9:31
 */
public class Employee extends BasePo implements java.io.Serializable{
    private Integer id;
    private String name;
    private String sex;
    private Integer age;
    private String dept;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
