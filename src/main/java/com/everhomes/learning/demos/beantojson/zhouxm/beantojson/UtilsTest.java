package com.everhomes.learning.demos.beantojson.zhouxm.beantojson;

/**
 * @ClassName UtilsTest
 * @Description  工具测试类
 * @Author feiyue
 * @Date 2018/6/30
 **/
public class UtilsTest {

    public static void main(String[] args) throws Exception{
        Bag bag = new Bag();
        bag.setSize("中等");
        bag.setColor("red");
       // System.out.println(Bean2JsonUtils.bean2Json(bag));

        Student student = new Student();
        student.setAge(18);
        student.setSex((byte)0);
        student.setName("黎明");
        student.setBag(bag);
        System.out.println(Bean2JsonUtils.bean2Json(student));
    }

}
