package com.andanyoung.aop;

/**
 * @author andanyang
 * @since 2023/3/1 17:22
 */
public class AgentTest {
    public static void main(String[] args) {

        //testJDKProxy();
        testCGLBProxy();
    }

    public static void testJDKProxy(){

        JDKProxyInterface proxy = JDKProxy.getProxy(JDKProxyInterface.class);

        proxy.getAge(18);
        proxy.getName("Agent");

        User user = new User();
        proxy = JDKProxy.getProxy(user);
        proxy.getAge(18);
        proxy.getName("Agent");
    }

    public static void testCGLBProxy(){

        User user = new User();
        User proxy = CGLBProxy.getProxy(user);
        proxy.getAge(18);
        //proxy.getName("CGLBProxy");
    }
}
