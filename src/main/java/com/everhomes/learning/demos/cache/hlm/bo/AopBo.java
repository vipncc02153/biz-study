package com.everhomes.learning.demos.cache.hlm.bo;

import java.util.Date;

public class AopBo {

    private Integer id ;
    private String name ;
    private Integer age ;
    private String  sex ;
    private Date birth;

    public AopBo() {
    }

    public AopBo(Integer id , String name, Integer age, String sex, Date birth) {

        this.id = id ;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.birth = birth;
    }

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "id:"+this.id +";name:"+this.name+";sex:"+this.sex+";age:"+this.age;
    }
}
