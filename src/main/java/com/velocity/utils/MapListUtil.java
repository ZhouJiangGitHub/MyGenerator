package com.velocity.utils;

import java.util.HashMap;
import java.util.List;

/**
 * @author zj
 */
public class MapListUtil {
    public HashMap<String,Object> combineListInMap(
            HashMap<String,Object> hashMap,HashMap<String,Object> hashMapWithBLOBs, String key){
        List<String> fieldTypeList1=(List<String>) hashMap.get(key);
        List<String> fieldTypeList2=(List<String>) hashMapWithBLOBs.get(key);
        fieldTypeList1.addAll(fieldTypeList2);
        hashMap.put(key,fieldTypeList1);
        return hashMap;
    }

}
