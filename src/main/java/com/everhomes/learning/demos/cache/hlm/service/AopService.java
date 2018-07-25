package com.everhomes.learning.demos.cache.hlm.service;



import com.everhomes.learning.demos.cache.hlm.bo.AopBo;

import java.util.List;

public interface AopService {


    /**
     * 更新BO
     * @param bo
     */
    void updateAopBo(AopBo bo) ;

    /**
     * 删除BO
     * @param bo
     */
    void deleteAopBo(AopBo bo);


    /**
     * 通过主键查询BO
     * @param id
     * @return
     */
    AopBo getAopBoById(Integer id);

    /**
     * 通过姓名或性别查询BO
     * @param name
     * @param sex
     * @return
     */
    List<AopBo> findAopBo(String name, String sex);
}
