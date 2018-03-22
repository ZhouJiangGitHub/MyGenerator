package com.generator.main;

import com.mybatis.generator.GeneratorSqlmap;
import com.velocity.generator.MyVelocityGenerator;

import java.util.Date;

/**
 * @author zj
 */
public class MainGenerator {
    public static void main(String[] args) {
        long x=System.currentTimeMillis();
        GeneratorSqlmap.main(null);
        MyVelocityGenerator.main(null);
        long y=System.currentTimeMillis();
        System.out.println("耗时："+(y-x)+"ms");
    }

}
