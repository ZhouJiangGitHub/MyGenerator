package com.velocity.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zj
 */
public class JsonToFields {

    public List<String> jsonToFields(String jsonObject) {
        int len1 = jsonObject.length();
        String subString1 = jsonObject.substring(1, len1 - 1);
        String[] strings1 = subString1.split(",");
        List<String> fieldList=new ArrayList<>();
        for (String e : strings1) {
            String[] strings2=e.split(":");
            int len2=strings2[0].length();
            String subString2=strings2[0].substring(1,len2-1);
            fieldList.add(subString2);
        }
        return fieldList;
    }

}
