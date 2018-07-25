package com.everhomes.learning.demos.cache.hlm.controlleer;


import com.everhomes.learning.demos.cache.hlm.bo.AopBo;
import com.everhomes.learning.demos.cache.hlm.service.AopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/hlm")
public class AopController {

    @Autowired
    private AopService aopService;

    @RequestMapping("/aoptest")
    public ResponseEntity test(){

        testGet(1);
        testGet(2);
        testFind("bo","");
        //testFind(null,"男");

        testUpdate(1);
        testUpdate(2);

        testGet(1);
        testGet(2);
        testFind("bo","");
       // testFind(null,"男");
        
        testDelete(2);
        testGet(2);
        testFind("bo","");

        return null ;
    }


    public   void testGet(Integer i){

        if(i == null){
            i = new Integer(1);
        }
        AopBo bo = aopService.getAopBoById( i );
        alert(bo==null?"查询不到结果！":bo.toString());
    }

    public void testUpdate(Integer i){
        if(i == null){
            i = new Integer(1);
        }
        AopBo bo = aopService.getAopBoById( i );
        bo.setName(bo.getName()+"new");
        bo.setAge(bo.getAge()+10);
        aopService.updateAopBo(bo);
        alert(bo.toString());
    }

    public void testDelete(Integer i){
        if(i == null){
            i = new Integer(1);
        }
        AopBo bo = aopService.getAopBoById( i );
        aopService.deleteAopBo(bo);
    }

    public  void testFind(String name, String sex){

        List<AopBo> list =  aopService.findAopBo(name , sex);
        if(list == null || list.size() < 1) return ;
        System.out.println("----------以下为数组内空-----------");
        for(AopBo bo : list){
            alert(bo==null?"查询不到结果！":bo.toString()) ;
        }
        System.out.println("----------end-----------");
    }

    public  void alert( String str ){
        System.out.println(str);
    }
}
