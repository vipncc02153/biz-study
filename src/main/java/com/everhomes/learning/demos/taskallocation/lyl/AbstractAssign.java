package com.everhomes.learning.demos.taskallocation.lyl;

import java.util.List;
import java.util.Map;

/**
 * Created by Long on 2018/7/7.
 */
public abstract class AbstractAssign {
    protected abstract Map assigned(List serverList, List taskList);

    public final Map templateMethod(List serverList, List taskList){
        return assigned(serverList,taskList);
    }
}
