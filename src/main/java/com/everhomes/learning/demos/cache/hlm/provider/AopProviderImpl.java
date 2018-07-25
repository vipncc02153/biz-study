package com.everhomes.learning.demos.cache.hlm.provider;


import com.everhomes.learning.demos.cache.hlm.bo.AopBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AopProviderImpl implements AopProvider {

    Logger log = LoggerFactory.getLogger(AopProviderImpl.class);

    private AopBo bo1 = new AopBo(1,"bo1", 11, "男" ,null) ;
    private AopBo bo2 = new  AopBo(2,"bo2", 12, "女" ,null) ;
    private AopBo bo3 = new  AopBo(3,"bo3", 13, "男" ,null) ;
    private AopBo bo4 = new  AopBo(4,"bo4", 14, "女" ,null) ;



    @Override
    public void updateAopBo(AopBo bo) {
        log.info("进入到updateAopBo方法！");

        if(bo != null && bo.getId() != null){

            //模仿更新
            switch(bo.getId().intValue()){
                case 1 : bo1  = bo ;
                    break ;
                case 2 : bo2  = bo ;
                    break ;
                case 3 : bo3  = bo ;
                    break ;
                case 4 : bo4  = bo ;
                    break ;

            }

        }


    }


    @Override
    public void deleteAopBo(AopBo bo) {
        log.info("进入到deleteAopBo方法！");
        if(bo != null && bo.getId() != null){

            //模仿删除
            switch(bo.getId().intValue()){
                case 1 : bo1  = null ;
                    break ;
                case 2 : bo2  = null  ;
                    break ;
                case 3 : bo3  = null  ;
                    break ;
                case 4 : bo4  = null  ;
                    break ;

            }

        }
    }

    @Override
    public AopBo getAopBoById(Integer id) {
        log.info("进入到getAopBoById方法！");
        log.info("查询数据库中的结果！");
        AopBo bo = null ;
        if(id != null){

            //模仿数据库查询
            switch(id.intValue()){

                case 1 : bo =bo1  ;
                    break ;
                case 2 : bo =bo2   ;
                    break ;
                case 3 : bo =bo3  ;
                    break ;
                case 4 : bo =bo4   ;
                    break ;

            }

        }
        return bo;
    }

    @Override
    public List<AopBo> findAopBo(String name, String sex) {
        log.info("进入到findAopBo方法！");
        log.info("查询数据库中的结果！");
        name = name==null?"":name;
        sex =  sex ==null?"":sex;
        List<AopBo> list = new ArrayList<AopBo>();
        switch(name+sex){
            case "bo男" : list.add(bo1)  ;list.add(bo3) ;
                break ;
            case "bo女" : list.add(bo2)  ;list.add(bo4)  ;
                break ;
            case "bo" : list.add(bo2)  ;list.add(bo4) ;list.add(bo1)  ;list.add(bo3) ;
                break ;
            case "男" : list.add(bo1)  ;list.add(bo3) ;
                break ;
            case "女" : list.add(bo2)  ;list.add(bo4)  ;
                break ;
        }

        return list;
    }
}
