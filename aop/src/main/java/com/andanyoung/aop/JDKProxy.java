package com.andanyoung.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author andanyang
 * @since 2023/3/1 17:20
 */
public class JDKProxy {

    /**
     * 代理类,只能放回接口
     * @param object
     * @return
     * @param <T>
     */
    public static <T> T getProxy(Object object) {
        Class<?> klass = object.getClass();
        ClassLoader classLoder = klass.getClassLoader();
        Class<?>[] interfaces = klass.getInterfaces();

        return (T) Proxy.newProxyInstance(classLoder, interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("前置拦截");
                Object invoke = method.invoke(object, args);
                System.out.println("后置拦截");
                return invoke;
            }
        });
    }

    /**
     * 代理接口
     * @param interfaceClass
     * @return
     * @param <T>
     */
    public static <T> T getProxy(Class<?>  interfaceClass) {

        ClassLoader classLoder = interfaceClass .getClassLoader();

        /**
         * 进行自定义处理，或者交给其他类处理。比如 JDKProxyInvocationHandler
         */
        return (T) Proxy.newProxyInstance(classLoder, new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("getProxy 前置拦截 " + method.getName());
                //自定义处理逻辑
                Object result;
                if (method.getName().equals("getName")){
                    result= "getName";
                }else{
                    result= 189;
                }
                System.out.println("getProxy 后置拦截 "+ method.getName());
                return result;
            }
        });
    }

    /**
     * 自定义方法拦截器
     */
    class JDKProxyInvocationHandler implements InvocationHandler,JDKProxyInterface{

        @Override
        public int getAge(int age) {
            return age;
        }

        @Override
        public String getName(String name) {
            return name;
        }

        /**
         *
         * @param proxy 当前代理的实例对象
         *
         * @param method  当前执行方法
         *
         * @param args 执行方法参数
         *
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("JDKProxyInvocationHandler 前置拦截");
            Object invoke = method.invoke(this, args);
            //自定义处理逻辑
            System.out.println("JDKProxyInvocationHandler 后置拦截");
            return invoke;
        }
    }
}
