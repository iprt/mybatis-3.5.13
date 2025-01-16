package org.iproute.examples.proxydemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Main
 *
 * @author zhuzhenjie
 * @since 3/21/2023
 */
public class ProxyMain {
    public static void main(String[] args) {
        // testStatic();
        testDynamic();

        testDynamic2();
    }


    static void testStatic() {
        Hello world = new HelloWorld();
        Hello staticProxy = new HelloStaticProxy(world);
        String result = staticProxy.sayHello("zhuzhenjie");
        System.out.println(result);
    }


    static void testDynamic() {
        Hello world = new HelloWorld();
        Hello dynamicProxy = (Hello) Proxy.newProxyInstance(
                Hello.class.getClassLoader(),
                new Class[]{Hello.class},
                new HelloDynamicProxy(world)
        );

        String result = dynamicProxy.sayHello("zhuzhenjie");

        System.out.println(result);
    }


    static void testDynamic2() {
        Hello dynamicProxy = (Hello) Proxy.newProxyInstance(
                Hello.class.getClassLoader(),
                new Class[]{Hello.class, BeforeAfter.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return method.getName();
                    }
                }
        );

        String s = dynamicProxy.sayHello("???");

        System.out.println(s);
    }

}
