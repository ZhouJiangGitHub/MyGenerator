package com.mybatis.generator;

/**
 * @author zj
 */
public class Test {

    public static void main(String[] args) {
        Integer i=1;
        String a="111#d111#d111";
        a=a.replaceFirst("#d","ccc").replaceFirst("#d","rrr");
        System.out.println(a);
    }

}
