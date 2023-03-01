package com.andanyoung.aop;

/**
 * @author andanyang
 * @since 2023/3/1 17:18
 */
public class User implements JDKProxyInterface{

    @Override
    public int getAge(int age) {
        System.out.println("getAge方法执行了");
        return age;
    }

    @Override
    public String getName(String name) {
        System.out.println("getName方法执行了");
        return "[" + name + "]";
    }
}
