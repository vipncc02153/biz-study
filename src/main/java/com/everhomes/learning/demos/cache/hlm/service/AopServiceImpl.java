package com.everhomes.learning.demos.cache.hlm.service;


import com.everhomes.learning.demos.cache.hlm.bo.AopBo;
import com.everhomes.learning.demos.cache.hlm.provider.AopProvider;
import com.everhomes.learning.demos.cache.hlm.provider.AopProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AopServiceImpl implements AopService {

    Logger log = LoggerFactory.getLogger(AopProviderImpl.class);


    @Autowired
    private AopProvider aopProvider ;

   /* public AopServiceImpl(){
        aopProvider = new AopProviderImpl();
    }*/

    @Override
    public void updateAopBo(AopBo bo) {

        aopProvider.updateAopBo(bo);
    }

    @Override
    public void deleteAopBo(AopBo bo) {

        aopProvider.deleteAopBo(bo);
    }

    @Override
    public AopBo getAopBoById(Integer id) {
        return aopProvider.getAopBoById(id);
    }

    @Override
    public List<AopBo> findAopBo(String name, String sex) {
        return aopProvider.findAopBo(name , sex);
    }
}
