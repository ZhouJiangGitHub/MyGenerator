package com.velocity.generator;

import com.velocity.utils.MapListUtil;
import com.velocity.utils.ReadEntityAndParse;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static java.lang.System.exit;

/**
 * @author zj
 *         在 MyVelocityGenerator 的代码中，首先 new 了一个 VelocityEngine 类，这个类设置了 Velocity
 *         使用的一些配置，在初始化引擎之后就可以读取 hellovelocity.vm 这个模板生成的 Template 这个类。
 *         之后的 VelocityContext 类是配置 Velocity 模板读取的内容。这个 context 可以存入任意类型的对象或者变量，
 *         让 template 来读取。这个操作就像是在使用 JSP 开发时，往 request 里面放入 key-value，让 JSP 读取一样。
 */

public class MyVelocityGenerator {

    public void generateDirectoryIfNotExist(String directory){
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public Template getTemplate(String templatePath,String templateName) {
        VelocityEngine ve = new VelocityEngine();
        Properties properties = new Properties();
        // 设置模板的路径
        properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, templatePath);
        // 初始花velocity 让设置的路径生效
        ve.init(properties);

        System.out.println("velocity初始化成功");
        Template template = ve.getTemplate(templateName);
        return template;
    }

    public void processData(HashMap<String, Object> hashMap) {
        //取出数据
        String className = (String) hashMap.get("className");
        String targetBasePackage = (String) hashMap.get("targetBasePackage");
        String classDescription = (String) hashMap.get("classDescription");
        Integer manageOrUserFlag = (Integer) hashMap.get("manageOrUserFlag");
        Integer hasBlobs = (Integer) hashMap.get("hasBlobs");

        //合成各种路径
        String srcPath = System.getProperty("user.dir") + "\\src\\main";
        String generatedPath = System.getProperty("user.dir") + "\\src\\main\\generated";
        String targetBasePackagePath = targetBasePackage.replace(".", "\\");
        String entityPackagePath = targetBasePackagePath + "\\entity";
        String templatePackagePath = "com\\velocity\\template";
        String templatePath = srcPath + "\\java\\" + templatePackagePath;
        //String basePackageName = new MyGenerator().getClass().getPackage().getName();
        String entityFilePath = srcPath + "\\generated\\" + entityPackagePath + "\\" + className + ".java";
        System.out.println("entityFilePath: " + entityFilePath);

        //合成控制器输出路径
        String controllerFileTargetDirectory = generatedPath + "\\" + targetBasePackagePath + "\\controller";
        generateDirectoryIfNotExist(controllerFileTargetDirectory);
        String controllerFileTargetPath=controllerFileTargetDirectory.replace("\\", "/")+ "/" + className + "Controller.java";
        File file=new File(controllerFileTargetPath);
        if (file.exists()){
            file.delete();
        }

        String serviceFileTargetDirectory = generatedPath + "\\" + targetBasePackagePath + "\\service";
        generateDirectoryIfNotExist(serviceFileTargetDirectory);
        String serviceFileTargetPath = serviceFileTargetDirectory.replace("\\", "/")+ "/" + className + "Service.java";
        file=new File(serviceFileTargetPath);
        if (file.exists()){
            file.delete();
        }

        String serviceImplFileTargetDirectory = generatedPath + "\\" + targetBasePackagePath + "\\service\\impl";
        generateDirectoryIfNotExist(serviceImplFileTargetDirectory);
        String serviceImplFileTargetPath = serviceImplFileTargetDirectory.replace("\\", "/")+ "/" + className + "ServiceImpl.java";
        file=new File(serviceImplFileTargetPath);
        if (file.exists()){
            file.delete();
        }

        //解析实体类获取数据
        ReadEntityAndParse readEntityAndParse = new ReadEntityAndParse();
        HashMap<String, Object> dataMap = readEntityAndParse.parseEntity(entityFilePath);
        boolean res = readEntityAndParse.firstCheck(dataMap);
        if (!res) {
            exit(0);
        }

        if (hasBlobs == 1) {
            entityFilePath = srcPath + "\\\\generated\\\\" + entityPackagePath + "\\\\" + className + "WithBLOBs.java";
            HashMap<String, Object> hashMapWithBLOBs = readEntityAndParse.parseEntity(entityFilePath);
            res = readEntityAndParse.firstCheck(dataMap);
            if (!res) {
                exit(0);
            }

            MapListUtil mapListUtil = new MapListUtil();
            mapListUtil.combineListInMap(dataMap, hashMapWithBLOBs, "fieldTypeList");
        }

        List<String> fieldTypeList = (List<String>) dataMap.get("fieldTypeList");
        List<String> fieldNameList = (List<String>) dataMap.get("fieldNameList");
        List<String> fieldDescriptionList = (List<String>) dataMap.get("fieldDescriptionList");

        //参数列表中省略createTime和updateTime参数后，最后一个参数行后会生成逗号，提供这个参数是为了判断是否加逗号时使用
        int parameterNumberExcludeTime = fieldNameList.size() - 1;
        if (fieldNameList.contains("createTime")) {
            parameterNumberExcludeTime--;
        }
        if (fieldNameList.contains("updateTime")) {
            parameterNumberExcludeTime--;
        }

        //生成对象名
        String objectName = className.substring(0, 1).toLowerCase() + className.substring(1);

        //manage or user模块
        String manageOrUser = "";
        if (manageOrUserFlag == 1) {
            manageOrUser = "manage";
        } else {
            manageOrUser = "user";
        }
        String upManageOrUser = manageOrUser.substring(0, 1).toUpperCase() + manageOrUser.substring(1);

        String apiValue = upManageOrUser;

        String apiDescription = "";
        if (manageOrUserFlag == 1) {
            apiDescription = "管理" + classDescription + "的相关接口";
        } else {
            apiDescription = "用户端" + classDescription + "的相关接口";
        }

        String requestMappingOfClass = "/" + manageOrUser + "/" + objectName;

        VelocityContext ctx = new VelocityContext();
        ctx.put("target_base_package", targetBasePackage);
        ctx.put("has_blobs", hasBlobs);
        ctx.put("Api_value", apiValue);
        ctx.put("Api_description", apiDescription);
        ctx.put("RequestMapping_of_class", requestMappingOfClass);
        ctx.put("class_name", className);
        ctx.put("class_description", classDescription);
        ctx.put("object_name", objectName);
        ctx.put("field_type_list", fieldTypeList);
        ctx.put("field_name_list", fieldNameList);
        ctx.put("field_description_list", fieldDescriptionList);
        ctx.put("field_number", fieldNameList.size());
        ctx.put("max_index_no_time", parameterNumberExcludeTime);
        List list = new ArrayList();
        list.add("1");
        list.add("2");
        ctx.put("list", list);

        StringWriter sw = new StringWriter();

        Template template=getTemplate(templatePath,"Restful-Controller.vm");
        merge(template, ctx, controllerFileTargetPath);//生成目标文件
        //template.merge(ctx, sw);//打印到控制台

        template=getTemplate(templatePath,"Service.vm");
        merge(template, ctx, serviceFileTargetPath);

        template=getTemplate(templatePath,"ServiceImpl.vm");
        merge(template, ctx, serviceImplFileTargetPath);

    }

    public static void main(String[] args) {
        String className = "UserGoldBill";
        String classDescription ="用户金币账单";
        String targetBasePackage = "cn.edu.nju.software";
        Integer manageOrUserFlag = 1;//manage=1,user=2
        Integer hasBlobs = 0;

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("className", className);
        hashMap.put("classDescription", classDescription);
        hashMap.put("targetBasePackage", targetBasePackage);
        hashMap.put("manageOrUserFlag", manageOrUserFlag);
        hashMap.put("hasBlobs", hasBlobs);

        MyVelocityGenerator myVelocityGenerator = new MyVelocityGenerator();
        myVelocityGenerator.processData(hashMap);
    }

    private static void merge(Template template, VelocityContext ctx, String path) {

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(path);
            template.merge(ctx, writer);
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }

}
