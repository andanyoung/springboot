package com.andanyoung.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

/**
 * @author andanyang
 * @since 2023/3/1 17:33
 */
public class CGLBProxy {

    public static <T> T getProxy(T object) {
        Class<?> klass = object.getClass();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(klass);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

                System.out.println("CGLB前置拦截");
                Object result = method.invoke(object, objects);
                System.out.println("CGLB后置拦截");

                return result;
            }
        });
        return (T)enhancer.create();
    }
}
