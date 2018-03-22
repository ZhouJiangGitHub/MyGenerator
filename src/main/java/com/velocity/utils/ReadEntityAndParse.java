package com.velocity.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.System.exit;

/**
 * IO流这部分要独立成util并测试
 * 对读取到的字符串进行判断时要考虑到非格式的多样性如花括号的竖直对其的格式等，以增强程序的适用性
 *
 * @author zj
 */
public class ReadEntityAndParse {

    public HashMap<String, Object> parseEntity(String entityFile) {
        String className = null;
        String fieldType;
        String fieldName;
        String fieldDescription;
        FileReader fileReader;
        BufferedReader bufferedReader;
        String[] cells;
        List<String> fieldTypeList = new ArrayList<>();
        List<String> fieldNameList = new ArrayList<>();
        List<String> fieldDescriptionList = new ArrayList<>();

        try {
            fileReader = new FileReader(entityFile);
            bufferedReader = new BufferedReader(fileReader);
            String aLine;
            while ((aLine = bufferedReader.readLine()) != null) {
                //这句不能放在上面的判断条件里，因为万一读到空行，就会引起空指针异常
                aLine = aLine.trim();
                //提取类名
                if (aLine.contains("public class")) {
                    cells = aLine.split(" ");
                    className = cells[2];
                    break;
                }
            }
            while ((aLine = bufferedReader.readLine()) != null) {
                //这句不能放在上面的判断条件里，因为万一读到空行，就会引起空指针异常
                aLine = aLine.trim();
                if (aLine.contains("private")) {
                    cells = aLine.split(" ");
                    fieldType = cells[1];
                    fieldTypeList.add(fieldType);
                    if (cells[2].contains("=")) {
                        fieldName = cells[2].split("=")[0];
                    } else if (cells[2].contains(";")) {
                        fieldName = cells[2].split(";")[0];
                    } else {//这种情况就是字段名后面有空格。空格后后面可能是“=”或“;”
                        fieldName = cells[2];
                    }
                    fieldNameList.add(fieldName);
                    cells = aLine.split("//");
                    if (cells.length > 1) {
                        fieldDescription = cells[1];
                    } else {
                        fieldDescription = "";
                    }
                    fieldDescriptionList.add(fieldDescription);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("className", className);
        hashMap.put("fieldTypeList", fieldTypeList);
        hashMap.put("fieldNameList", fieldNameList);
        hashMap.put("fieldDescriptionList", fieldDescriptionList);
        return hashMap;
    }

    public boolean firstCheck(HashMap<String, Object> hashMap) {
        System.out.println(hashMap.get("className"));

        List<String> fieldTypeList = (List<String>) hashMap.get("fieldTypeList");
        System.out.println(fieldTypeList);
        int typeNumber = fieldTypeList.size();
        System.out.println(typeNumber);

        List<String> fieldNameList = (List<String>) hashMap.get("fieldNameList");
        System.out.println(fieldNameList);
        int nameNumber = fieldNameList.size();
        System.out.println(nameNumber);

        List<String> fieldDescriptionList = (List<String>) hashMap.get("fieldDescriptionList");
        System.out.println(fieldDescriptionList);
        int desNumber = fieldDescriptionList.size();
        System.out.println(desNumber);

        if (typeNumber == nameNumber && nameNumber == desNumber) {
            System.out.println("解析实体类成功");
            return true;
        }
        else{
            System.out.println("解析实体类失败，字段类型个数、字段名、字段描述的个数不完全一致");
            return false;
        }
    }

/*    public static void main(String[] args) {
        String entityPath = "D:\\Ideal\\FastDev\\MyVelocity\\src\\com\\velocity\\entity\\User.java";
        ReadEntityAndParse parseEntityWithIoReturnMap=new ReadEntityAndParse();
        HashMap<String, Object> hashMap = parseEntityWithIoReturnMap.parseEntity(entityPath);
        boolean res=parseEntityWithIoReturnMap.firstCheck(hashMap);
        if (!res){
            exit(0);
        } else{
            System.out.println("first check passed!");
        }
    }*/


}
