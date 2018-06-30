package com.everhomes.learning.demos.beantojson.zhouxm.beantojson;

/**
 * @ClassName Student
 * @Description 学生
 * @Author feiyue
 * @Date 2018/6/30
 **/
public class Student {

    private String name;
    private Integer age;
    private Byte sex;  // 0 - 男, 1 - 女
    private Bag bag;

    public Student() {
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

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }
}
